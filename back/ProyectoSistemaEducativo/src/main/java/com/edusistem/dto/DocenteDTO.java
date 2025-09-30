package com.edusistem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocenteDTO {

	private Long id;
	private String email;
	private String categoria;
	private String nombreCompleto;
}
