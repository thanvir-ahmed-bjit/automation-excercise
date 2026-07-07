package com.bjitgroup.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Date and time formatting helpers.
 */
public final class DateUtils {

    private DateUtils() { /* utility */ }

    /** Returns current date-time formatted with the given pattern. */
    public static String now(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /** Returns today's date formatted with the given pattern. */
    public static String today(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /** Returns current date-time as {@code yyyy-MM-dd_HH-mm-ss} (safe for file names). */
    public static String timestamp() {
        return now("yyyy-MM-dd_HH-mm-ss");
    }
}


