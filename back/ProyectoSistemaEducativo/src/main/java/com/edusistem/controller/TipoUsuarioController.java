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

import com.edusistem.dto.TipoUsuarioDTO;
import com.edusistem.service.TipoUsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tipos-usuario")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TipoUsuarioController {

	private final TipoUsuarioService tipoUsuarioService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrar(@RequestBody TipoUsuarioDTO request) {
        return tipoUsuarioService.registrarTipoUsuario(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Long id) {
        return tipoUsuarioService.obtenerTipoUsuarioPorId(id);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "codigoTipoUsuario") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return tipoUsuarioService.listarTiposUsuario(page, size, sortBy, sortDir);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long id, @RequestBody TipoUsuarioDTO request) {
        return tipoUsuarioService.actualizarTipoUsuario(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return tipoUsuarioService.eliminarTipoUsuario(id);
    }
}
