package com.learn.spring;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.support.JdbcUtils;

import com.learn.util.ClasspathUtils;
import com.learn.util.SpringApplicationContext;


public class C3SelectTest {
    protected static final Logger LOG = Logger.getLogger(C3SelectTest.class);

    private static final String StageDateFormat = "M/dd/yyyy";
    private static final String StageDateFormatISO = "yyyy/MM/dd";
    private static final String StageTimestampFormat = "M/dd/yyyy HH:mm:ss";
    private static final String StageTimestampFormatISO = "yyyy-MM-dd HH:mm:ss";

    private Connection connDev = null;

    private Connection connDev2 = null;

    private Connection connSit = null;
    private Connection connSit2 = null;
    private Connection connSit3 = null;

    private static final int c3SysSupportId = 789;

    // @Test
    // @Ignore
    public void validateFionaStageCollection()
        throws Exception {
        LOG.info("validateFionaStageCollection()");

        final int docTypeFSA = 1;
        final int docTypeRRA = 2;
        final boolean isPriority = false;

        /**
         * TODO: 1. Select from DEV Staging Collection.
         */
        String fileStageCollection = "Sql-Select-Collection.sql";
        String sqlStageCollection = ClasspathUtils.readSqlFromFile(fileStageCollection);
        PreparedStatement stmtStageCollection = connDev.prepareStatement(sqlStageCollection);

        String fileFileNetDocuments = "Sql-Select-Collection-FileNetDocuments.sql";
        String sqlFileNetDocuments = ClasspathUtils.readSqlFromFile(fileFileNetDocuments);
        PreparedStatement stmtFileNetDocuments = connSit.prepareStatement(sqlFileNetDocuments);

        String fileAssignment = "Sql-Select-Collection-Assignment.sql";
        String sqlAssignment = ClasspathUtils.readSqlFromFile(fileAssignment);
        PreparedStatement stmtAssignment = connSit2.prepareStatement(sqlAssignment);

        String fileCollectionInvalidRow = "Sql-Select-Collection-InvalidRow.sql";
        String sqlCollectionInvalidRow = ClasspathUtils.readSqlFromFile(fileCollectionInvalidRow);
        PreparedStatement stmtCollectionInvalidRow = connDev2.prepareStatement(sqlCollectionInvalidRow);

        ResultSet resultStageCollection = null;
        int rowCount = 0;

        try {
            stmtStageCollection.clearParameters();
            resultStageCollection = stmtStageCollection.executeQuery();

            while (resultStageCollection.next()) {
                rowCount++;

                Integer rowID = resultStageCollection.getInt("RowID");
                LOG.info("RowID = " + rowID);

                if (rowID.equals(677)) {
                    LOG.info("################ RowID = " + rowID);

                    // continue;
                }

                Integer scPropertyID = resultStageCollection.getInt("PropertyID");
                if (scPropertyID.equals(0)) {
                    LOG.info("PropertyID = " + scPropertyID);
                    continue;
                }

                Integer scCalendarID = resultStageCollection.getInt("CalendarID");
                String scInvestorNumber = resultStageCollection.getString("InvestorNumber");
                String scLoanNumber = resultStageCollection.getString("LoanNumber");
                String scFS_RequestedItem = resultStageCollection.getString("FS_RequestedItem");
                String scFS_WFB_Received = resultStageCollection.getString("FS_WFB_Received");
                String scRR_RequestedItem = resultStageCollection.getString("RR_RequestedItem");
                String scRR_WFB_Received = resultStageCollection.getString("RR_WFB_Received");
                String scOSAR_WFB_Received = resultStageCollection.getString("OSAR_WFB_Received");
                String scFS_ImageToVendor = resultStageCollection.getString("FS_ImageToVendor");
                String scOSAR_ImageToVendor = resultStageCollection.getString("OSAR_ImageToVendor");
                String scOSARSpreadVendor = resultStageCollection.getString("OSARSpreadVendor");

                LOG.info("propID: " + scPropertyID + " calID: " + scCalendarID + " invNbr: "
                        + scInvestorNumber + " loanNbr: " + scLoanNumber + ". ");

                /**
                 * TODO: Verify data exists from SIT FileNetDocuments and SIT Assignment for data from step 2.
                 */

                /** TODO: Check SIT FileNetDocuments table. */
                ResultSet resultFileNetDocuments = null;

                boolean foundInFileNetDocumentsTable = false;

                try {
                    LOG.info("Verifying FileNetDocuments table...");

                    stmtFileNetDocuments.clearParameters();

                    stmtFileNetDocuments.setInt(1, scPropertyID);
                    stmtFileNetDocuments.setInt(2, scCalendarID);

                    resultFileNetDocuments = stmtFileNetDocuments.executeQuery();

                    while (resultFileNetDocuments.next()) {
                        foundInFileNetDocumentsTable = true;

                        Boolean fFSRequestedItem = resultFileNetDocuments.getBoolean("FSRequestedItem");
                        Date fFSImageDate = resultFileNetDocuments.getDate("FSImageDate");
                        Boolean fRRRequestedItem = resultFileNetDocuments.getBoolean("RRRequestedItem");
                        Date fRRImageDate = resultFileNetDocuments.getDate("RRImageDate");
                        Date fOSARImageDate = resultFileNetDocuments.getDate("OSARImageDate");

                        Assert.assertTrue("FS_RequestedItem/FSRequestedItem",
                                equal(scFS_RequestedItem, fFSRequestedItem));

                        Assert.assertTrue("FS_WFB_Received/FSImageDate",
                                equal(scFS_WFB_Received, fFSImageDate));

                        Assert.assertTrue("RR_RequestedItem/RRRequestedItem",
                                equal(scRR_RequestedItem, fRRRequestedItem));

                        Assert.assertTrue("RR_WFB_Received/RRImageDate",
                                equal(scRR_WFB_Received, fRRImageDate));

                        Assert.assertTrue("OSAR_WFB_Received/OSARImageDate",
                                equal(scOSAR_WFB_Received, fOSARImageDate));
                    }

                    if (!foundInFileNetDocumentsTable) {
                        LOG.error("Not found in FileNetDocuments.");
                    }

                    // Assert.assertTrue("foundInFileNetDocumentsTable", foundInFileNetDocumentsTable);
                } finally {
                    JdbcUtils.closeResultSet(resultFileNetDocuments);
                }

                /** TODO: Check SIT Assignment table. */
                ResultSet resultAssignment = null;
                boolean foundInAssignmentTable = false;

                try {
                    LOG.info("Verifying Assignment table...");

                    stmtAssignment.clearParameters();

                    stmtAssignment.setInt(1, scPropertyID);
                    stmtAssignment.setInt(2, scCalendarID);

                    resultAssignment = stmtAssignment.executeQuery();
                    LOG.info("Verifying Assignment table query executed.");

                    while (resultAssignment.next()) {
                        LOG.info("\t\tVerifying Assignment table inside ResultSet loop.");

                        foundInAssignmentTable = true;

                        String aCompanyName = resultAssignment.getString("CompanyName");
                        Date aAssignDate = resultAssignment.getDate("AssignDate");
                        int aCreatedBy = resultAssignment.getInt("CreatedBy");
                        int aModifiedBy = resultAssignment.getInt("ModifiedBy");
                        int aDocTypeID = resultAssignment.getInt("DocTypeID");
                        boolean aIsPriority = resultAssignment.getBoolean("IsPriority");

                        LOG.info("11111111");
                        Assert.assertEquals("OSARSpreadVendor/CompanyName", scOSARSpreadVendor, aCompanyName);
                        LOG.info("22222222222");
                        Assert.assertTrue("FS_ImageToVendor or OSAR_ImageToVendor/AssignDate",
                                equal(scFS_ImageToVendor, scOSAR_ImageToVendor, aAssignDate));
                        LOG.info("3333333333");
                        Assert.assertEquals("CreatedBy", c3SysSupportId, aCreatedBy);
                        LOG.info("4444444");
                        Assert.assertEquals("ModifiedBy", c3SysSupportId, aModifiedBy);
                        LOG.info("55555555");
                        Assert.assertEquals("DocTypeID", docTypeFSA, aDocTypeID);
                        LOG.info("666666666");
                        Assert.assertEquals("IsPriority", isPriority, aIsPriority);
                        LOG.info("7777777777");
                    }

                    LOG.info("Verifying Assignment table ResultSet iterated.");

                    if (!foundInAssignmentTable) {
                        LOG.error("Not found in Assignment.");
                    }

                    // Assert.assertTrue("foundInAssignmentTable", foundInAssignmentTable);
                } finally {
                    LOG.info("aaaaaaaaaaaa");
                    JdbcUtils.closeResultSet(resultAssignment);
                    LOG.info("bbbbbbbbbbbb");
                }

                if (!(foundInFileNetDocumentsTable && foundInAssignmentTable)) {

                    LOG.info("Verifying InvalidRow Collection table...");

                    ResultSet resultCollectionInvalidRow = null;
                    boolean foundInInvalidRowTable = false;
                    try {
                        foundInInvalidRowTable = true;
                        stmtCollectionInvalidRow.clearParameters();
                        stmtCollectionInvalidRow.setInt(1, rowID);

                        resultCollectionInvalidRow = stmtCollectionInvalidRow.executeQuery();

                        Integer rows = 0;
                        while (resultCollectionInvalidRow.next()) {
                            rows = resultCollectionInvalidRow.getInt("Rows");
                        }
                        Assert.assertTrue("Found in invalid row", rows.equals(1));
                    } finally {
                        JdbcUtils.closeResultSet(resultCollectionInvalidRow);
                    }

                    if (!foundInInvalidRowTable) {
                        LOG.error("Not found in InvalidRow Collection for RowId = " + rowID);
                    }
                    Assert.assertTrue("Found from invalid row", foundInInvalidRowTable);
                }
            }

            JdbcUtils.closeStatement(stmtFileNetDocuments);
        } finally {
            JdbcUtils.closeResultSet(resultStageCollection);
            JdbcUtils.closeStatement(stmtStageCollection);
            JdbcUtils.closeStatement(stmtAssignment);
        }

        LOG.info("Completed. rowCount = " + rowCount);
    }

    private boolean equal(String scFS_ImageToVendor, String scOSAR_ImageToVendor, Date aAssignDate) {

        System.out.println("\tscFS_ImageToVendor = " + scFS_ImageToVendor + ". scOSAR_ImageToVendor = "
                + scOSAR_ImageToVendor + ". aAssignDate = " + aAssignDate);

        if (scFS_ImageToVendor == null && scOSAR_ImageToVendor == null && aAssignDate == null) {
            return true;
        }

        String stringDate = null;
        if (scFS_ImageToVendor != null) {
            stringDate = scFS_ImageToVendor;
        }
        if (scOSAR_ImageToVendor != null) {
            stringDate = scOSAR_ImageToVendor;
        }

        return equal(stringDate, aAssignDate);
    }

    // @Test
    //@Ignore
    public void validateFionaStageRentRollAnalysis()
        throws Exception {

        String fileRRA = "Sql-Select-RRA.sql";
        String sqlRRA = ClasspathUtils.readSqlFromFile(fileRRA);
        PreparedStatement stmtRRA = connDev.prepareStatement(sqlRRA);

        String fileRRAAssignment = "Sql-Select-RRA-Assignment.sql";
        String sqlRRAAssignment = ClasspathUtils.readSqlFromFile(fileRRAAssignment);
        PreparedStatement stmtRRAAssignment = connSit.prepareStatement(sqlRRAAssignment);

        String fileRRARentRollOverview = "Sql-Select-RRA-RentRollOverview.sql";
        String sqlRRARentRollOverview = ClasspathUtils.readSqlFromFile(fileRRARentRollOverview);
        PreparedStatement stmtRRARentRollOverview = connSit.prepareStatement(sqlRRARentRollOverview);

        String fileRRAProperty = "Sql-Select-RRA-Property.sql";
        String sqlRRAProperty = ClasspathUtils.readSqlFromFile(fileRRAProperty);
        PreparedStatement stmtRRAProperty = connSit.prepareStatement(sqlRRAProperty);

        String fileRRAInvalidRow = "Sql-Select-RRA-InvalidRow.sql";
        String sqlRRAInvalidRow = ClasspathUtils.readSqlFromFile(fileRRAInvalidRow);
        PreparedStatement stmtRRAInvalidRow = connDev2.prepareStatement(sqlRRAInvalidRow);

        ResultSet resultRRA = null;

        final int DocTypeID = 2;
        final int RentRollStatusID = 5;

        try {
            stmtRRA.clearParameters();
            resultRRA = stmtRRA.executeQuery();

            int rowRraCounter = 0;
            while (resultRRA.next()) {
                rowRraCounter++;

                Integer rowID = resultRRA.getInt("RowID");
                LOG.info("RowID = " + rowID);

                Integer rraPropertyID = resultRRA.getInt("PropertyID");
                Integer rraCalendarID = resultRRA.getInt("CalendarID");

                LOG.info("RRA: Prop Id = " + rraPropertyID + " Cal Id = " + rraCalendarID);

                if (rraPropertyID.equals(0)) {
                    LOG.info("rraPropertyID is null");
                    continue;
                }
                if (rraCalendarID.equals(0)) {
                    LOG.info("rraCalendarID is null");
                    continue;
                }

                String rraeServicerUploadedDate = resultRRA.getString("eServicerUploadedDate");
                String rraAssignedVendor = resultRRA.getString("AssignedVendor");
                String rraAssignmentDate = resultRRA.getString("AssignmentDate");
                String rraLeaseReviewMixedUseOverride = resultRRA.getString("LeaseReviewMixedUseOverride");

                Assert.assertNotNull("AssignedVendor", rraAssignedVendor);

                ResultSet resultRRAAssignment = null;

                try {
                    stmtRRAAssignment.clearParameters();

                    stmtRRAAssignment.setInt(1, rraPropertyID);
                    stmtRRAAssignment.setInt(2, rraCalendarID);

                    resultRRAAssignment = stmtRRAAssignment.executeQuery();

                    int rowCount = 0;
                    while (resultRRAAssignment.next()) {
                        rowCount++;

                        String aCompanyName = resultRRAAssignment.getString("CompanyName");
                        Integer aDocTypeID = resultRRAAssignment.getInt("DocTypeID");
                        Timestamp aAssignDate = resultRRAAssignment.getTimestamp("AssignDate");

                        Assert.assertTrue("AssignedVendor/CompanyName",
                                rraAssignedVendor.equals(aCompanyName));

                        Assert.assertTrue("DocTypeID", aDocTypeID.equals(DocTypeID));

                        Assert.assertTrue("AssignmentDate/AssignDate", equal(aAssignDate, rraAssignmentDate));

                        // TODO: Assert CreateBy/ModifyBy
                    }

                    LOG.info("111 resultRRAAssignment rowCount = " + rowCount);
                    if (rowCount == 0) {
                        int rowInvalidCount = 0;
                        ResultSet resultInvalidRow = null;

                        try {
                            stmtRRAInvalidRow.clearParameters();

                            stmtRRAInvalidRow.setInt(1, rraPropertyID);
                            stmtRRAInvalidRow.setInt(2, rraCalendarID);

                            resultInvalidRow = stmtRRAInvalidRow.executeQuery();

                            while (resultInvalidRow.next()) {
                                rowInvalidCount++;
                            }

                        } finally {
                            JdbcUtils.closeResultSet(resultInvalidRow);
                        }
                        LOG.info("222 resultInvalidRow rowInvalidCount = " + rowInvalidCount);
                        // TODO
                        Assert.assertTrue("resultInvalidRow rowInvalidCount", (rowInvalidCount > 0));
                    }

                    LOG.info("222 resultRRAAssignment rowCount = " + rowCount);

                    if (rraeServicerUploadedDate != null) {

                        ResultSet resultRRARentRollOverview = null;

                        try {
                            stmtRRARentRollOverview.clearParameters();

                            stmtRRARentRollOverview.setInt(1, rraPropertyID);
                            stmtRRARentRollOverview.setInt(2, rraCalendarID);

                            resultRRARentRollOverview = stmtRRARentRollOverview.executeQuery();

                            int rowRrCount = 0;
                            while (resultRRARentRollOverview.next()) {
                                rowRrCount++;

                                Integer rroRentRollStatusID = resultRRARentRollOverview
                                        .getInt("RentRollStatusID");
                                Assert.assertTrue("RentRollStatusID",
                                        (!rroRentRollStatusID.equals((RentRollStatusID - 1))));
                            }

                            LOG.info("111 resultRRAAssignment rowRrCount = " + rowRrCount);

                            if (rowRrCount == 0) {
                                int rowInvalidCount = 0;
                                ResultSet resultInvalidRow = null;

                                try {
                                    stmtRRAInvalidRow.clearParameters();

                                    stmtRRAInvalidRow.setInt(1, rraPropertyID);
                                    stmtRRAInvalidRow.setInt(2, rraCalendarID);

                                    resultInvalidRow = stmtRRAInvalidRow.executeQuery();

                                    while (resultInvalidRow.next()) {
                                        rowInvalidCount++;
                                    }

                                } finally {
                                    JdbcUtils.closeResultSet(resultInvalidRow);
                                }
                                LOG.info("333 resultInvalidRow rowInvalidCount = " + rowInvalidCount);
                                // TODO
                                Assert.assertTrue("resultInvalidRow rowInvalidCount", (rowInvalidCount > 0));
                            }
                        } finally {
                            JdbcUtils.closeResultSet(resultRRARentRollOverview);
                        }
                    }

                    //
                    if (rraLeaseReviewMixedUseOverride != null) {

                        ResultSet resultRRAProperty = null;

                        try {
                            stmtRRAProperty.clearParameters();

                            stmtRRAProperty.setInt(1, rraPropertyID);

                            resultRRAProperty = stmtRRAProperty.executeQuery();

                            int rowRrCount = 0;
                            while (resultRRAProperty.next()) {
                                rowRrCount++;

                                String rraRRARequiredOverride = resultRRAProperty
                                        .getString("RRARequiredOverride");
                                Assert.assertNotNull("RRARequiredOverride", rraRRARequiredOverride);
                                Assert.assertTrue("RRARequiredOverride", (rraRRARequiredOverride.equals("N")));
                            }

                            LOG.info("111 resultRRAProperty rowRrCount = " + rowRrCount);

                            if (rowRrCount == 0) {
                                int rowInvalidCount = 0;
                                ResultSet resultInvalidRow = null;

                                try {
                                    stmtRRAInvalidRow.clearParameters();

                                    stmtRRAInvalidRow.setInt(1, rraPropertyID);
                                    stmtRRAInvalidRow.setInt(2, rraCalendarID);

                                    resultInvalidRow = stmtRRAInvalidRow.executeQuery();

                                    while (resultInvalidRow.next()) {
                                        rowInvalidCount++;
                                    }

                                } finally {
                                    JdbcUtils.closeResultSet(resultInvalidRow);
                                }
                                LOG.info("333 resultInvalidRow rowInvalidCount = " + rowInvalidCount);
                                // TODO
                                Assert.assertTrue("resultInvalidRow rowInvalidCount", (rowInvalidCount > 0));
                            }
                        } finally {
                            JdbcUtils.closeResultSet(resultRRAProperty);
                        }
                    }

                    // TODO: Property table
                } finally {

                    JdbcUtils.closeResultSet(resultRRAAssignment);
                }
            }

            Assert.assertTrue("resultRRA rowRraCounter", (rowRraCounter > 0));
        } finally {
            JdbcUtils.closeStatement(stmtRRARentRollOverview);
            JdbcUtils.closeResultSet(resultRRA);
        }

        JdbcUtils.closeStatement(stmtRRA);
    }

    @Test
    //@Ignore
    public void validateFionaStagePropertyProfile()
        throws Exception {

        String filePropertyProfile = "Sql-Select-PropertyProfile.sql";
        String sqlPropertyProfile = ClasspathUtils.readSqlFromFile(filePropertyProfile);
        PreparedStatement stmtPropertyProfile = connDev.prepareStatement(sqlPropertyProfile);

        String filePropertyProfileProperty = "Sql-Select-PropertyProfile-Property.sql";
        String sqlPropertyProfileProperty = ClasspathUtils.readSqlFromFile(filePropertyProfileProperty);
        PreparedStatement stmtPropertyProfileProperty = connSit.prepareStatement(sqlPropertyProfileProperty);

        String filePropertyProfileInvalidRow = "Sql-Select-PropertyProfile-InvalidRow.sql";
        String sqlPropertyProfileInvalidRow = ClasspathUtils.readSqlFromFile(filePropertyProfileInvalidRow);
        PreparedStatement stmtPropertyProfileInvalidRow = connDev
                .prepareStatement(sqlPropertyProfileInvalidRow);

        ResultSet resultPropertyProfile = null;

        try {
            stmtPropertyProfile.clearParameters();
            resultPropertyProfile = stmtPropertyProfile.executeQuery();

            int rowCounter = 0;
            while (resultPropertyProfile.next()) {
                rowCounter++;

                Integer rowID = resultPropertyProfile.getInt("RowID");
                LOG.info("RowID = " + rowID);

                String ppInvestorNumber = resultPropertyProfile.getString("InvestorNumber");
                String ppLoanNumber = resultPropertyProfile.getString("LoanNumber");
                String ppPropertyNumber = resultPropertyProfile.getString("PropertyNumber");
                String ppSourceEastWest = resultPropertyProfile.getString("Source_East_West");
                String ppProspectusID = resultPropertyProfile.getString("ProspectusID");
                String ppSpreadInstructions = resultPropertyProfile.getString("SpreadInstructions");

                ResultSet resultPropertyProfileProperty = null;

                try {
                    stmtPropertyProfileProperty.clearParameters();

                    if (ppInvestorNumber.length() == 2) {
                        ppInvestorNumber = "0" + ppInvestorNumber;
                    }

                    if (ppSourceEastWest.equalsIgnoreCase("East")) {
                        ppInvestorNumber = "20" + ppInvestorNumber;
                    } else {
                        ppInvestorNumber = "10" + ppInvestorNumber;
                    }

                    stmtPropertyProfileProperty.setString(1, ppInvestorNumber);
                    stmtPropertyProfileProperty.setString(2, ppLoanNumber);
                    stmtPropertyProfileProperty.setString(3, ppPropertyNumber);
                    stmtPropertyProfileProperty.setString(4, ppProspectusID);

                    resultPropertyProfileProperty = stmtPropertyProfileProperty.executeQuery();

                    int rowCount = 0;
                    while (resultPropertyProfileProperty.next()) {
                        rowCount++;

                        String pSpreadInstructions = resultPropertyProfileProperty
                                .getString("SpreadInstructions");

                        LOG.info("dest: " + pSpreadInstructions);
                        LOG.info("src:  " + ppSpreadInstructions);
                        
                        Assert.assertTrue("SpreadInstructions",
                                pSpreadInstructions.equals(ppSpreadInstructions));
                    }

                    LOG.info("111 resultPropertyProfileProperty rowCount = " + rowCount);

                    if (rowCount == 0) {
                        int rowInvalidCount = 0;
                        ResultSet resultInvalidRow = null;

                        try {
                            stmtPropertyProfileInvalidRow.clearParameters();

                            stmtPropertyProfileInvalidRow.setInt(1, rowID);

                            resultInvalidRow = stmtPropertyProfileInvalidRow.executeQuery();

                            Integer rows = 0;
                            while (resultInvalidRow.next()) {
                                rows = resultInvalidRow.getInt("Rows");
                            }
                            Assert.assertTrue("Found in invalid row", rows.equals(1));

                        } finally {
                            JdbcUtils.closeResultSet(resultInvalidRow);
                        }
                        LOG.info("222 resultInvalidRow rowInvalidCount = " + rowInvalidCount);
                        // TODO
                        Assert.assertTrue("resultInvalidRow rowInvalidCount", (rowInvalidCount > 0));
                    }

                } finally {
                    JdbcUtils.closeResultSet(resultPropertyProfileProperty);
                }
            }

            Assert.assertTrue("resultPropertyProfile rowCounter", (rowCounter > 0));
        } finally {
            JdbcUtils.closeStatement(stmtPropertyProfile);
            JdbcUtils.closeResultSet(resultPropertyProfile);
        }

        JdbcUtils.closeStatement(stmtPropertyProfile);
    }

    //@Test
    //@Ignore
    public void validateFionaStageOsarReview()
        throws Exception {

        String filePropertyProfile = "Sql-Select-PropertyProfile.sql";
        String sqlPropertyProfile = ClasspathUtils.readSqlFromFile(filePropertyProfile);
        PreparedStatement stmtPropertyProfile = connDev.prepareStatement(sqlPropertyProfile);

        String filePropertyProfileProperty = "Sql-Select-PropertyProfile-Property.sql";
        String sqlPropertyProfileProperty = ClasspathUtils.readSqlFromFile(filePropertyProfileProperty);
        PreparedStatement stmtPropertyProfileProperty = connSit.prepareStatement(sqlPropertyProfileProperty);

        ResultSet resultPropertyProfile = null;

        try {
            stmtPropertyProfile.clearParameters();
            resultPropertyProfile = stmtPropertyProfile.executeQuery();

            int rowCounter = 0;
            while (resultPropertyProfile.next()) {
                rowCounter++;

                String ppInvestorNumber = resultPropertyProfile.getString("InvestorNumber");
                String ppLoanNumber = resultPropertyProfile.getString("LoanNumber");
                String ppPropertyNumber = resultPropertyProfile.getString("PropertyNumber");
                String ppProspectusID = resultPropertyProfile.getString("ProspectusID");
                String ppSpreadInstructions = resultPropertyProfile.getString("SpreadInstructions");

                ResultSet resultPropertyProfileProperty = null;

                try {
                    stmtPropertyProfileProperty.clearParameters();

                    stmtPropertyProfileProperty.setString(1, ppInvestorNumber);
                    stmtPropertyProfileProperty.setString(2, ppLoanNumber);
                    stmtPropertyProfileProperty.setString(3, ppPropertyNumber);
                    stmtPropertyProfileProperty.setString(4, ppProspectusID);

                    resultPropertyProfileProperty = stmtPropertyProfileProperty.executeQuery();

                    int rowCount = 0;
                    while (resultPropertyProfileProperty.next()) {
                        rowCount++;

                        String pSpreadInstructions = resultPropertyProfileProperty
                                .getString("SpreadInstructions");

                        Assert.assertTrue("SpreadInstructions",
                                pSpreadInstructions.equals(ppSpreadInstructions));

                        // TODO: Assert CreateBy/ModifyBy
                    }

                    /*LOG.info("111 resultPropertyProfileProperty rowCount = " + rowCount);
                    if (rowCount == 0) {
                        int rowInvalidCount = 0;
                        ResultSet resultInvalidRow = null;

                        try {
                            stmtRRAInvalidRow.clearParameters();

                            stmtRRAInvalidRow.setInt(1, rraPropertyID);
                            stmtRRAInvalidRow.setInt(2, rraCalendarID);

                            resultInvalidRow = stmtRRAInvalidRow.executeQuery();

                            while (resultInvalidRow.next()) {
                                rowInvalidCount++;
                            }

                        } finally {
                            JdbcUtils.closeResultSet(resultInvalidRow);
                        }
                        LOG.info("222 resultInvalidRow rowInvalidCount = " + rowInvalidCount);
                        // TODO
                        Assert.assertTrue("resultInvalidRow rowInvalidCount", (rowInvalidCount > 0));
                    }
                    */
                } finally {
                    JdbcUtils.closeResultSet(resultPropertyProfileProperty);
                }
            }

            Assert.assertTrue("resultPropertyProfile rowCounter", (rowCounter > 0));
        } finally {
            JdbcUtils.closeStatement(stmtPropertyProfile);
            JdbcUtils.closeResultSet(resultPropertyProfile);
        }

        JdbcUtils.closeStatement(stmtPropertyProfile);
    }

    private boolean equal(Timestamp aAssignDate, String rraAssignmentDate) {
        System.out.println("\taAssignDate = " + aAssignDate + ". rraAssignmentDate = " + rraAssignmentDate);

        if (aAssignDate == null && rraAssignmentDate == null) {
            return true;
        }

        if (aAssignDate != null && rraAssignmentDate != null) {
            java.util.Date utilDate = new java.util.Date(aAssignDate.getTime());
            try {
                String strDate = new SimpleDateFormat(StageTimestampFormat).format(utilDate);
                System.out.println("\t strDate = " + strDate);
                System.out.println("\t rraAssignmentDate = " + rraAssignmentDate);
                boolean res = strDate.equals(rraAssignmentDate);
                if (!res) {
                    strDate = new SimpleDateFormat(StageTimestampFormatISO).format(utilDate);
                    System.out.println("\t strDate = " + strDate);
                    System.out.println("\t rraAssignmentDate = " + rraAssignmentDate);
                    res = strDate.equals(rraAssignmentDate);
                }
                return res;
            } catch (Exception e) {
            }
        }

        return false;
    }

    private boolean equal(String stringYN, Boolean booleanTrueFalse) {
        System.out.println("\tstringYN = " + stringYN + ". booleanTrueFalse = " + booleanTrueFalse);

        if (stringYN == null && (booleanTrueFalse == null || booleanTrueFalse.equals(false))) {
            return true;
        }

        if (stringYN != null && stringYN.equals("Y") && booleanTrueFalse != null
                && booleanTrueFalse.equals(true)) {
            return true;
        }

        if (stringYN != null && stringYN.equals("N") && booleanTrueFalse != null
                && booleanTrueFalse.equals(false)) {
            return true;
        }

        return false;
    }

    private boolean equal(String stringDate, Date date) {
        System.out.println("\tstringDate = " + stringDate + ". date = " + date);

        if (stringDate == null && date == null) {
            return true;
        }

        Date fionaDate = null;
        if (stringDate != null) {
            //System.out.println("######## stringDate " + stringDate);
            java.util.Date utilDate = null;
            try {
                utilDate = new SimpleDateFormat(StageDateFormat).parse(stringDate);
            } catch (ParseException e) {
                try {
                    utilDate = new SimpleDateFormat(StageDateFormatISO).parse(stringDate);
                } catch (ParseException ee) {
                    utilDate = new Date(0);
                }
            }
            fionaDate = new Date(utilDate.getTime());
            //System.out.println("######## utilDate " + utilDate);
            //System.out.println("######## fionaDate " + fionaDate);

            return fionaDate.equals(date);
        }

        return false;
    }

    //@Test
    @Ignore
    public void selectDual()
        throws Exception {
        LOG.info("selectDual()");

        String selectFromDual = "select Calendar from Calendar";

        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            connDev.setAutoCommit(false);

            stmt = connDev.prepareStatement(selectFromDual);

            LOG.info("Before executeQuery().");
            resultSet = stmt.executeQuery();
            LOG.info("After executeQuery().");

            while (resultSet.next()) {
                Date date = resultSet.getDate("Calendar");

                LOG.info("Calendar: " + date);
            }

            // connection.commit();
        } catch (Exception e) {
            connDev.rollback();
            LOG.error("Unable to update table.", e);
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(stmt);
        }

        LOG.info("Completed.");
    }

    public Connection getConnSit()
        throws Exception {
        LOG.info("Connecting to sit database...");
        DataSource dataSource = SpringApplicationContext.getBean("dataSource-sit");
        Connection conn = dataSource.getConnection();
        LOG.info("Connected to sit database.");
        connDev.setAutoCommit(true);
        return conn;
    }

    @Before
    public void before()
        throws Exception {
        LOG.info("Connecting to dev database...");
        DataSource dataSource = SpringApplicationContext.getBean("dataSource-dev");
        connDev = dataSource.getConnection();
        LOG.info("Connected to dev database.");
        connDev.setAutoCommit(true);

        LOG.info("Connecting 2 to dev database...");
        dataSource = SpringApplicationContext.getBean("dataSource-dev");
        connDev2 = dataSource.getConnection();
        LOG.info("Connected 2 to dev database.");
        connDev2.setAutoCommit(true);

        LOG.info("Connecting to sit database...");

        dataSource = SpringApplicationContext.getBean("dataSource-sit");
        connSit = dataSource.getConnection();

        dataSource = SpringApplicationContext.getBean("dataSource-sit");
        connSit2 = dataSource.getConnection();

        dataSource = SpringApplicationContext.getBean("dataSource-sit");
        connSit3 = dataSource.getConnection();

        LOG.info("Connected to sit database.");
        connSit.setAutoCommit(true);
    }

    @After
    public void after()
        throws Exception {
        JdbcUtils.closeConnection(connDev);
        LOG.info("Connection dev closed.");

        JdbcUtils.closeConnection(connDev2);
        LOG.info("Connection 2 dev closed.");

        JdbcUtils.closeConnection(connSit);
        JdbcUtils.closeConnection(connSit2);
        JdbcUtils.closeConnection(connSit3);
        LOG.info("Connection sit closed.");
    }

}
