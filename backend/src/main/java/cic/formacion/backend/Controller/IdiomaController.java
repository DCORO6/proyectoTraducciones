package cic.formacion.backend.Controller;

import java.util.List;

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
import cic.formacion.backend.Service.DiccionarioService;

@RestController
@RequestMapping("/idiomas")
@CrossOrigin(origins = "http://localhost:5173")
public class IdiomaController {

    @Autowired
    private DiccionarioService diccionarioService;

    @GetMapping
    public List<Idioma> getAllIdiomas() {
        return diccionarioService.getAllIdiomas();
    }

    @PostMapping
    public ResponseEntity<Idioma> createIdioma(@RequestBody Idioma idioma) {
        Idioma createdIdioma = diccionarioService.createIdioma(idioma);
        return ResponseEntity.ok(createdIdioma);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idioma> getIdiomaById(@PathVariable Long id) {
        return diccionarioService.getIdiomaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Idioma> updateIdioma(@PathVariable Long id, @RequestBody Idioma idioma) {
        if (!diccionarioService.existsIdiomaById(id)) {
            return ResponseEntity.notFound().build();
        }
        Idioma updatedIdioma = diccionarioService.updateIdioma(id, idioma);
        return ResponseEntity.ok(updatedIdioma);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIdioma(@PathVariable Long id) {
        if (diccionarioService.existsIdiomaById(id)) {
            diccionarioService.deleteIdioma(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

