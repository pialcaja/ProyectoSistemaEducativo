package com.edusistem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edusistem.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {

	Optional<Rol> findById(int id);
	
	Optional<Rol> findByNombreIgnoreCase(String nombre);
}
