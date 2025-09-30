package com.edusistem.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edusistem.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

	Optional<Curso> findByNombreCursoIgnoreCase(String nombre);
	
	Page<Curso> findByCategoriaCodigoCategoria(Long codigoCategoria, Pageable pageable);
	
	Page<Curso> findByDuracionHorasCursoGreaterThanEqual(int duracionHorasCurso, Pageable pageable);
}
