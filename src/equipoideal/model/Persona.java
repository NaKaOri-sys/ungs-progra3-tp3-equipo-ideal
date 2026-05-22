package equipoideal.model;

public class Persona {
	private String nombre;
	private String apellido;
	private int puntos;
	private String rol;
	
	public Persona(String nombre, String apellido, int puntos, String rol) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.puntos = puntos;
		this.rol = rol;
	}

	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}

	public String getApellido() {
		// TODO Auto-generated method stub
		return apellido;
	}

	public String getRol() {
		return rol;
	}

	public int getPuntos() {
		return puntos;
	}

	
}
