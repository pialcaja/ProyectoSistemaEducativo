package com.edusistem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private int codigoSalon;
	
	@Column(nullable = false)
	private int aforoSalon;
}
