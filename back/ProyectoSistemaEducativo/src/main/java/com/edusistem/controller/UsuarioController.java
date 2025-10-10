package com.edusistem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.edusistem.dto.UsuarioRequestDTO;
import com.edusistem.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

    @GetMapping("/listar")
    public ResponseEntity<Map<String, Object>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String filtro) {
        return usuarioService.listar(page, size, filtro, sortDir, filtro);
    }
	
    @PostMapping("/registrar")
    public ResponseEntity<Map<String, Object>> registrar(@RequestBody UsuarioRequestDTO dto) {
        return usuarioService.registrar(dto);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtener(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
        return usuarioService.actualizar(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return usuarioService.eliminar(id);
    }
}
