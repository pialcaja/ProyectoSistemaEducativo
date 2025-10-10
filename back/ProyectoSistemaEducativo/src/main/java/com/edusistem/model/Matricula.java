package com.edusistem.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_alumno", nullable = false)
	private Alumno alumno;
	
	@ManyToOne
	@JoinColumn(name = "id_cds", nullable = false)
	private CursoDocenteSalon cds;
	
	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime fechaCreacion = LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	private EstadoMatricula estado = EstadoMatricula.ACTIVA;
}
