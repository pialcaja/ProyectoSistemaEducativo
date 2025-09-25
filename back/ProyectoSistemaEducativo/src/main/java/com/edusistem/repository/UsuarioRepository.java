package com.edusistem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edusistem.model.EstadoUsuario;
import com.edusistem.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByDniUsuario(String dniUsuario);
	
	List<Usuario> findByEstadoUsuario(EstadoUsuario estadoUsuario);
	
	@Query("SELECT u FROM tb_usuario u " + 
			"WHERE LOWER(u.nombreUsuario) LIKE LOWER(CONCAT('%', :nombre, '%')) " +
			"AND LOWER(u.apellidoPaternoUsuario) LIKE LOWER(CONCAT('%', :apellido, '%'))")
	List<Usuario> buscarPorNombreApellido(@Param("nombre") String nombre, @Param("apellido") String apellido);
}
