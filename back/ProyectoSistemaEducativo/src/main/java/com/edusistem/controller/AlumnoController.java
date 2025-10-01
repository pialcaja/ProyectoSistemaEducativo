package com.edusistem.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edusistem.dto.AlumnoRegistroRequest;
import com.edusistem.service.AlumnoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/alumno")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AlumnoController {

	private final AlumnoService alumnoService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarAlumno(@RequestBody AlumnoRegistroRequest request) {
        return alumnoService.registrarAlumno(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerAlumnoPorId(@PathVariable Long id) {
        return alumnoService.obtenerAlumnoPorId(id);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarAlumnos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filtro,
            @RequestParam(defaultValue = "codigoUsuario") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return alumnoService.listarAlumnos(page, size, filtro, sortBy, sortDir);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarAlumno(
            @PathVariable Long id,
            @RequestBody AlumnoRegistroRequest request) {
        return alumnoService.actualizarAlumno(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarAlumno(@PathVariable Long id) {
        return alumnoService.eliminarAlumno(id);
    }
}
