package equipoideal.model.dto;

import equipoideal.util.RolEnum;

public class PersonaDto {
	private String nombre;
	private String apellido;
	private int calificacion;

	private RolEnum rol;
	private String rutaFoto;

	public PersonaDto(String nombre, String apellido, int calificacion, RolEnum rol, String rutaFoto) {

		this.nombre = nombre;
		this.apellido = apellido;
		this.calificacion = calificacion;
		this.rol = rol;
		this.rutaFoto = rutaFoto;
	}

	public String getRutaFoto() {
		return rutaFoto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getApellido() {
		return this.apellido;
	}

	public RolEnum getRol() {
		return this.rol;
	}

	public int getCalificacion() {
		return this.calificacion;
	}

	@Override
	public String toString() {
		return this.nombre + " " + this.apellido + "(" + this.rol + ")";
	}
}
