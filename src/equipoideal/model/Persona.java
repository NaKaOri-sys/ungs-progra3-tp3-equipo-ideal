package equipoideal.model;

import java.util.Objects;

import equipoideal.model.dto.PersonaDto;
import equipoideal.util.RolEnum;

public class Persona implements Comparable<Persona> {
	private String nombre;
	private String apellido;
	private int calificacion;
	private RolEnum rol;
	private String rutaFoto;

	public Persona(String nombre, String apellido, int calificacion, RolEnum rol, String rutaFoto) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.calificacion = calificacion;
		this.rol = rol;
		this.rutaFoto = rutaFoto;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public RolEnum getRol() {
		return rol;
	}

	public int getCalificacion() {
		return calificacion;
	}

	@Override
	public int compareTo(Persona persona) {
		if (this.getCalificacion() > persona.getCalificacion())
			return 1;
		if (this.getCalificacion() == persona.getCalificacion())
			return 0;
		return -1;
	}

	public String getRutaFoto() {
		return rutaFoto;
	}

	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	public PersonaDto toDto() {
		return new PersonaDto(nombre, apellido, calificacion, rol, rutaFoto);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Persona other = (Persona) obj;
		return Objects.equals(nombre, other.nombre) && Objects.equals(apellido, other.apellido);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre, apellido);
	}
	
	@Override
	public String toString() {
		return this.nombre + " " + this.apellido + "(" + this.rol + ")";
	}
}
