package org.vcruz.test.springboot.app.repositories;

import org.springframework.stereotype.Repository;
import org.vcruz.test.springboot.app.models.Banco;

import java.util.List;

@Repository
public class BancoRepositoryImpl implements BancoRepository{
    @Override
    public List<Banco> findAll() {
        return null;
    }

    @Override
    public Banco findById(Long id) {
        return null;
    }

    @Override
    public void update(Banco banco) {

    }
}
