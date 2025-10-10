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
@Table(name = "tb_cds_horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CdsHorario {

	@EmbeddedId
	private CdsHorarioId id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("cds")
	@JoinColumn(name = "id_cds", nullable = false)
	private CursoDocenteSalon cds;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("horario")
	@JoinColumn(name = "id_horario", nullable = false)
	private Horario horario;
}
