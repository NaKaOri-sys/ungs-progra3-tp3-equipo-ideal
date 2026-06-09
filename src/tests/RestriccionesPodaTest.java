package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import equipoideal.model.Equipo;
import equipoideal.model.Persona;
import equipoideal.util.RestriccionesPoda;
import equipoideal.util.RolEnum;

public class RestriccionesPodaTest {

	private Persona messi;
	private Persona cristiano;
	private Persona mbappe;
	private Persona ramos;
	private Persona maguire;

	private LinkedHashMap<RolEnum, Integer> requerimientos;
	private List<Persona> listaPersonas;

	@Before
	public void setUp() {
		messi = new Persona("Leo", "Messi", 5, RolEnum.LIDER, null);
		cristiano = new Persona("Cristiano", "Ronaldo", 4, RolEnum.PROGRAMADOR, null);
		mbappe = new Persona("Kylian", "Mbappe", 3, RolEnum.TESTER, null);
		ramos = new Persona("Sergio", "Ramos", 5, RolEnum.TESTER, null);
		maguire = new Persona("Harry", "Maguire", 2, RolEnum.PROGRAMADOR, null);

		listaPersonas = new ArrayList<>();
		listaPersonas.add(messi);
		listaPersonas.add(cristiano);
		listaPersonas.add(mbappe);
		listaPersonas.add(ramos);
		listaPersonas.add(maguire);

		requerimientos = new LinkedHashMap<>();
		requerimientos.put(RolEnum.LIDER, 1);
		requerimientos.put(RolEnum.PROGRAMADOR, 1);
		requerimientos.put(RolEnum.TESTER, 2);
	}

	@Test
	public void debePodar_conMejorEquipoVacioNoPoda() {
		Equipo mejorEquipo = new Equipo(new ArrayList<>());
		Equipo solucionParcial = new Equipo(new ArrayList<>());

		boolean resultado = RestriccionesPoda.debePodar(solucionParcial, 0, mejorEquipo, listaPersonas, requerimientos);

		assertFalse("No debe podar cuando aún no se encontró ninguna solución (mejor equipo vacío)",
				resultado);
	}

	@Test
	public void debePodar_cuandoElPuntajePosibleEsMayorAlMejorNoPoda() {
		List<Persona> integrantesMejor = new ArrayList<>();
		integrantesMejor.add(messi);
		integrantesMejor.add(maguire);
		Equipo mejorEquipo = new Equipo(integrantesMejor);
		Equipo solucionParcial = new Equipo(new ArrayList<>());

		boolean resultado = RestriccionesPoda.debePodar(solucionParcial, 0, mejorEquipo, listaPersonas, requerimientos);

		assertFalse("No debe podar cuando el puntaje máximo alcanzable supera al mejor equipo conocido",
				resultado);
	}

	@Test
	public void debePodar_cuandoElPuntajePosibleEsIgualAlMejorSiPoda() {
		List<Persona> integrantesMejor = new ArrayList<>();
		integrantesMejor.add(messi);
		integrantesMejor.add(cristiano);
		integrantesMejor.add(mbappe);
		integrantesMejor.add(ramos);
		Equipo mejorEquipo = new Equipo(integrantesMejor);
		Equipo solucionParcial = new Equipo(new ArrayList<>());

		boolean resultado = RestriccionesPoda.debePodar(solucionParcial, 0, mejorEquipo, listaPersonas, requerimientos);

		assertTrue("Debe podar cuando el puntaje máximo alcanzable es igual (no puede mejorar el mejor equipo)",
				resultado);
	}

	@Test
	public void calcularRemanenteMaximo_conSolucionParcialVaciaUsaTodasLasPersonas() {
		Equipo solucionParcial = new Equipo(new ArrayList<>());

		int remanente = RestriccionesPoda.calcularRemanenteMaximo(0, listaPersonas, solucionParcial, requerimientos);

		assertEquals("El remanente máximo desde índice 0 con solución parcial vacía debe ser 17",
				17, remanente);
	}

	@Test
	public void calcularRemanenteMaximo_conSolucionParcialConUnLiderNoVuelveAContarLider() {
		List<Persona> parciales = new ArrayList<>();
		parciales.add(messi);
		Equipo solucionParcial = new Equipo(parciales);

		int remanente = RestriccionesPoda.calcularRemanenteMaximo(1, listaPersonas, solucionParcial, requerimientos);

		assertEquals("Con el LIDER ya en la solución parcial, el remanente debe ser 12 (solo prog y testers)",
				12, remanente);
	}

	@Test
	public void calcularRemanenteMaximo_desdeUltimoIndiceRetornaCero() {
		Equipo solucionParcial = new Equipo(new ArrayList<>());

		int remanente = RestriccionesPoda.calcularRemanenteMaximo(listaPersonas.size(), listaPersonas, solucionParcial, requerimientos);

		assertEquals("El remanente debe ser 0 cuando ya se procesaron todas las personas",
				0, remanente);
	}

	@Test
	public void calcularRemanenteMaximo_tomaLosMasCalificadosDeEachRol() {
		Equipo solucionParcial = new Equipo(new ArrayList<>());

		int remanente = RestriccionesPoda.calcularRemanenteMaximo(0, listaPersonas, solucionParcial, requerimientos);

		assertEquals("Debe seleccionar el programador de mayor calificacion (cristiano=4, no maguire=2)",
				17, remanente);
	}
}
