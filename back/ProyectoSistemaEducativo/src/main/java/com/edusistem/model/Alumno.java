package com.edusistem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_alumno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@PrimaryKeyJoinColumn(name = "id")
public class Alumno extends Usuario {

	@Column(nullable = false)
	private int edad;

	public static Alumno desdeUsuarioBase(Usuario base, Rol rol, int edad) {
		Alumno a = new Alumno();
		a.setId(base.getId());
		a.setNombre(base.getNombre());
		a.setApepa(base.getApepa());
		a.setApema(base.getApema());
		a.setDni(base.getDni());
		a.setEmail(base.getEmail());
		a.setPwd(base.getPwd());
		a.setRol(rol);
		a.setEstado(base.getEstado());
		a.setEdad(edad);
		
		return a;
	}
	
	
}
