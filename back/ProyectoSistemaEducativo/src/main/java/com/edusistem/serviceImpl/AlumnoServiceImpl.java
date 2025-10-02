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

import com.edusistem.dto.AlumnoDTO;
import com.edusistem.dto.AlumnoRegistroRequest;
import com.edusistem.model.Alumno;
import com.edusistem.model.EstadoUsuario;
import com.edusistem.model.TipoUsuario;
import com.edusistem.model.Usuario;
import com.edusistem.repository.AlumnoRepository;
import com.edusistem.repository.TipoUsuarioRepository;
import com.edusistem.repository.UsuarioRepository;
import com.edusistem.service.AlumnoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {
	
	private final AlumnoRepository alumnoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    @Override
    @Transactional
    public ResponseEntity<Map<String, Object>> registrarAlumno(AlumnoRegistroRequest request) {
    	Map<String, Object> response = new HashMap<>();
        try {
            Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            TipoUsuario tipoAlumno = tipoUsuarioRepository.findByNombreTipoUsuarioIgnoreCase("ALUMNO")
                    .orElseThrow(() -> new RuntimeException("Tipo de usuario ALUMNO no encontrado"));
            usuario.setTipoUsuario(tipoAlumno);
            usuarioRepository.save(usuario);

            Alumno alumno = new Alumno();
            alumno.setCodigoUsuario(usuario.getCodigoUsuario());
            alumno.setEdadAlumno(request.getEdad());
            Alumno alumnoGuardado = alumnoRepository.save(alumno);

            response.put("success", true);
            response.put("message", "Alumno registrado correctamente");
            response.put("data", mapToDTO(alumnoGuardado));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al registrar alumno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

	@Override
	public ResponseEntity<Map<String, Object>> obtenerAlumnoPorId(Long id) {
		Map<String, Object> response = new HashMap<>();
		return alumnoRepository.findById(id).map(alumno -> {
			response.put("success", true);
			response.put("data", mapToDTO(alumno));
			return ResponseEntity.ok(response);
		}).orElseGet(() -> {
			response.put("success", false);
			response.put("message", "Alumno no encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		});
	}

	@Override
	public ResponseEntity<Map<String, Object>> listarAlumnos(int page, int size, String filtro, String sortBy, String sortDir, String estado) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        EstadoUsuario estadoFinal = (estado == null || estado.isEmpty())
                ? EstadoUsuario.ACTIVO
                : EstadoUsuario.valueOf(estado.toUpperCase());

        Page<Alumno> alumnos = alumnoRepository.buscarPorEstadoYNombre(estadoFinal, filtro, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("alumnos", alumnos.getContent());
        response.put("currentPage", alumnos.getNumber());
        response.put("totalItems", alumnos.getTotalElements());
        response.put("totalPages", alumnos.getTotalPages());

        return ResponseEntity.ok(response);
    }

	@Override
	public ResponseEntity<Map<String, Object>> actualizarAlumno(Long id, AlumnoRegistroRequest request) {
		Map<String, Object> response = new HashMap<>();
		try {
			Alumno alumno = alumnoRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

			alumno.setEdadAlumno(request.getEdad());
			Alumno alumnoActualizado = alumnoRepository.save(alumno);

			response.put("success", true);
			response.put("message", "Alumno actualizado correctamente");
			response.put("data", mapToDTO(alumnoActualizado));

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error al actualizar alumno: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	private AlumnoDTO mapToDTO(Alumno alumno) {
        String nombreCompleto = alumno.getNombreUsuario() + " " +
                alumno.getApellidoPaternoUsuario() + " " +
                alumno.getApellidoMaternoUsuario();

        return new AlumnoDTO(
                alumno.getCodigoUsuario(),
                nombreCompleto,
                alumno.getEdadAlumno()
        );
    }
}
