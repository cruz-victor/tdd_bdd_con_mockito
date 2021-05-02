package org.ubicuosoft.services;

import org.ubicuosoft.models.Examen;

public interface ExamenService {
    Examen findExamenPorNombre(String nombre);
    Examen findExamenPorNombreConPreguntas(String nombre);
}
