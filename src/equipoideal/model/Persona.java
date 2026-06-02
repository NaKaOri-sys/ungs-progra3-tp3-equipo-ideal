package equipoideal.model;

import equipoideal.model.dto.PersonaDto;

public class Persona implements Comparable<Persona> {
	private String nombre;
	private String apellido;
	private int calificacion;
	private String rol;
	private String rutaFoto;

	//TODO el rol deberia ser el mismo Enum existente, porque sino posibilita ingresar por ej Albañil y quedaria disparejo con el Enum.
	public Persona(String nombre, String apellido, int calificacion, String rol) {
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

	public String getRol() {
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
	    return new PersonaDto(nombre, apellido, calificacion,rol);
	}

}
