package com.bjitgroup.dataproviders;

import com.bjitgroup.models.UserData;
import org.testng.annotations.DataProvider;

/**
 * TestNG DataProviders for user-related test data.
 */
public class UserDataProvider {

    /**
     * Provides a single randomised user for a test that does not care about
     * specific field values - just needs a valid, unique user.
     */
    @DataProvider(name = "randomUser")
    public static Object[][] randomUser() {
        return new Object[][]{{TestDataManager.randomUser()}};
    }

    /**
     * Provides multiple randomised users for parameterised tests.
     */
    @DataProvider(name = "multipleRandomUsers", parallel = true)
    public static Object[][] multipleRandomUsers() {
        return new Object[][]{
                {TestDataManager.randomUser()},
                {TestDataManager.randomUser()},
                {TestDataManager.randomUser()}
        };
    }

    /**
     * Provides users loaded from a CSV file.
     * Each row is returned as a {@code Map<String,String>}.
     */
    @DataProvider(name = "usersFromCsv")
    public static Object[][] usersFromCsv() {
        return TestDataManager.usersFromCsv("testdata/users.csv")
                .stream()
                .map(row -> new Object[]{TestDataManager.userFromMap(row)})
                .toArray(Object[][]::new);
    }
}
