package com.edusistem.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioDTO {

	private Long id;
	private String dia;
	private LocalTime horaInicio;
	private LocalTime horaFin;
}
