package com.edusistem.model;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_curso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoSalon;
	
	@Column(nullable = false)
	private int capacidadMaxima;
	
	@OneToMany(mappedBy = "salon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CursoDocenteSalon> cursosAsignados = new ArrayList<>();

    @Transient
    public int getAforoOcupado() {
        return cursosAsignados.stream()
                .mapToInt(c -> c.getMatriculas().size())
                .sum();
    }

    @Transient
    public int getAforoDisponible() {
        return capacidadMaxima - getAforoOcupado();
    }
}
