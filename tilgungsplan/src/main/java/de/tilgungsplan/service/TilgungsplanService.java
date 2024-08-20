package de.tilgungsplan.service;

import de.tilgungsplan.controller.dto.TilgungsplanEnd;
import de.tilgungsplan.controller.dto.TilgungsplanEntry;
import de.tilgungsplan.controller.dto.TilgungsplanRequest;
import de.tilgungsplan.controller.dto.TilgungsplanResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TilgungsplanService {

    public TilgungsplanResponse getTilgungsplan(final TilgungsplanRequest tilgungsplanRequest) {
        final TilgungsplanEntry startZeile = getStartZeile(tilgungsplanRequest);
        final List<TilgungsplanEntry> tilgungsplanMonate = getTilgungsplanMonate(tilgungsplanRequest);
        final TilgungsplanEnd endeZeile = getPlanEnde(tilgungsplanMonate);

        final List<TilgungsplanEntry> plan = new ArrayList<>();
        plan.add(startZeile);
        plan.addAll(tilgungsplanMonate);

        return new TilgungsplanResponse(plan, endeZeile);
    }

    private TilgungsplanEntry getStartZeile(final TilgungsplanRequest tilgungsplanRequest) {
        final LocalDate startDatum = getEndOfMonth(tilgungsplanRequest.startdatum());
        final double darlehensbetragEuro = tilgungsplanRequest.darlehensbetragEuro() * -1;
        return new TilgungsplanEntry(startDatum, darlehensbetragEuro, 0.0, darlehensbetragEuro, darlehensbetragEuro);
    }

    private List<TilgungsplanEntry> getTilgungsplanMonate(final TilgungsplanRequest tilgungsplanRequest) {
        final int monateGesamt = 12 * tilgungsplanRequest.zinsbindungJahre();
        final double rate = getRate(tilgungsplanRequest);
        final List<TilgungsplanEntry> entries = new ArrayList<>();

        double restschuld = tilgungsplanRequest.darlehensbetragEuro();
        LocalDate folgeMonat = getEndOfMonth(tilgungsplanRequest.startdatum());

        for (int i = 0; i < monateGesamt; i++) {
            double zinsenEuro = getZinsenInEuro(restschuld, tilgungsplanRequest.sollzinsProzent());
            double tilgung = rate - zinsenEuro;
            restschuld = restschuld - tilgung;
            folgeMonat = getEndOfMonth(folgeMonat.plusMonths(1));
            entries.add(new TilgungsplanEntry(
                    folgeMonat,
                    roundNumber(restschuld * -1),
                    roundNumber(zinsenEuro),
                    roundNumber(tilgung),
                    roundNumber(rate)));
        }

        return entries;
    }

    private TilgungsplanEnd getPlanEnde(final List<TilgungsplanEntry> tilgungsplanMonate) {
        return new TilgungsplanEnd(
                roundNumber(tilgungsplanMonate.getLast().restschuldEuro()),
                roundNumber(tilgungsplanMonate.stream().map(TilgungsplanEntry::zinsenEuro).mapToDouble(Double::doubleValue).sum()),
                roundNumber(tilgungsplanMonate.stream().map(TilgungsplanEntry::tilgungAuszahlungEuro).mapToDouble(Double::doubleValue).sum()),
                roundNumber(tilgungsplanMonate.stream().map(TilgungsplanEntry::rateEuro).mapToDouble(Double::doubleValue).sum()));
    }

    private double getZinsenInEuro(final Double restschuldEuro, final Double zinsenProzent) {
        return (restschuldEuro * zinsenProzent / 100) / 12;
    }

    private double getRate(final TilgungsplanRequest tilgungsplanRequest) {
        final double zinsenEuro = getZinsenInEuro(tilgungsplanRequest.darlehensbetragEuro(), tilgungsplanRequest.sollzinsProzent());
        final double anfaenglicheTilgungEuro = getZinsenInEuro(tilgungsplanRequest.darlehensbetragEuro(), tilgungsplanRequest.anfaenglicheTilgungProzent());
        return zinsenEuro + anfaenglicheTilgungEuro;
    }

    private LocalDate getEndOfMonth(final LocalDate date) {
        return date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));
    }

    private double roundNumber(final double number) {
        return new BigDecimal(number).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }


}
