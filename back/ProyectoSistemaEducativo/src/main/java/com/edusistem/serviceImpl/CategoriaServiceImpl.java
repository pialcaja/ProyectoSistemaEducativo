package com.edusistem.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.edusistem.dto.CategoriaDTO;
import com.edusistem.dto.CategoriaRegistroRequest;
import com.edusistem.model.Categoria;
import com.edusistem.repository.CategoriaRepository;
import com.edusistem.repository.DocenteRepository;
import com.edusistem.service.CategoriaService;
import com.edusistem.utils.TextoUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

	private final CategoriaRepository categoriaRepository;
    private final DocenteRepository docenteRepository;

    @Override
    public ResponseEntity<Map<String, Object>> registrarCategoria(CategoriaRegistroRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String nombreFormateado = TextoUtils.formatoPrimeraLetraMayuscula(request.getNombre());

            if (categoriaRepository.findByNombreCategoriaIgnoreCase(nombreFormateado).isPresent()) {
                response.put("success", false);
                response.put("message", "La categoría ya existe");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            Categoria categoria = new Categoria();
            categoria.setNombreCategoria(nombreFormateado);

            Categoria guardado = categoriaRepository.save(categoria);

            response.put("success", true);
            response.put("message", "Categoría registrada correctamente");
            response.put("data", mapToDTO(guardado));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al registrar categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> obtenerCategoriaPorId(Long id) {
        Map<String, Object> response = new HashMap<>();
        return categoriaRepository.findById(id).map(cat -> {
            response.put("success", true);
            response.put("data", mapToDTO(cat));
            return ResponseEntity.ok(response);
        }).orElseGet(() -> {
            response.put("success", false);
            response.put("message", "Categoría no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        });
    }

    @Override
    public ResponseEntity<Map<String, Object>> listarCategorias(int page, int size, String sortBy, String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Categoria> categorias = categoriaRepository.findAll(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("categorias", categorias.getContent().stream().map(this::mapToDTO).toList());
            response.put("currentPage", categorias.getNumber());
            response.put("totalItems", categorias.getTotalElements());
            response.put("totalPages", categorias.getTotalPages());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al listar categorías: " + e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> actualizarCategoria(Long id, CategoriaRegistroRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

            String nombreFormateado = TextoUtils.formatoPrimeraLetraMayuscula(request.getNombre());

            if (!categoria.getNombreCategoria().equalsIgnoreCase(nombreFormateado)
                    && categoriaRepository.findByNombreCategoriaIgnoreCase(nombreFormateado).isPresent()) {
                response.put("success", false);
                response.put("message", "Ya existe otra categoría con ese nombre");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            categoria.setNombreCategoria(nombreFormateado);
            Categoria actualizado = categoriaRepository.save(categoria);

            response.put("success", true);
            response.put("message", "Categoría actualizada correctamente");
            response.put("data", mapToDTO(actualizado));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> eliminarCategoria(Long id) {
        Map<String, Object> response = new HashMap<>();

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        long countDocentes = docenteRepository.countByCategoria(categoria);
        if (countDocentes > 0) {
            response.put("mensaje", "No se puede eliminar la categoría porque está siendo utilizada por " + countDocentes + " docente(s)");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        categoriaRepository.delete(categoria);

        response.put("mensaje", "Categoría eliminada correctamente");
        return ResponseEntity.ok(response);
    }

    private CategoriaDTO mapToDTO(Categoria categoria) {
        return new CategoriaDTO(categoria.getCodigoCategoria(), categoria.getNombreCategoria());
    }
}
