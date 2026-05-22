package model.repository;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.PersonaArchivo;

public class PersonaRepositoryJson implements PersonaRepository{
	private String archivo;
	
	public PersonaRepositoryJson(String archivo) {
		this.archivo = archivo;
	}

	@Override
	public ArrayList<Persona> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAll(ArrayList<Persona> personas) {
		System.out.print("hola");
		PersonaArchivo.generarJsonPersona(archivo, personas);
		
	}

	@Override
	public void cleanAll() {
		// TODO Auto-generated method stub
		
	}

}
