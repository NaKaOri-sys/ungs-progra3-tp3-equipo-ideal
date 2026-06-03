package equipoideal.model.dto;

public class PersonaDto {
	private String nombre;
	private String apellido;
	private int calificacion;
	private String rol;
	private String rutaFoto;

	public PersonaDto(String nombre, String apellido, int calificacion, String rol, String rutaFoto) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.calificacion = calificacion;
		this.rol = rol;
		this.rutaFoto = rutaFoto;
	}

	public String getRutaFoto() {
		return this.rutaFoto;
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
