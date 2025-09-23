package com.edusistem.model;

import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoHorario;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 9)
	private Dia diaHorario;
	
	@Column(nullable = false)
	private Time horaInicioHorario;
	
	@Column(nullable = false)
	private Time horaFinHorario;
}
