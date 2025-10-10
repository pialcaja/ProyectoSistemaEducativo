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
public class CdsHorarioId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long cds;
	
	private Long horario;
}
