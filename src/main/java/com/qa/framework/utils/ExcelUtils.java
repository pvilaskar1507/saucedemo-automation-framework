package com.qa.framework.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Reads test data from an Excel file using Apache POI.
 * Row 1 is treated as the header and skipped. Every cell is returned as a String.
 */
public final class ExcelUtils {

    private ExcelUtils() {
    }

    /**
     * @param resourcePath classpath location, e.g. "/testdata/TestData.xlsx"
     * @param sheetName    name of the sheet to read
     */
    public static Object[][] getSheetData(String resourcePath, String sheetName) {
        try (InputStream in = ExcelUtils.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IllegalArgumentException("Excel file not found on classpath: " + resourcePath);
            }
            try (Workbook workbook = WorkbookFactory.create(in)) {
                Sheet sheet = workbook.getSheet(sheetName);
                if (sheet == null) {
                    throw new IllegalArgumentException("Sheet not found: " + sheetName);
                }

                DataFormatter formatter = new DataFormatter();
                int rowCount = sheet.getLastRowNum();
                int colCount = sheet.getRow(0).getLastCellNum();
                Object[][] data = new Object[rowCount][colCount];

                for (int i = 1; i <= rowCount; i++) {
                    Row row = sheet.getRow(i);
                    for (int j = 0; j < colCount; j++) {
                        Cell cell = (row == null) ? null : row.getCell(j);
                        data[i - 1][j] = formatter.formatCellValue(cell).trim();
                    }
                }
                return data;
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read Excel file: " + resourcePath, e);
        }
    }
}
