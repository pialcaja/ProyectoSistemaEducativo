package com.edusistem.dto;

import lombok.Data;

@Data
public class UsuarioResponseDTO {

	private Long id;
	private String nombreCompleto;
	private String email;
	private String rol;
	private int estado;
}
