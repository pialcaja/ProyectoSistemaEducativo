package com.edusistem.dto;

import com.edusistem.model.EstadoMatricula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaUpdateRequest {
	
    private Long matriculaId;
    private EstadoMatricula nuevoEstado;
}
