package com.edusistem.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.edusistem.model.Administrador;
import com.edusistem.model.Rol;
import com.edusistem.model.Usuario;
import com.edusistem.repository.AdministradorRepository;
import com.edusistem.repository.RolRepository;
import com.edusistem.repository.UsuarioRepository;
import com.edusistem.service.AdministradorService;

@Service
public class AdministradorServiceImpl implements AdministradorService {

	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private RolRepository rolRepo;
	
	@Autowired
	private AdministradorRepository administradorRepo;
	
	@Override
	public ResponseEntity<Map<String, Object>> completarRegistro(Long usuarioId) {
		Map<String, Object> response = new HashMap<>();
		try {
			Usuario usuarioBase = usuarioRepo.findById(usuarioId)
					.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
			
			if (!usuarioBase.getRol().getNombre().equalsIgnoreCase("PENDIENTE")) {
				response.put("mensaje", "El usuario ya tiene un rol asignado");
				
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}
			
			Rol rolAdmin = rolRepo.findByNombreIgnoreCase("ADMIN")
					.orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));
			
			usuarioBase.setRol(rolAdmin);
			usuarioRepo.save(usuarioBase);
			
			Administrador admin = new Administrador();
			admin.setUsaurio(usuarioBase);
			
			administradorRepo.save(admin);
			
			response.put("mensaje", "Registro de administrador completado exitosamente");
			response.put("admin", admin);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("mensaje", "Error al completar el registro de administrador: " + e.getMessage());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}
