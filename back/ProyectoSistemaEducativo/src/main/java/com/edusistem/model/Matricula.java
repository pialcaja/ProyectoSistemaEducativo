package com.edusistem.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "tb_matricula")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoMatricula;
	
	@ManyToOne
	@JoinColumn(name = "codigo_alumno", nullable = false)
	private Alumno alumno;
	
	@ManyToOne
	@JoinColumn(name = "codigo_curso_docente_salon", nullable = false)
	private CursoDocenteSalon cursoDocenteSalon;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime fechaMatricula = LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	@Column(length = 15, nullable = false)
	private EstadoMatricula estadoMatricula = EstadoMatricula.ACTIVA;
}
