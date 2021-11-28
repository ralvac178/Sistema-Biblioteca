package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Interfaces.PrestamoInterface;
import Model.Prestamo;
import Model.Usuario;
import Utils.MySqlConnection;

public class PrestamoController implements PrestamoInterface {

	@Override
	public int prestar(Prestamo prestamo) {

		int rs = 0;
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pst = null;
		try {
			
			con = MySqlConnection.getConexion();
			//String sql = "INSERT INTO tb_loan VALUES (?, ?, ?, ?, NOW(), ?, 1)";
			String sql = "CALL sp_prestar(?, ?, ?, ?, ?)";
			pst = con.prepareStatement(sql);
			
			pst.setString(1, prestamo.getIdPrest());
			pst.setString(2, prestamo.getIdLibro());	
			pst.setString(3, prestamo.getDniUser());
			pst.setString(4, prestamo.getIdBib());
			pst.setString(5, prestamo.getFechaEntregaPrest());
					
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
	public int devolver(Prestamo prestamo) {

		int rs = 0;
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pst = null;
		try {
			
			con = MySqlConnection.getConexion();
			//String sql = "DELETE tb_loan FROM WHERE id_loan = ?";
			String sql = "CALL sp_devolver(?, ?)";
			
			pst = con.prepareStatement(sql);
					
			//Where
			pst.setString(1, prestamo.getIdPrest());
			pst.setString(2, prestamo.getIdLibro());
			
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
	public int modificar(Prestamo prestamo) {

		int rs = 0;
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pst = null;
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "UPDATE tb_loan SET id_book = ?, dni_user = ?, dni_user = ?, fecha_loan = ?, fechaEntrega_loan = ? WHERE id_loan = ?";

			pst = con.prepareStatement(sql);
			
			//Set
			pst.setString(1, prestamo.getIdLibro());	
			pst.setString(2, prestamo.getDniUser());
			pst.setString(3, prestamo.getIdBib());
			pst.setString(4, prestamo.getFechaPrest());
			pst.setString(5, prestamo.getFechaEntregaPrest());
					
			//Where
			pst.setString(6, prestamo.getIdPrest());
			
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
	public ArrayList<Prestamo> listar() {

		ArrayList<Prestamo> prestamoArrayList = new ArrayList<Prestamo>();
		
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		try {	
			con = MySqlConnection.getConexion();
			String sql = "SELECT * FROM tb_loan WHERE estado_loan = 1";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				Prestamo prestamo = new Prestamo();
				prestamo.setIdPrest(rs.getString(1));
				prestamo.setIdLibro(rs.getString(2));
				prestamo.setDniUser(rs.getString(3));
				prestamo.setIdBib(rs.getString(4));
				prestamo.setFechaPrest(rs.getString(5));
				prestamo.setFechaEntregaPrest(rs.getString(6));
				
				prestamoArrayList.add(prestamo);
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
		
		return prestamoArrayList;
	}

	@Override
	public String generarIDPretsamo() {

		String codigo = "P0001";
		
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "SELECT MAX(SUBSTRING(id_loan,2)) FROM  tb_loan;";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
				
			if(rs.next()) {
				int nID = rs.getInt(1) + 1;
				codigo = String.format("P%04d", nID);
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
