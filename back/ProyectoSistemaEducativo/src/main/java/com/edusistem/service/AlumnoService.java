package com.edusistem.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.edusistem.dto.AlumnoRegistroRequest;

public interface AlumnoService {

	public ResponseEntity<Map<String, Object>> registrarAlumno(AlumnoRegistroRequest request);
	
	public ResponseEntity<Map<String, Object>> obtenerAlumnoPorId(Long id);

	public ResponseEntity<Map<String, Object>> listarAlumnos(int page, int size, String filtro, String sortBy,String sortDir);

	public ResponseEntity<Map<String, Object>> actualizarAlumno(Long id, AlumnoRegistroRequest request);

	public ResponseEntity<Map<String, Object>> eliminarAlumno(Long id);
}
