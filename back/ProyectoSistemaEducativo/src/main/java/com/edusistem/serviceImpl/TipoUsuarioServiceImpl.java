package com.edusistem.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.edusistem.dto.TipoUsuarioDTO;
import com.edusistem.model.TipoUsuario;
import com.edusistem.repository.TipoUsuarioRepository;
import com.edusistem.repository.UsuarioRepository;
import com.edusistem.service.TipoUsuarioService;
import com.edusistem.utils.TextoUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoUsuarioServiceImpl implements TipoUsuarioService {

	private final TipoUsuarioRepository tipoUsuarioRepository;
	
	private final UsuarioRepository usuarioRepository;

    @Override
    public ResponseEntity<Map<String, Object>> registrarTipoUsuario(TipoUsuarioDTO request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String nombreFormateado = TextoUtils.formatoTodoMayuscula(request.getNombre());

            if (tipoUsuarioRepository.findByNombreTipoUsuarioIgnoreCase(nombreFormateado).isPresent()) {
                response.put("success", false);
                response.put("message", "El tipo de usuario ya existe");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            TipoUsuario tipoUsuario = new TipoUsuario();
            tipoUsuario.setNombreTipoUsuario(nombreFormateado);

            TipoUsuario guardado = tipoUsuarioRepository.save(tipoUsuario);

            response.put("success", true);
            response.put("message", "Tipo de usuario registrado correctamente");
            response.put("data", mapToDTO(guardado));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al registrar tipo de usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> obtenerTipoUsuarioPorId(Long id) {
        Map<String, Object> response = new HashMap<>();
        return tipoUsuarioRepository.findById(id).map(tipo -> {
            response.put("success", true);
            response.put("data", mapToDTO(tipo));
            return ResponseEntity.ok(response);
        }).orElseGet(() -> {
            response.put("success", false);
            response.put("message", "Tipo de usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        });
    }

    @Override
    public ResponseEntity<Map<String, Object>> listarTiposUsuario(int page, int size, String sortBy, String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<TipoUsuario> tipos = tipoUsuarioRepository.findAll(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("tiposUsuario", tipos.getContent().stream().map(this::mapToDTO).toList());
            response.put("currentPage", tipos.getNumber());
            response.put("totalItems", tipos.getTotalElements());
            response.put("totalPages", tipos.getTotalPages());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al listar tipos de usuario: " + e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> actualizarTipoUsuario(Long id, TipoUsuarioDTO request) {
        Map<String, Object> response = new HashMap<>();
        try {
            TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado"));

            String nombreFormateado = TextoUtils.formatoTodoMayuscula(request.getNombre());

            if (!tipoUsuario.getNombreTipoUsuario().equalsIgnoreCase(nombreFormateado)
                    && tipoUsuarioRepository.findByNombreTipoUsuarioIgnoreCase(nombreFormateado).isPresent()) {
                response.put("success", false);
                response.put("message", "Ya existe otro tipo de usuario con ese nombre");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            tipoUsuario.setNombreTipoUsuario(nombreFormateado);
            TipoUsuario actualizado = tipoUsuarioRepository.save(tipoUsuario);

            response.put("success", true);
            response.put("message", "Tipo de usuario actualizado correctamente");
            response.put("data", mapToDTO(actualizado));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar tipo de usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> eliminarTipoUsuario(Long id) {
        Map<String, Object> response = new HashMap<>();

        TipoUsuario tipo = tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado"));

        long countUsuarios = usuarioRepository.countByTipoUsuario(tipo);
        if (countUsuarios > 0) {
            response.put("mensaje", "No se puede eliminar el tipo de usuario porque está siendo utilizado por " + countUsuarios + " usuario(s)");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        tipoUsuarioRepository.delete(tipo);

        response.put("mensaje", "Tipo de usuario eliminado correctamente");
        return ResponseEntity.ok(response);
    }

    private TipoUsuarioDTO mapToDTO(TipoUsuario tipo) {
        return new TipoUsuarioDTO(tipo.getCodigoTipoUsuario(), tipo.getNombreTipoUsuario());
    }
}
