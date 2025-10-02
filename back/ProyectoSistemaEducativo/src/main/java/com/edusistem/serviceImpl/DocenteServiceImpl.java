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

import com.edusistem.dto.DocenteDTO;
import com.edusistem.dto.DocenteRegistroRequest;
import com.edusistem.dto.DocenteUpdateRequest;
import com.edusistem.model.Categoria;
import com.edusistem.model.Docente;
import com.edusistem.model.EstadoUsuario;
import com.edusistem.model.TipoUsuario;
import com.edusistem.model.Usuario;
import com.edusistem.repository.CategoriaRepository;
import com.edusistem.repository.DocenteRepository;
import com.edusistem.repository.TipoUsuarioRepository;
import com.edusistem.repository.UsuarioRepository;
import com.edusistem.service.DocenteService;
import com.edusistem.utils.TextoUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocenteServiceImpl implements DocenteService {

	private final DocenteRepository docenteRepository;
	private final UsuarioRepository usuarioRepository;
	private final CategoriaRepository categoriaRepository;
	private final TipoUsuarioRepository tipoUsuarioRepository;

	@Override
	public ResponseEntity<Map<String, Object>> registrarDocente(DocenteRegistroRequest request) {
		Map<String, Object> response = new HashMap<>();
		try {
			Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
					.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

			TipoUsuario tipoDocente = tipoUsuarioRepository.findByNombreTipoUsuarioIgnoreCase("DOCENTE")
					.orElseThrow(() -> new RuntimeException("Tipo de usuario DOCENTE no encontrado"));
			usuario.setTipoUsuario(tipoDocente);
			usuarioRepository.save(usuario);
			
			String emailFormateado = TextoUtils.formatoTodoMinuscula(request.getEmail());
			
			if (docenteRepository.existsByEmailDocente(emailFormateado)) {
			    response.put("success", false);
			    response.put("message", "El email ya está registrado en otro docente");
			    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}

			if (docenteRepository.existsByTelefonoDocente(request.getTelefono())) {
			    response.put("success", false);
			    response.put("message", "El teléfono ya está registrado en otro docente");
			    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}

			Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
					.orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

			Docente docente = new Docente();
			docente.setCodigoUsuario(usuario.getCodigoUsuario());
			docente.setCategoria(categoria);
			docente.setEmailDocente(emailFormateado);
			docente.setTelefonoDocente(request.getTelefono());

			Docente docenteGuardado = docenteRepository.save(docente);

			response.put("success", true);
			response.put("message", "Docente registrado correctamente");
			response.put("data", mapToDTO(docenteGuardado));
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error al registrar docente: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> obtenerDocentePorId(Long id) {
		Map<String, Object> response = new HashMap<>();
		return docenteRepository.findById(id).map(docente -> {
			String nombreCompleto = docente.getNombreUsuario() + " " + docente.getApellidoPaternoUsuario() + " "
					+ docente.getApellidoMaternoUsuario();

			DocenteDTO dto = new DocenteDTO(docente.getCodigoUsuario(), nombreCompleto,
					docente.getCategoria().getNombreCategoria(), docente.getEmailDocente(),
					docente.getTelefonoDocente());
			response.put("success", true);
			response.put("data", dto);
			return ResponseEntity.ok(response);
		}).orElseGet(() -> {
			response.put("success", false);
			response.put("message", "Docente no encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		});
	}

	@Override
    public ResponseEntity<Map<String, Object>> listarDocentes(int page, int size, String filtro, String sortBy, String sortDir, String estado) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        EstadoUsuario estadoFinal = (estado == null || estado.isEmpty())
                ? EstadoUsuario.ACTIVO
                : EstadoUsuario.valueOf(estado.toUpperCase());

        Page<Docente> docentes = docenteRepository.buscarPorEstadoYNombre(estadoFinal, filtro, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("docentes", docentes.getContent());
        response.put("currentPage", docentes.getNumber());
        response.put("totalItems", docentes.getTotalElements());
        response.put("totalPages", docentes.getTotalPages());

        return ResponseEntity.ok(response);
    }

	@Override
	public ResponseEntity<Map<String, Object>> actualizarDocente(Long id, DocenteUpdateRequest request) {
		Map<String, Object> response = new HashMap<>();
		try {
			Docente docente = docenteRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Docente no encontrado con id " + id));

			String emailFormateado = TextoUtils.formatoTodoMinuscula(request.getEmail());
			
			if (emailFormateado != null && !emailFormateado.isBlank()
			        && !docente.getEmailDocente().equalsIgnoreCase(emailFormateado)
			        && docenteRepository.existsByEmailDocenteAndCodigoUsuarioNot(emailFormateado, id)) {
			    response.put("success", false);
			    response.put("message", "El email ya está registrado en otro docente");
			    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			} else {
				docente.setEmailDocente(emailFormateado);
			}

			if (request.getTelefono() != null && !request.getTelefono().isBlank()
			        && !docente.getTelefonoDocente().equals(request.getTelefono())
			        && docenteRepository.existsByTelefonoDocenteAndCodigoUsuarioNot(request.getTelefono(), id)) {
			    response.put("success", false);
			    response.put("message", "El teléfono ya está registrado en otro docente");
			    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			} else {
				docente.setTelefonoDocente(request.getTelefono());
			}

			if (request.getCategoriaId() != null) {
				Categoria categoria = categoriaRepository.findById(request.getCategoriaId()).orElseThrow(
						() -> new RuntimeException("Categoría no encontrada con id " + request.getCategoriaId()));
				docente.setCategoria(categoria);
			}

			Docente docenteActualizado = docenteRepository.save(docente);

			String nombreCompleto = docenteActualizado.getNombreUsuario() + " "
					+ docenteActualizado.getApellidoPaternoUsuario() + " "
					+ docenteActualizado.getApellidoMaternoUsuario();

			DocenteDTO dto = new DocenteDTO(docenteActualizado.getCodigoUsuario(), nombreCompleto,
					docenteActualizado.getCategoria().getNombreCategoria(), docenteActualizado.getEmailDocente(),
					docenteActualizado.getTelefonoDocente());

			response.put("success", true);
			response.put("message", "Docente actualizado correctamente");
			response.put("data", dto);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error al actualizar docente: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	private DocenteDTO mapToDTO(Docente docente) {
		String nombreCompleto = docente.getNombreUsuario() + " " + docente.getApellidoPaternoUsuario() + " "
				+ docente.getApellidoMaternoUsuario();

		return new DocenteDTO(docente.getCodigoUsuario(), nombreCompleto, docente.getCategoria().getNombreCategoria(),
				docente.getEmailDocente(), docente.getTelefonoDocente());
	}
}
