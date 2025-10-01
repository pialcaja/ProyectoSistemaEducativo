package com.edusistem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaRegistroRequest {
	
    private Long alumnoId;
    private Long cursoDocenteSalonId;
}
