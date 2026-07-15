package com.bjitgroup.dataproviders;

import com.bjitgroup.models.UserData;
import com.bjitgroup.utils.CsvUtils;
import com.bjitgroup.utils.ExcelUtils;
import com.bjitgroup.utils.JsonUtils;
import com.bjitgroup.utils.RandomDataUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Centralised test-data factory.
 * <p>
 * Aggregates all data sources (random, JSON, CSV, Excel) into convenient
 * typed methods so tests never call file-reading utilities directly.
 * </p>
 */
public final class TestDataManager {

    private TestDataManager() { /* utility */ }

    // Random

    /** Creates a fully-randomised {@link UserData} object. */
    public static UserData randomUser() {
        return new UserData(
                RandomDataUtils.username(),
                RandomDataUtils.firstName(),
                RandomDataUtils.lastName(),
                RandomDataUtils.email(),
                "Enabled",
                "ESS"
        );
    }

    // JSON

    /** Loads a single {@link UserData} from a JSON classpath resource. */
    public static UserData userFromJson(String resourcePath) {
        return JsonUtils.read(resourcePath, UserData.class);
    }

    /** Loads a list of users from a JSON array classpath resource. */
    public static List<UserData> usersFromJson(String resourcePath) {
        return Arrays.asList(JsonUtils.read(resourcePath, UserData[].class));
    }

    // CSV

    /** Loads raw rows from a CSV classpath resource. */
    public static List<Map<String, String>> usersFromCsv(String resourcePath) {
        return CsvUtils.read(resourcePath);
    }

    // Excel

    /** Loads raw rows from a named Excel sheet. */
    public static List<Map<String, String>> usersFromExcel(String resourcePath, String sheetName) {
        return ExcelUtils.readSheet(resourcePath, sheetName);
    }
}


