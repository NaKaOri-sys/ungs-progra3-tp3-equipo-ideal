package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.Persona;
import equipoideal.model.dto.EquipoDto;

public class CalculadorBacktrackingTest {

	private ArrayList<Persona> personas;
	private HashMap<String, Integer> requerimientos;
	private boolean[][] incompatibilidades;
	private CalculadorBacktracking calc;

	@Before
	public void setUp() {
		personas = new ArrayList<>();
		personas.add(new Persona("Leo", "Messi", 5, "Lider"));
		personas.add(new Persona("Cristiano", "Ronaldo", 4, "Dev"));
		personas.add(new Persona("Kylian", "Mbappe", 3, "Tester"));
		personas.add(new Persona("Harry", "Maguire", 2, "Dev"));
		
		requerimientos = new HashMap<>();
		incompatibilidades = new boolean[personas.size()][personas.size()];
	}

	@Test
	public void testBacktracking_EncuentraSolucion() {
		requerimientos.put("Lider", 1);
		requerimientos.put("Dev", 1);
		
		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.calcularMejorEquipo();
		
		assertFalse("Debe encontrar solución", resultado.getIntegrantes().isEmpty());
	}

	@Test
	public void testBacktracking_SolucionEsOptima() {
		requerimientos.put("Lider", 1);
		requerimientos.put("Dev", 1);
		
		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.calcularMejorEquipo();
		
		int total = resultado.getIntegrantes().stream().mapToInt(p -> p.getCalificacion()).sum();
		assertEquals("Debe sumar 9 (5+4)", 9, total);
	}

	@Test
	public void testBacktracking_RespetaIncompatibilidades() {
		incompatibilidades[0][1] = true;
		incompatibilidades[1][0] = true;
		
		requerimientos.put("Lider", 1);
		requerimientos.put("Dev", 1);
		
		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.calcularMejorEquipo();
		
		boolean tieneMessi = resultado.getIntegrantes().stream().anyMatch(p -> "Messi".equals(p.getApellido()));
		boolean tieneRonaldo = resultado.getIntegrantes().stream().anyMatch(p -> "Ronaldo".equals(p.getApellido()));
		
		assertFalse("No puede tener ambos incompatibles", tieneMessi && tieneRonaldo);
	}

	@Test
	public void testBacktracking_SinSolucion() {
		requerimientos.put("Lider", 1);
		requerimientos.put("Dev", 1);
		requerimientos.put("Tester", 2);  // Solo hay 1 Tester
		
		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.calcularMejorEquipo();
		
		assertTrue("Debe estar vacío sin solución", resultado.getIntegrantes().isEmpty());
	}
}