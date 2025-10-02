package com.edusistem.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.edusistem.dto.AdministradorRegistroRequest;
import com.edusistem.dto.AdministradorUpdateRequest;

public interface AdministradorService {

	public ResponseEntity<Map<String, Object>> registrarAdministrador(AdministradorRegistroRequest request);
	
	public ResponseEntity<Map<String, Object>> obtenerAdministradorPorId(Long id);
	
	public ResponseEntity<Map<String, Object>> listarAdministradores(int page, int size, String filtro, String sortBy, String sortDir, String estado);
	
	public ResponseEntity<Map<String, Object>> actualizarAdministrador(Long id, AdministradorUpdateRequest request);
}
