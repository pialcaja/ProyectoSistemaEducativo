package com.edusistem.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.edusistem.dto.AlumnoRequestDTO;

public interface AlumnoService {

	public ResponseEntity<Map<String, Object>> completarRegistro(Long usuarioId, AlumnoRequestDTO dto);
}
