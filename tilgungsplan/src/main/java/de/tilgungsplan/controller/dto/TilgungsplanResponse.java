package de.tilgungsplan.controller.dto;

import java.util.List;

public record TilgungsplanResponse(
        List<TilgungsplanEntry> tilgungsplanMonate,
        TilgungsplanEnd tilgungsplanEnde
) {
}
