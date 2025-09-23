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
@Table(name = "tb_tipo_nota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoNota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoTipoNota;
	
	@Column(nullable = false, unique = true, length = 50)
	private String nombreTipoNota;
}
