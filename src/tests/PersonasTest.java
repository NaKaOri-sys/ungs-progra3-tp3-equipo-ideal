package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import equipoideal.model.Persona;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.repository.PersonaRepository;
import equipoideal.model.repository.PersonaRepositoryJson;
import equipoideal.util.PersonaValidator;
import equipoideal.util.RolEnum;

public class PersonasTest {
	private ArrayList<Persona> personas;
	private Persona persona1;
	private Persona persona2;
	private Persona persona3;
	
	private PersonaDialogModel modelo;
	private PersonaRepository personaRepository;
	private File archivoJsonTemporal;
	private File carpetaFotosTemporal;
	
	@Rule
	public TemporaryFolder carpetaTemporal = new TemporaryFolder();
	
	@Before
	public void setUp() throws IOException {
		
		this.archivoJsonTemporal = carpetaTemporal.newFile("personas_test.json");
		this.carpetaFotosTemporal = carpetaTemporal.newFolder("carpeta_fotos");
		this.personaRepository = new PersonaRepositoryJson(archivoJsonTemporal.getAbsolutePath());
		this.modelo = new PersonaDialogModel(personaRepository, carpetaFotosTemporal.getAbsolutePath());
		
		personas = new ArrayList<>();
		persona1 = new Persona("Leo", "Messi", 5, RolEnum.PROGRAMADOR);
		persona2 = new Persona("Cristiano", "Ronaldo", 4, RolEnum.ARQUITECTO);
		persona3 = new Persona("Kylian", "Mbappe", 4, RolEnum.TESTER);
		
		personas.add(persona1);
		personas.add(persona2);
		personas.add(persona3);
	}
		
	//TEST CLASE PERSONA
	@Test
	public void crearPersonaTest() {
		assertEquals("Leo", persona1.getNombre());

	    assertEquals("Messi", persona1.getApellido());

	    assertEquals(5, persona1.getCalificacion());

	    assertEquals(RolEnum.PROGRAMADOR, persona1.getRol());
	}
	
	@Test
	public void compareToMayorTest() {
		assertEquals(1, persona1.compareTo(persona2));
	}
	
	@Test
	public void compareToMenorTest() {
		assertEquals(-1, persona2.compareTo(persona1));
	}
	
	@Test
	public void compareToIgualTest() {
		assertEquals(0, persona2.compareTo(persona3));
	}
	
	@Test
	public void crearDtoTest() {
		PersonaDto dto = persona1.toDto();

	    assertEquals("Leo", dto.getNombre());

	    assertEquals("Messi", dto.getApellido());

	    assertEquals(5, dto.getCalificacion());

	    assertEquals("PROGRAMADOR", dto.getRol());
	}
	
	//Test PersonaDialogModel
	
	@Test
	public void agregarPersonaTest() {
		modelo.agregarPersona("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "rutaFoto");
		Persona p = modelo.getListaPersonas().get(0);
		
		assertEquals(1, modelo.getListaPersonas().size());

	    assertEquals("Leo", p.getNombre());

	    assertEquals("Messi", p.getApellido());

	    assertEquals(5, p.getCalificacion());

	    assertEquals(RolEnum.PROGRAMADOR, p.getRol());
	    
	    assertEquals("rutaFoto", p.getRutaFoto());
	}
	
	@Test
	public void guardarEnJsonTest() {
		modelo.agregarPersona("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "rutaFoto");
		modelo.agregarPersona("Kylian", "Mbappe", 4, RolEnum.TESTER, "rutaFoto2");
		modelo.guardarPersonaEnJSON();
		
		ArrayList<Persona> personasGuardadas = personaRepository.loadAll(archivoJsonTemporal.getAbsolutePath());
		
		assertEquals(2, personasGuardadas.size());
		
		Persona primera = personasGuardadas.get(0);
	    assertEquals("Leo", primera.getNombre());
	    assertEquals("Messi", primera.getApellido());
	    assertEquals(5, primera.getCalificacion());
	    assertEquals(RolEnum.PROGRAMADOR, primera.getRol());
		
	}
	
	@Test
	public void cargarPersonasDesdeJsonTest() {
		personaRepository.saveAll(personas);
		modelo.cargarDesdeJSON(archivoJsonTemporal.getAbsolutePath());
		
		ArrayList<Persona> personasGuardadas = modelo.getListaPersonas();		
		Persona primera = personasGuardadas.get(0);
		
		assertEquals("Leo", primera.getNombre());
	    assertEquals("Messi", primera.getApellido());
	    assertEquals(5, primera.getCalificacion());
	    assertEquals(RolEnum.PROGRAMADOR, primera.getRol());
	}

	@Test
	public void guardarFotoTest() throws IOException {
		File fotoOrigen = carpetaTemporal.newFile("origen.jpg");
		
		String rutaDestino = modelo.guardarFoto(fotoOrigen);
		File archivoCopiado = new File(rutaDestino);
		
		
		assertEquals(carpetaFotosTemporal.getAbsolutePath(), archivoCopiado.getParentFile().getAbsolutePath());
		
	}
	

	@Test
	public void exportarJsonTest() {
	modelo.agregarPersona("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "rutaFoto");
	modelo.guardarPersonaEnJSON();

	File destino = new File(carpetaTemporal.getRoot(), "exportado.json");

	modelo.exportarJson(destino.getAbsolutePath());

	assertEquals(true, destino.exists());
	assertEquals(true, destino.length() > 0);

	}

	@Test
	public void limpiarCacheFotosTest() throws IOException {
	File foto1 = new File(carpetaFotosTemporal, "foto1.jpg");
	File foto2 = new File(carpetaFotosTemporal, "foto2.jpg");

	foto1.createNewFile();
	foto2.createNewFile();

	modelo.limpiarCacheFotos();

	assertEquals(0, carpetaFotosTemporal.listFiles().length);

	}
	
	//TEST ERRORES
	
	@Test(expected = IllegalArgumentException.class)
	public void personaConNombreVacioTest() {
	modelo.agregarPersona("", "Messi", 5, RolEnum.PROGRAMADOR, "rutaFoto");
	}

	@Test(expected = IllegalArgumentException.class)
	public void personaConApellidoVacioTest() {
	modelo.agregarPersona("Leo", "", 5, RolEnum.PROGRAMADOR, "rutaFoto");
	}

	@Test(expected = IllegalArgumentException.class)
	public void personaConPuntuacionInvalidaTest() {
	modelo.agregarPersona("Leo", "Messi", 10, RolEnum.PROGRAMADOR, "rutaFoto");
	}

	@Test(expected = IllegalArgumentException.class)
	public void personaConRolVacioTest() {
	modelo.agregarPersona("Leo", "Messi", 5, null, "rutaFoto");
	}

	@Test(expected = IllegalArgumentException.class)
	public void personaNombreInvalidoTest() {
	modelo.agregarPersona("1234", "Messi", 5, RolEnum.PROGRAMADOR, "rutaFoto");
	}

	@Test(expected = IllegalArgumentException.class)
	public void personaApellidoInvalidoTest() {
	modelo.agregarPersona("Leo", "1234", 5, RolEnum.PROGRAMADOR, "rutaFoto");
	}

	@Test(expected = IllegalArgumentException.class)
	public void archivoInvalidoTest() {
	modelo.cargarDesdeJSON("archivo.txt");
	}

	@Test(expected = IllegalArgumentException.class)
	public void formatoImagenInvalidoTest() throws IOException {
	File archivo = carpetaTemporal.newFile("archivo.txt");
	modelo.guardarFoto(archivo);
	}
	
	
}
