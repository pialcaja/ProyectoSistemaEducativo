package com.edusistem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edusistem.model.Docente;
import com.edusistem.model.EstadoUsuario;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {

	Optional<Docente> findByEmailDocente(String emailDocente);
	
	List<Docente> findByCategoriaCodigoCategoria(Long codigoCategoria);
	
	@Query("SELECT d FROM Docente d " +
		       "WHERE d.estadoUsuario = :estado " +
		       "AND (:filtro IS NULL OR " +
		       "LOWER(d.nombreUsuario) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
		       "OR LOWER(d.apellidoPaternoUsuario) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
		       "OR LOWER(d.apellidoMaternoUsuario) LIKE LOWER(CONCAT('%', :filtro, '%'))) ")
		Page<Docente> buscarPorEstadoYNombre(@Param("estado") EstadoUsuario estado,
		                                     @Param("filtro") String filtro,
		                                     Pageable pageable);
	
	boolean existsByEmailDocente(String emailDocente);
	
	boolean existsByTelefonoDocente(String telefonoDocente);

	boolean existsByEmailDocenteAndCodigoUsuarioNot(String emailDocente, Long id);
	
	boolean existsByTelefonoDocenteAndCodigoUsuarioNot(String telefonoDocente, Long id);
}
