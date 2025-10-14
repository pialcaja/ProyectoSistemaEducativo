package com.edusistem.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface AdministradorService {

	public ResponseEntity<Map<String, Object>> completarRegistro(Long usuarioId);
}
