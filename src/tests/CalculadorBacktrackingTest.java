package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import equipoideal.model.CalculadorBacktracking;

public class CalculadorBacktrackingTest {

	private Map<String, Persona> personas;
	private CalculadorBacktracking calculador;
	private List<Equipo> resultadoTop;

	@Before
	public void setUp() {
		personas = new LinkedHashMap<>();
		personas.put("1233531", new Persona("Messi", 99, "Lider Técnico", 100));
		personas.put("1111111", new Persona("Ronaldo", 90, "Desarrollador", 90));
		personas.put("1222222", new Persona("Mbappe", 85, "Tester", 80));
		personas.put("2222222", new Persona("Maguire", 40, "Desarrollador", 50));

		calculador = new CalculadorBacktracking(personas);
		resultadoTop = calculador.calcularMejoresEquipos();
	}

	@Test
	public void testPodioNoExcedeElMaximoDeTresElementos() {
		assertEquals("El podio debe tener exactamente 3 equipos", 3, resultadoTop.size());
	}

	@Test
	public void testPrimerPuestoEsElOptimoAbsoluto() {
		int puntajePrimero = resultadoTop.get(0).getCalificacionTotal();
		assertEquals("El primer puesto debe ser el óptimo absoluto (189 pts)", 189, puntajePrimero);
	}

	@Test
	public void testSegundoPuestoTieneElPuntajeEsperado() {
		int puntajeSegundo = resultadoTop.get(1).getCalificacionTotal();
		assertEquals("El segundo puesto debe tener 184 pts", 184, puntajeSegundo);
	}

	@Test
	public void testTercerPuestoTieneElPuntajeEsperado() {
		int puntajeTercero = resultadoTop.get(2).getCalificacionTotal();
		assertEquals("El tercer puesto debe tener 175 pts", 175, puntajeTercero);
	}

	@Test
	public void testElPodioMantieneOrdenDescendienteEstricto() {
		int puntajePrimero = resultadoTop.get(0).getCalificacionTotal();
		int puntajeSegundo = resultadoTop.get(1).getCalificacionTotal();
		int puntajeTercero = resultadoTop.get(2).getCalificacionTotal();

		assertTrue("El orden debe ser estrictamente descendiente",
				puntajePrimero >= puntajeSegundo && puntajeSegundo >= puntajeTercero);
	}
}