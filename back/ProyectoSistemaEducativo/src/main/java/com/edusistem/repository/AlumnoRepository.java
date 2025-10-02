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
	
	@Query("SELECT a FROM Alumno a " +
		       "WHERE a.estadoUsuario = :estado " +
		       "AND (:filtro IS NULL OR " +
		       "LOWER(a.nombreUsuario) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
		       "OR LOWER(a.apellidoPaternoUsuario) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
		       "OR LOWER(a.apellidoMaternoUsuario) LIKE LOWER(CONCAT('%', :filtro, '%'))) ")
		Page<Alumno> buscarPorEstadoYNombre(@Param("estado") EstadoUsuario estado,
		                                    @Param("filtro") String filtro,
		                                    Pageable pageable);
	
	boolean existsByCodigoUsuario(Long id);
}
