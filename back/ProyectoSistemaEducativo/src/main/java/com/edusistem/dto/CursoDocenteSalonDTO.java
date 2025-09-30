package com.edusistem.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDocenteSalonDTO {

	private Long id;
	private Long cursoId;
	private Long docenteId;
	private Long salonId;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
}
