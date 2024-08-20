package de.tilgungsplan.controller.dto;

import java.time.LocalDate;

public record TilgungsplanRequest(
        double darlehensbetragEuro,
        double sollzinsProzent,
        double anfaenglicheTilgungProzent,
        int zinsbindungJahre,
        LocalDate startdatum
) {
}
