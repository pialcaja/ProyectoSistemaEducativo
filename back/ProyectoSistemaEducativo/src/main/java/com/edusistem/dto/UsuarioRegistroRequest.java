package com.edusistem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegistroRequest {

	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String dni;
}
