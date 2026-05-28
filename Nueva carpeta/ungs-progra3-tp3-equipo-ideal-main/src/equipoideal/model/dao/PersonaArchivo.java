package equipoideal.model.dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import equipoideal.model.Persona;

public class PersonaArchivo {

	public static void generarJsonPersona(String archivo, ArrayList<Persona> personas) {
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
		Gson gson = new Gson();

		try (Reader reader = new FileReader(archivo)) {

			Type personaType = new TypeToken<ArrayList<Persona>>() {}.getType();

			ArrayList<Persona> personas = gson.fromJson(reader, personaType);
			reader.close();

			return personas;

		} catch (Exception e) {

			System.err.println("No se pudo cargar el JSON: "+ e.getMessage()
			);

			throw new RuntimeException(e.getMessage());
		}
	}

	public static void limpiarArchivoJson(String archivo){

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

		

}
