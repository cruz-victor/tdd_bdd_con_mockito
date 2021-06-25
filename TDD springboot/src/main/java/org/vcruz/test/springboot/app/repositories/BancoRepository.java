package org.vcruz.test.springboot.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vcruz.test.springboot.app.models.Banco;

import java.util.List;
import java.util.Optional;

public interface BancoRepository extends JpaRepository<Banco, Long> {
//    List<Banco> findAll();
//    Optional<Banco> findById(Long id);
//    void update(Banco banco);
}
