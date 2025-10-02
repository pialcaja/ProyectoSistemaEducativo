package com.edusistem.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edusistem.dto.AdministradorDTO;
import com.edusistem.dto.AdministradorRegistroRequest;
import com.edusistem.dto.AdministradorUpdateRequest;
import com.edusistem.model.Administrador;
import com.edusistem.model.EstadoUsuario;
import com.edusistem.model.TipoUsuario;
import com.edusistem.model.Usuario;
import com.edusistem.repository.AdministradorRepository;
import com.edusistem.repository.TipoUsuarioRepository;
import com.edusistem.repository.UsuarioRepository;
import com.edusistem.service.AdministradorService;
import com.edusistem.utils.TextoUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdministradorServiceImpl implements AdministradorService {

	private final AdministradorRepository administradorRepository;
	private final UsuarioRepository usuarioRepository;
	private final TipoUsuarioRepository tipoUsuarioRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<Map<String, Object>> registrarAdministrador(AdministradorRegistroRequest request) {
		Map<String, Object> response = new HashMap<>();
	    try {
	        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
	                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	        TipoUsuario tipoAdmin = tipoUsuarioRepository.findByNombreTipoUsuarioIgnoreCase("ADMIN")
	                .orElseThrow(() -> new RuntimeException("Tipo de usuario ADMIN no encontrado"));
	        usuario.setTipoUsuario(tipoAdmin);
	        usuarioRepository.save(usuario);
	        
	        String emailFormateado = TextoUtils.formatoTodoMinuscula(request.getEmail());
	        
	        if (administradorRepository.existsByEmailAdministrador(emailFormateado)) {
	            response.put("success", false);
	            response.put("message", "El email ya está registrado en otro administrador");
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	        }

	        Administrador admin = new Administrador();
	        admin.setCodigoUsuario(usuario.getCodigoUsuario());
	        admin.setEmailAdministrador(emailFormateado);
	        admin.setPwdAdministrador(request.getPwd());

	        Administrador adminGuardado = administradorRepository.save(admin);

	        response.put("success", true);
	        response.put("message", "Administrador registrado correctamente");
	        response.put("data", mapToDTO(adminGuardado));
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", "Error al registrar administrador: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}

	@Override
	public ResponseEntity<Map<String, Object>> obtenerAdministradorPorId(Long id) {
		Map<String, Object> response = new HashMap<>();
		return administradorRepository.findById(id).map(admin -> {
			String nombreCompleto = admin.getNombreUsuario() + " " + admin.getApellidoPaternoUsuario() + " "
					+ admin.getApellidoMaternoUsuario();

			AdministradorDTO dto = new AdministradorDTO(admin.getCodigoUsuario(), admin.getEmailAdministrador(),
					nombreCompleto);

			response.put("success", true);
			response.put("data", dto);
			return ResponseEntity.ok(response);
		}).orElseGet(() -> {
			response.put("success", false);
			response.put("message", "Administrador no encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		});
	}

	@Override
    public ResponseEntity<Map<String, Object>> listarAdministradores(int page, int size, String filtro, String sortBy, String sortDir, String estado) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        EstadoUsuario estadoFinal = (estado == null || estado.isEmpty()) ? EstadoUsuario.ACTIVO : EstadoUsuario.valueOf(estado.toUpperCase());

        Page<Administrador> admins = administradorRepository.buscarPorEstadoYNombre(estadoFinal, filtro, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("administradores", admins.getContent());
        response.put("currentPage", admins.getNumber());
        response.put("totalItems", admins.getTotalElements());
        response.put("totalPages", admins.getTotalPages());

        return ResponseEntity.ok(response);
    }

	@Override
	public ResponseEntity<Map<String, Object>> actualizarAdministrador(Long id, AdministradorUpdateRequest request) {
		Map<String, Object> response = new HashMap<>();
		try {
			Administrador admin = administradorRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Administrador no encontrado con id " + id));

			String emailFormateado = TextoUtils.formatoTodoMinuscula(request.getEmail());
			
			if (emailFormateado != null && !emailFormateado.isBlank()
			        && !admin.getEmailAdministrador().equalsIgnoreCase(emailFormateado)
			        && administradorRepository.existsByEmailAdministradorAndCodigoUsuarioNot(emailFormateado, id)) {
			    response.put("success", false);
			    response.put("message", "El email ya está registrado en otro administrador");
			    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			} else {
				admin.setEmailAdministrador(emailFormateado);
			}

			if (request.getPwd() != null && !request.getPwd().isBlank()) {
				admin.setPwdAdministrador(passwordEncoder.encode(request.getPwd()));
			}

			Administrador adminActualizado = administradorRepository.save(admin);

			String nombreCompleto = adminActualizado.getNombreUsuario() + " "
					+ adminActualizado.getApellidoPaternoUsuario() + " " + adminActualizado.getApellidoMaternoUsuario();

			AdministradorDTO dto = new AdministradorDTO(adminActualizado.getCodigoUsuario(),
					adminActualizado.getEmailAdministrador(), nombreCompleto);

			response.put("success", true);
			response.put("message", "Administrador actualizado correctamente");
			response.put("data", dto);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error al actualizar administrador: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	private AdministradorDTO mapToDTO(Administrador admin) {
        String nombreCompleto = admin.getNombreUsuario() + " " +
        		admin.getApellidoPaternoUsuario() + " " +
        		admin.getApellidoMaternoUsuario();

        return new AdministradorDTO(
        		admin.getCodigoUsuario(),
                nombreCompleto,
                admin.getEmailAdministrador()
        );
    }
}
