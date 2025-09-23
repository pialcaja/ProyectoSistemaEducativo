package com.edusistem.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long alumno;
	
	private Long curso;
	
	private Long tipoNota;
}
