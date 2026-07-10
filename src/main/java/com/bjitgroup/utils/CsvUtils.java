package com.bjitgroup.utils;

import com.bjitgroup.exceptions.AutomationException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CSV reader backed by Apache Commons CSV.
 */
public final class CsvUtils {

    private CsvUtils() { /* utility */ }

    /**
     * Read all rows from a header-based CSV classpath resource.
     *
     * @param resourcePath classpath-relative path (e.g. "testdata/users.csv")
     * @return list of row maps keyed by column header
     */
    public static List<Map<String, String>> read(String resourcePath) {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        if (in == null) throw new AutomationException("CSV resource not found: " + resourcePath);

        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build()
                .parse(new InputStreamReader(in, StandardCharsets.UTF_8))) {

            List<Map<String, String>> rows = new ArrayList<>();
            for (CSVRecord record : parser.getRecords()) {
                rows.add(record.toMap());
            }
            return rows;

        } catch (IOException e) {
            throw new AutomationException("Failed to parse CSV: " + resourcePath, e);
        }
    }
}


