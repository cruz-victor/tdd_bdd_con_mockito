package org.ubicusoft.junit5app.ejemplos.models;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.ubicusoft.junit5app.ejemplos.exceptions.DineroInsfuficienteException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

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

    @RepeatedTest(value = 10, name = "Repeticion {currentRepetition} de {totalRepetitions}")
    void test_debito_cuenta_con_repeticion_10_veces(RepetitionInfo info) {
        if (info.getCurrentRepetition()==3){
            System.out.println("Estamos en la repeticion "+info.getCurrentRepetition());
        }

        Cuenta cuenta = new Cuenta("Victor", new BigDecimal("1000.123"));
        cuenta.debito(new BigDecimal("100"));
        assertNotNull(cuenta.getSaldo());
        assertEquals("900.123", cuenta.getSaldo().toPlainString());
    }

    @ParameterizedTest
    @ValueSource(strings={"100","200","300","500","700","2000"})
    void test_debito_cuenta_con_parametro(String monto) {
        cuenta.debito(new BigDecimal(monto));
        assertNotNull(cuenta.getSaldo());
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);
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

    @Nested
    class PruebasSistemaOperativo{
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void test_solo_windows() {

        }

        @Test
        @EnabledOnOs(OS.LINUX)
        void test_solo_linux() {

        }
    }

    @Nested
    class PruebasVersionJdk{
        @Test
        @EnabledOnJre(JRE.JAVA_8)
        void test_solo_jdk8() {
        }

        @Test
        @EnabledOnJre(JRE.JAVA_15)
        void test_solo_jdk15() {
        }
    }

    @Nested
    class PruebasPropiedadesSistema{
        @Test
        void test_imprimir_system_properties() {
            Properties properties=System.getProperties();
            properties.forEach((k,v)-> System.out.println(k+":"+v));
        }

        @Test
        @EnabledIfSystemProperty(named="java.version", matches = "14.0.2")
        void test_comprobar_version_java() {

        }

        @Test
        @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
        void test_deshabilitar_si_la_arquitectura_es_x64() {

        }

        @Test
        @EnabledIfSystemProperty(named = "user.name",matches = "vic")
        void test_habilitar_si_es_user_name_habilitado() {

        }
    }

    @Nested
    class PruebasVariablesEntorno{
        @Test
        void test_imprimir_variables_ambiente() {
            Map<String, String> getenv = System.getenv();
            getenv.forEach((k,v)-> System.out.println(k+"="+v));
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = "C://Program Files//Java//jdk1.8.0_271")
        void test_java_home() {
        }

        @Test
        @EnabledIfEnvironmentVariable(named="NUMBER_OF_PROCESSORS",matches = "4")
        void test_numero_procesadores() {
        }
    }




    @Test
    void test_debito_cuenta_con_assuming() {
        boolean esDev="dev".equals(System.getProperty("ENV"));

        Cuenta cuenta = new Cuenta("Victor", new BigDecimal("1000.123"));
        cuenta.debito(new BigDecimal("100"));

        assumingThat(esDev,()->{
            //assertNotNull(cuenta.getSaldo());
            assertNotNull(null);
        });

        assertEquals("900.123", cuenta.getSaldo().toPlainString());
    }
}

