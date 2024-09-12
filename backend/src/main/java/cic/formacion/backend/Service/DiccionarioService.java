package cic.formacion.backend.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cic.formacion.backend.Models.Idioma;
import cic.formacion.backend.Models.Palabra;
import cic.formacion.backend.Repository.IdiomaRepository;
import cic.formacion.backend.Repository.PalabraRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

@Service
public class DiccionarioService {

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private PalabraRepository palabraRepository;

    @Transactional(readOnly = true)
    public List<Idioma> getAllIdiomas() {
        return idiomaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Idioma> getIdiomaById(Long id) {
        return idiomaRepository.findById(id);
    }

    @Transactional
    public Idioma createIdioma(Idioma idioma) {
        return idiomaRepository.save(idioma);
    }

    @Transactional
    public void deleteIdioma(Long id) {
        idiomaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsIdiomaById(Long id) {
        return idiomaRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<Palabra> getAllPalabras() {
        return palabraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Palabra> getPalabraById(Long id) {
        return palabraRepository.findById(id);
    }

    @Transactional
    public Idioma updateIdioma(Long id, Idioma updatedIdioma) {
        return idiomaRepository.findById(id)
                .map(existingIdioma -> {
                    existingIdioma.setNombre(updatedIdioma.getNombre());
                    existingIdioma.setCodigoIso(updatedIdioma.getCodigoIso());
                    existingIdioma.setAlfabeto(updatedIdioma.getAlfabeto());
                    existingIdioma.setRegion(updatedIdioma.getRegion());
                    return idiomaRepository.save(existingIdioma);
                })
                .orElseThrow(() -> new EntityNotFoundException("Idioma not found"));
    }

    @Transactional
    public Optional<Palabra> createPalabra(Long idiomaId, Palabra palabra) {
        return idiomaRepository.findById(idiomaId).map(idioma -> {
            palabra.setIdioma(idioma);
            return palabraRepository.save(palabra);
        });
    }

    @Transactional
    public Palabra updatePalabra(Long id, Palabra updatedPalabra) {
        return palabraRepository.findById(id).map(existingPalabra -> {
            existingPalabra.setPalabra(updatedPalabra.getPalabra());

            existingPalabra.setDescripcion(updatedPalabra.getDescripcion());
            existingPalabra.setEjemploUso(updatedPalabra.getEjemploUso());
            existingPalabra.setNivelDificultad(updatedPalabra.getNivelDificultad());
            existingPalabra.setFrecuenciaUso(updatedPalabra.getFrecuenciaUso());
            existingPalabra.setFechaCreacion(updatedPalabra.getFechaCreacion());

            Optional<Idioma> idioma = idiomaRepository.findById(updatedPalabra.getIdioma().getId());
            if (idioma.isPresent()) {
                existingPalabra.setIdioma(idioma.get());
            } else {
                throw new EntityNotFoundException("Idioma not found");
            }

            return palabraRepository.save(existingPalabra);
        }).orElseThrow(() -> new EntityNotFoundException("Palabra not found"));
    }

    @Transactional(readOnly = true)
    public Optional<Palabra> getRandomPalabra() {
        List<Palabra> palabras = palabraRepository.findAll();
        if (palabras.isEmpty()) {
            return Optional.empty();
        }
        int randomIndex = (int) (Math.random() * palabras.size());
        return Optional.of(palabras.get(randomIndex));
    }

    @Transactional
    public void deletePalabra(Long id) {
        palabraRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsPalabraById(Long id) {
        return palabraRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<Palabra> searchPalabrasByName(String palabra) {
        return palabraRepository.findByPalabraContainingIgnoreCase(palabra);
    }

}
