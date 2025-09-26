package com.edusistem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edusistem.model.TipoNota;


public interface TipoNotaRepository extends JpaRepository<TipoNota, Long> {

	Optional<TipoNota> findByNombreTipoNotaIgnoreCase(String nombreTipoNota);
}
