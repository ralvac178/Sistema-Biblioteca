package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.BibliotecarioController;
import Model.Bibliotecario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);

		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/library.png")));
		setTitle("Biblioteca");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 523, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBiblioteca = new JLabel("Biblioteca");
		lblBiblioteca.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		lblBiblioteca.setBounds(181, 27, 177, 33);
		contentPane.add(lblBiblioteca);
		
		JLabel lblNewLabel = new JLabel("Bibliotecario");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblNewLabel.setBounds(40, 114, 83, 14);
		contentPane.add(lblNewLabel);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(133, 111, 151, 25);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblContrasea.setBounds(40, 162, 95, 14);
		contentPane.add(lblContrasea);
		
		JButton btnAceptar = new JButton("Aceptar");
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				validLogin();
			}
		});
		btnAceptar.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnAceptar.setBounds(114, 226, 89, 23);
		contentPane.add(btnAceptar);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setIcon(new ImageIcon(Login.class.getResource("/img/library.png")));
		lblImagen.setBounds(336, 94, 128, 128);		
		contentPane.add(lblImagen);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validLogin();
				}
			}
		});
		passwordField.setBounds(133, 157, 151, 25);
		contentPane.add(passwordField);
	}
	
	void validLogin() {
		//Get data from fields
		String user = txtUsuario.getText();
		String password = String.valueOf(passwordField.getPassword());
		
		//Get Bibliothecary
		BibliotecarioController bc = new BibliotecarioController();
		ArrayList<Bibliotecario> bibliotecarioLista = bc.listar();
		for (Bibliotecario bibliotecario : bibliotecarioLista) {
			if(bibliotecario.getIdBib().equals(user) && bibliotecario.getPassword().equals(password)) {
				LoanBooks loanBooks = new LoanBooks();
				loanBooks.setVisible(true);
				dispose();
				return;
			}
		}JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");
	}
}
