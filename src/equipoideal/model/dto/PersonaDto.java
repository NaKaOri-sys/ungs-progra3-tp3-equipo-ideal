package equipoideal.model.dto;

public class PersonaDto {
	private String nombre;
	private String apellido;
	private int calificacion;
	private String rol;

	public PersonaDto(String nombre, String apellido, int calificacion, String rol) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.calificacion = calificacion;
		this.rol = rol;
	}

	public String getRutaFoto() {
		return null;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public String getApellido() {
		return this.apellido;
	}

	public String getRol() {
		return this.rol;
	}

	public int getCalificacion() {
		return this.calificacion;
	}
}
