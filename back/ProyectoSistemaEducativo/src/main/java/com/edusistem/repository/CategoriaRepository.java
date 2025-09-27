package com.edusistem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edusistem.model.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	Optional<Categoria> findByNombreCategoriaIgnoreCase(String nombreCategoria);
}
