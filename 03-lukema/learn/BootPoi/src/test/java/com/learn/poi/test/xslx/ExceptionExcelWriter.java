package com.learn.poi.test.xslx;


import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;


public class ExceptionExcelWriter {

    private static final Logger LOG = LogManager.getLogger();

    private static final String FILE_NAME = "MyException.xlsx";

    @Test
    public void writer()
        throws IOException {

        try (XSSFWorkbook workbook = new XSSFWorkbook(); FileOutputStream outputStream = new FileOutputStream(FILE_NAME, false)) {

            XSSFSheet sheet = workbook.createSheet("Exception");

            Row row0 = sheet.createRow(0);
            Cell cell0_0 = row0.createCell(0);

            String value = "FLA Bulk File Exceptions";
            cell0_0.setCellValue(value);

            CellStyle bold = row0.getSheet().getWorkbook().createCellStyle();
            cell0_0.setCellStyle(bold);

            //Create a new font and alter it.
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            bold.setFont(font);

            Row row1 = sheet.createRow(1);
            Cell cell1_0 = row1.createCell(0);
            Cell cell1_1 = row1.createCell(1);
            cell1_0.setCellValue("Date");
            String date = "09/06/2018";
            cell1_1.setCellValue(date);

            CellStyle right = row1.getSheet().getWorkbook().createCellStyle();
            right.setAlignment(HorizontalAlignment.RIGHT);
            cell1_1.setCellStyle(right);

            Row row2 = sheet.createRow(2);
            Cell cell2_0 = row2.createCell(0);
            Cell cell2_1 = row2.createCell(1);
            cell2_0.setCellValue("Time");
            cell2_1.setCellValue("10:30");

            right = row2.getSheet().getWorkbook().createCellStyle();
            right.setAlignment(HorizontalAlignment.RIGHT);
            cell2_1.setCellStyle(right);

            Row row3 = sheet.createRow(3);
            Cell cell3_0 = row3.createCell(0);
            Cell cell3_1 = row3.createCell(1);
            cell3_0.setCellValue("Exceptions");
            cell3_1.setCellValue(3);

            right = row3.getSheet().getWorkbook().createCellStyle();
            right.setAlignment(HorizontalAlignment.RIGHT);
            cell3_1.setCellStyle(right);

            Row row4 = sheet.createRow(3);
            Cell cell4_0 = row4.createCell(0);
            Cell cell4_1 = row4.createCell(1);
            cell4_0.setCellValue("Amount");
            double amount = -213452278.236;

            /*
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String moneyString = formatter.format(amount);

            cell4_1.setCellValue(moneyString);
            */
            
            XSSFCellStyle style3 = workbook.createCellStyle();
            style3.setAlignment(HorizontalAlignment.RIGHT);  // THIS LINE HERE!
            style3.setDataFormat((short) 7);
            
            cell4_1.setCellValue(amount);
            
            right = row4.getSheet().getWorkbook().createCellStyle();
            right.setAlignment(HorizontalAlignment.RIGHT);
            
            right.setDataFormat((short) 7);
            
            cell4_1.setCellStyle(right);
            
            right.setBorderBottom(BorderStyle.THIN);
            right.setBorderTop(BorderStyle.THIN);
            right.setBorderLeft(BorderStyle.THIN);
            right.setBorderRight(BorderStyle.THIN);
            

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

            int rowNum = 7;
            LOG.info("Creating excel");

            for (Object[] datatype : datatypes1) {
                Row row = sheet.createRow(rowNum++);
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

            LOG.info("Creating excel");

            sheet.setColumnWidth(0, (value.length() + 1) * 256);
            sheet.setColumnWidth(1, (date.length() + 1) * 256);

            workbook.write(outputStream);

            LOG.info("Done with sheet 2.");

        }
    }

}
