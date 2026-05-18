package com.learn.poi;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;


public class ExcelFormula {

    private static final Logger LOG = LogManager.getLogger();

    private static final String TEMPLATE_FILE_NAME = "MyFormula-template.xlsx";
    private static final String FILE_NAME = "MyFormula.xlsx";

    @Test
    public void testEditCells() {

        try (FileInputStream inputStream = new FileInputStream(new File(TEMPLATE_FILE_NAME));
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                FileOutputStream outputStream = new FileOutputStream(FILE_NAME, false);) {

            LOG.info("Getting Sheet");

            XSSFSheet sheet = workbook.getSheet("MyFormula");

            LOG.info(sheet.getPhysicalNumberOfRows());

            XSSFRow row = sheet.getRow(1);

            if (row == null) {
                LOG.info("row is null.");
            } else {
                row.createCell(0).setCellValue(1);
                row.createCell(1).setCellValue(2);
            }

            workbook.write(outputStream);

            LOG.info("Done with sheet.");

        } catch (Exception e) {
            LOG.error("Writer Exception", e);
        }
    }

}
