package com.edusistem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edusistem.model.Alumno;
import com.edusistem.model.EstadoUsuario;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

	Optional<Alumno> findByEdadAlumnoGreaterThanEqual(int edadAlumno);
	
	@Query("SELECT a FROM Alumno a WHERE a.usuario.estadoUsuario = :estado")
	List<Alumno> findByEstadoUsuario(@Param("estado") EstadoUsuario estado);
}
