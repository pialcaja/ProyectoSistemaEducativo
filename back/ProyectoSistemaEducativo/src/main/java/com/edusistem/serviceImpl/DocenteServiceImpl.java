package com.edusistem.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edusistem.dto.DocenteRequestDTO;
import com.edusistem.model.Docente;
import com.edusistem.model.Materia;
import com.edusistem.model.Rol;
import com.edusistem.model.Usuario;
import com.edusistem.repository.DocenteRepository;
import com.edusistem.repository.MateriaRepository;
import com.edusistem.repository.RolRepository;
import com.edusistem.repository.UsuarioRepository;
import com.edusistem.service.DocenteService;

@Service
public class DocenteServiceImpl implements DocenteService {

	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private RolRepository rolRepo;
	
	@Autowired
	private DocenteRepository docenteRepo;
	
	@Autowired
	private MateriaRepository materiaRepo;
	
	@Transactional
	@Override
	public ResponseEntity<Map<String, Object>> completarRegistro(Long usuarioId, DocenteRequestDTO dto) {
		Map<String, Object> response = new HashMap<>();
		try {
			Usuario usuarioBase = usuarioRepo.findById(usuarioId)
					.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
			
			if (!usuarioBase.getRol().getNombre().equalsIgnoreCase("PENDIENTE")) {
				response.put("mensaje", "El usuario ya tiene un rol asignado");
				
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}
			
			Materia materia = materiaRepo.findById(dto.getMateriaId()).orElseThrow(() 
					-> new RuntimeException("Materia no encontrada"));
			
			if (docenteRepo.existsByTelefono(dto.getTelefono())) {
				response.put("mensaje", "Telefono ya enlazado a otro docente");
				
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}
			
			Rol rolDocente = rolRepo.findByNombreIgnoreCase("DOCENTE")
					.orElseThrow(() -> new RuntimeException("Rol DOCENTE no encontrado"));

			usuarioBase.setRol(rolDocente);
			usuarioRepo.save(usuarioBase);
			
			Docente docente = new Docente();
			docente.setUsuario(usuarioBase);
			docente.setMateria(materia);
			docente.setTelefono(dto.getTelefono());
			
			docenteRepo.save(docente);
			
			response.put("mensaje", "Registro de docente completado exitosamente");
			response.put("docente", docente);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("mensaje", "Error al completar el registro de docente: " + e.getMessage());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
}
