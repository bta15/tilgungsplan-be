package de.tilgungsplan.controller;

import de.tilgungsplan.controller.dto.TilgungsplanRequest;
import de.tilgungsplan.controller.dto.TilgungsplanResponse;
import de.tilgungsplan.service.TilgungsplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@CrossOrigin // TODO: sollte später richtig gelöst werden
@RestController
@RequestMapping("tilgungsplan")
public class TilgungsplanResource {

    final TilgungsplanService tilgungsplanService;

    @Autowired
    public TilgungsplanResource(final TilgungsplanService tilgungsplanService) {
        this.tilgungsplanService = tilgungsplanService;
    }

    @PostMapping
    public ResponseEntity<TilgungsplanResponse> getTilgungsplan(
            @RequestBody final TilgungsplanRequest tilgungsplanRequest
    ) {
        if (tilgungsplanRequest.startdatum() == null) {
            return ResponseEntity.ok(tilgungsplanService.calculateTilgungsplan(
                    new TilgungsplanRequest(
                            tilgungsplanRequest.darlehensbetragEuro(),
                            tilgungsplanRequest.sollzinsProzent(),
                            tilgungsplanRequest.anfaenglicheTilgungProzent(),
                            tilgungsplanRequest.zinsbindungJahre(),
                            LocalDate.now())));
        } else {
            return ResponseEntity.ok(tilgungsplanService.calculateTilgungsplan(tilgungsplanRequest));
        }
    }
}
