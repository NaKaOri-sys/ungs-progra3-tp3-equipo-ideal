package equipoideal.model.event;

import equipoideal.model.Persona;

public interface IObserverIncompatible {
	void alCrearIncompatibilidad(Persona persona, Persona incompatible);
	void alEliminarIncompatibilidad(Persona persona, Persona incompatible);
}
