package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

import equipoideal.model.CalculadorBacktracking;

public class CalculadorBacktrackingTest {

	private List<Persona> listaPersonas;
	private Map<String, Integer> requerimientosRoles;
	private boolean[][] matrizIncompatibilidades;
	private CalculadorBacktracking calculador;

	private Persona messi;
	private Persona ronaldo;
	private Persona mbappe;
	private Persona maguire;

	@Before
	public void setUp() {
		listaPersonas = new ArrayList<>();
		requerimientosRoles = new HashMap<>();

		messi = new Persona("Messi", "Lider Técnico", 5);
		ronaldo = new Persona("Ronaldo", "Desarrollador", 4);
		mbappe = new Persona("Mbappe", "Tester", 4);
		maguire = new Persona("Maguire", "Desarrollador", 2);

		listaPersonas.add(messi);
		listaPersonas.add(ronaldo);
		listaPersonas.add(mbappe);
		listaPersonas.add(maguire);

		matrizIncompatibilidades = new boolean[listaPersonas.size()][listaPersonas.size()];
	}

	private void prepararEscenarioCasoFeliz() {
		requerimientosRoles.put("Lider Técnico", 1);
		requerimientosRoles.put("Desarrollador", 1);
		requerimientosRoles.put("Tester", 1);
		calculador = new CalculadorBacktracking(listaPersonas, requerimientosRoles, matrizIncompatibilidades);
	}

	@Test
	public void testCasoFeliz_EncuentraSoluciones() {
		prepararEscenarioCasoFeliz();
		List<Equipo> resultadoTop = calculador.calcularMejoresEquipos();

		assertFalse("El podio no debería estar vacío al haber candidatos válidos", resultadoTop.isEmpty());
	}

	@Test
	public void testCasoFeliz_PuntajeMaximoEsOptimo() {
		prepararEscenarioCasoFeliz();
		List<Equipo> resultadoTop = calculador.calcularMejoresEquipos();

		Equipo primerPuesto = resultadoTop.get(0);
		assertEquals("El primer puesto debe sumar la calificación ideal (5+4+4=13)", 13,
				primerPuesto.getCalificacionTotal());
	}

	private void prepararEscenarioIncompatibilidad() {
		requerimientosRoles.put("Lider Técnico", 1);
		requerimientosRoles.put("Desarrollador", 1);

		// Messi (0) y Ronaldo (1) no pueden coexistir
		matrizIncompatibilidades[0][1] = true;
		matrizIncompatibilidades[1][0] = true;

		calculador = new CalculadorBacktracking(listaPersonas, requerimientosRoles, matrizIncompatibilidades);
	}

	@Test
	public void testIncompatibilidad_BajaElPuntajeMaximo() {
		prepararEscenarioIncompatibilidad();
		List<Equipo> resultadoTop = calculador.calcularMejoresEquipos();

		int puntajePrimero = resultadoTop.get(0).getCalificacionTotal();
		assertEquals("El puntaje ideal debe caer a 7 (Messi + Maguire) por la restricción", 7, puntajePrimero);
	}

	@Test
	public void testIncompatibilidad_NoCoexistenPersonasIncompatibles() {
		prepararEscenarioIncompatibilidad();
		List<Equipo> resultadoTop = calculador.calcularMejoresEquipos();
		List<Persona> integrantesGanadores = resultadoTop.get(0).getIntegrantes();

		boolean tienenAmbos = integrantesGanadores.contains(messi) && integrantesGanadores.contains(ronaldo);
		assertFalse("El equipo ganador no puede contener simultáneamente a dos personas marcadas como incompatibles",
				tienenAmbos);
	}

	@Test
	public void testSinSolucion_PodioVacioPorFaltaDeRoles() {
		// Pedimos 2 Testers, pero sólo cargamos 1 (Mbappe)
		requerimientosRoles.put("Tester", 2);

		calculador = new CalculadorBacktracking(listaPersonas, requerimientosRoles, matrizIncompatibilidades);
		List<Equipo> resultadoTop = calculador.calcularMejoresEquipos();

		assertTrue("Si los requerimientos mínimos de roles exceden el personal disponible, el resultado debe ser vacío",
				resultadoTop.isEmpty());
	}
}