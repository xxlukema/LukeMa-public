/* package com.learn.snmp;

import java.io.IOException;
import java.util.Vector;

import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

// SNMP4j v3 trap sender example
public class SnmpUtilSendTrapV3 {

    private Snmp snmp = null;
    private Address targetAddress = null;

    public void initComm() throws IOException {
        targetAddress = GenericAddress.parse("127.0.0.1/162");
        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        snmp.listen();

    }

    public void sendPDU() throws IOException {
        UserTarget target = new UserTarget();
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(1500);
        // snmp version
        target.setVersion(SnmpConstants.version3);

        //target.setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);
        target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
        target.setSecurityName(new OctetString("MD5DES"));

        USM usm = new USM(SecurityProtocols.getInstance(),
                new OctetString(MPv3.createLocalEngineID()), 0);
        usm.setEngineDiscoveryEnabled(true);
        SecurityModels.getInstance().addSecurityModel(usm);

        UsmUser user = new UsmUser(new OctetString("MD5DES"),
                AuthMD5.ID,
                new OctetString("MD5DESUserAuthPassword"),
                PrivDES.ID,
                new OctetString("MD5DESUserPrivPassword"));
        snmp.getUSM().addUser(new OctetString("MD5DES"), user);

        // create PDU
        ScopedPDU pdu = new ScopedPDU();
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.3.0"),
                new OctetString("SnmpTrapv3")));
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.5.0"),
                new OctetString("JavaEE")));
        pdu.setType(PDU.TRAP);

        // send PDU to Agent and recieve Response
        ResponseEvent respEvnt = snmp.send(pdu, target);

        // analyze Response
        if (respEvnt != null && respEvnt.getResponse() != null) {
            Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse()
                    .getVariableBindings();
            for (int i = 0; i < recVBs.size(); i++) {
                VariableBinding recVB = recVBs.elementAt(i);
                System.out.println(recVB.getOid() + " : " + recVB.getVariable());
            }
        }
        snmp.close();
    }

    public static void main(String[] args) {
        try {
            SnmpUtilSendTrapV3 util = new SnmpUtilSendTrapV3();
            util.initComm();
            util.sendPDU();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 */
