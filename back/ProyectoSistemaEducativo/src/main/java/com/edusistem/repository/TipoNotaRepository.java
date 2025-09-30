package com.edusistem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edusistem.model.TipoNota;

@Repository
public interface TipoNotaRepository extends JpaRepository<TipoNota, Long> {

	Optional<TipoNota> findByNombreTipoNotaIgnoreCase(String nombreTipoNota);
}
