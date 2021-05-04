package org.ubicuosoft.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.ubicuosoft.models.Examen;
import org.ubicuosoft.repositories.ExamenRepository;
import org.ubicuosoft.repositories.PreguntaRepository;

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
        //GIVEN
//        ExamenRepository repository=new ExamenRepositoryImpl();
        ExamenRepository repository=mock(ExamenRepository.class);
        ExamenService service=new ExamenServiceImpl(repository, preguntaRepository);

        //Que pasa si quieremos hacer un test para comprobar si el servicio devuelve una lista vacia?
        //R. Sin mockito se tendria que modificar ExamenRepositoryImpl-findall para que devuelva una lista vacia.
        //WHEN
        Examen examen= service.findExamenPorNombre("Matematicas");

        //THEN
        assertNotNull(examen);
        assertEquals(5L, examen.getId());
        assertEquals("Matematicas",examen.getNombre());
    }

    @Test
    void find_examen_por_nombre_con_mockito() {
        //GIVEN
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

        //WHEN
        ExamenService service=new ExamenServiceImpl(repository, preguntaRepository);
        Examen examen= service.findExamenPorNombre("Matematicas");

        //THEN
        assertNotNull(examen);
        assertEquals(1L, examen.getId());
        assertEquals("Matematicas",examen.getNombre());
    }

    @Test
    void find_examen_por_nombre_lista_vacia_con_mockito() {
        //GIVEN
        //--Inicio Mock--
        ExamenRepository repository=mock(ExamenRepository.class);
        List<Examen> examenes= Collections.emptyList();
        when(repository.findAll()).thenReturn(examenes);
        //--Fin Mock---
        //WHEN
        ExamenService service=new ExamenServiceImpl(repository,preguntaRepository);
        Examen examen=service.findExamenPorNombre("Lenguaje");
        //THEN
        assertNull(examen);
    }

    @Test
    void find_examen_por_nombre_lista_vacia_con_mockito_v2() {
        //GIVEN
        List<Examen> examenes= Collections.emptyList();
        when(repository.findAll()).thenReturn(examenes);
        //WHEN
        Examen examen=service.findExamenPorNombre("Lenguaje");
        //THEN
        assertNull(examen);
    }

    @Test
    void buscar_un_examen_por_nombre_con_preguntas() {
        //GIVEN
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(1L)).thenReturn(Datos.PREGUNTAS);
        //WHEN
        Examen examen=service.findExamenPorNombreConPreguntas("Matematicas");
        //THEN
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Pregunta 1"));
        assertTrue(examen.getNombre().equals("Matematicas"));
    }

    @Test
    void buscar_un_examen_por_nombre_con_preguntas_con_any() {
        //GIVEN
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        //WHEN
        Examen examen=service.findExamenPorNombreConPreguntas("Historia");
        //THEN
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Pregunta 1"));
    }

    @Test
    void buscar_un_examen_por_nombre_con_preguntas_con_verify() {
        //GIVEN
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        //WHEN
        Examen examen=service.findExamenPorNombreConPreguntas("Historia");
        //THEN
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Pregunta 1"));
        //Verificar si se han llamados metodos.
        verify(repository).findAll();//Verificar si se ha llamado el metodo findAll().
        verify(preguntaRepository).findPreguntasPorExamenId(3L); //Verifica si se ha llmado el metodo findPreguntasPorExamenId().
    }

    @Test
    @Disabled
    void buscar_un_examen_por_nombre_con_preguntas_con_verify_v2() {
        //GIVEN
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        //WHEN
        Examen examen=service.findExamenPorNombreConPreguntas("NOEXISTE");
        //THEN
        assertNull(examen);
        //Verificar si se han llamados metodos.
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong()); //Aqui fallara porque no fue invocado
    }

    @Test
    void guardar_examen_con_preguntas() {
        //GIVEN
        Examen examenConPreguntas=Datos.EXAMEN;
        examenConPreguntas.setPreguntas(Datos.PREGUNTAS);
        when(repository.guardar(any(Examen.class))).thenReturn(Datos.EXAMEN);

        //WHEN
        Examen examen=service.guardar(examenConPreguntas);

        //THEN
        assertNotNull(examen.getId());
        assertEquals(4L, examen.getId());
        assertEquals("Fisica", examen.getNombre());

        verify(repository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());
    }

    @Test
    void guardar_examen_con_preguntas_autoincrementable() {
        //---GIVEN (Dado. Un entorno de prueba)---
        Examen examenConPreguntas=Datos.EXAMEN;
        examenConPreguntas.setPreguntas(Datos.PREGUNTAS);
        when(repository.guardar(any(Examen.class))).then(new Answer<Examen>(){
            Long secuencia=4L;
            @Override
            public Examen answer(InvocationOnMock invocationOnMock) throws Throwable {
                Examen examen=invocationOnMock.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
        });
        //---WHEN (Cuando. Ejecutamos el metodo a probar)---
        Examen examen=service.guardar(examenConPreguntas);
        //---THEN (Entonces. Validar el resultado)---
        assertNotNull(examen.getId());
        assertEquals(4L, examen.getId());
        assertEquals("Fisica", examen.getNombre());
        verify(repository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());
    }

    @Test
    void manejo_excepcion() {
        //GIVE
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NULL);//Lista de examenes con ID=null
        when(preguntaRepository.findPreguntasPorExamenId(isNull())).thenThrow(IllegalArgumentException.class);//Al pasar un parametro diferente de Long (ID=null) deberia generar una excepcion.
        //WHEN - THEN
        Exception exception=assertThrows(IllegalArgumentException.class,()->{
            service.findExamenPorNombreConPreguntas("Matematicas");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());//Se cumple con la excepcion esperada.
        verify(repository).findAll();//verificar si se ha invocado al parametro findAll()
        verify(preguntaRepository).findPreguntasPorExamenId(isNull());//verificar si se ha invocado al parametro findPregundasPorExamenId con parametro null
    }

    @Test
    void argument_matchers() {
        //GIVEN
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        //WHEN
        service.findExamenPorNombreConPreguntas("Matematicas");
        //THEN
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(argThat(arg->arg!=null && arg>=1));
    }
}