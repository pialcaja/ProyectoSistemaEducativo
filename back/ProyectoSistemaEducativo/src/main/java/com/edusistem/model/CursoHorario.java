package com.edusistem.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_curso_horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoHorario {

	@EmbeddedId
	private CursoHorarioId id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("cursoDocenteSalon")
	@JoinColumn(name = "codigo_curso_docente_salon", nullable = false)
	private CursoDocenteSalon cursoDocenteSalon;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("horario")
	@JoinColumn(name = "codigo_horario", nullable = false)
	private Horario horario;
}
