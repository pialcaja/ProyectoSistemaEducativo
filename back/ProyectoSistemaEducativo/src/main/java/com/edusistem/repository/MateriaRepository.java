package com.edusistem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edusistem.model.Materia;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

	
}
