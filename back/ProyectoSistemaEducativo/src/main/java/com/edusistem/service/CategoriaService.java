package com.edusistem.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.edusistem.dto.CategoriaRegistroRequest;

public interface CategoriaService {

	public ResponseEntity<Map<String, Object>> registrarCategoria(CategoriaRegistroRequest request);

	public ResponseEntity<Map<String, Object>> obtenerCategoriaPorId(Long id);

	public ResponseEntity<Map<String, Object>> listarCategorias(int page, int size, String sortBy, String sortDir);

	public ResponseEntity<Map<String, Object>> actualizarCategoria(Long id, CategoriaRegistroRequest request);

	public ResponseEntity<Map<String, Object>> eliminarCategoria(Long id);
}
