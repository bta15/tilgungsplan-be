package de.tilgungsplan.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberUtils {

    public static BigDecimal round(final BigDecimal number, final RoundingMode roundingMode) {
        return number.setScale(2, roundingMode);
    }

    public static BigDecimal calculateProzentInEuro(final BigDecimal betrag, final double prozent) {
        final BigDecimal anzahlMonate = new BigDecimal(12);
        return betrag.multiply(BigDecimal.valueOf(prozent / 100)).divide(anzahlMonate, RoundingMode.HALF_UP);
    }
}
