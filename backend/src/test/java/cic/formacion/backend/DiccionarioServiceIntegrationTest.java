package cic.formacion.backend;
import cic.formacion.backend.Models.Alfabeto;
import cic.formacion.backend.Models.Idioma;
import cic.formacion.backend.Models.Palabra;
import cic.formacion.backend.Repository.IdiomaRepository;
import cic.formacion.backend.Repository.PalabraRepository;
import cic.formacion.backend.Service.DiccionarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class DiccionarioServiceIntegrationTest {

    @Autowired
    private DiccionarioService diccionarioService;

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private PalabraRepository palabraRepository;

    @BeforeEach
    void setUp() {
        palabraRepository.deleteAll();
        idiomaRepository.deleteAll();
    }   

    @Test
    void testCreateAndRetrieveIdioma() {
        Idioma idioma = new Idioma();
        idioma.setNombre("Griego");
        idioma.setCodigoIso("GRC");
        idioma.setAlfabeto(Alfabeto.LATINO);
        idioma.setRegion("Grecia");

        Idioma savedIdioma = diccionarioService.createIdioma(idioma);

        assertNotNull(savedIdioma.getId());
        assertEquals("Griego", savedIdioma.getNombre());
        assertEquals(Alfabeto.LATINO, savedIdioma.getAlfabeto());

        List<Idioma> idiomas = diccionarioService.getAllIdiomas();
        assertEquals(1, idiomas.size());
        assertEquals("Griego", idiomas.get(0).getNombre());
        assertEquals(Alfabeto.LATINO, idiomas.get(0).getAlfabeto());
    }

    @Test
    void testCreateAndRetrievePalabra() {
        Idioma idioma = new Idioma();
        idioma.setNombre("Ingles");
        idioma.setCodigoIso("ENG");
        idioma.setAlfabeto(Alfabeto.LATINO);
        idioma.setRegion("Inglaterra");

        Idioma savedIdioma = diccionarioService.createIdioma(idioma);

        Palabra palabra = new Palabra();
        palabra.setPalabra("Hello");
        palabra.setDescripcion("A common greeting");
        palabra.setEjemploUso("Hello, how are you?");
        palabra.setNivelDificultad(1);
        palabra.setFrecuenciaUso(10);
        palabra.setFechaCreacion("2024-09-09");
        palabra.setIdioma(savedIdioma);

        Optional<Palabra> optionalSavedPalabra = diccionarioService.createPalabra(savedIdioma.getId(), palabra);
        
        assertTrue(optionalSavedPalabra.isPresent());
        Palabra savedPalabra = optionalSavedPalabra.get();

        assertNotNull(savedPalabra.getId());
        assertEquals("Hello", savedPalabra.getPalabra());
        assertEquals("A common greeting", savedPalabra.getDescripcion());

        List<Palabra> palabras = diccionarioService.getAllPalabras();
        assertEquals(1, palabras.size());
        assertEquals("Hello", palabras.get(0).getPalabra());
        assertEquals(savedIdioma.getId(), palabras.get(0).getIdioma().getId());
    }
}