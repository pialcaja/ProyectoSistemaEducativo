package com.edusistem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDetalleResponse {

	private Long id;
	private String nombre;
	private int duracionHoras;
	private String categoria;
	private String docentes;
	private String salones;
}
