package de.tilgungsplan.controller.dto;

import java.time.LocalDate;

public record TilgungsplanEntry(
        LocalDate datum,
        double restschuldEuro,
        double zinsenEuro,
        double tilgungAuszahlungEuro,
        double rateEuro
) {
}
