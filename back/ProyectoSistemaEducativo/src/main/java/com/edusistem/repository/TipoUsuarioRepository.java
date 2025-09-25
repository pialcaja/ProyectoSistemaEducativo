package com.edusistem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edusistem.model.TipoUsuario;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {

	Optional<TipoUsuario> findByNombreTipoUsuarioIgnoreCase(String nombreTipoUsuario);
}
