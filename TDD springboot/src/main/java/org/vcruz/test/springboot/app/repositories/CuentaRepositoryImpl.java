package org.vcruz.test.springboot.app.repositories;

import org.springframework.stereotype.Repository;
import org.vcruz.test.springboot.app.models.Cuenta;

import java.util.List;

@Repository
public class CuentaRepositoryImpl implements  CuentaRepository {
    @Override
    public List<Cuenta> findAll() {
        return null;
    }

    @Override
    public Cuenta findById(Long id) {
        return null;
    }

    @Override
    public void update(Cuenta cuenta) {

    }
}
