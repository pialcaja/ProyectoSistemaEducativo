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

import com.edusistem.dto.AdministradorRegistroRequest;
import com.edusistem.dto.AdministradorUpdateRequest;
import com.edusistem.service.AdministradorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AdministradorController {

	private final AdministradorService administradorService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarAdministrador(@RequestBody AdministradorRegistroRequest request) {
        return administradorService.registrarAdministrador(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerAdministradorPorId(@PathVariable Long id) {
        return administradorService.obtenerAdministradorPorId(id);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarAdministradores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filtro,
            @RequestParam(defaultValue = "codigoUsuario") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String estado) {
        return administradorService.listarAdministradores(page, size, filtro, sortBy, sortDir, estado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarAdministrador(
            @PathVariable Long id,
            @RequestBody AdministradorUpdateRequest request) {
        return administradorService.actualizarAdministrador(id, request);
    }
}
