package com.learn.snmp;


import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.ireasoning.protocol.snmp.SnmpConst;
import com.ireasoning.protocol.snmp.SnmpPdu;
import com.ireasoning.protocol.snmp.SnmpSession;
import com.ireasoning.protocol.snmp.SnmpTarget;
import com.ireasoning.util.MibParseException;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class IreasoningSnmpv3Test {

    @Test
    public void testGet()
        throws MibParseException, IOException {
        log.info("Begin Test.");

        // MibUtil.loadMib2();
        // MibUtil.loadMib("/usr/local/share/snmp/mibs/PATS_MMS_NE_PARSED.mib");

        final String oidUptime = ".1.3.6.1.2.1.1.3.0";
        final String oidLocation = ".1.3.6.1.2.1.1.6.0";
        // final String oidTimeFull = ".iso.org.dod.internet.mgmt.mib-2.system.sysUpTime.sysUpTimeInstance";
        // final String oidTimeString = "sysUpTimeInstance";
        // final String oidLocationFull = ".iso.org.dod.internet.mgmt.mib-2.system.sysLocation";

        // var sysUpTimeOid = MibUtil.lookupOID("sysUpTime");
        // log.info("sysUpTimeOid: {}", () -> sysUpTimeOid);

        final int port = 161;
        final String host = "localhost";
        final String securityName = "luke";
        final String passphraseString = "ChangeMe";


        final SnmpTarget target = new SnmpTarget(host, port);
        target.setVersion(SnmpConst.SNMPV3);

        final SnmpSession session = new SnmpSession(target);

        // either
        // session.setV3Params(username, SnmpConst.MD5, passphraseString, passphraseString);
        // or
        session.setV3Params(securityName, SnmpConst.MD5, passphraseString, SnmpConst.DES, passphraseString);

        session.setRetries(3);
        session.setTimeout(10_000);


        SnmpPdu retPduUptime = session.snmpGetRequest(oidUptime);
        log.debug("retPduUptime: {}", () -> retPduUptime);

        SnmpPdu retPduLocation = session.snmpGetRequest(oidLocation);
        log.debug("retPduLocation: {}", () -> retPduLocation);

        var table = session.snmpGetTable("SNMPv2-MIB::sysORTable");
        log.debug("table: {}", () -> table);

        if (table != null) {
            for (int i = 0; i < table.getRowCount(); i++) {
                log.debug(table.getRow(i));
            }
        }

        session.close();

        log.info(() -> "End Test.");
    }


    @Test
    public void testMibUtils()
        throws MibParseException, IOException {
        log.info("Begin Test.");

        // MibUtil.loadMib2();
        // MibUtil.loadMib("/usr/local/share/snmp/mibs/PATS_MMS_NE_PARSED.mib");

        final String oidUptime = ".1.3.6.1.2.1.1.3.0";
        final String oidLocation = ".1.3.6.1.2.1.1.6.0";
        // final String oidTimeFull = ".iso.org.dod.internet.mgmt.mib-2.system.sysUpTime.sysUpTimeInstance";
        // final String oidTimeString = "sysUpTimeInstance";
        // final String oidLocationFull = ".iso.org.dod.internet.mgmt.mib-2.system.sysLocation";

        // var sysUpTimeOid = MibUtil.lookupOID("sysUpTime");
        // log.info("sysUpTimeOid: {}", () -> sysUpTimeOid);

        final int port = 161;
        final String host = "localhost";

        final SnmpTarget target = new SnmpTarget(host, port);
        target.setVersion(SnmpConst.SNMPV3);

        final SnmpSession session = new SnmpSession(target);

        // either
        // session.setV3Params("luke", SnmpConst.MD5, "ChangeMe", "ChangeMe");
        // or
        session.setV3Params("luke", SnmpConst.MD5, "ChangeMe", SnmpConst.DES, "ChangeMe");

        session.setRetries(3);
        session.setTimeout(10_000);

        // SnmpSession.loadMib2();

        /*
        if (isSnmpV3) {
            session.setV3Params(user, authProtocol, authPassword, privPassword);
        }
        */
        // SnmpPdu retPdu = session.snmpGetRequest(oids);//send out get request11.12.
        // log.debug("retPdu: {}", () -> retPdu);

        // var sysUpTimeOid = MibUtil.lookupOID("SNMPv2-MIB::sysUpTime.0");
        // var sysUpTimeOid = MibUtil.lookupOID(oid);

        /*
        var time = MibUtil.translateOID(oidUptime, false);
        log.debug("time: {}", () -> time);

        var fulltime = MibUtil.translateOID(oidTimeFull, false);
        log.debug("full time: {}", () -> fulltime);


        var location = MibUtil.translateOID(oidLocation, false);
        log.debug("location: {}", () -> location);

        var sysUpTimeOid = MibUtil.lookupOID(oidUptime);
        log.info("MibUtil.lookupOID sysUpTimeOid: {}", () -> sysUpTimeOid);

        // var trans = MibUtil.translateValue("SNMPv2-MIB", "sysUpTime.0");
        var trans = MibUtil.translateValue("SNMPv2-MIB::sysUpTime.0", "numbers");
        log.info("trans: {}", () -> trans);
        */

        SnmpPdu retPduUptime = session.snmpGetRequest(oidUptime);
        log.debug("retPduUptime: {}", () -> retPduUptime);

        SnmpPdu retPduLocation = session.snmpGetRequest(oidLocation);
        log.debug("retPduLocation: {}", () -> retPduLocation);

        var table = session.snmpGetTable("SNMPv2-MIB::sysORTable");
        log.debug("table: {}", () -> table);

        if (table != null) {
            for (int i = 0; i < table.getRowCount(); i++) {
                log.debug(table.getRow(i));
            }
        }

        session.close();

        log.info(() -> "End Test.");
    }
}
