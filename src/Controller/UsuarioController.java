package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Interfaces.UsuarioInterface;
import Model.Usuario;
import Utils.MySqlConnection;

public class UsuarioController implements UsuarioInterface {

	@Override
	public int agregar(Usuario usuario) {
		
		int rs = 0;
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pst = null;
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "INSERT INTO tb_user (dni_user, nom_user, apell_user, telf_user, edad_user) VALUES (?, ?, ?, ?, ?)";

			pst = con.prepareStatement(sql);
			
			pst.setString(1, usuario.getDniUser());
			pst.setString(2, usuario.getNomUser());	
			pst.setString(3, usuario.getApellUser());
			pst.setString(4, usuario.getTelfUser());
			pst.setInt(5, usuario.getEdadUser());
					
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
	public int quitar(Usuario usuario) {

		int rs = 0;
		
		Connection con = null;
		PreparedStatement pst = null;
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "UPDATE tb_user SET estado_user = 2 WHERE dni_user = ?";

			pst = con.prepareStatement(sql);
			
			//WHERE
			pst.setString(1, usuario.getDniUser());
					
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
	public int modificar(Usuario usuario) {

		int rs = 0;
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pst = null;
		try {
			
			con = MySqlConnection.getConexion();
			String sql = "UPDATE tb_user SET nom_user = ?, apell_user = ?, telf_user = ?, edad_user = ? WHERE dni_user = ?";

			pst = con.prepareStatement(sql);
			
			//SET
			pst.setString(1, usuario.getNomUser());
			pst.setString(2, usuario.getApellUser());	
			pst.setString(3, usuario.getTelfUser());
			pst.setInt(4, usuario.getEdadUser());
				
			//WHERE
			pst.setString(5, usuario.getDniUser());
			
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
	public ArrayList<Usuario> listar() {

		ArrayList<Usuario> usuarioArrayList = new ArrayList<Usuario>();
		
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		try {	
			con = MySqlConnection.getConexion();
			String sql = "SELECT * FROM tb_user WHERE estado_user = 1";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				Usuario usuario = new Usuario();
				usuario.setDniUser(rs.getString(1));
				usuario.setNomUser(rs.getString(2));
				usuario.setApellUser(rs.getString(3));
				usuario.setTelfUser(rs.getString(4));
				usuario.setEdadUser(rs.getInt(5));
				
				usuarioArrayList.add(usuario);
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
		
		return usuarioArrayList;
	}

}
