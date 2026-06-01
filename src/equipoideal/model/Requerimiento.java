package equipoideal.model;

import equipoideal.util.RolEnum;

public class Requerimiento {
	private RolEnum rol;
	private int cantidad;
	
	public Requerimiento(RolEnum rol, int cantidad) {
		this.rol = rol;
		this.cantidad = cantidad;
	}
	
	public RolEnum getRol() {
		return rol;
	}
	
	public int getCantidad() {
		return cantidad;
	}

}
