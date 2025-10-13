package com.edusistem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edusistem.dto.DocenteRequestDTO;
import com.edusistem.service.DocenteService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/docente")
@CrossOrigin(origins = "http://localhost:4200")
public class DocenteController {

	@Autowired
	private DocenteService docenteService;
	
	@PostMapping("/completarRegistro/{usuarioId}")
	public ResponseEntity<Map<String, Object>> completarRegistro(@PathVariable Long usuarioId, @RequestBody DocenteRequestDTO dto) {
		return docenteService.completarRegistro(usuarioId, dto);
	}
	
}
