package de.tilgungsplan.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    public static LocalDate getEndOfMonth(final LocalDate date) {
        return date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));
    }
}
