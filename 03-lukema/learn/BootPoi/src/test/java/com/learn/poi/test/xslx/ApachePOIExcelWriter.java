package com.learn.poi.test.xslx;


import java.io.FileOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;


public class ApachePOIExcelWriter {

    private static final Logger LOG = LogManager.getLogger();

    private static final String FILE_NAME = "target/MyFirstExcel.xlsx";

    @Test
    public void writer() {

        try (XSSFWorkbook workbook = new XSSFWorkbook(); FileOutputStream outputStream = new FileOutputStream(FILE_NAME, false)) {

            // @formatter:off
            Object[][] datatypes1 = { 
                    { "Datatype", "Type", "Size(in bytes)" }, 
                    { "int", "Primitive", 2 }, 
                    { "float", "Primitive", 4 }, 
                    { "double", "Primitive", 8 },
                    { "char", "Primitive", 1 }, 
                    { "String", "Non-Primitive", "No fixed size" } 
                };
            // @formatter:on

            int rowNum = 0;
            LOG.info("Creating excel");

            XSSFSheet sheet1 = workbook.createSheet("Datatypes in Java");

            for (Object[] datatype : datatypes1) {
                Row row = sheet1.createRow(rowNum++);
                int colNum = 0;
                for (Object field : datatype) {
                    Cell cell = row.createCell(colNum++);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }
            }

            LOG.info("Done with sheet 1.");

            ////////////////

            // @formatter:off
            Object[][] datatypes2 = { 
                    { "Datatype", "Type", "Size(in bytes)" }, 
                    { "int", "Primitive", 2 }, 
                };
            // @formatter:on

            int rowNum2 = 0;
            LOG.info("Creating excel");

            XSSFSheet sheet2 = workbook.createSheet("Datatypes in C++");

            for (Object[] datatype : datatypes2) {
                Row row = sheet2.createRow(rowNum2++);
                int colNum = 0;
                for (Object field : datatype) {
                    Cell cell = row.createCell(colNum++);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }
            }

            workbook.write(outputStream);

            LOG.info("Done with sheet 2.");

        } catch (Exception e) {
            LOG.error("Writer Exception", e);
        }
    }

}
