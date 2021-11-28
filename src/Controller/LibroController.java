package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Interfaces.LibroInterface;
import Model.Bibliotecario;
import Model.Libro;
import Utils.MySqlConnection;

public class LibroController implements LibroInterface {

	@Override
	public int agregar(Libro libro) {
		
		int rs = 0;
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pst = null;
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "INSERT INTO tb_book (id_book, tit_book, aut_book, cat_book, año_book, pais_book) VALUES (?, ?, ?, ?, ?, ?)";

			pst = con.prepareStatement(sql);
			
			pst.setString(1, libro.getIdLibro());
			pst.setString(2, libro.getTitLibro());	
			pst.setString(3, libro.getAutorLibro());
			pst.setString(4, libro.getCatLibro());
			pst.setString(5, libro.getAñoLibro());
			pst.setString(6, libro.getPaisLibro());
					
			rs = pst.executeUpdate();
			
		}catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Error en la sentencia: " + e.getMessage());
		}
		finally {
			
			try {
				con.close();
				
			}catch(SQLException e) {
					System.out.println("Error al cerrar conexión");
				}
			}
		
		return rs;

	}

	@Override
	public int quitar(Libro libro) {

		int rs = 0;
		
		Connection con = null;
		PreparedStatement pst = null;
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "UPDATE tb_book SET estado_placed_book = 2 WHERE id_book = ?";

			pst = con.prepareStatement(sql);
			
			//WHERE
			pst.setString(1, libro.getIdLibro());
					
			rs = pst.executeUpdate();
			
		}catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Error en la sentencia: " + e.getMessage());
		}
		finally {
			
			try {
				con.close();
				
			}catch(SQLException e) {
					System.out.println("Error al cerrar conexión");
				}
			}
		
		return rs;
	}

	@Override
	public int modificar(Libro libro) {
		
		int rs = 0;
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pst = null;
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "UPDATE tb_book SET tit_book = ?,  cat_book = ?, año_book = ?, pais_book = ?, aut_book = ? WHERE id_book = ?";
			pst = con.prepareStatement(sql);
			
			//Set
			pst.setString(1, libro.getTitLibro());	
			pst.setString(2, libro.getCatLibro());
			pst.setString(3, libro.getAñoLibro());
			pst.setString(4, libro.getPaisLibro());
			pst.setString(5, libro.getAutorLibro());
			//Where condition
			pst.setString(6, libro.getIdLibro());
					
			rs = pst.executeUpdate();
			
		}catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Error en la sentencia: " + e.getMessage());
		}
		finally {
			
			try {
				con.close();
				
			}catch(SQLException e) {
					System.out.println("Error al cerrar conexión");
				}
			}
		
		return rs;

	}

	@Override
	public ArrayList<Libro> listar(String column, String estado) {

		ArrayList<Libro> libroArrayList = new ArrayList<Libro>();
		
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		try {	
			con = MySqlConnection.getConexion();
			String sql = String.format("SELECT * FROM tb_book WHERE %s = %s", column, estado);
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				Libro libro = new Libro();
				libro.setIdLibro(rs.getString(1));
				libro.setTitLibro(rs.getString(2));
				libro.setAutorLibro(rs.getString(3));
				libro.setCatLibro(rs.getString(4));
				libro.setAñoLibro(rs.getString(5));
				libro.setPaisLibro(rs.getString(6));

				libroArrayList.add(libro);
			}
			
			
		}catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Error en la sentencia: " + e.getMessage());
		}
		finally {
			
			try {
				con.close();
				
			}catch(SQLException e) {
					System.out.println("Error al cerrar conexión");
				}
			}
		
		return libroArrayList;
	}

	@Override
	public String generarID() {

		String codigo = "L0001";
		
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "SELECT MAX(SUBSTRING(id_book,2)) FROM  tb_book;";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
				
			if(rs.next()) {
				int nID = rs.getInt(1) + 1;
				codigo = String.format("L%04d", nID);
			}
			
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error en la sentencia: " + e.getMessage());
		}
		
		finally {
			
			try {
				con.close();
				
			}catch(SQLException e) {
					System.out.println("Error al cerrar conexión");
				}
			}
		return codigo;
	}

}
