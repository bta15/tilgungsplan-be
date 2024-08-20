package de.tilgungsplan.controller;

import de.tilgungsplan.controller.dto.TilgungsplanEnd;
import de.tilgungsplan.controller.dto.TilgungsplanEntry;
import de.tilgungsplan.controller.dto.TilgungsplanRequest;
import de.tilgungsplan.controller.dto.TilgungsplanResponse;
import de.tilgungsplan.service.TilgungsplanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TilgungsplanResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TilgungsplanService service;

    @Test
    void getTilgungsplan() throws Exception {
        TilgungsplanRequest request = new TilgungsplanRequest(100000.0, 2.12, 2.0, 10, LocalDate.now());
        TilgungsplanResponse response = new TilgungsplanResponse(
                List.of(new TilgungsplanEntry(LocalDate.now().minusMonths(1), 12345.0, 123.0, 123.0, 123.0)),
                new TilgungsplanEnd(1234.0, 123.0, 123.0, 123.0));
        when(service.getTilgungsplan(request)).thenReturn(response);

        String requestBody = """
                {
                    "darlehensbetragEuro": 100000.0,
                    "sollzinsProzent": 2.12,
                    "anfaenglicheTilgungProzent": 2,
                    "zinsbindungJahre": 10,
                    "startdatum": "2015-11-15"
                }
                """;

        this.mockMvc.perform(post("/tilgungsplan").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isOk()); //TODO: hier könnte man weiter testen

        verify(service).getTilgungsplan(any());
    }
}