package com.edusistem.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.edusistem.dto.UsuarioDTO;
import com.edusistem.dto.UsuarioRegistroRequest;
import com.edusistem.model.EstadoUsuario;
import com.edusistem.model.TipoUsuario;
import com.edusistem.model.Usuario;
import com.edusistem.repository.TipoUsuarioRepository;
import com.edusistem.repository.UsuarioRepository;
import com.edusistem.service.UsuarioService;
import com.edusistem.utils.TextoUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final TipoUsuarioRepository tipoUsuarioRepository;

	@Override
	@Transactional
	public ResponseEntity<Map<String, Object>> registrarUsuario(UsuarioRegistroRequest request) {
		Map<String, Object> response = new HashMap<>();
		try {
			if (usuarioRepository.existsByDniUsuario(request.getDni())) {
				response.put("success", false);
				response.put("message", "El DNI ya está registrado en otro usuario");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}

			Usuario usuario = new Usuario();
			usuario.setNombreUsuario(TextoUtils.formatoPrimeraLetraMayuscula(request.getNombre()));
			usuario.setApellidoPaternoUsuario(TextoUtils.formatoPrimeraLetraMayuscula(request.getApellidoPaterno()));
			usuario.setApellidoMaternoUsuario(TextoUtils.formatoPrimeraLetraMayuscula(request.getApellidoMaterno()));
			usuario.setDniUsuario(request.getDni());

			TipoUsuario pendiente = tipoUsuarioRepository.findByNombreTipoUsuarioIgnoreCase("PENDIENTE")
					.orElseThrow(() -> new RuntimeException("TipoUsuario PENDIENTE no encontrado"));
			usuario.setTipoUsuario(pendiente);
			usuario.setEstadoUsuario(EstadoUsuario.ACTIVO);

			Usuario usuarioGuardado = usuarioRepository.save(usuario);

			response.put("success", true);
			response.put("message", "Usuario registrado en estado PENDIENTE");
			response.put("data", mapToDTO(usuarioGuardado));
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error al registrar usuario: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> obtenerUsuarioPorId(Long id) {
		Map<String, Object> response = new HashMap<>();
		return usuarioRepository.findById(id).map(usuario -> {
			response.put("success", true);
			response.put("data", mapToDTO(usuario));
			return ResponseEntity.ok(response);
		}).orElseGet(() -> {
			response.put("success", false);
			response.put("message", "Usuario no encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		});
	}

	@Override
	public ResponseEntity<Map<String, Object>> listarUsuarios(int page, int size, String filtro, String sortBy, 
			String sortDir, String estado) {
		try {
	        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
	                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

	        Pageable pageable = PageRequest.of(page, size, sort);

	        EstadoUsuario estadoFinal = (estado == null || estado.isEmpty())
	                ? EstadoUsuario.ACTIVO
	                : EstadoUsuario.valueOf(estado.toUpperCase());

	        Page<Usuario> usuarios = usuarioRepository.buscarPorEstadoYNombre(estadoFinal, filtro, pageable);

	        Map<String, Object> response = new HashMap<>();
	        response.put("usuarios", usuarios.getContent());
	        response.put("currentPage", usuarios.getNumber());
	        response.put("totalItems", usuarios.getTotalElements());
	        response.put("totalPages", usuarios.getTotalPages());

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of("mensaje", "Error al listar usuarios: " + e.getMessage()));
	    }
	}

	@Override
	@Transactional
	public ResponseEntity<Map<String, Object>> actualizarUsuario(Long id, UsuarioRegistroRequest request) {
		Map<String, Object> response = new HashMap<>();
		try {
			Usuario usuario = usuarioRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

			if (!usuario.getDniUsuario().equals(request.getDni())
					&& usuarioRepository.existsByDniUsuarioAndCodigoUsuarioNot(request.getDni(), id)) {
				response.put("success", false);
				response.put("message", "El DNI ya está registrado en otro usuario");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}

			usuario.setNombreUsuario(TextoUtils.formatoPrimeraLetraMayuscula(request.getNombre()));
			usuario.setApellidoPaternoUsuario(TextoUtils.formatoPrimeraLetraMayuscula(request.getApellidoPaterno()));
			usuario.setApellidoMaternoUsuario(TextoUtils.formatoPrimeraLetraMayuscula(request.getApellidoMaterno()));
			usuario.setDniUsuario(request.getDni());

			Usuario usuarioActualizado = usuarioRepository.save(usuario);

			response.put("success", true);
			response.put("message", "Usuario actualizado correctamente");
			response.put("data", mapToDTO(usuarioActualizado));
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error al actualizar usuario: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<Map<String, Object>> eliminarUsuario(Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
			if (usuarioOpt.isEmpty()) {
				response.put("success", false);
				response.put("message", "Usuario no encontrado");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			Usuario usuario = usuarioOpt.get();

			usuario.setEstadoUsuario(EstadoUsuario.INACTIVO);
			usuarioRepository.save(usuario);

			response.put("success", true);
			response.put("message", "Usuario desactivado correctamente");
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error al desactivar usuario: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	private UsuarioDTO mapToDTO(Usuario usuario) {
		String nombreCompleto = usuario.getNombreUsuario() + " " + usuario.getApellidoPaternoUsuario() + " "
				+ usuario.getApellidoMaternoUsuario();

		return new UsuarioDTO(usuario.getCodigoUsuario(), nombreCompleto, usuario.getDniUsuario(),
				usuario.getTipoUsuario().getNombreTipoUsuario(), usuario.getEstadoUsuario().name());
	}
}
