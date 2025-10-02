package com.edusistem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocenteDTO {

	private Long id;
	private String nombreCompleto;
	private String categoria;
	private String email;
	private String telefono;
}
