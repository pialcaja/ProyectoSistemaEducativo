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
@Table(name = "tb_curso_docente_salon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDocenteSalon {
	
	@EmbeddedId
	private CursoDocenteSalonId id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("curso")
	@JoinColumn(name = "id_curso", nullable = false)
	private Curso curso;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("docente")
	@JoinColumn(name = "id_docente", nullable = false)
	private Docente docente;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("salon")
	@JoinColumn(name = "id_salon", nullable = false)
	private Salon salon;
}
