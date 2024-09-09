package cic.formacion.backend.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;




@Entity
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true,length = 100)
    private String nombre;

    @Column(name = "codigo_iso" ,nullable = false, unique = true, length = 3)
    private String codigoIso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Alfabeto alfabeto;

    @OneToMany(mappedBy = "idioma", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Palabra> palabras;

    @Column(nullable = false, length = 100)
    private String region;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Palabra> getPalabras() { return palabras; }
    public void setPalabras(List<Palabra> palabras) { this.palabras = palabras; }

    public String getCodigoIso() { return codigoIso; }
    public void setCodigoIso(String codigoIso) { this.codigoIso = codigoIso; }

    public Alfabeto getAlfabeto() { return alfabeto; }
    public void setAlfabeto(Alfabeto alfabeto) { this.alfabeto = alfabeto; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
}

