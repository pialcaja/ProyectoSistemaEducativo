package com.edusistem.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.edusistem.dto.DocenteRegistroRequest;
import com.edusistem.dto.DocenteUpdateRequest;

public interface DocenteService {

	public ResponseEntity<Map<String, Object>> registrarDocente(DocenteRegistroRequest request);
	
	public ResponseEntity<Map<String, Object>> obtenerDocentePorId(Long id);
	
	public ResponseEntity<Map<String, Object>> listarDocentes(int page, int size, String filtro, String sortBy, String sortDir, String estado);

	public ResponseEntity<Map<String, Object>> actualizarDocente(Long id, DocenteUpdateRequest request);
}
