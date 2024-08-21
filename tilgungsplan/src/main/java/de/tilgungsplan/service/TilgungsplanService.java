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

import static de.tilgungsplan.utils.DateUtils.getEndOfMonth;
import static de.tilgungsplan.utils.NumberUtils.calculateProzentInEuro;
import static de.tilgungsplan.utils.NumberUtils.round;

@Service
public class TilgungsplanService {

    public TilgungsplanResponse calculateTilgungsplan(final TilgungsplanRequest tilgungsplanRequest) {
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
        final BigDecimal darlehensbetragEuro = BigDecimal.valueOf(tilgungsplanRequest.darlehensbetragEuro()).negate();
        return new TilgungsplanEntry(
                startDatum,
                darlehensbetragEuro.doubleValue(),
                0.0,
                darlehensbetragEuro.doubleValue(),
                darlehensbetragEuro.doubleValue());
    }

    private List<TilgungsplanEntry> getTilgungsplanMonate(final TilgungsplanRequest tilgungsplanRequest) {
        final List<TilgungsplanEntry> entries = new ArrayList<>();
        final int monateGesamt = 12 * tilgungsplanRequest.zinsbindungJahre();
        final BigDecimal rate = getRate(tilgungsplanRequest);

        BigDecimal restschuld = BigDecimal.valueOf(tilgungsplanRequest.darlehensbetragEuro());
        LocalDate folgeMonat = getEndOfMonth(tilgungsplanRequest.startdatum());

        for (int i = 0; i < monateGesamt; i++) {
            final BigDecimal zinsenEuro = calculateProzentInEuro(restschuld, tilgungsplanRequest.sollzinsProzent());
            final BigDecimal zinsenEuroGerundet = round(zinsenEuro, RoundingMode.HALF_UP);
            final BigDecimal tilgung = round(rate.subtract(zinsenEuroGerundet), RoundingMode.DOWN);
            restschuld = restschuld.subtract(tilgung);
            folgeMonat = getEndOfMonth(folgeMonat.plusMonths(1));
            entries.add(new TilgungsplanEntry(
                    folgeMonat,
                    round(restschuld.negate(), RoundingMode.HALF_UP).doubleValue(),
                    zinsenEuroGerundet.doubleValue(),
                    tilgung.doubleValue(),
                    round(rate, RoundingMode.HALF_UP).doubleValue()));
        }

        return entries;
    }

    private BigDecimal getRate(final TilgungsplanRequest tilgungsplanRequest) {
        final BigDecimal zinsenEuro = calculateProzentInEuro(BigDecimal.valueOf(tilgungsplanRequest.darlehensbetragEuro()), tilgungsplanRequest.sollzinsProzent());
        final BigDecimal anfaenglicheTilgungEuro = calculateProzentInEuro(BigDecimal.valueOf(tilgungsplanRequest.darlehensbetragEuro()), tilgungsplanRequest.anfaenglicheTilgungProzent());
        return zinsenEuro.add(anfaenglicheTilgungEuro);
    }

    private TilgungsplanEnd getPlanEnde(final List<TilgungsplanEntry> tilgungsplanMonate) {
        return new TilgungsplanEnd(
                round(BigDecimal.valueOf(tilgungsplanMonate.getLast().restschuldEuro()), RoundingMode.HALF_UP).doubleValue(),
                round(BigDecimal.valueOf(tilgungsplanMonate.stream().map(TilgungsplanEntry::zinsenEuro).mapToDouble(Double::doubleValue).sum()), RoundingMode.HALF_UP).doubleValue(),
                round(BigDecimal.valueOf(tilgungsplanMonate.stream().map(TilgungsplanEntry::tilgungAuszahlungEuro).mapToDouble(Double::doubleValue).sum()), RoundingMode.DOWN).doubleValue(),
                round(BigDecimal.valueOf(tilgungsplanMonate.stream().map(TilgungsplanEntry::rateEuro).mapToDouble(Double::doubleValue).sum()), RoundingMode.HALF_UP).doubleValue());
    }
}
