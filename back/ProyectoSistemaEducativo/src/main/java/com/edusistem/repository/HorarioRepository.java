package com.edusistem.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edusistem.model.Dia;
import com.edusistem.model.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

	List<Horario> findByDiaHorario(Dia dia);
	
	@Query("SELECT h FROM Horario h WHERE h.horaInicioHorario >= :inicio AND h.horaFinHorario <= :fin")
	List<Horario> buscarEntreHoras(@Param("inicio") LocalTime inicio, @Param("fin") LocalTime fin);
}
