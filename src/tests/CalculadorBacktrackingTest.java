package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.junit.Before;
import org.junit.Test;

import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.Persona;
import equipoideal.model.dto.EquipoDto;
import equipoideal.util.RolEnum;

public class CalculadorBacktrackingTest {

	private ArrayList<Persona> personas;
	private LinkedHashMap<RolEnum, Integer> requerimientos;
	private boolean[][] incompatibilidades;
	private CalculadorBacktracking calc;

	@Before
	public void setUp() {
		personas = new ArrayList<>();
		personas.add(new Persona("Leo", "Messi", 5, RolEnum.LIDER));
		personas.add(new Persona("Cristiano", "Ronaldo", 4, RolEnum.PROGRAMADOR));
		personas.add(new Persona("Kylian", "Mbappe", 3, RolEnum.TESTER));
		personas.add(new Persona("Harry", "Maguire", 2, RolEnum.PROGRAMADOR));
		personas.add(new Persona("Sergio", "Ramos", 5, RolEnum.TESTER));

		requerimientos = new LinkedHashMap<RolEnum, Integer>();
		requerimientos.put(RolEnum.LIDER, 1);
		requerimientos.put(RolEnum.PROGRAMADOR, 1);
		requerimientos.put(RolEnum.TESTER, 2);
		
		incompatibilidades = new boolean[personas.size()][personas.size()];
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBacktracking_listaPersonasVacia() {
		new CalculadorBacktracking(new ArrayList<>(), requerimientos, incompatibilidades);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBacktracking_listaRequerimientosVacia() {
		new CalculadorBacktracking(personas, new LinkedHashMap<RolEnum, Integer>(), incompatibilidades);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBacktracking_matrizIncompatibilidadesVacia() {
		new CalculadorBacktracking(personas, requerimientos, new boolean[0][0]);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBacktracking_matrizIncompatibilidadesDistintoTamanioListaPersonas() {
		new CalculadorBacktracking(personas, requerimientos, new boolean[personas.size() - 1][personas.size() - 1]);
	}

	@Test
	public void testBacktracking_SolucionEsOptima() {
		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.calcularMejorEquipo();

		int total = resultado.getIntegrantes().stream().mapToInt(p -> p.getCalificacion()).sum();
		assertEquals("Debe sumar 17 (lider 5, los dos tester 8 y el dev 4", 17, total);
	}

	@Test
	public void testBacktracking_RespetaIncompatibilidades() {
		incompatibilidades[0][1] = true;
		incompatibilidades[1][0] = true;

		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.calcularMejorEquipo();

		boolean tieneMessi = resultado.getIntegrantes().stream().anyMatch(p -> "Messi".equals(p.getApellido()));
		boolean tieneRonaldo = resultado.getIntegrantes().stream().anyMatch(p -> "Ronaldo".equals(p.getApellido()));

		assertFalse("No puede tener ambos incompatibles", tieneMessi && tieneRonaldo);
	}

	@Test
	public void testBacktracking_SinSolucion() {
		requerimientos.put(RolEnum.TESTER, 3);
		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.calcularMejorEquipo();

		assertTrue("Debe estar vacío sin solución", resultado.getIntegrantes().isEmpty());
	}
}