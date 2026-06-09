package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import equipoideal.model.IncompatibleModel;
import equipoideal.model.Persona;
import equipoideal.util.RolEnum;

public class IncompatibleModelTest {

	private IncompatibleModel modelo;
	private Persona messi;
	private Persona cristiano;
	private Persona mbappe;

	@Before
	public void setUp() {
		modelo = new IncompatibleModel();
		messi = new Persona("Leo", "Messi", 5, RolEnum.LIDER, null);
		cristiano = new Persona("Cristiano", "Ronaldo", 4, RolEnum.PROGRAMADOR, null);
		mbappe = new Persona("Kylian", "Mbappe", 3, RolEnum.TESTER, null);

		List<Persona> personas = new ArrayList<>();
		personas.add(messi);
		personas.add(cristiano);
		personas.add(mbappe);
		modelo.sincronizarPersonasEnMapa(personas);
	}

	@Test(expected = IllegalArgumentException.class)
	public void registrarIncompatibilidad_personaNoRegistradaEnElSistemaLanzaExcepcion() {
		Persona foranea = new Persona("Nadie", "Conocido", 1, RolEnum.TESTER, null);
		modelo.registrarIncompatibilidad(messi, foranea);
	}

	@Test(expected = IllegalArgumentException.class)
	public void eliminarIncompatibilidad_personaNoRegistradaEnElSistemaLanzaExcepcion() {
		Persona foranea = new Persona("Nadie", "Conocido", 1, RolEnum.TESTER, null);
		modelo.eliminarIncompatibilidad(messi, foranea);
	}

	@Test(expected = IllegalArgumentException.class)
	public void sonIncompatibles_personaNoRegistradaEnElSistemaLanzaExcepcion() {
		Persona foranea = new Persona("Nadie", "Conocido", 1, RolEnum.TESTER, null);
		modelo.sonIncompatibles(messi, foranea);
	}

	@Test
	public void sincronizarPersonasEnMapa_agregaPersonasNuevasSinBorrarExistentes() {
		modelo.registrarIncompatibilidad(messi, cristiano);

		Persona nueva = new Persona("Harry", "Maguire", 2, RolEnum.PROGRAMADOR, null);
		List<Persona> personasActualizadas = new ArrayList<>();
		personasActualizadas.add(messi);
		personasActualizadas.add(cristiano);
		personasActualizadas.add(mbappe);
		personasActualizadas.add(nueva);

		modelo.sincronizarPersonasEnMapa(personasActualizadas);

		assertTrue("La incompatibilidad previa debe seguir existiendo después de sincronizar",
				modelo.sonIncompatibles(messi, cristiano));
	}

	@Test
	public void sincronizarPersonasEnMapa_personaNuevaQuedaRegistradaEnElMapa() {
		Persona nueva = new Persona("Harry", "Maguire", 2, RolEnum.PROGRAMADOR, null);
		List<Persona> actualizadas = new ArrayList<>();
		actualizadas.add(messi);
		actualizadas.add(cristiano);
		actualizadas.add(mbappe);
		actualizadas.add(nueva);

		modelo.sincronizarPersonasEnMapa(actualizadas);
		modelo.registrarIncompatibilidad(nueva, messi);

		assertTrue("La persona nueva debe poder participar en incompatibilidades tras sincronizar",
				modelo.sonIncompatibles(nueva, messi));
	}

	@Test
	public void registrarIncompatibilidad_ambosQuedaronMarcadosComoBidireccional() {
		modelo.registrarIncompatibilidad(messi, cristiano);

		assertTrue("Messi debe aparecer como incompatible con Cristiano", modelo.sonIncompatibles(messi, cristiano));
		assertTrue("Cristiano debe aparecer como incompatible con Messi (bidireccional)",
				modelo.sonIncompatibles(cristiano, messi));
	}

	@Test
	public void registrarIncompatibilidad_noAfectaATerceros() {
		modelo.registrarIncompatibilidad(messi, cristiano);

		assertFalse("Mbappe no debe verse afectado por la incompatibilidad entre Messi y Cristiano",
				modelo.sonIncompatibles(messi, mbappe));
	}

	@Test
	public void eliminarIncompatibilidad_ambosDejandeDeSonIncompatibles() {
		modelo.registrarIncompatibilidad(messi, cristiano);
		modelo.eliminarIncompatibilidad(messi, cristiano);

		assertFalse("Messi no debe ser incompatible con Cristiano después de eliminar",
				modelo.sonIncompatibles(messi, cristiano));
		assertFalse("Cristiano no debe ser incompatible con Messi después de eliminar (bidireccional)",
				modelo.sonIncompatibles(cristiano, messi));
	}

	@Test
	public void eliminarIncompatibilidad_noAfectaOtrasIncompatibilidades() {
		modelo.registrarIncompatibilidad(messi, cristiano);
		modelo.registrarIncompatibilidad(messi, mbappe);
		modelo.eliminarIncompatibilidad(messi, cristiano);

		assertTrue("La incompatibilidad entre Messi y Mbappe debe mantenerse tras eliminar la de Cristiano",
				modelo.sonIncompatibles(messi, mbappe));
	}

	@Test
	public void sonIncompatibles_dosPersonasSinIncompatibilidadRetornaFalse() {
		assertFalse("Dos personas sin incompatibilidad registrada no deben ser incompatibles",
				modelo.sonIncompatibles(messi, cristiano));
	}

	@Test
	public void hayIncompatibilidades_sinNingunaRegistradaRetornaFalse() {
		assertFalse("Un modelo recién inicializado no debe tener incompatibilidades reales",
				modelo.hayIncompatibilidades());
	}

	@Test
	public void hayIncompatibilidades_conUnaRegistradaRetornaTrue() {
		modelo.registrarIncompatibilidad(messi, cristiano);

		assertTrue("Debe retornar true cuando hay al menos una incompatibilidad registrada",
				modelo.hayIncompatibilidades());
	}

	@Test
	public void hayIncompatibilidades_despuesDeEliminarLaUnicaRegistradaRetornaFalse() {
		modelo.registrarIncompatibilidad(messi, cristiano);
		modelo.eliminarIncompatibilidad(messi, cristiano);

		assertFalse("Debe retornar false cuando se eliminó la única incompatibilidad existente",
				modelo.hayIncompatibilidades());
	}
}
