package com.edusistem.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edusistem.dto.AlumnoRequestDTO;
import com.edusistem.model.Alumno;
import com.edusistem.model.Rol;
import com.edusistem.model.Usuario;
import com.edusistem.repository.AlumnoRepository;
import com.edusistem.repository.RolRepository;
import com.edusistem.repository.UsuarioRepository;
import com.edusistem.service.AlumnoService;

@Service
public class AlumnoServiceImpl implements AlumnoService {

	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private RolRepository rolRepo;
	
	@Autowired
	private AlumnoRepository alumnoRepo;
	
	@Transactional
	@Override
	public ResponseEntity<Map<String, Object>> completarRegistro(Long usuarioId, AlumnoRequestDTO dto) {
		Map<String, Object> response = new HashMap<>();
		try {
			Usuario usuarioBase = usuarioRepo.findById(usuarioId)
					.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

			if (!usuarioBase.getRol().getNombre().equalsIgnoreCase("PENDIENTE")) {
				response.put("mensaje", "El usuario ya tiene un rol asignado");
				
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}

			Rol rolAlumno = rolRepo.findByNombreIgnoreCase("ALUMNO")
					.orElseThrow(() -> new RuntimeException("Rol ALUMNO no encontrado"));
			
			Alumno alumno = Alumno.desdeUsuarioBase(usuarioBase, rolAlumno, dto.getEdad());

			alumnoRepo.save(alumno);
			
			usuarioRepo.delete(usuarioBase);

			response.put("mensaje", "Registro de alumno completado exitosamente");
			response.put("alumno", alumno);
			
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("mensaje", "Error al completar registro de alumno: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
}
