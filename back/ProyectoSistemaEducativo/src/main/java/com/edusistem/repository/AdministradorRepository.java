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

    @Query("SELECT a FROM Administrador a " +
    	       "WHERE a.estadoUsuario = :estado " +
    	       "AND (:filtro IS NULL OR " +
    	       "LOWER(a.nombreUsuario) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
    	       "OR LOWER(a.apellidoPaternoUsuario) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
    	       "OR LOWER(a.apellidoMaternoUsuario) LIKE LOWER(CONCAT('%', :filtro, '%'))) ")
    	Page<Administrador> buscarPorEstadoYNombre(@Param("estado") EstadoUsuario estado,
    	                                           @Param("filtro") String filtro,
    	                                           Pageable pageable);
    
    boolean existsByEmailAdministrador(String emailAdministrador);

    boolean existsByEmailAdministradorAndCodigoUsuarioNot(String emailAdministrador, Long id);
}
