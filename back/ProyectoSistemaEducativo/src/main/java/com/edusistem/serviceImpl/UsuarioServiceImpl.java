package com.edusistem.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.edusistem.dto.UsuarioRequestDTO;
import com.edusistem.dto.UsuarioUpdateDTO;
import com.edusistem.model.Alumno;
import com.edusistem.model.Docente;
import com.edusistem.model.Materia;
import com.edusistem.model.Rol;
import com.edusistem.model.Usuario;
import com.edusistem.repository.AlumnoRepository;
import com.edusistem.repository.DocenteRepository;
import com.edusistem.repository.MateriaRepository;
import com.edusistem.repository.RolRepository;
import com.edusistem.repository.UsuarioRepository;
import com.edusistem.service.UsuarioService;
import com.edusistem.utils.TextoUtils;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private RolRepository rolRepo;
	
	@Autowired
	private AlumnoRepository alumnoRepo;
	
	@Autowired
	private MateriaRepository materiaRepo;
	
	@Autowired
	private DocenteRepository docenteRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public ResponseEntity<Map<String, Object>> listar(int page, int size, String sortBy, 
			String order, String filtro) {
        Map<String, Object> response = new HashMap<>();
		try {
	        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) 
	        			? Sort.by(sortBy).ascending() 
	        			: Sort.by(sortBy).descending();

	        Pageable pageable = PageRequest.of(page, size, sort);

	        Page<Usuario> usuarios;
	        
	        if (filtro != null && !filtro.trim().isEmpty()) {
	            usuarios = usuarioRepo.buscarPorFiltro(filtro.trim(), pageable);
	        } else {
	            usuarios = usuarioRepo.findAll(pageable);
	        }

	        response.put("usuarios", usuarios.getContent());
	        response.put("currentPage", usuarios.getNumber());
	        response.put("totalItems", usuarios.getTotalElements());
	        response.put("totalPages", usuarios.getTotalPages());

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        response.put("mensaje", "Error al listar usuarios: " + e.getMessage());
	        
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	@Override
	@Transactional
	public ResponseEntity<Map<String, Object>> registrar(UsuarioRequestDTO dto) {
		Map<String, Object> response = new HashMap<>();
		try {

			validarDuplicadosUsuario(dto.getEmail(), dto.getDni(), null);

			Usuario usuario = new Usuario();
			usuario.setNombre(TextoUtils.formatoPrimeraLetraMayuscula(dto.getNombre()));
			usuario.setApepa(TextoUtils.formatoPrimeraLetraMayuscula(dto.getApepa()));
			usuario.setApema(TextoUtils.formatoPrimeraLetraMayuscula(dto.getApema()));
			usuario.setDni(dto.getDni());
			usuario.setEmail(TextoUtils.formatoTodoMinuscula(dto.getEmail()));
			usuario.setPwd(passwordEncoder.encode(dto.getPwd()));

			Rol rolPendiente = rolRepo.findByNombreIgnoreCase("PENDIENTE").orElseThrow(() 
								-> new RuntimeException("TipoUsuario PENDIENTE no encontrado"));
			usuario.setRol(rolPendiente);
			usuario.setEstado(1);

			usuarioRepo.save(usuario);

			response.put("usuario", usuario);
			
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("mensaje", "Error al registrar usuario: " + e.getMessage());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> obtenerPorId(Long id) {
		Map<String, Object> response = new HashMap<>();
		Usuario usuario = usuarioRepo.findById(id).orElseThrow(() 
							-> new RuntimeException("Usuario no encontrado"));
		response.put("usuario", usuario);
		
		return ResponseEntity.ok(response);
	}

	@Override
	@Transactional
	public ResponseEntity<Map<String, Object>> actualizar(Long id, UsuarioUpdateDTO dto) {
		Map<String, Object> response = new HashMap<>();
		try {
			Usuario usuario = usuarioRepo.findById(id)
					.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

			validarDuplicadosUsuario(dto.getEmail(), dto.getDni(), id);

			// CAMPOS GENERALES
			usuario.setNombre(TextoUtils.formatoPrimeraLetraMayuscula(dto.getNombre()));
			usuario.setApepa(TextoUtils.formatoPrimeraLetraMayuscula(dto.getApepa()));
			usuario.setApema(TextoUtils.formatoPrimeraLetraMayuscula(dto.getApema()));
			usuario.setDni(dto.getDni());
			usuario.setEmail(TextoUtils.formatoTodoMinuscula(dto.getEmail()));
			usuario.setEstado(usuario.getEstado());
			
			if (dto.getPwd() != null && !dto.getPwd().isBlank()) {
				usuario.setPwd(passwordEncoder.encode(dto.getPwd()));
			}
			
			// CAMPOS ESPECIFICOS POR ROL
			String rol = usuario.getRol().getNombre().toUpperCase();

	        switch (rol) {

	            case "ALUMNO" -> {
	                Alumno alumno = alumnoRepo.findById(usuario.getId())
	                        .orElseThrow(() -> new RuntimeException("Registro de alumno no encontrado"));

	                if (dto.getEdad() != null && dto.getEdad() > 0) {
	                    alumno.setEdad(dto.getEdad());
	                }

	                alumnoRepo.save(alumno);
	            }

	            case "DOCENTE" -> {
	                Docente docente = docenteRepo.findById(usuario.getId())
	                        .orElseThrow(() -> new RuntimeException("Registro de docente no encontrado"));

	                if (dto.getMateriaId() != null) {
	                    Materia materia = materiaRepo.findById(dto.getMateriaId())
	                            .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
	                    docente.setMateria(materia);
	                }

	                if (dto.getTelefono() != null && !dto.getTelefono().isBlank()) {
	                    validarTelefonoDocente(dto.getTelefono(), usuario.getId());
	                    docente.setTelefono(dto.getTelefono());
	                }

	                docenteRepo.save(docente);
	            }

	            case "ADMIN" -> {
	            }

	            default -> {
	                throw new RuntimeException("Rol no válido para actualización: " + rol);
	            }
	        }

			usuarioRepo.save(usuario);

			response.put("usuario", usuario);
			
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("mensaje", "Error al actualizar usuario: " + e.getMessage());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<Map<String, Object>> eliminar(Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);
			
			if (usuarioOpt.isEmpty()) {
				response.put("mensaje", "Usuario no encontrado");
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			Usuario usuario = usuarioOpt.get();
			
			if (usuario.getEstado() == 2) {
				response.put("mensaje", "Usuario actualmente eliminado");
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
			usuario.setEstado(2);
			
			usuarioRepo.save(usuario);

			response.put("mensaje", "Usuario desactivado correctamente");
			
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("mensaje", "Error al desactivar usuario: " + e.getMessage());
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> recuperar(Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);

			if (usuarioOpt.isEmpty()) {
				response.put("mensaje", "Usuario no encontrado");

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			Usuario usuario = usuarioOpt.get();

			if (usuario.getEstado() == 1) {
				response.put("mensaje", "Usuario actualmente activo");

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			usuario.setEstado(1);

			usuarioRepo.save(usuario);

			response.put("mensaje", "Usuario recuperado correctamente");

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("mensaje", "Error al recuperar usuario: " + e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	private void validarDuplicadosUsuario(String email, String dni, Long idExcluido) {
		email = TextoUtils.formatoTodoMinuscula(email);

		boolean existeEmail = (idExcluido == null) ? usuarioRepo.existsByEmail(email)
				: usuarioRepo.existsByEmailAndIdNot(email, idExcluido);

		boolean existeDni = (idExcluido == null) ? usuarioRepo.existsByDni(dni)
				: usuarioRepo.existsByDniAndIdNot(dni, idExcluido);

		if (existeEmail) {
			throw new RuntimeException("Email ya enlazado a otro usuario");
		}

		if (existeDni) {
			throw new RuntimeException("DNI ya enlazado a otro usuario");
		}
	}
	
	private void validarTelefonoDocente(String telefono, Long idExcluido) {
		boolean existeTelefono = (idExcluido == null) ? docenteRepo.existsByTelefono(telefono)
				: docenteRepo.existsByTelefonoAndIdNot(telefono, idExcluido);

		if (existeTelefono) {
			throw new RuntimeException("Teléfono ya enlazado a otro docente");
		}
	}
}
