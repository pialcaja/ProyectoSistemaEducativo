package com.edusistem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edusistem.model.Nota;
import com.edusistem.model.NotaId;

@Repository
public interface NotaRepository extends JpaRepository<Nota, NotaId> {

	List<Nota> findByAlumnoCodigoUsuario(Long codigoAlumno);
	
	List<Nota> findByCursoCodigoCurso(Long codigoCurso);
	
	@Query("SELECT n FROM Nota n WHERE n.alumno.codigoUsuario = :alumno AND n.curso.codigoCurso = :curso")
    Page<Nota> findNotasAlumnoEnCurso(@Param("alumno") Long codigoAlumno, 
                                      @Param("curso") Long codigoCurso, 
                                      Pageable pageable);
}
