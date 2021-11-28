package Interfaces;

import java.util.ArrayList;

import Model.Prestamo;

public interface PrestamoInterface {
	
	public int prestar(Prestamo prestamo);
	
	public int devolver(Prestamo prestamo);
	
	public int modificar(Prestamo prestamo);
	
	public ArrayList<Prestamo> listar();
	
	public String generarIDPretsamo();
}
