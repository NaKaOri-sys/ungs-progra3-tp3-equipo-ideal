package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import equipoideal.model.CalculadorHeuristica;
import equipoideal.model.Persona;
import equipoideal.model.dto.EquipoDto;
import equipoideal.util.RolEnum;

public class CalculadorHeuristicaTest {

	private ArrayList<Persona> personas;
	private LinkedHashMap<RolEnum, Integer> requerimientos;
	private Map<Persona, Set<Persona>> incompatibilidades;
	private CalculadorHeuristica calc;
	private Persona messi;
	private Persona cristiano;
	private Persona mbappe;
	private Persona maguire;
	private Persona ramos;

	@Before
	public void setUp() {
		personas = new ArrayList<>();
		messi = new Persona("Leo", "Messi", 5, RolEnum.LIDER, null);
		cristiano = new Persona("Cristiano", "Ronaldo", 4, RolEnum.PROGRAMADOR, null);
		mbappe = new Persona("Kylian", "Mbappe", 3, RolEnum.TESTER, null);
		maguire = new Persona("Harry", "Maguire", 2, RolEnum.PROGRAMADOR, null);
		ramos = new Persona("Sergio", "Ramos", 5, RolEnum.TESTER, null);

		personas.add(messi);
		personas.add(cristiano);
		personas.add(mbappe);
		personas.add(maguire);
		personas.add(ramos);

		requerimientos = new LinkedHashMap<RolEnum, Integer>();
		requerimientos.put(RolEnum.LIDER, 1);
		requerimientos.put(RolEnum.PROGRAMADOR, 1);
		requerimientos.put(RolEnum.TESTER, 2);

		incompatibilidades = new HashMap<Persona, Set<Persona>>();
		for (Persona persona : personas) {
			incompatibilidades.put(persona, new HashSet<Persona>());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHeuristica_listaPersonasVacia() {
		new CalculadorHeuristica(new ArrayList<>(), requerimientos, incompatibilidades);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHeuristica_listaRequerimientosVacia() {
		new CalculadorHeuristica(personas, new LinkedHashMap<RolEnum, Integer>(), incompatibilidades);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHeuristica_matrizIncompatibilidadesDistintoTamanioListaPersonas() {
		incompatibilidades.put(new Persona("Pablito", "Clavo un Clavito", 2, RolEnum.ARQUITECTO, null),
				new HashSet<Persona>());
		new CalculadorHeuristica(personas, requerimientos, incompatibilidades);
	}

	@Test
	public void testHeuristica_EncuentraSolucionEnCasoFeliz() {
		calc = new CalculadorHeuristica(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.ejecutarHeuristica();
		assertFalse("Debe encontrar solución", resultado.getIntegrantes().isEmpty());
	}

	@Test
	public void testHeuristica_RespetaIncompatibilidades() {
		// Registrar bidireccional, igual que lo hace IncompatibleModel.registrarIncompatibilidad
		Set<Persona> incompatiblesConMessi = new HashSet<Persona>();
		incompatiblesConMessi.add(cristiano);
		incompatibilidades.put(messi, incompatiblesConMessi);

		Set<Persona> incompatiblesConCristiano = new HashSet<Persona>();
		incompatiblesConCristiano.add(messi);
		incompatibilidades.put(cristiano, incompatiblesConCristiano);

		calc = new CalculadorHeuristica(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.ejecutarHeuristica();

		boolean tieneRonaldo = resultado.getIntegrantes().stream().anyMatch(p -> "Ronaldo".equals(p.getApellido()));
		assertFalse("No debe estar Ronaldo en el equipo al ser incompatible con Messi", tieneRonaldo);
	}

	@Test
	public void testHeuristica_SeleccionaPersonaConMayorCalificacion() {
		calc = new CalculadorHeuristica(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.ejecutarHeuristica();

		assertEquals("Debe elegir al de mayor calificación", 5, resultado.getIntegrantes().get(0).getCalificacion());
	}
}
