package cic.formacion.backend.Controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cic.formacion.backend.Models.Idioma;
import cic.formacion.backend.Models.Palabra;
import cic.formacion.backend.Service.DiccionarioService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/palabras")
@CrossOrigin(origins = "http://localhost:5173")
public class PalabraController {

    @Autowired
    private DiccionarioService diccionarioService;

    @GetMapping
    public List<Palabra> getAllPalabras() {
        return diccionarioService.getAllPalabras();
    }

    @PostMapping("/{idiomaId}/agregar")
    public ResponseEntity<Palabra> createPalabra(@PathVariable Long idiomaId, @RequestBody Palabra palabra) {
        return diccionarioService.createPalabra(idiomaId, palabra)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Palabra> getPalabraById(@PathVariable Long id) {
        return diccionarioService.getPalabraById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

  

    @PutMapping("/{id}")
public ResponseEntity<Palabra> updatePalabra(@PathVariable Long id, @RequestBody Palabra palabra) {
    if (!diccionarioService.existsPalabraById(id)) {
        return ResponseEntity.notFound().build();
    }

    if (palabra.getIdioma() == null || palabra.getIdioma().getId() == null) {
        return ResponseEntity.badRequest().body(null);
    }

    Palabra updatedPalabra = diccionarioService.updatePalabra(id, palabra);
    return ResponseEntity.ok(updatedPalabra);
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePalabra(@PathVariable Long id) {
        if (diccionarioService.existsPalabraById(id)) {
            diccionarioService.deletePalabra(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/random")
    public ResponseEntity<Palabra> getRandomPalabra() {
        return diccionarioService.getRandomPalabra()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

}
