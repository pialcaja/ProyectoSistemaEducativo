package com.edusistem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaRegistroRequest {

	private Long alumnoId;
	private Long cursoId;
	private Long tipoNotaId;
	private int valor;
}
