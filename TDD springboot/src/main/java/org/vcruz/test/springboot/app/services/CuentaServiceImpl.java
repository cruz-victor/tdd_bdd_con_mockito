package org.vcruz.test.springboot.app.services;

import org.springframework.stereotype.Service;
import org.vcruz.test.springboot.app.models.Banco;
import org.vcruz.test.springboot.app.models.Cuenta;
import org.vcruz.test.springboot.app.repositories.BancoRepository;
import org.vcruz.test.springboot.app.repositories.CuentaRepository;

import java.math.BigDecimal;

@Service //Para usar en mock springboot
public class CuentaServiceImpl implements CuentaService {

    private CuentaRepository cuentaRepository;
    private BancoRepository bancoRepository;

    //Para inyectar con mockito
    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco=bancoRepository.findById(bancoId);
        return banco.getTotalTransferencia();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta=cuentaRepository.findById(cuentaId);
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long bancoId) {

        Cuenta cuentaOrigen=cuentaRepository.findById(numeroCuentaOrigen);
        cuentaOrigen.debito(monto);
        cuentaRepository.update(cuentaOrigen);

        Cuenta cuentaDestino=cuentaRepository.findById(numeroCuentaDestino);
        cuentaDestino.credito(monto);
        cuentaRepository.update(cuentaDestino);

        Banco banco=bancoRepository.findById(bancoId);
        int totalTransferencia=banco.getTotalTransferencia();
        banco.setTotalTransferencia(++totalTransferencia);
        bancoRepository.update(banco);
    }
}
