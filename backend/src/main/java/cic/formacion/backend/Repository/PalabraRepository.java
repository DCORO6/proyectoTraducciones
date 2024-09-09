package cic.formacion.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cic.formacion.backend.Models.Palabra;

public interface PalabraRepository extends JpaRepository<Palabra, Long> {
}
