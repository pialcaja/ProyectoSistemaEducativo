package com.edusistem.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaDTO {
	
    private Long id;
    private Long alumnoId;
    private String alumnoNombreCompleto;
    private Long cursoDocenteSalonId;
    private String cursoNombre;
    private String docenteNombreCompleto;
    private Long salonId;
    private LocalDateTime fechaMatricula;
    private String estado;
}
