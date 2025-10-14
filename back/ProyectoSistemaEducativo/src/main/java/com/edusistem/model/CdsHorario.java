package com.edusistem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cds", nullable = false)
	private CursoDocenteSalon cds;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_horario", nullable = false)
	private Horario horario;
}
