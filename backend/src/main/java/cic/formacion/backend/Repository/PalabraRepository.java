package cic.formacion.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cic.formacion.backend.Models.Palabra;
@Repository
public interface PalabraRepository extends JpaRepository<Palabra, Long> {
}
