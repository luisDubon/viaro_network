package com.viaro.network.doSoftware.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtils {
    public static LocalDate convertToLocalDate(java.sql.Date dateToConvert) {
        return dateToConvert
                .toLocalDate();
    }

    public static Date convertToDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
