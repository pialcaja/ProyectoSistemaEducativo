package com.edusistem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edusistem.model.Docente;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {

	boolean existsByTelefono(String telefono);
	
	boolean existsByTelefonoAndIdNot(String telefono, Long id);
}
