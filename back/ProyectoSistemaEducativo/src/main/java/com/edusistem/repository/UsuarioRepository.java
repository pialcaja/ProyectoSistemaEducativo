package com.edusistem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edusistem.model.EstadoUsuario;
import com.edusistem.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByDniUsuario(String dniUsuario);
	
	List<Usuario> findByEstadoUsuario(EstadoUsuario estadoUsuario);
	
	@Query("SELECT u FROM Usuario u " +
	           "WHERE (:filtro IS NULL OR " +
	           "LOWER(u.nombreUsuario) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
	           "OR LOWER(u.apellidoPaternoUsuario) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
	           "OR LOWER(u.apellidoMaternoUsuario) LIKE LOWER(CONCAT('%', :filtro, '%')))")
	Page<Usuario> buscarPorNombreCompleto(@Param("filtro") String filtro, Pageable pageable);
}
