package org.vcruz.test.springboot.app.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vcruz.test.springboot.app.models.Banco;
import org.vcruz.test.springboot.app.models.Cuenta;
import org.vcruz.test.springboot.app.repositories.BancoRepository;
import org.vcruz.test.springboot.app.repositories.CuentaRepository;

import java.math.BigDecimal;
import java.util.List;

@Service //Para usar en mock springboot
public class CuentaServiceImpl implements CuentaService {

    private CuentaRepository cuentaRepository;
    private BancoRepository bancoRepository;

    //Para inyectar con mockito
    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id).orElse(new Cuenta());
    }

    @Transactional
    @Override
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    @Transactional(readOnly = true)
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco=bancoRepository.findById(bancoId).orElse(new Banco());
        return banco.getTotalTransferencia();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta=cuentaRepository.findById(cuentaId).orElse(new Cuenta());
        return cuenta.getSaldo();
    }

    @Override
    @Transactional()
    public void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long bancoId) {

        Cuenta cuentaOrigen=cuentaRepository.findById(numeroCuentaOrigen).orElse(new Cuenta());
        cuentaOrigen.debito(monto);
        cuentaRepository.save(cuentaOrigen);

        Cuenta cuentaDestino=cuentaRepository.findById(numeroCuentaDestino).orElse(new Cuenta());
        cuentaDestino.credito(monto);
        cuentaRepository.save(cuentaDestino);

        Banco banco=bancoRepository.findById(bancoId).orElse(new Banco());
        int totalTransferencia=banco.getTotalTransferencia();
        banco.setTotalTransferencia(++totalTransferencia);
        bancoRepository.save(banco);
    }
}
