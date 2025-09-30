package com.edusistem.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaDTO {

	private Long alumnoId;
	private Long cursoId;
	private Long tipoNotaId;
	private int valor;
	private LocalDateTime fecha;
}
