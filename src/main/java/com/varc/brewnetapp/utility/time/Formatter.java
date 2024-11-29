package com.varc.brewnetapp.utility.time;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Formatter {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    private Formatter() {}

    public static LocalDateTime toLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }
}
