package com.edusistem.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.edusistem.dto.UsuarioRequestDTO;
import com.edusistem.dto.UsuarioUpdateDTO;

public interface UsuarioService {

    public ResponseEntity<Map<String, Object>> listar(int page, int size, String sortBy, String order, String filtro);
    
	public ResponseEntity<Map<String, Object>> registrar(UsuarioRequestDTO dto);

	public ResponseEntity<Map<String, Object>> obtenerPorId(Long id);

    public ResponseEntity<Map<String, Object>> actualizar(Long id, UsuarioUpdateDTO dto);

    public ResponseEntity<Map<String, Object>> eliminar(Long id);
}
