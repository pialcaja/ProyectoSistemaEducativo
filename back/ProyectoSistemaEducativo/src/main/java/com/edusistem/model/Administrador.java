package com.edusistem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_administrador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administrador {
	
	@Id
	private Long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private Usuario usaurio;
}
