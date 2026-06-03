package equipoideal.model.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import equipoideal.model.Persona;
import equipoideal.util.PersonaValidator;

public class PersonaArchivo {

	public static void generarJsonPersona(String archivo, ArrayList<Persona> personas) {
		if (archivo == null || archivo.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del archivo no puede estar vacío.");
		}

		if (personas == null) {
			throw new IllegalArgumentException("La lista de personas no puede ser null.");
		}
		
		
		  Gson gson = new GsonBuilder().setPrettyPrinting().create();

			
		    String json =gson.toJson(personas);

			
		    try (FileWriter writer =new FileWriter(archivo)) {
		    	writer.write(json);
		    } catch (Exception e) {
		        System.err.println( "Error al generar JSON: " + e.getMessage());

		        throw new RuntimeException( e.getMessage() );
		    }
	}

	public static ArrayList<Persona> cargarJSON(String archivo) {

		if (archivo == null || archivo.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del archivo no puede estar vacío.");
		}
		
		File file = new File(archivo);
		if (!file.exists()) {
		    throw new IllegalArgumentException("El archivo no existe.");
		}

		Gson gson = new Gson();

		try (Reader reader = new FileReader(archivo)) {

			Type personaType = new TypeToken<ArrayList<Persona>>() {}.getType();

			ArrayList<Persona> personas = gson.fromJson(reader, personaType);

			for (Persona p : personas) {
				PersonaValidator.validarPersona(p.getNombre(), p.getApellido(), p.getCalificacion(), p.getRol());
			}

			return personas;

		} catch (JsonSyntaxException e) {

			System.err.println("El JSON tiene errores de sintaxis.");

			throw new IllegalArgumentException ("Formato JSON inválido.");

		} catch (IOException e) {

			System.err.println("Error al leer el archivo: " + e.getMessage());

			throw new IllegalArgumentException("No se pudo leer el archivo.");

		} catch (IllegalArgumentException e) {

			throw e;
		}
	}

	public static void limpiarArchivoJson(String archivo){
		
		File file = new File(archivo);
		if (!file.exists()) {
		    throw new IllegalArgumentException("El archivo no existe.");
		}

		if (archivo == null || archivo.trim().isEmpty()) {

			throw new IllegalArgumentException("El nombre del archivo no puede ser nulo o vacío.");
		}

		try (FileWriter writer = new FileWriter(archivo)) {
			writer.write("[]");

		} catch (Exception e) {

			System.err.println("No se pudo limpiar el JSON: "+ e.getMessage());

			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static void exportarArchivoJSON(String archivo, String destino) {
		File origen = new File(archivo);

	    File archivoDestino = new File(destino);

	    try {

	        Files.copy(origen.toPath(), archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

	    } catch (IOException e) {

	        throw new RuntimeException("No se pudo exportar el JSON.");
	    }
	}
	
	

		

}
