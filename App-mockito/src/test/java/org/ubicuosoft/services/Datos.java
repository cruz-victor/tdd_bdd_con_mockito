package org.ubicuosoft.services;

import org.ubicuosoft.models.Examen;

import java.util.Arrays;
import java.util.List;

public class Datos {
    public static final List<Examen> EXAMENES = Arrays.asList(
            new Examen(5L,"Matematicas"),
            new Examen(6L,"Lenguaje"),
            new Examen(7L,"Historia"));


    public static final List<String> PREGUNTAS = Arrays.asList(
            "Pregunta 1",
            "Pregunta 2",
            "Pregunta 3",
            "Pregunta 4",
            "Pregunta 5") ;
}

