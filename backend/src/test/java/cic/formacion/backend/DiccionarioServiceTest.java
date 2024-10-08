package cic.formacion.backend;

import cic.formacion.backend.Models.Alfabeto;
import cic.formacion.backend.Models.Idioma;
import cic.formacion.backend.Models.Palabra;
import cic.formacion.backend.Repository.IdiomaRepository;
import cic.formacion.backend.Repository.PalabraRepository;
import cic.formacion.backend.Service.DiccionarioService;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
public class DiccionarioServiceTest {

    @Mock
    private IdiomaRepository idiomaRepository;

    @Mock
    private PalabraRepository palabraRepository;

    @InjectMocks
    private DiccionarioService diccionarioService;

    
    @Test
    void testGetAllIdiomas() {
        Idioma idioma1 = new Idioma();
        idioma1.setNombre("Español");
        Idioma idioma2 = new Idioma();
        idioma2.setNombre("Inglés");

        when(idiomaRepository.findAll()).thenReturn(Arrays.asList(idioma1, idioma2));

        assertEquals(2, diccionarioService.getAllIdiomas().size());
        verify(idiomaRepository, times(1)).findAll();
    }

    @Test
    void testGetIdiomaById() {
        Idioma idioma = new Idioma();
        idioma.setId(1L);
        idioma.setNombre("Francés");

        when(idiomaRepository.findById(1L)).thenReturn(Optional.of(idioma));

        Optional<Idioma> foundIdioma = diccionarioService.getIdiomaById(1L);
        assertTrue(foundIdioma.isPresent());
        assertEquals("Francés", foundIdioma.get().getNombre());
        verify(idiomaRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateIdioma() {
        Idioma idioma = new Idioma();
        idioma.setNombre("Alemán");

        when(idiomaRepository.save(idioma)).thenReturn(idioma);

        Idioma savedIdioma = diccionarioService.createIdioma(idioma);
        assertEquals("Alemán", savedIdioma.getNombre());
        verify(idiomaRepository, times(1)).save(idioma);
    }

    @Test
    void testDeleteIdioma() {
        Long idiomaId = 1L;

        doNothing().when(idiomaRepository).deleteById(idiomaId);

        diccionarioService.deleteIdioma(idiomaId);

        verify(idiomaRepository, times(1)).deleteById(idiomaId);
    }

    @Test
    void testExistsIdiomaById() {
        when(idiomaRepository.existsById(1L)).thenReturn(true);

        assertTrue(diccionarioService.existsIdiomaById(1L));
        verify(idiomaRepository, times(1)).existsById(1L);
    }

    @Test
    void testGetAllPalabras() {
        Palabra palabra1 = new Palabra();
        palabra1.setPalabra("Hola");
        Palabra palabra2 = new Palabra();
        palabra2.setPalabra("Mundo");

        when(palabraRepository.findAll()).thenReturn(Arrays.asList(palabra1, palabra2));

        assertEquals(2, diccionarioService.getAllPalabras().size());
        verify(palabraRepository, times(1)).findAll();
    }

    @Test
    void testGetPalabraById() {
        Palabra palabra = new Palabra();
        palabra.setId(1L);
        palabra.setPalabra("Salud");

        when(palabraRepository.findById(1L)).thenReturn(Optional.of(palabra));

        Optional<Palabra> foundPalabra = diccionarioService.getPalabraById(1L);
        assertTrue(foundPalabra.isPresent());
        assertEquals("Salud", foundPalabra.get().getPalabra());
        verify(palabraRepository, times(1)).findById(1L);
    }

    @Test
    void testCreatePalabra() {
        Idioma idioma = new Idioma();
        idioma.setId(1L);
        Palabra palabra = new Palabra();
        palabra.setPalabra("Libro");

        when(idiomaRepository.findById(1L)).thenReturn(Optional.of(idioma));
        when(palabraRepository.save(palabra)).thenReturn(palabra);

        Optional<Palabra> savedPalabra = diccionarioService.createPalabra(1L, palabra);
        assertTrue(savedPalabra.isPresent());
        assertEquals("Libro", savedPalabra.get().getPalabra());
        verify(idiomaRepository, times(1)).findById(1L);
        verify(palabraRepository, times(1)).save(palabra);
    }

    @Test
    void testDeletePalabra() {
        Long palabraId = 1L;

        doNothing().when(palabraRepository).deleteById(palabraId);

        diccionarioService.deletePalabra(palabraId);

        verify(palabraRepository, times(1)).deleteById(palabraId);
    }

    @Test
    void testExistsPalabraById() {
        when(palabraRepository.existsById(1L)).thenReturn(true);

        assertTrue(diccionarioService.existsPalabraById(1L));
        verify(palabraRepository, times(1)).existsById(1L);
    }

    

    @Test
    void testUpdateIdioma() {
        Long idiomaId = 1L;

        Idioma existingIdioma = new Idioma();
        existingIdioma.setId(idiomaId);
        existingIdioma.setNombre("Español");
        existingIdioma.setCodigoIso("ESP");
        existingIdioma.setRegion("España");

        Idioma updatedIdiomaDetails = new Idioma();
        updatedIdiomaDetails.setNombre("Español Actualizado");
        updatedIdiomaDetails.setCodigoIso("ESP");
        updatedIdiomaDetails.setRegion("España Actualizada");

        when(idiomaRepository.findById(idiomaId)).thenReturn(Optional.of(existingIdioma));
        when(idiomaRepository.save(existingIdioma)).thenReturn(existingIdioma);

        Idioma updatedIdioma = diccionarioService.updateIdioma(idiomaId, updatedIdiomaDetails);

        assertEquals("Español Actualizado", updatedIdioma.getNombre());
        assertEquals("ESP", updatedIdioma.getCodigoIso());
        assertEquals("España Actualizada", updatedIdioma.getRegion());

        verify(idiomaRepository, times(1)).findById(idiomaId);
        verify(idiomaRepository, times(1)).save(existingIdioma);
    }

    @Test
    void testUpdatePalabra() {
        Idioma idioma = new Idioma();
        idioma.setId(1L);

        Palabra existingPalabra = new Palabra();
        existingPalabra.setId(1L);
        existingPalabra.setPalabra("Hola");
        existingPalabra.setDescripcion("Saludo en español");
        existingPalabra.setEjemploUso("Hola, ¿cómo estás?");
        existingPalabra.setNivelDificultad(1);
        existingPalabra.setFrecuenciaUso(10);
        existingPalabra.setFechaCreacion("10-02-2023");
        existingPalabra.setIdioma(idioma);

        Palabra updatedPalabraDetails = new Palabra();
        updatedPalabraDetails.setPalabra("Hola Actualizado");
        updatedPalabraDetails.setDescripcion("Saludo en español actualizado");
        updatedPalabraDetails.setEjemploUso("Hola, ¿cómo estás? Actualizado");
        updatedPalabraDetails.setNivelDificultad(2);
        updatedPalabraDetails.setFrecuenciaUso(9);
        updatedPalabraDetails.setFechaCreacion("10-02-2023");
        updatedPalabraDetails.setIdioma(idioma);

        when(palabraRepository.findById(1L)).thenReturn(Optional.of(existingPalabra));
        when(idiomaRepository.findById(1L)).thenReturn(Optional.of(idioma));
        when(palabraRepository.save(existingPalabra)).thenReturn(existingPalabra);

        Palabra updatedPalabra = diccionarioService.updatePalabra(1L, updatedPalabraDetails);

        assertNotNull(updatedPalabra);
        assertEquals("Hola Actualizado", updatedPalabra.getPalabra());
        assertEquals("Saludo en español actualizado", updatedPalabra.getDescripcion());
        assertEquals("Hola, ¿cómo estás? Actualizado", updatedPalabra.getEjemploUso());
        assertEquals(2, updatedPalabra.getNivelDificultad());
        assertEquals(9, updatedPalabra.getFrecuenciaUso());
        assertEquals("10-02-2023", updatedPalabra.getFechaCreacion());
        assertEquals(idioma, updatedPalabra.getIdioma());

        verify(palabraRepository, times(1)).findById(1L);
        verify(idiomaRepository, times(1)).findById(1L);
        verify(palabraRepository, times(1)).save(existingPalabra);
    }


    @Test
void testGetRandomPalabra() {
    Palabra palabra1 = new Palabra();
    palabra1.setPalabra("Hola");
    Palabra palabra2 = new Palabra();
    palabra2.setPalabra("Adiós");
    Palabra palabra3 = new Palabra();
    palabra3.setPalabra("Mundo");

    when(palabraRepository.findAll()).thenReturn(Arrays.asList(palabra1, palabra2, palabra3));

    Optional<Palabra> randomPalabra = diccionarioService.getRandomPalabra();

    assertTrue(randomPalabra.isPresent());
    assertTrue(
        randomPalabra.get().getPalabra().equals("Hola") ||
        randomPalabra.get().getPalabra().equals("Adiós") ||
        randomPalabra.get().getPalabra().equals("Mundo")
    );

    verify(palabraRepository, times(1)).findAll();
}

@Test
void testGetRandomPalabraEmptyList() {
    when(palabraRepository.findAll()).thenReturn(Arrays.asList());

    Optional<Palabra> randomPalabra = diccionarioService.getRandomPalabra();

    assertFalse(randomPalabra.isPresent());

    verify(palabraRepository, times(1)).findAll();
}

@Test
void testSearchPalabrasByName() {
    Palabra palabra1 = new Palabra();
    palabra1.setPalabra("Hola");
    Palabra palabra2 = new Palabra();
    palabra2.setPalabra("Holanda");

    when(palabraRepository.findByPalabraContainingIgnoreCase("hola")).thenReturn(Arrays.asList(palabra1, palabra2));

    List<Palabra> foundPalabras = diccionarioService.searchPalabrasByName("hola");

    assertEquals(2, foundPalabras.size());
    assertEquals("Hola", foundPalabras.get(0).getPalabra());
    assertEquals("Holanda", foundPalabras.get(1).getPalabra());

    verify(palabraRepository, times(1)).findByPalabraContainingIgnoreCase("hola");
}



@Test
void testSearchPalabrasByNameNoResults() {
    when(palabraRepository.findByPalabraContainingIgnoreCase("xyz")).thenReturn(Arrays.asList());

    List<Palabra> foundPalabras = diccionarioService.searchPalabrasByName("xyz");

    assertTrue(foundPalabras.isEmpty());

    verify(palabraRepository, times(1)).findByPalabraContainingIgnoreCase("xyz");
}


}

