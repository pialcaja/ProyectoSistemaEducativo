package com.edusistem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edusistem.dto.AlumnoRequestDTO;
import com.edusistem.service.AlumnoService;

@RestController
@RequestMapping("/api/alumno")
@CrossOrigin(origins = "http://localhost:4200")
public class AlumnoController {

	@Autowired
	private AlumnoService alumnoService;
	
	@PostMapping("/completarRegistro/{usuarioId}")
	public ResponseEntity<Map<String, Object>> completarRegistro(@PathVariable Long usuarioId, @RequestBody AlumnoRequestDTO dto){
		return alumnoService.completarRegistro(usuarioId, dto);
	}
}
