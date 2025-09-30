package com.edusistem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

	private Long id;
	private String dni;
	private String nombreCompleto;
	private String email;
	private String estado;
	private String tipoUsuario;
}
