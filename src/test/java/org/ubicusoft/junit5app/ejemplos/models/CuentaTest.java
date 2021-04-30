package org.ubicusoft.junit5app.ejemplos.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void test_nombre_cuenta() {
        Cuenta cuenta=new Cuenta("Victor",new BigDecimal("1000.12345"));
        //cuenta.setPersona("Victor");

        String esperado="Victor".toUpperCase();
        String real=cuenta.getPersona();

        assertEquals(esperado, real);
    }

    @Test
    void test_saldo_cuenta(){
        Cuenta cuenta = new Cuenta("Victor", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(false);
    }
}