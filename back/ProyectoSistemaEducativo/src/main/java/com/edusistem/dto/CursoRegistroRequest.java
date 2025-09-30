package com.edusistem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoRegistroRequest {

	private String nombre;
	private int duracionHoras;
	private Long categoriaId;
}
