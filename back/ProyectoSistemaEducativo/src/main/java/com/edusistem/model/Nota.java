package com.edusistem.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
@Table(name = "tb_nota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nota {

	@EmbeddedId
	private NotaId id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("alumno")
	@JoinColumn(name = "codigo_usuario", nullable = false)
	private Alumno alumno;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("curso")
	@JoinColumn(name = "codigo_curso", nullable = false)
	private Curso curso;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("tipoNota")
	@JoinColumn(name = "codigo_tipo_nota", nullable = false)
	private TipoNota tipoNota;
	
	@Column(nullable = false)
	private int valorNota;
	
	@Column(nullable = false)
	private LocalDateTime fechaNota;
}
