package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

import equipoideal.model.CalculadorHeuristica;
import equipoideal.model.Persona;
import equipoideal.model.dto.EquipoDto;

public class CalculadorHeuristicaTest {

	private ArrayList<Persona> personas;
	private HashMap<String, Integer> requerimientos;
	private boolean[][] incompatibilidades;

	@Before
	public void setUp() {
		personas = new ArrayList<>();
		personas.add(new Persona("Leo", "Messi", 5, "Lider"));
		personas.add(new Persona("Cristiano", "Ronaldo", 4, "Dev"));
		personas.add(new Persona("Kylian", "Mbappe", 3, "Tester"));
		
		requerimientos = new HashMap<>();
		incompatibilidades = new boolean[personas.size()][personas.size()];
	}

	@Test
	public void testHeuristica_EncuentraSolucionEnCasoFeliz() {
		requerimientos.put("Lider", 1);
		requerimientos.put("Dev", 1);
		
		var calculador = new CalculadorHeuristica(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calculador.ejecutarHeuristica();
		
		assertFalse("Debe encontrar solución", resultado.getIntegrantes().isEmpty());
	}

	@Test
	public void testHeuristica_RespetaIncompatibilidades() {
		incompatibilidades[0][1] = true;
		incompatibilidades[1][0] = true;
		
		requerimientos.put("Lider", 1);
		requerimientos.put("Dev", 1);
		
		var calculador = new CalculadorHeuristica(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calculador.ejecutarHeuristica();
		
		boolean tieneMessi = resultado.getIntegrantes().stream().anyMatch(p -> "Messi".equals(p.getApellido()));
		boolean tieneRonaldo = resultado.getIntegrantes().stream().anyMatch(p -> "Ronaldo".equals(p.getApellido()));
		
		assertFalse("No puede tener ambos incompatibles", tieneMessi && tieneRonaldo);
	}

	@Test
	public void testHeuristica_SeleccionaPersonaConMayorCalificacion() {
		requerimientos.put("Dev", 1);
		
		var calculador = new CalculadorHeuristica(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calculador.ejecutarHeuristica();
		
		assertEquals("Debe elegir al de mayor calificación", 4, resultado.getIntegrantes().get(0).getCalificacion());
	}
}