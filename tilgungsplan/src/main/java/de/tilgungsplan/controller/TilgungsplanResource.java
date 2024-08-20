package de.tilgungsplan.controller;

import de.tilgungsplan.controller.dto.TilgungsplanRequest;
import de.tilgungsplan.controller.dto.TilgungsplanResponse;
import de.tilgungsplan.service.TilgungsplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
        return ResponseEntity.ok(tilgungsplanService.getTilgungsplan(tilgungsplanRequest));
    }
}
