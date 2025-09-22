package com.edusistem.model;

import java.util.Date;

import jakarta.persistence.Column;
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
@Table(name = "tb_curso_docente_salon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDocenteSalon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigoCursoDocenteSalon;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "codigo_curso", nullable = false)
	private Curso curso;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "codigo_docente", nullable = false)
	private Docente docente;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "codigo_salon", nullable = false)
	private Salon salon;
	
	@Column(nullable = false)
	private Date fechaInicioCurso;
	
	@Column(nullable = false)
	private Date fechaFinCurso;
}
