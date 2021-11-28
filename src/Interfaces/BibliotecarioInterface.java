package Interfaces;

import java.util.ArrayList;

import Model.Bibliotecario;

public interface BibliotecarioInterface {

	public int agregar(Bibliotecario bibliotecario);
	
	public int quitar(Bibliotecario bibliotecario);
	
	public int modificar(Bibliotecario bibliotecario);
	
	public ArrayList<Bibliotecario> listar();
	
	public String generarID();
}
