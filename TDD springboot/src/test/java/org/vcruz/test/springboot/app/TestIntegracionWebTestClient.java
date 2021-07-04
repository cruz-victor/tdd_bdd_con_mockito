package org.vcruz.test.springboot.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.vcruz.test.springboot.app.models.Cuenta;
import org.vcruz.test.springboot.app.models.TransaccionDto;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

//Para usar webTestClient agregar la dependecia: <artifactId>spring-boot-starter-webflux</artifactId>
//Para correr los test, primero ejecutar la App y luego los test.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Para los testintegracion es importante el orden
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
    @Order(1)
    void testTransferir() throws JsonProcessingException {
        //GIVEN
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

        //WHEN
        //webTestClient.post().uri("http://localhost:8080/api/cuentas/transferir") //Es necesario levantar la aplicacion
        //webTestClient.post().uri("/api/cuentas/transferir")//Cuando esta en el mismo servidor no es necesario indicar el dominio ni levantar la aplicacion
        webTestClient.post().uri("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
        .exchange()//Enviar un solicitud a cambio de un respuesta
        //THEN
        .expectStatus().isOk()
        .expectBody()
        //.expectBody(String.class)//El cuerpo esperado por defecto es byte, se podria cambiar a String
        .consumeWith(respuesta->{
            try {
                JsonNode json = objectMapper.readTree(respuesta.getResponseBody());
                assertEquals("Transferencia realizada con exito", json.path("mensaje").asText());
                assertEquals(1L, json.path("transaccion").path("cuentaOrigenId").asLong());
                assertEquals(LocalDate.now().toString(), json.path("date").asText());
                assertEquals("100", json.path("transaccion").path("monto").asText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        })
        .jsonPath("$.mensaje").isNotEmpty()
        .jsonPath("$.mensaje").value(is("Transferencia realizada con exito"))
        .jsonPath("$.mensaje").value(valor->assertEquals("Transferencia realizada con exito", "Transferencia realizada con exito"))
        .jsonPath("$.mensaje").isEqualTo("Transferencia realizada con exito")
        .jsonPath("$.transaccion.cuentaOrigenId").isEqualTo(dto.getCuentaOrigenId())
        .jsonPath("$.date").isEqualTo(LocalDate.now().toString())
        .json(objectMapper.writeValueAsString(response));
    }

    @Test
    @Order(2)
    void testDetalle() throws JsonProcessingException {
        Cuenta cuentaEsperada=new Cuenta(1L, "Victor", new BigDecimal("900"));

        webTestClient.get().uri("/api/cuentas/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.persona").isEqualTo("Victor")
                .jsonPath("$.saldo").isEqualTo(900)
                .json(objectMapper.writeValueAsString(cuentaEsperada));
    }

    @Test
    @Order(3)
    void testDetalle2() {
        webTestClient.get().uri("/api/cuentas/2")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Cuenta.class) //Mapear el resultado json a clase
                .consumeWith(response->{
                    Cuenta cuenta=response.getResponseBody();
                    assertEquals("Grace", cuenta.getPersona());
                    assertEquals("2100.00", cuenta.getSaldo().toPlainString());
                });
    }

    @Test
    @Order(4)
    void testListar() {
        webTestClient.get().uri("api/cuentas")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].persona").isEqualTo("Victor")
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].saldo").isEqualTo(900)
                .jsonPath("$[1].persona").isEqualTo("Grace")
                .jsonPath("$[1].id").isEqualTo(2)
                .jsonPath("$[1].saldo").isEqualTo(2100)
                .jsonPath("$").isArray()
                .jsonPath("$").value(hasSize(2));
    }

    @Test
    @Order(5)
    void testListar2() {
        webTestClient.get().uri("api/cuentas")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Cuenta.class)
                .consumeWith(response->{
                    List<Cuenta> cuentas=response.getResponseBody();
                    assertNotNull(cuentas);
                    assertEquals(2, cuentas.size());
                    assertEquals(1L,cuentas.get(0).getId());
                    assertEquals("Victor", cuentas.get(0).getPersona());
                    assertEquals(900, cuentas.get(0).getSaldo().intValue());
                    assertEquals(2L, cuentas.get(1).getId());
                    assertEquals("Grace", cuentas.get(1).getPersona());
                    assertEquals("2100.0", cuentas.get(1).getSaldo().toPlainString());
                })
                .hasSize(2)
                .value(hasSize(2));
    }

    @Test
    @Order(6)
    void testGuardar() {
        //GIVEN
        Cuenta cuenta=new Cuenta(null,"Pepe", new BigDecimal("3000"));

        //WHEN
        webTestClient.post().uri("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cuenta)
                .exchange()
        //THEN
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(3L)
                .jsonPath("$.persona").isEqualTo("Pepe")
                .jsonPath("$.persona").value(is("Pepe"))
                .jsonPath("$.saldo").isEqualTo(3000);
    }
}