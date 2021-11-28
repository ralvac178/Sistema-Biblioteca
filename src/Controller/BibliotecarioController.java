package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Interfaces.BibliotecarioInterface;
import Model.Bibliotecario;
import Utils.MySqlConnection;

public class BibliotecarioController implements BibliotecarioInterface {

	@Override
	public int agregar(Bibliotecario bibliotecario) {
	
		int rs = 0;
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pst = null;
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "INSERT INTO tb_bibliothecary (id_bib, nom_bib, apell_bib, pass_bib) VALUES (?, ?, ?, ?)";

			pst = con.prepareStatement(sql);
			
			pst.setString(1, bibliotecario.getIdBib());
			pst.setString(2, bibliotecario.getNomBib());	
			pst.setString(3, bibliotecario.getApellBib());
			pst.setString(4, bibliotecario.getPassword());
			
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
	public int quitar(Bibliotecario bibliotecario) {

		int rs = 0;
		
		Connection con = null;
		PreparedStatement pst = null;
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "UPDATE tb_bibliothecary SET estado_bib = 2 WHERE id_bib = ?";

			pst = con.prepareStatement(sql);
			
			//WHERE
			pst.setString(1, bibliotecario.getIdBib());
					
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
	public int modificar(Bibliotecario bibliotecario) {

		int rs = 0;
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pst = null;
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "UPDATE tb_bibliothecary SET nom_bib = ?,  apell_bib = ?, pass_bib = ? WHERE id_bib = ?";

			pst = con.prepareStatement(sql);
			
			//Set
			pst.setString(1, bibliotecario.getNomBib());	
			pst.setString(2, bibliotecario.getApellBib());
			pst.setString(3, bibliotecario.getPassword());
			//Where condition
			pst.setString(4, bibliotecario.getIdBib());
					
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
	public ArrayList<Bibliotecario> listar() {

		ArrayList<Bibliotecario> bibliotecarioArrayList = new ArrayList<Bibliotecario>();
		
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		try {	
			con = MySqlConnection.getConexion();
			String sql = "SELECT * FROM tb_bibliothecary WHERE estado_bib = 1";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				Bibliotecario bibliotecario = new Bibliotecario();
				bibliotecario.setIdBib(rs.getString(1));
				bibliotecario.setNomBib(rs.getString(2));
				bibliotecario.setApellBib(rs.getString(3));
				bibliotecario.setPassword(rs.getString(4));
				
				bibliotecarioArrayList.add(bibliotecario);
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
		
		return bibliotecarioArrayList;
	}

	@Override
	public String generarID() {
		String codigo = "B0001";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "SELECT MAX(SUBSTRING(id_bib,2)) FROM  tb_bibliothecary;";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
				
			if(rs.next()) {
				int nID = rs.getInt(1) + 1;
				codigo = String.format("B%04d", nID);
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
