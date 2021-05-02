package org.ubicuosoft.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.ubicuosoft.models.Examen;
import org.ubicuosoft.repositories.ExamenRepository;
import org.ubicuosoft.repositories.ExamenRepositoryImpl;
import org.ubicuosoft.repositories.PreguntaRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamenServiceImplTest {
    ExamenRepository repository;
    PreguntaRepository preguntaRepository;
    ExamenService service;

    @BeforeEach
    void setUp() {
        repository=mock(ExamenRepository.class);//Objeto simulado de repository
        preguntaRepository=mock(PreguntaRepository.class);//Objeto simulado de repository
        service=new ExamenServiceImpl(repository, preguntaRepository);
    }

    @Test
    void find_examen_por_nombre() {
        ExamenRepository repository=new ExamenRepositoryImpl();
        ExamenService service=new ExamenServiceImpl(repository, preguntaRepository);

        //Que pasa si quieremos hacer un test para comprobar si el servicio devuelve una lista vacia?
        //R. Sin mockito se tendria que modificar ExamenRepositoryImpl-findall para que devuelva una lista vacia.
        Examen examen= service.findExamenPorNombre("Matematicas");

        assertNotNull(examen);
        assertEquals(5L, examen.getId());
        assertEquals("Matematicas",examen.getNombre());
    }

    @Test
    void find_examen_por_nombre_con_mockito() {
        //--Inicio Mock--
        //Mockeando el repositorio con datos preestablecidos.
        ExamenRepository repository= mock(ExamenRepository.class);
//        List<Examen> examenes= Arrays.asList(
//                new Examen(5L,"Matematicas"),
//                new Examen(6L,"Lenguaje"),
//                new Examen(7L,"Historia"));
        //El metodo Repository-FindAll() retorna una lista de examenes 'List<Examen>'
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        //--Fin Mock--

        ExamenService service=new ExamenServiceImpl(repository, preguntaRepository);
        Examen examen= service.findExamenPorNombre("Matematicas");

        assertNotNull(examen);
        assertEquals(5L, examen.getId());
        assertEquals("Matematicas",examen.getNombre());
    }

    @Test
    void find_examen_por_nombre_lista_vacia_con_mockito() {
        //--Inicio Mock--
        ExamenRepository repository=mock(ExamenRepository.class);
        List<Examen> examenes= Collections.emptyList();
        when(repository.findAll()).thenReturn(examenes);
        //--Fin Mock---
        ExamenService service=new ExamenServiceImpl(repository,preguntaRepository);
        Examen examen=service.findExamenPorNombre("Lenguaje");

        assertNull(examen);
    }

    @Test
    void find_examen_por_nombre_lista_vacia_con_mockito_v2() {
        List<Examen> examenes= Collections.emptyList();
        when(repository.findAll()).thenReturn(examenes);

        Examen examen=service.findExamenPorNombre("Lenguaje");

        assertNull(examen);
    }

    @Test
    void buscar_un_examen_por_nombre_con_preguntas() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(1L)).thenReturn(Datos.PREGUNTAS);

        Examen examen=service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Pregunta 1"));
        assertTrue(examen.getNombre().equals("Matematicas"));
    }

    @Test
    void buscar_un_examen_por_nombre_con_preguntas_con_any() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        Examen examen=service.findExamenPorNombreConPreguntas("Historia");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Pregunta 1"));
    }
}