package Interfaces;

import java.util.ArrayList;

import Model.Usuario;

public interface UsuarioInterface {

	public int agregar(Usuario usuario);
	
	public int quitar(Usuario usuario);
	
	public int modificar(Usuario usuario);
	
	public ArrayList<Usuario> listar();
	
}
