package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.Persona;
import equipoideal.model.Requerimiento;
import equipoideal.model.dto.EquipoDto;
import equipoideal.util.RolEnum;

public class CalculadorBacktrackingTest {

	private ArrayList<Persona> personas;
	private List<Requerimiento> requerimientos;
	private boolean[][] incompatibilidades;
	private CalculadorBacktracking calc;
	private Requerimiento lider;
	private Requerimiento dev;
	private Requerimiento qa;

	@Before
	public void setUp() {
		personas = new ArrayList<>();
		personas.add(new Persona("Leo", "Messi", 5, RolEnum.LIDER));
		personas.add(new Persona("Cristiano", "Ronaldo", 4, RolEnum.PROGRAMADOR));
		personas.add(new Persona("Kylian", "Mbappe", 3, RolEnum.TESTER));
		personas.add(new Persona("Harry", "Maguire", 2, RolEnum.PROGRAMADOR));

		requerimientos = new ArrayList<>();
		lider = new Requerimiento(RolEnum.LIDER, 1);
		dev = new Requerimiento(RolEnum.PROGRAMADOR, 1);
		qa = new Requerimiento(RolEnum.TESTER, 2);
		incompatibilidades = new boolean[personas.size()][personas.size()];
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBacktracking_listaPersonasVacia() {
		new CalculadorBacktracking(new ArrayList<>(), requerimientos, incompatibilidades);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBacktracking_listaRequerimientosVacia() {
		new CalculadorBacktracking(personas, new ArrayList<>(), incompatibilidades);
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
	public void testBacktracking_EncuentraSolucion() {
		requerimientos.add(lider);
		requerimientos.add(dev);

		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.calcularMejorEquipo();

		assertFalse("Debe encontrar solución", resultado.getIntegrantes().isEmpty());
	}

	@Test
	public void testBacktracking_SolucionEsOptima() {
		requerimientos.add(lider);
		requerimientos.add(dev);

		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.calcularMejorEquipo();

		int total = resultado.getIntegrantes().stream().mapToInt(p -> p.getCalificacion()).sum();
		assertEquals("Debe sumar 9 (5+4)", 9, total);
	}

	@Test
	public void testBacktracking_RespetaIncompatibilidades() {
		incompatibilidades[0][1] = true;
		incompatibilidades[1][0] = true;

		requerimientos.add(lider);
		requerimientos.add(dev);

		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.calcularMejorEquipo();

		boolean tieneMessi = resultado.getIntegrantes().stream().anyMatch(p -> "Messi".equals(p.getApellido()));
		boolean tieneRonaldo = resultado.getIntegrantes().stream().anyMatch(p -> "Ronaldo".equals(p.getApellido()));

		assertFalse("No puede tener ambos incompatibles", tieneMessi && tieneRonaldo);
	}

	@Test
	public void testBacktracking_SinSolucion() {
		requerimientos.add(lider);
		requerimientos.add(dev);
		requerimientos.add(qa); // Solo hay 1 Tester

		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.calcularMejorEquipo();

		assertTrue("Debe estar vacío sin solución", resultado.getIntegrantes().isEmpty());
	}
}