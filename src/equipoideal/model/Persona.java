package equipoideal.model;

public class Persona implements Comparable<Persona> {
	private String nombre;
	private String apellido;
	private int puntos;
	private String rol;

	// TODO ver si puntos deberia ser calificación ya q es un nro entre 1 y 5
	public Persona(String nombre, String apellido, int puntos, String rol) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.puntos = puntos;
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
	public int getPuntos() {
		return puntos;
	}

	@Override
	public int compareTo(Persona persona) {
		if (this.getPuntos() > persona.getPuntos())
			return 1;
		if (this.getPuntos() == persona.getPuntos())
			return 0;
		return -1;
	}

}
