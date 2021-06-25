package org.vcruz.test.springboot.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcruz.test.springboot.app.models.Cuenta;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    @Query("select c from Cuenta c where c.persona=?1")
    Optional<Cuenta> findByPersona(String persona);//El Optional es opcional
//    List<Cuenta> findAll();
//    Optional<Cuenta> findById(Long id);
//    void update(Cuenta cuenta);
}
