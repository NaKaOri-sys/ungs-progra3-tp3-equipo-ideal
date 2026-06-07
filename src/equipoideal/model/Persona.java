package equipoideal.model;

import java.util.ArrayList;
import java.util.Objects;

import equipoideal.model.dto.PersonaDto;
import equipoideal.util.RolEnum;

public class Persona implements Comparable<Persona> {
	private String nombre;
	private String apellido;
	private int calificacion;
	private RolEnum rol;
	private String rutaFoto;

	public Persona(String nombre, String apellido, int calificacion, RolEnum rol) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.calificacion = calificacion;
		this.rol = rol;
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

	// TODO esto debe ir en un util o un helper o algo asi, ya que esto es logica de
	// transformacion, no de la entidad Persona, ademas el metodo se llama
	// transformarEnDto pero recibe una lista, lo cual es confuso, revisar esto...podria ir en otro archivo y llamarse personasToDto o algo asi.
	public ArrayList<PersonaDto> transformarEnDto(ArrayList<Persona> lista) {
		ArrayList<PersonaDto> dto = new ArrayList<>();

		for (Persona p : lista) {
			dto.add(p.toDto());
		}

		return dto;
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

}
