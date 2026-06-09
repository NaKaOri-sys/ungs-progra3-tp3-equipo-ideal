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

import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.Persona;
import equipoideal.model.dto.EquipoDto;
import equipoideal.model.dto.PersonaDto;
import equipoideal.util.RolEnum;

public class CalculadorBacktrackingTest {

	private ArrayList<Persona> personas;
	private LinkedHashMap<RolEnum, Integer> requerimientos;
	private Map<Persona, Set<Persona>> incompatibilidades;
	private CalculadorBacktracking calc;
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
	public void testBacktracking_listaPersonasVacia() {
		new CalculadorBacktracking(new ArrayList<>(), requerimientos, incompatibilidades);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBacktracking_listaRequerimientosVacia() {
		new CalculadorBacktracking(personas, new LinkedHashMap<RolEnum, Integer>(), incompatibilidades);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBacktracking_incompatibilidadesDistintoTamanioListaPersonas() {
		incompatibilidades.put(new Persona("Pablito", "Clavo un Clavito", 2, RolEnum.ARQUITECTO, null),
				new HashSet<Persona>());
		new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
	}

	@Test(expected = RuntimeException.class)
	public void testBacktracking_SinSolucion() {
		requerimientos.put(RolEnum.TESTER, 3);
		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		calc.calcularMejorEquipo();
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
		Set<Persona> incompatibleMaguire = new HashSet<Persona>();
		incompatibleMaguire.add(maguire);

		incompatibilidades.put(cristiano, incompatibleMaguire);

		calc = new CalculadorBacktracking(personas, requerimientos, incompatibilidades);
		EquipoDto resultado = calc.calcularMejorEquipo();

		boolean tieneMaguire = resultado.getIntegrantes().stream().anyMatch(p -> "Maguire".equals(p.getApellido()));
		assertFalse("No debe estar Maguire en el equipo al ser incompatible con cristiano", tieneMaguire);
	}

}