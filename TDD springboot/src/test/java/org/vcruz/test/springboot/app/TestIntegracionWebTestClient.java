package org.vcruz.test.springboot.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.vcruz.test.springboot.app.models.TransaccionDto;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

//Para usar webTestClient agregar la dependecia: <artifactId>spring-boot-starter-webflux</artifactId>
//Para correr los test, primero ejecutar la App y luego los test.
@SpringBootTest(webEnvironment = RANDOM_PORT) //De forma automaitca levante un servidor real con puerto aleatorio
class TestIntegracionWebTestClient {
    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        objectMapper=new ObjectMapper();
    }

    @Test
    void testTransferir() throws JsonProcessingException {
        TransaccionDto dto=new TransaccionDto();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setBancoId(1L);
        dto.setMonto(new BigDecimal("100"));

        Map<String, Object> response=new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con exito");
        response.put("transaccion", dto);

        //webTestClient.post().uri("http://localhost:8080/api/cuentas/transferir")
        webTestClient.post().uri("/api/cuentas/transferir")//Cuando esta en el mismo servidor no es necesario indicar el dominio.
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
                .exchange()//Enviar un solicitud a cambio de un respuesta
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.mensaje").isNotEmpty()
        .jsonPath("$.mensaje").value(is("Transferencia realizada con exito"))
        .jsonPath("$.mensaje").value(valor->assertEquals("Transferencia realizada con exito", "Transferencia realizada con exito"))
        .jsonPath("$.mensaje").isEqualTo("Transferencia realizada con exito")
        .jsonPath("$.transaccion.cuentaOrigenId").isEqualTo(dto.getCuentaOrigenId())
        .jsonPath("$.date").isEqualTo(LocalDate.now().toString())
        .json(objectMapper.writeValueAsString(response));
    }
}