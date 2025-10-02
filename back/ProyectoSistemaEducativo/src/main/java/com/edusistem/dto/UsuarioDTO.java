package com.edusistem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

	private Long id;
	private String nombreCompleto;
	private String dni;
	private String tipoUsuario;
	private String estado;
}
