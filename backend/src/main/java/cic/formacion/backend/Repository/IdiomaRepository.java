package cic.formacion.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cic.formacion.backend.Models.Idioma;
@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
}
