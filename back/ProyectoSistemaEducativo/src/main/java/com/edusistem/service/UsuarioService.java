package com.edusistem.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.edusistem.dto.UsuarioRegistroRequest;

public interface UsuarioService {

	ResponseEntity<Map<String, Object>> registrarUsuario(UsuarioRegistroRequest request);

    ResponseEntity<Map<String, Object>> obtenerUsuarioPorId(Long id);

    public ResponseEntity<Map<String, Object>> listarUsuarios(int page, int size, String filtro, String sortBy, String sortDir, String estado);

    ResponseEntity<Map<String, Object>> actualizarUsuario(Long id, UsuarioRegistroRequest request);

    ResponseEntity<Map<String, Object>> eliminarUsuario(Long id);
}
