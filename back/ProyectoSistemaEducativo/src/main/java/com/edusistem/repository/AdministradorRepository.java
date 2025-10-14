package com.edusistem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edusistem.model.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

}
