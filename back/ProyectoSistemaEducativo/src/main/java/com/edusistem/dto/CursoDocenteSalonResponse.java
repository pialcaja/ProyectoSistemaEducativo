package com.edusistem.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDocenteSalonResponse {

	private Long id;
	private String curso;
	private String docente;
	private String salon;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
}
