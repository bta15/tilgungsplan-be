package de.tilgungsplan.controller.dto;

public record TilgungsplanEnd(
        double restschuldEuro,
        double zinsenEuro,
        double tilgungAuszahlungEuro,
        double rateEuro
) {
}
