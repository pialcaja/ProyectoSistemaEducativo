package com.edusistem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edusistem.model.Alumno;
import com.edusistem.model.EstadoUsuario;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

	List<Alumno> findByEdadAlumnoGreaterThanEqual(int edadAlumno);
	
	@Query("SELECT a FROM Alumno a WHERE a.estadoUsuario = :estado")
    Page<Alumno> findByEstadoUsuario(@Param("estado") EstadoUsuario estado, Pageable pageable);
	
	boolean existsByCodigoUsuario(Long id);
}
