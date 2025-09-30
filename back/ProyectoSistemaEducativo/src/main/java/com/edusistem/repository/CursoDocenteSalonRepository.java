package com.edusistem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edusistem.model.CursoDocenteSalon;

@Repository
public interface CursoDocenteSalonRepository extends JpaRepository<CursoDocenteSalon, Long> {

	List<CursoDocenteSalon> findByCursoCodigoCurso(Long codigoCurso);
	
	List<CursoDocenteSalon> findByDocenteCodigoUsuario(Long codigoDocente);
	
	@Query("SELECT cds FROM CursoDocenteSalon cds WHERE cds.fechaInicioCurso <= :fecha AND cds.fechaFinCurso >= :fecha")
	List<CursoDocenteSalon> cursosActivosEnFecha(@Param("fecha") LocalDateTime fecha);
}
