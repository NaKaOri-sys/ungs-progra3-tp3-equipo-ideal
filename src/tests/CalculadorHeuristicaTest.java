package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.CalculadorHeuristica;
import equipoideal.model.Persona;
import equipoideal.model.Requerimiento;
import equipoideal.model.dto.EquipoDto;
import equipoideal.util.RolEnum;

public class CalculadorHeuristicaTest {

	private ArrayList<Persona> personas;
	private List<Requerimiento> requerimientos;
	private boolean[][] incompatibilidades;
	private CalculadorHeuristica calc;
	private Requerimiento lider;
	private Requerimiento dev;
	private Requerimiento qa;

	@Before
	public void setUp() {
		personas = new ArrayList<>();
		personas.add(new Persona("Leo", "Messi", 5, "LIDER"));
		personas.add(new Persona("Cristiano", "Ronaldo", 4, "PROGRAMADOR"));
		personas.add(new Persona("Kylian", "Mbappe", 3, "TESTER"));
		personas.add(new Persona("Harry", "Maguire", 2, "PROGRAMADOR"));

		requerimientos = new ArrayList<>();
		lider = new Requerimiento(RolEnum.LIDER, 1);
		dev = new Requerimiento(RolEnum.PROGRAMADOR, 1);
		qa = new Requerimiento(RolEnum.TESTER, 2);
		incompatibilidades = new boolean[personas.size()][personas.size()];
	}

	@Test
	public void testHeuristica_EncuentraSolucionEnCasoFeliz() {
		requerimientos.add(lider);
		requerimientos.add(dev);

		calc = new CalculadorHeuristica(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.ejecutarHeuristica();

		assertFalse("Debe encontrar solución", resultado.getIntegrantes().isEmpty());
	}

	@Test
	public void testHeuristica_RespetaIncompatibilidades() {
		incompatibilidades[0][1] = true;
		incompatibilidades[1][0] = true;

		requerimientos.add(lider);
		requerimientos.add(dev);

		calc = new CalculadorHeuristica(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.ejecutarHeuristica();

		boolean tieneMessi = resultado.getIntegrantes().stream().anyMatch(p -> "Messi".equals(p.getApellido()));
		boolean tieneRonaldo = resultado.getIntegrantes().stream().anyMatch(p -> "Ronaldo".equals(p.getApellido()));

		assertFalse("No puede tener ambos incompatibles", tieneMessi && tieneRonaldo);
	}

	@Test
	public void testHeuristica_SeleccionaPersonaConMayorCalificacion() {
		requerimientos.add(dev);

		calc = new CalculadorHeuristica(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.ejecutarHeuristica();

		assertEquals("Debe elegir al de mayor calificación", 4, resultado.getIntegrantes().get(0).getCalificacion());
	}
}