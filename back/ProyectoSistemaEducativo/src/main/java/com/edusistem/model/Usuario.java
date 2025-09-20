package com.edusistem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigoUsuario;
	
	@Column(nullable = false, length = 50)
    private String nombreUsuario;

	@Column(nullable = false, length = 50)
    private String apellidoPaternoUsuario;

	@Column(nullable = false, length = 50)
    private String apellidoMaternoUsuario;
    
	@Column(nullable = false, unique = true, length = 8)
    private String dniUsuario;
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "codigo_tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;
    
	@Enumerated(EnumType.STRING)
	@Column(name = "estado_usuario", nullable = false, length = 10)
    private EstadoUsuario estadoUsuario;
}
