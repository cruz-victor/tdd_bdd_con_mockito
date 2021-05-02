package org.ubicuosoft.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ubicuosoft.models.Examen;
import org.ubicuosoft.repositories.ExamenRepository;
import org.ubicuosoft.repositories.ExamenRepositoryImpl;
import org.ubicuosoft.repositories.PreguntaRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Forma 1. Habilitar las anotaciones de mockito.
@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {
    @Mock
    ExamenRepository repository;
    @Mock
    PreguntaRepository preguntaRepository;
    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {
//--Forma 2. Habilitar las anotaciones de mockito.
//        MockitoAnnotations.openMocks(this);

//--Crear los mocks.
//        repository=mock(ExamenRepository.class);//Objeto simulado de repository
//        preguntaRepository=mock(PreguntaRepository.class);//Objeto simulado de repository
//        service=new ExamenServiceImpl(repository, preguntaRepository);
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
        assertEquals(1L, examen.getId());
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

    @Test
    void buscar_un_examen_por_nombre_con_preguntas_con_verify() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        Examen examen=service.findExamenPorNombreConPreguntas("Historia");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Pregunta 1"));

        //Verificar si se han llamados metodos.
        verify(repository).findAll();//Verificar si se ha llamado el metodo findAll().
        verify(preguntaRepository).findPreguntasPorExamenId(3L); //Verifica si se ha llmado el metodo findPreguntasPorExamenId().
    }

    @Test
    @Disabled
    void buscar_un_examen_por_nombre_con_preguntas_con_verify_v2() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        Examen examen=service.findExamenPorNombreConPreguntas("NOEXISTE");
        assertNull(examen);

        //Verificar si se han llamados metodos.
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong()); //Aqui fallara porque no fue invocado
    }



}