package com.edusistem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocenteRegistroRequest {

	private String email;
	private Long usuarioId;
	private Long categoriaId;
}
