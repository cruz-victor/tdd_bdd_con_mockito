package org.ubicuosoft.repositories;

import org.ubicuosoft.Datos;
import org.ubicuosoft.models.Examen;

import java.util.List;

public class ExamenRepositoryImpl implements ExamenRepository{
    @Override
    public List<Examen> findAll() {
        System.out.println("ExmaneRepositoryImpl.findAll");
        return Datos.EXAMENES;
    }

    @Override
    public Examen guardar(Examen examen) {
        System.out.println("ExmaneRepositoryImpl.guardar");
        return new Examen(4L,"Fisica");
    }
}
