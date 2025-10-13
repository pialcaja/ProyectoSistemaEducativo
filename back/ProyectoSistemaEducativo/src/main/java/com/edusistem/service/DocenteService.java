package com.edusistem.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.edusistem.dto.DocenteRequestDTO;

public interface DocenteService {

	public ResponseEntity<Map<String, Object>> completarRegistro(Long usuarioId, DocenteRequestDTO dto);
}
