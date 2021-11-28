package Interfaces;

import java.util.ArrayList;

import Model.Libro;

public interface LibroInterface {

	public int agregar(Libro libro);
	
	public int quitar(Libro libro);
	
	public int modificar(Libro libro);
	
	public ArrayList<Libro> listar(String column, String estado);
	
	public String generarID();
}
