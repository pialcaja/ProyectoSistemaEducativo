package com.edusistem.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edusistem.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);
	
	Optional<Usuario> findByDni(String dni);
	
	boolean existsByEmail(String email);
	
	boolean existsByDni(String dni);
	
	boolean existsByDniAndIdNot(String dni, Long id);

	boolean existsByEmailAndIdNot(String email, Long id);
	
	@Query("""
			SELECT u FROM Usuario u
			WHERE LOWER(u.nombre) LIKE LOWER(CONCAT('%', :filtro, '%'))
			   OR LOWER(u.apepa) LIKE LOWER(CONCAT('%', :filtro, '%'))
			   OR LOWER(u.apema) LIKE LOWER(CONCAT('%', :filtro, '%'))
			   OR LOWER(u.email) LIKE LOWER(CONCAT('%', :filtro, '%'))
			""")
	Page<Usuario> buscarPorFiltro(@Param("filtro") String filtro, Pageable pageable);
}
