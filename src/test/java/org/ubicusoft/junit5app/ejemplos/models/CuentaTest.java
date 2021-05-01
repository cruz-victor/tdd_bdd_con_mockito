package org.ubicusoft.junit5app.ejemplos.models;

import org.junit.jupiter.api.*;
import org.ubicusoft.junit5app.ejemplos.exceptions.DineroInsfuficienteException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS) //Por defecto el ciclo de vida es por metodo.
class CuentaTest {

    Cuenta cuenta;

    @BeforeEach
    void initMetodoTest(){
        this.cuenta = new Cuenta("Victor", new BigDecimal("1000.12345"));
        System.out.println("Iniciando el metodo.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando el metodo de preuba.");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando el test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando el test");
    }

    @Test
    @DisplayName("Comprabando el nombre de la cuenta.")
    void test_nombre_cuenta() {
        //Cuenta cuenta = new Cuenta("Victor", new BigDecimal("1000.12345"));

        String esperado = "Victor";
        String real = cuenta.getPersona();

        assertEquals(esperado, real);
    }

    @Test
    @DisplayName("Compranbo el saldo de la cuenta")
    void test_saldo_cuenta() {
        //Cuenta cuenta = new Cuenta("Victor", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue(),"El saldo no es el esperado.");
        assertFalse(false,"No se cumple la condicion False.");
    }

    @Test
    void test_referencia_cuenta() {
        Cuenta cuenta = new Cuenta("Victor", new BigDecimal("123.123"));
        Cuenta cuenta2 = new Cuenta("Victor", new BigDecimal("123.123"));

        //Para comparar dos objetos por valor en sus atributos, sobreescribir el metodo equeals.
        assertEquals(cuenta2, cuenta);
    }

    @Test
    @DisplayName("Comprobando la accion debitar en un cuenta")
    void test_debito_cuenta() {
        Cuenta cuenta = new Cuenta("Victor", new BigDecimal("1000.123"));
        cuenta.debito(new BigDecimal("100"));
        assertNotNull(cuenta.getSaldo());
        assertEquals("900.123", cuenta.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Comprobando la accion credito en un cuenta")
    void test_credito_cuenta() {
        Cuenta cuenta = new Cuenta("Victor", new BigDecimal("1000.123"));
        cuenta.credito(new BigDecimal("50"));
        assertEquals("1050.123", cuenta.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Comprobando la generacion de excepcion cuando el dinero es insuficiente")
    void test_dinero_insuficiente_exceptions_cuenta() {
        Cuenta cuenta = new Cuenta("Victor", new BigDecimal("1000.123"));

        Exception exception = assertThrows(DineroInsfuficienteException.class, () -> {
            cuenta.debito(new BigDecimal("1001.123"));
        });
//        Exception exception=assertThrows(NumberFormatException.class, ()->{
//            cuenta.debito(new BigDecimal("1001.123"));
//        });

        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";

        assertEquals(esperado, actual);
    }

    @Test
    @DisplayName("Comprobando la transferencia entre cuentas")
    @Disabled
    void test_transferir_dinero_entre_cuentas() {
        Cuenta cuenta1 = new Cuenta("Victor", new BigDecimal("1000"));
        Cuenta cuenta2 = new Cuenta("Grace Adriana", new BigDecimal("2000"));

        Banco banco = new Banco();
        banco.setNombre("Banco del estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal("200"));
        assertEquals("1800", cuenta2.getSaldo().toPlainString());
        assertEquals("1200", cuenta1.getSaldo().toPlainString());
    }

    @Test
    void test_relacion_banco_con_cuentas() {
        Cuenta cuenta1 = new Cuenta("Victor", new BigDecimal("1000"));
        Cuenta cuenta2 = new Cuenta("Grace Adriana", new BigDecimal("2000"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal("200"));

        assertAll(
                () -> {
                    assertEquals("1800", cuenta2.getSaldo().toPlainString());
                },
                () -> {
                    assertEquals("1200", cuenta1.getSaldo().toPlainString());
                },
                () -> {
                    assertEquals(2, banco.getCuentas().size());
                },
                () -> {
                    assertEquals("Banco del Estado", cuenta1.getBanco().getNombre());//Acoplado. Ne se cumple la ley demeter "Principio de menor conocimiento".
                },
                () -> {
                    assertEquals("Victor", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Victor"))
                            .findFirst()
                            .get().getPersona());
                },
                () -> {
                    assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Victor")));
                }
        );
    }
}