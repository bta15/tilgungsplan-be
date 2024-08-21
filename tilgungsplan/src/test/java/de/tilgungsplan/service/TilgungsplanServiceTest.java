package de.tilgungsplan.service;

import de.tilgungsplan.controller.dto.TilgungsplanRequest;
import de.tilgungsplan.controller.dto.TilgungsplanResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TilgungsplanServiceTest {

    @InjectMocks
    private TilgungsplanService service;

    @Test
    void calculateTilgungsplan() {
        TilgungsplanRequest request = new TilgungsplanRequest(100000.0, 2.12, 2.0, 10, LocalDate.now());
        TilgungsplanResponse response = service.calculateTilgungsplan(request);
        assertThat(response.tilgungsplanMonate().size()).isEqualTo(121);

        assertThat(response.tilgungsplanMonate().getFirst().datum().toString()).isEqualTo("2024-08-31");
        assertThat(response.tilgungsplanMonate().getFirst().restschuldEuro()).isEqualTo(-100000.0);
        assertThat(response.tilgungsplanMonate().getFirst().zinsenEuro()).isEqualTo(0.0);
        assertThat(response.tilgungsplanMonate().getFirst().tilgungAuszahlungEuro()).isEqualTo(-100000.0);
        assertThat(response.tilgungsplanMonate().getFirst().rateEuro()).isEqualTo(-100000.0);

        assertThat(response.tilgungsplanMonate().getLast().datum().toString()).isEqualTo("2034-08-31");
        assertThat(response.tilgungsplanMonate().getLast().restschuldEuro()).isEqualTo(-77744.14);
        assertThat(response.tilgungsplanMonate().getLast().zinsenEuro()).isEqualTo(137.71);
        assertThat(response.tilgungsplanMonate().getLast().tilgungAuszahlungEuro()).isEqualTo(205.62);
        assertThat(response.tilgungsplanMonate().getLast().rateEuro()).isEqualTo(343.33);

        assertThat(response.tilgungsplanEnde()).isNotNull();
        assertThat(response.tilgungsplanEnde().restschuldEuro()).isEqualTo(-77744.14);
        assertThat(response.tilgungsplanEnde().zinsenEuro()).isEqualTo(18943.74);
        assertThat(response.tilgungsplanEnde().tilgungAuszahlungEuro()).isEqualTo(22255.86);
        assertThat(response.tilgungsplanEnde().rateEuro()).isEqualTo(41199.60);
    }
}