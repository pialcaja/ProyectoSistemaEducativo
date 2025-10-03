package com.edusistem.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.edusistem.dto.TipoUsuarioDTO;

public interface TipoUsuarioService {

	public ResponseEntity<Map<String, Object>> registrarTipoUsuario(TipoUsuarioDTO request);
	
	public ResponseEntity<Map<String, Object>> obtenerTipoUsuarioPorId(Long id);
	
	public ResponseEntity<Map<String, Object>> listarTiposUsuario(int page, int size, String sortBy, String sortDir);
	
	public ResponseEntity<Map<String, Object>> actualizarTipoUsuario(Long id, TipoUsuarioDTO request);
	
	public ResponseEntity<Map<String, Object>> eliminarTipoUsuario(Long id);
}
