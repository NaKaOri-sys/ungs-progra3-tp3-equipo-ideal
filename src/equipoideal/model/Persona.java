package equipoideal.model;

public class Persona implements Comparable<Persona> {
	private String nombre;
	private String apellido;
	private int calificación;
	private String rol;
	private String rutaFoto;

	// TODO ver si puntos deberia ser calificación ya q es un nro entre 1 y 5
	public Persona(String nombre, String apellido, int calificación, String rol) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.calificación = calificación;
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

	// TODO ver si esto no seria getCalificacion
	public int getCalificacion() {
		return calificación;
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

}
