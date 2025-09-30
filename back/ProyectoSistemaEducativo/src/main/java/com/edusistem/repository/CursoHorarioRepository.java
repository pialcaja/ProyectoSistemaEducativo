package com.edusistem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edusistem.model.CursoHorario;
import com.edusistem.model.CursoHorarioId;

@Repository
public interface CursoHorarioRepository extends JpaRepository<CursoHorario, CursoHorarioId> {

	List<CursoHorario> findByCursoDocenteSalonCodigoCursoDocenteSalon(Long codigoCursoDocenteSalon);
	
	List<CursoHorario> findByHorarioCodigoHorario(Long codigoHorario);
}
