package com.edusistem.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edusistem.model.Administrador;
import com.edusistem.model.EstadoUsuario;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    Optional<Administrador> findByEmailAdministrador(String emailAdministrador);

    @Query("SELECT a FROM Administrador a WHERE a.estadoUsuario = :estado")
    Page<Administrador> listarAdministradoresActivos(@Param("estado") EstadoUsuario estado, Pageable pageable);
}
