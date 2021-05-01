package org.ubicusoft.junit5app.ejemplos.models;

import org.junit.jupiter.api.Test;
import org.ubicusoft.junit5app.ejemplos.exceptions.DineroInsfuficienteException;

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

    @Test
    void test_referencia_cuenta() {
        Cuenta cuenta = new Cuenta("Victor", new BigDecimal("123.123"));
        Cuenta cuenta2 = new Cuenta("Victor", new BigDecimal("123.123"));

        //Para comparar dos objetos por valor en sus atributos, sobreescribir el metodo equeals.
        assertEquals(cuenta2,cuenta);
    }

    @Test
    void test_debito_cuenta(){
        Cuenta cuenta=new Cuenta("Victor",new BigDecimal("1000.123"));
        cuenta.debito(new BigDecimal("100"));
        assertNotNull(cuenta.getSaldo());
        assertEquals("900.123", cuenta.getSaldo().toPlainString());
    }

    @Test
    void test_credito_cuenta(){
        Cuenta cuenta=new Cuenta("Victor",new BigDecimal("1000.123"));
        cuenta.credito(new BigDecimal("50"));
        assertEquals("1050.123", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteExceptionsCuenta() {
        Cuenta cuenta=new Cuenta("Victor",new BigDecimal("1000.123"));

        Exception exception=assertThrows(DineroInsfuficienteException.class, ()->{
            cuenta.debito(new BigDecimal("1001.123"));
        });
//        Exception exception=assertThrows(NumberFormatException.class, ()->{
//            cuenta.debito(new BigDecimal("1001.123"));
//        });

        String actual=exception.getMessage();
        String esperado="Dinero Insuficiente";

        assertEquals(esperado,actual);
    }
}