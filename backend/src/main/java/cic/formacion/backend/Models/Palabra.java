package cic.formacion.backend.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Palabra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String palabra;

    @Column(length = 1000)
    private String descripcion;

    @Column(name = "ejemplo_uso",length = 1000)
    private String ejemploUso;

    @Column(name = "nivel_dificultad", nullable = false)
    @Min(0)
    @Max(10)
    private int nivelDificultad;

    @Column(name = "frecuencia_uso",nullable = false)
    @Min(0)
    @Max(10)
    private int frecuenciaUso;

    @Column(name = "fecha_creacion",nullable = false)
    private String fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "idioma_id", nullable = false)
    private Idioma idioma;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    

    public String getTexto() { return palabra; }
    public void setTexto(String texto) { this.palabra = texto; }

    public Idioma getIdioma() { return idioma; }
    public void setIdioma(Idioma idioma) { this.idioma = idioma; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String definicion) { this.descripcion = definicion; }

    public String getEjemploUso() { return ejemploUso; }
    public void setEjemploUso(String ejemploUso) { this.ejemploUso = ejemploUso; }  

    public int getNivelDificultad() { return nivelDificultad; }
    public void setNivelDificultad(int nivelDificultad) { this.nivelDificultad = nivelDificultad; }

    public int getFrecuenciaUso() { return frecuenciaUso; }
    public void setFrecuenciaUso(int frecuenciaUso) { this.frecuenciaUso = frecuenciaUso; }
    
    public String getPalabra() {
        return palabra;
    }
    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    
}

