package org.ubicuosoft.services;

import org.ubicuosoft.models.Examen;
import org.ubicuosoft.repositories.ExamenRepository;
import org.ubicuosoft.repositories.PreguntaRepository;

import java.util.List;
import java.util.Optional;

public class ExamenServiceImpl implements ExamenService {

    private ExamenRepository examenRepository;
    private PreguntaRepository preguntaRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository, PreguntaRepository preguntaRepository) {
        this.examenRepository = examenRepository;
        this.preguntaRepository = preguntaRepository;
    }

    @Override
    public Examen findExamenPorNombre(String nombre) {
        Optional<Examen> examenOptional= examenRepository.findAll()
                .stream()
                .filter(e->e.getNombre().contains(nombre))
                .findFirst();

        Examen examen=null;
        if (examenOptional.isPresent())
            examen=examenOptional.get();

        return examen;
    }

    @Override
    public Examen findExamenPorNombreConPreguntas(String nombre) {
        Examen examen = findExamenPorNombre(nombre);
        if (!(examen ==null)){
            List<String> preguntas =preguntaRepository.findPreguntasPorExamenId(examen.getId());
            examen.setPreguntas(preguntas);
        }

        return examen;
    }

    @Override
    public Examen guardar(Examen examen) {
        if (!examen.getPreguntas().isEmpty()){
            preguntaRepository.guardarVarias(examen.getPreguntas());
        }
        return examenRepository.guardar(examen);
    }
}
