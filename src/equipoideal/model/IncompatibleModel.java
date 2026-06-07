package equipoideal.model;

public class IncompatibleModel {
	private boolean[][] matrizIncompatibilidades;

	public IncompatibleModel(int cantidadPersonas) {
		this.matrizIncompatibilidades = new boolean[cantidadPersonas][cantidadPersonas];
	}

	// Cuando el NavigationController abre mi pantalla, llamo a esto para actualizar el tamaño
	public void actualizarTamañoMatriz(int cantidadActual) {
		if (cantidadActual > matrizIncompatibilidades.length) {
			boolean[][] nuevaMatriz = new boolean[cantidadActual][cantidadActual];
			// Copia lo que ya teníaa marcado para no perder los datos previos
			for (int i = 0; i < matrizIncompatibilidades.length; i++) {
				System.arraycopy(matrizIncompatibilidades[i], 0, nuevaMatriz[i], 0, matrizIncompatibilidades[i].length);
			}
			this.matrizIncompatibilidades = nuevaMatriz;
		}
	}

	// Registra la incompatibilidad de forma directa usando los índices de la pantalla.
	// es bidireccional, osea si A es incompatible con B, B lo es con A
	public void registrarIncompatibilidad(int indiceEmpleadoA, int indiceEmpleadoB) {
		if (indiceEmpleadoA >= 0 && indiceEmpleadoA < matrizIncompatibilidades.length && indiceEmpleadoB >= 0
				&& indiceEmpleadoB < matrizIncompatibilidades.length) {

			matrizIncompatibilidades[indiceEmpleadoA][indiceEmpleadoB] = true;
			matrizIncompatibilidades[indiceEmpleadoB][indiceEmpleadoA] = true;
		}
	}

	public boolean sonIncompatibles(int indiceEmpleadoA, int indiceEmpleadoB) {
		if (indiceEmpleadoA >= matrizIncompatibilidades.length || indiceEmpleadoB >= matrizIncompatibilidades.length)
			return false;
		return matrizIncompatibilidades[indiceEmpleadoA][indiceEmpleadoB];
	}
	
	public boolean tieneIncompatibilidades() {
	    if (this.matrizIncompatibilidades == null) {
	        return false;
	    }
	    for (int i = 0; i < matrizIncompatibilidades.length; i++) {
	        for (int j = 0; j < matrizIncompatibilidades[i].length; j++) {
	            if (matrizIncompatibilidades[i][j] == true) { 
	                return true; 
	            }
	        }
	    }
	    return false; 
	}

	public boolean[][] getMatrizIncompatibilidades() {
		return matrizIncompatibilidades;
	}
}