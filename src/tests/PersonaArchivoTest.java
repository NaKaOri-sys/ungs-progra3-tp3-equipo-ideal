package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import equipoideal.model.Persona;
import equipoideal.model.dao.PersonaArchivo;
import equipoideal.util.RolEnum;

public class PersonaArchivoTest {
	private ArrayList<Persona> personas;

	File destino;
	@Rule
	public TemporaryFolder carpetaTemporal = new TemporaryFolder();
	
	@Before
	public void setUp() throws IOException {
		personas = new ArrayList<>();
		
		personas.add(new Persona("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "ruta"));
		personas.add(new Persona("Cristiano", "Ronaldo", 4, RolEnum.ARQUITECTO, "ruta"));
		personas.add(new Persona("Kylian", "Mbappe", 4, RolEnum.TESTER, "ruta"));
		
		destino = carpetaTemporal.newFile("destino.json");
	}
	
	@Test
	public void generarJsonPersonaTest() throws IOException {
		File archivoTemporal = carpetaTemporal.newFile("JSON_Nuevo.json");

		PersonaArchivo.exportarArchivoJSON(archivoTemporal.getAbsolutePath(), personas);

		assertTrue(archivoTemporal.exists());
		assertTrue(archivoTemporal.length() > 0);
	}
	
	@Test
	public void cargarJsonPersonaTest() throws IOException {
		File archivoTemporal = carpetaTemporal.newFile("JSON_Nuevo.json");

		PersonaArchivo.exportarArchivoJSON(archivoTemporal.getAbsolutePath(), personas);

		ArrayList<Persona> personasEnArchivo = PersonaArchivo.cargarJSON(archivoTemporal.getAbsolutePath());

		assertEquals(3, personasEnArchivo.size());
		assertEquals("Leo", personasEnArchivo.get(0).getNombre());
		assertEquals("Cristiano", personasEnArchivo.get(1).getNombre());
	}
	
	@Test
	public void exportarArchivoJsonTest() throws IOException {
		File destinoExportar = carpetaTemporal.newFile("destino_exportar.json");
		
		
		PersonaArchivo.exportarArchivoJSON(destinoExportar.getAbsolutePath(), personas);
		
		assertTrue(destinoExportar.exists());
		assertTrue(destinoExportar.length() > 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void generarJsonConRutaVaciaTest() {
		PersonaArchivo.exportarArchivoJSON("", personas);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void generarJsonConListaVaciaTest() {
		PersonaArchivo.exportarArchivoJSON("archivo.json", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cargarJsonArchivoInexistenteTest() {
		PersonaArchivo.cargarJSON("archivo_que_no_existe.json");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cargarJsonConRutaVaciaTest() {
		PersonaArchivo.cargarJSON("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cargarJsonConSintaxisInvalidaTest() throws IOException {
		File archivoTemporal = carpetaTemporal.newFile("invalido.json");
		
		FileWriter writer = new FileWriter(archivoTemporal);
		writer.write("{ JSON INVALIDO }");
		writer.close();
		
		PersonaArchivo.cargarJSON(archivoTemporal.getAbsolutePath());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cargarJsonConPersonaInvalidaTest() throws IOException {
		File archivoTemporal = carpetaTemporal.newFile("invalido.json");
		
		FileWriter writer = new FileWriter(archivoTemporal);
		writer.write("[{\"nombre\":\"Leo\",\"apellido\":\"Messi\",\"calificacion\":5,\"rol\":\"Jugador\"}]");
		writer.close();
		
		PersonaArchivo.cargarJSON(archivoTemporal.getAbsolutePath());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cargarJsonArchivoTotalmenteVacioTest() throws IOException {
		File archivoVacio = carpetaTemporal.newFile("vacio.json");
		
		PersonaArchivo.cargarJSON(archivoVacio.getAbsolutePath());
	}
}