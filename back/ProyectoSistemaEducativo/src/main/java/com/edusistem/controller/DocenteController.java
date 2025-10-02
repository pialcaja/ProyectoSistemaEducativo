package com.edusistem.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edusistem.dto.DocenteRegistroRequest;
import com.edusistem.dto.DocenteUpdateRequest;
import com.edusistem.service.DocenteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/docente")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DocenteController {

	private final DocenteService docenteService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarDocente(@RequestBody DocenteRegistroRequest request) {
        return docenteService.registrarDocente(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerDocentePorId(@PathVariable Long id) {
        return docenteService.obtenerDocentePorId(id);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarDocentes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filtro,
            @RequestParam(defaultValue = "codigoUsuario") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String estado) {
        return docenteService.listarDocentes(page, size, filtro, sortBy, sortDir, estado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarDocente(
            @PathVariable Long id,
            @RequestBody DocenteUpdateRequest request) {
        return docenteService.actualizarDocente(id, request);
    }
}
