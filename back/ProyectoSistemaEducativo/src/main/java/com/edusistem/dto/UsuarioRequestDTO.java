package com.edusistem.dto;

import lombok.Data;

@Data
public class UsuarioRequestDTO {

	private String nombre;
	private String apepa;
	private String apema;
	private String dni;
	private String email;
	private String pwd;
	private Long idRol;
}
