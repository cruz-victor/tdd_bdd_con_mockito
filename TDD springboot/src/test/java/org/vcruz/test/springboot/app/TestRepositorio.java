package org.vcruz.test.springboot.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.vcruz.test.springboot.app.models.Cuenta;
import org.vcruz.test.springboot.app.repositories.CuentaRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //Habilita, el contexto de persistencia, h2, repositorios, inyeccion de dependencia de spring
public class TestRepositorio {
    @Autowired
    CuentaRepository cuentaRepository;

    @Test
    void testFindById() {
        //Al usar h2, se borrar, crean las  tablas e insertan datos.
        //Cada vez que se ejecuta el test, crea todo y al finalizar elimina todo.
        //Cada metodo test es independiente uno del otro. Al finalizar realiza un rollback.
        Optional<Cuenta> cuenta= cuentaRepository.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("Victor", cuenta.orElse(new Cuenta()).getPersona());
    }

    @Test
    void testFindByPersona() {
        Optional<Cuenta> cuenta= cuentaRepository.findByPersona("Victor");
        assertTrue(cuenta.isPresent());
        assertEquals("Victor", cuenta.orElse(new Cuenta()).getPersona());
        assertEquals("1000.00", cuenta.orElse(new Cuenta()).getSaldo().toPlainString());
    }

//    @Test
//    void testFindByPersonaThrowException() {
//        Optional<Cuenta> cuenta= cuentaRepository.findByPersona("Victorino");
//        assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
//        assertFalse(cuenta.isPresent());
//    }

    @Test
    void testFindAll() {
        List<Cuenta> cuentas=cuentaRepository.findAll();
        assertFalse(cuentas.isEmpty());
        assertEquals(2,cuentas.size());
    }
}
