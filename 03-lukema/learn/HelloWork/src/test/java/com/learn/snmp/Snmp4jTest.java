package com.learn.snmp;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.fluent.SnmpBuilder;
import org.snmp4j.fluent.SnmpCompletableFuture;
import org.snmp4j.fluent.TargetBuilder;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

import com.ireasoning.util.MibParseException;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class Snmp4jTest {

    @Test
    public void testWalk()
        throws IOException {
        CommunityTarget<Address> target = new CommunityTarget<>();
        target.setCommunity(new OctetString("read"));
        target.setAddress(GenericAddress.parse("udp:localhost/161")); // supply your own IP and port
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);

        // final String oid = ".1.3.6.1.2.1.2.2";
        final String oid = ".1.3.6.1.2.1.1";

        Map<String, String> result = doWalk(oid, target); // ifTable, mib-2 interfaces

        for (Map.Entry<String, String> entry : result.entrySet()) {
            log.info(entry);
        }
    }

    private Map<String, String> doWalk(String oid, CommunityTarget<Address> target)
        throws IOException {

        final Map<String, String> map = new TreeMap<>();

        TransportMapping<? extends Address> transport = new DefaultUdpTransportMapping();
        Snmp snmp = new Snmp(transport);
        transport.listen();

        TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
        List<TreeEvent> events = treeUtils.getSubtree(target, new OID(oid));

        // log.info(events);

        if (events == null || events.size() == 0) {
            log.error("Error: Unable to read table...");
            return map;
        }

        for (TreeEvent event : events) {

            if (event == null) {
                continue;
            }
            if (event.isError()) {
                log.error("Error: table OID [ {} ] ", event.getErrorMessage());
                continue;
            }

            VariableBinding[] varBindings = event.getVariableBindings();
            if (varBindings == null || varBindings.length == 0) {
                continue;
            }
            for (VariableBinding varBinding : varBindings) {
                if (varBinding == null) {
                    continue;
                }

                map.put("." + varBinding.getOid().toString(), varBinding.getVariable().toString());
            }

        }

        snmp.close();

        return map;
    }

    @Test
    public void testGet()
        throws MibParseException, IOException {
        log.info("Begin Test.");

        final String sysNameOid = ".1.3.6.1.2.1.1.5.0";

        final int port = 161;
        final String host = "localhost";
        final String securityName = "luke";
        final String passphrase = "ChangeMe";

        SnmpBuilder snmpBuilder = new SnmpBuilder();
        Snmp snmp = snmpBuilder.udp().v3().usm().threads(2).build();
        snmp.listen();

        Address targetAddress = GenericAddress.parse(String.format("udp:%s/%s", host, port));

        byte[] targetEngineID = snmp.discoverAuthoritativeEngineID(targetAddress, 1_000);
        if (targetEngineID != null) {
            TargetBuilder<?> targetBuilder = snmpBuilder.target(targetAddress);
            Target<?> userTarget = targetBuilder
                    .user(securityName, targetEngineID)
                    .auth(TargetBuilder.AuthProtocol.md5).authPassphrase(passphrase)
                    .priv(TargetBuilder.PrivProtocol.des).privPassphrase(passphrase)
                    .done()
                    .timeout(5_000).retries(1)
                    .build();

            PDU pdu = targetBuilder.pdu().type(PDU.GET).oids(sysNameOid).contextName("authPriv").build();
            SnmpCompletableFuture snmpRequestFuture = SnmpCompletableFuture.send(snmp, userTarget, pdu);
            try {
                List<VariableBinding> vbs = snmpRequestFuture.get().getAll();

                log.info("Received: " + snmpRequestFuture.getResponseEvent().getResponse());
                log.info("Payload:  " + vbs);
            } catch (ExecutionException | InterruptedException ex) {
                log.error(ex);
            }
        } else {
            log.error("Timeout on engine ID discovery for {} , GETNEXT not sent.", targetAddress);
        }

        snmp.close();

        log.info(() -> "End Test.");

        snmp.close();
    }

}
