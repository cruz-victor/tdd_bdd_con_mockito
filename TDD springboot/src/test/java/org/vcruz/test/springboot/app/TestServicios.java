package org.vcruz.test.springboot.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.vcruz.test.springboot.app.exceptions.DineroInsuficienteException;
import org.vcruz.test.springboot.app.models.Banco;
import org.vcruz.test.springboot.app.models.Cuenta;
import org.vcruz.test.springboot.app.repositories.BancoRepository;
import org.vcruz.test.springboot.app.repositories.CuentaRepository;
import org.vcruz.test.springboot.app.services.CuentaService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest //internamente usa mockito jupiter
class TestServicios {

//Forma mockito
//    CuentaRepository cuentaRepository;
//
//    BancoRepository bancoRepository;
//
//    CuentaService service;

//Forma mockito
//    @Mock
//    CuentaRepository cuentaRepository;
//    @Mock
//    BancoRepository bancoRepository;
//    @InjectMocks //Cuando se utiliza InjectMock, usar la implementacion del servicio ya que contiene el constructor
//    CuentaServiceImpl service;

//Forma mockito de springboot
    @MockBean
    CuentaRepository cuentaRepository;
    @MockBean
    BancoRepository bancoRepository;
    @Autowired
    CuentaService service; //Tambien se puede usar la implementacion

    @BeforeEach
    void setUp() {
//        cuentaRepository= mock(CuentaRepository.class);
//        bancoRepository=mock(BancoRepository.class);
//        service=new CuentaServiceImpl(cuentaRepository, bancoRepository);

        //Reiniciar los datos de las cuentas y banco
//        Datos.CUENTA_001.setSaldo(new BigDecimal(1000));
//        Datos.CUENTA_002.setSaldo(new BigDecimal(1000));
//        Datos.BANCO.setTotalTransferencia(0);
    }

    @Test
    void contextLoads() {
        //GIVEN
        when(cuentaRepository.findById(1L)).thenReturn( Datos.crearCuenta001()); //1
        when(cuentaRepository.findById(2L)).thenReturn(Datos.crearCuenta002()); //1
        when(bancoRepository.findById(1L)).thenReturn(Datos.crearBanco());
        //WHEN
        BigDecimal saldoOrigen=service.revisarSaldo(1L);//2
        BigDecimal saldoDestino=service.revisarSaldo(2L);//2
        //THEN
        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("1000", saldoDestino.toPlainString());

        service.transferir(1L, 2L, new BigDecimal("100"), 1L);
        saldoOrigen=service.revisarSaldo(1L);//3
        saldoDestino=service.revisarSaldo(2L);//3
        assertEquals("900", saldoOrigen.toPlainString());
        assertEquals("1100", saldoDestino.toPlainString());

        int total=service.revisarTotalTransferencias(1L);
        assertEquals(1, total);

        //La idea es vierificar la mayor parte de contextos.
        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(3)).findById(2L);
        verify(cuentaRepository, times(2)).save(any(Cuenta.class));

        verify(bancoRepository,times(2)).findById(1L);
        verify(bancoRepository).save(any(Banco.class));

        verify(cuentaRepository, never()).findAll();
        verify(cuentaRepository, times(6)).findById(anyLong());
    }

    @Test
    void contextLoads2() {
        //GIVEN
        when(cuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());
        when(cuentaRepository.findById(2L)).thenReturn(Datos.crearCuenta002());
        when(bancoRepository.findById(1L)).thenReturn(Datos.crearBanco());
        //WHEN
        BigDecimal saldoOrigen=service.revisarSaldo(1L);
        BigDecimal saldoDestino=service.revisarSaldo(2L);
        //THEN
        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("1000", saldoDestino.toPlainString());

        assertThrows(DineroInsuficienteException.class, ()->{
            service.transferir(1L, 2L, new BigDecimal("1200"), 1L);
        });

        saldoOrigen=service.revisarSaldo(1L);
        saldoDestino=service.revisarSaldo(2L);
        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("1000", saldoDestino.toPlainString());

        int total=service.revisarTotalTransferencias(1L);
        assertEquals(0, total);

        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(2)).findById(2L);
        verify(cuentaRepository, never()).save(any(Cuenta.class));

        verify(bancoRepository,times(1)).findById(1L);
        verify(bancoRepository, never()).save(any(Banco.class));

        verify(cuentaRepository, never()).findAll();
        verify(cuentaRepository, times(5)).findById(anyLong());
    }

    @Test
    void contextLoad3() {
        when(cuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());
        Cuenta cuenta1=service.findById(1L);
        Cuenta cuenta2=service.findById(1L);

        //Afirma que dos cuentas sean iguales
        assertSame(cuenta1, cuenta2);
        assertTrue(cuenta1==cuenta2);
        assertEquals("Victor", cuenta1.getPersona());
        assertEquals("Victor", cuenta1.getPersona());

        verify(cuentaRepository,times(2)).findById(1L);
    }

    @Test
    void testFindAll() {
        //GIVEN
        List<Cuenta> datos= Arrays.asList(Datos.crearCuenta001().get(),Datos.crearCuenta002().get());
        when(cuentaRepository.findAll()).thenReturn(datos);

        //WHEN
        List<Cuenta> cuentas=service.findAll();

        //THEN
        assertFalse(cuentas.isEmpty());
        assertEquals(2,cuentas.size());
        assertTrue(cuentas.contains(Datos.crearCuenta002().get()));//Que la lista de cuentas contenga la cuenta 2.

        verify(cuentaRepository).findAll();
    }

    @Test
    void testSave() {
        //GIVEN
        Cuenta cuentaVic=new Cuenta(null, "Victor", new BigDecimal("3000"));
        when(cuentaRepository.save(any())).then(invocation->{
            Cuenta c=invocation.getArgument(0);//recupera la cuenta del parmetro 0. Save(Cuenta)
            c.setId(3L);
            return c;
        });
        //WHEN
        Cuenta cuenta=service.save(cuentaVic);
        //THEN
        assertEquals("Victor", cuenta.getPersona());
        assertEquals( 3, cuenta.getId());
        assertEquals("3000", cuenta.getSaldo().toPlainString());

        verify(cuentaRepository).save(any());
    }
}
