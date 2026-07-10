package com.bjitgroup.utils;

import com.bjitgroup.exceptions.AutomationException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel (.xlsx) reader backed by Apache POI.
 */
public final class ExcelUtils {

    private ExcelUtils() { /* utility */ }

    /**
     * Read all rows from a named sheet in an Excel classpath resource.
     *
     * @param resourcePath classpath-relative path (e.g. "testdata/users.xlsx")
     * @param sheetName    exact sheet name
     * @return list of row maps keyed by the header row values
     */
    public static List<Map<String, String>> readSheet(String resourcePath, String sheetName) {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        if (in == null) throw new AutomationException("Excel resource not found: " + resourcePath);

        try (Workbook wb = new XSSFWorkbook(in)) {
            Sheet sheet = wb.getSheet(sheetName);
            if (sheet == null) throw new AutomationException("Sheet not found: " + sheetName);

            DataFormatter fmt = new DataFormatter();
            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue().trim());
            }

            List<Map<String, String>> rows = new ArrayList<>();
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                Map<String, String> map = new HashMap<>();
                for (int c = 0; c < headers.size(); c++) {
                    Cell cell = row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    map.put(headers.get(c), fmt.formatCellValue(cell).trim());
                }
                rows.add(map);
            }
            return rows;

        } catch (IOException e) {
            throw new AutomationException("Failed to parse Excel: " + resourcePath, e);
        }
    }
}


