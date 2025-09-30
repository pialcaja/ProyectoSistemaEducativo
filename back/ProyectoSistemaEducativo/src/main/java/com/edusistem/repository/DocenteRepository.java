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

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {

	Optional<Docente> findByEmailDocente(String emailDocente);
	
	List<Docente> findByCategoriaCodigoCategoria(Long codigoCategoria);
	
	@Query("SELECT d FROM Docente d " +
	           "WHERE (:nombre IS NULL OR LOWER(d.nombreUsuario) LIKE LOWER(CONCAT('%', :nombre, '%')))")
	Page<Docente> buscarPorNombre(@Param("nombre") String nombre, Pageable pageable);
}
