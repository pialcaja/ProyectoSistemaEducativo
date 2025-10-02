package com.edusistem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoDTO {

	private Long id;
	private String nombreCompleto;
	private int edad;
}
