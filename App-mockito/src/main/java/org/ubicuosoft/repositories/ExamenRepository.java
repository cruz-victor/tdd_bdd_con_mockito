package org.ubicuosoft.repositories;

import org.ubicuosoft.models.Examen;

import java.util.List;

public interface ExamenRepository {
    List<Examen> findAll();

    Examen guardar(Examen examen);
}
