package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controller.BibliotecarioController;
import Controller.LibroController;
import Controller.UsuarioController;
import Model.Bibliotecario;
import Model.Usuario;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Panel;
import javax.swing.border.TitledBorder;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Toolkit;

public class BibRegister extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtDni;
	private JTable table;
	
	DefaultTableModel model;
	private JPasswordField txtPBib;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BibRegister frame = new BibRegister();
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
	public BibRegister() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BibRegister.class.getResource("/img/library.png")));
		setTitle("Registro de Bibliotecarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 482);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Inicio");
		menuBar.add(mnNewMenu);
		
		JMenuItem miCerrarSesion = new JMenuItem("Logout");
		miCerrarSesion.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Login login = new Login();
				login.setVisible(true);
				dispose();
			}
		});
		miCerrarSesion.setIcon(new ImageIcon(BibRegister.class.getResource("/img/sesion.png")));
		mnNewMenu.add(miCerrarSesion);
		
		JMenuItem miSalir = new JMenuItem("Salir");
		miSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		miSalir.setIcon(new ImageIcon(BibRegister.class.getResource("/img/exit.png")));
		mnNewMenu.add(miSalir);
		
		JMenu mnNewMenu_1 = new JMenu("Registros");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem miLibros = new JMenuItem("Libros");
		miLibros.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				BooksRegister booksRegister = new BooksRegister();
				booksRegister.setVisible(true);
				dispose();
			}
		});
		miLibros.setIcon(new ImageIcon(BibRegister.class.getResource("/img/books.png")));
		mnNewMenu_1.add(miLibros);
		
		JMenuItem miPrest = new JMenuItem("Pr\u00E9stamos");
		miPrest.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				LoanBooks loanBooks = new LoanBooks();
				loanBooks.setVisible(true);
				dispose();
			}
		});
		miPrest.setIcon(new ImageIcon(BibRegister.class.getResource("/img/loan.png")));
		mnNewMenu_1.add(miPrest);
		
		JMenuItem miUsers = new JMenuItem("Usuarios");
		miUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				UserRegister userRegister = new UserRegister();
				userRegister.setVisible(true);
				dispose();
			}
		});
		miUsers.setIcon(new ImageIcon(BibRegister.class.getResource("/img/user.png")));
		mnNewMenu_1.add(miUsers);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Get Controller
				BibliotecarioController bc = new BibliotecarioController();
				
				//Read Fields
				String nombres = leerNombres();
				String apellidos = leerApellidos();
				String id = bc.generarID();
				String password = leerPassword();
				
				//Validation
				boolean validation = nombres != null && apellidos != null && id != null && password != null;
				
				if(validation) {
					Bibliotecario bib = new Bibliotecario(id, nombres, apellidos, password);
					
					int rs = bc.agregar(bib);
					if(rs != 0) {
						aviso("Bibliotecario ingresado");
					}else aviso("Error al ingresar Bibliotecario");
				}else aviso("Datos no válidos");
				
				listar();
				
			}
		});
		btnAgregar.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnAgregar.setBounds(106, 200, 89, 23);
		contentPane.add(btnAgregar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Read Fields
				String id = leerID();

				//Validation
				boolean validation = id != null;
				
				if(validation) {
					Bibliotecario bib = new Bibliotecario();
					bib.setIdBib(id);
					BibliotecarioController bc = new BibliotecarioController();
					int rs = bc.quitar(bib);
					if(rs != 0) {
						aviso("Bibliotecario eliminado");
					}else aviso("Error al eliminar Bibliotecario");
				}else aviso("Datos no válidos");
				
				listar();
			}
		});
		btnEliminar.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnEliminar.setBounds(360, 200, 89, 23);
		contentPane.add(btnEliminar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Read Fields
				String nombres = leerNombres();
				String apellidos = leerApellidos();
				String id = leerID();
				String password = leerPassword();
				
				//Validation
				boolean validation = nombres != null && apellidos != null && id != null && password != null;
				
				if(validation) {
					Bibliotecario bib = new Bibliotecario(id, nombres, apellidos, password);
					BibliotecarioController bc = new BibliotecarioController();
					int rs = bc.modificar(bib);
					if(rs != 0) {
						aviso("Bibliotecario modificado");
					}else aviso("Error al modificar Bibliotecario");
				}else aviso("Datos no válidos");
				
				listar();
				
				
			}
		});
		btnModificar.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnModificar.setBounds(238, 200, 89, 23);
		contentPane.add(btnModificar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 245, 539, 165);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int column = 0;
				int raw = table.getSelectedRow();
				String id = (String)table.getValueAt(raw, column);
				String nombre = (String)table.getValueAt(raw, column + 1);
				String apellido = (String)table.getValueAt(raw, column + 2);
				
				txtDni.setText(id);
				txtNombre.setText(nombre);
				txtApellidos.setText(apellido);
			}
		});
		model = new DefaultTableModel();
		
		table.setModel(model);
		
		model.addColumn("ID");
		model.addColumn("Nombres");
		model.addColumn("Apellidos");

		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registro de Bibliotecarios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 21, 539, 158);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtDni = new JTextField();
		txtDni.setBounds(115, 52, 125, 21);
		panel.add(txtDni);
		txtDni.setEnabled(false);
		txtDni.setColumns(10);
		
		JLabel lblID = new JLabel("ID");
		lblID.setBounds(30, 48, 75, 25);
		panel.add(lblID);
		lblID.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		txtNombre = new JTextField();
		txtNombre.setBounds(115, 103, 125, 21);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNombres = new JLabel("Nombres");
		lblNombres.setBounds(30, 99, 75, 25);
		panel.add(lblNombres);
		lblNombres.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(290, 99, 75, 25);
		panel.add(lblApellidos);
		lblApellidos.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		txtApellidos = new JTextField();
		txtApellidos.setBounds(375, 103, 125, 21);
		panel.add(txtApellidos);
		txtApellidos.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblContrasea.setBounds(290, 48, 75, 25);
		panel.add(lblContrasea);
		
		txtPBib = new JPasswordField();
		txtPBib.setEchoChar('*');
		txtPBib.setBounds(375, 52, 96, 20);
		panel.add(txtPBib);
		
		JLabel lblContrasea_1 = new JLabel("");
		lblContrasea_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				seePassword();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				hidePassword();
			}
		});
		lblContrasea_1.setIcon(new ImageIcon(BibRegister.class.getResource("/img/show.png")));
		lblContrasea_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblContrasea_1.setBounds(476, 50, 24, 24);
		panel.add(lblContrasea_1);
		
		listar();
	}
	
	public String leerNombres() {
		String nombres = txtNombre.getText();
		if(nombres.length() <= 16 && nombres.matches("[a-zA-Z ]+")) return nombres;
		else {
			aviso("Error al ingresar Nombres");
			return null;
		}
	}
	
	public String leerApellidos() {
		String apellidos = txtApellidos.getText();
		if(apellidos.length() <= 16 && apellidos.matches("[a-zA-Z ]+")) return apellidos;
		else {
			aviso("Error al ingresar Apellidos");
			return null;
		}
	}
	
	public String leerID() {
		String id = txtDni.getText();
		if(id.length() == 5 && id.matches("[B][0-9]+")) return id;
		else {
			aviso("Error al ingresar ID");
			return null;
		}
	}
	
	public String leerPassword() {
		String password = String.valueOf(txtPBib.getPassword());
		if(password.length() == 8 && password.matches("[^ ]{8}")) return password;
		else {
			aviso("Error al ingresar la contraseña");
			return null;
		}
	}
	
	public void seePassword() {
		txtPBib.setEchoChar((char)0); 
	}
	
	public void hidePassword() {
		txtPBib.setEchoChar('*'); 
	}


	void aviso(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	public void listar() {
		BibliotecarioController bc = new BibliotecarioController();
		ArrayList<Bibliotecario> bibliotecarioArrayList = bc.listar();
		model.setRowCount(0);
		if(!bibliotecarioArrayList.isEmpty()) {
			for(Bibliotecario bib : bibliotecarioArrayList) {
				Object[] o = {bib.getIdBib(), bib.getNomBib(), bib.getApellBib()};
				model.addRow(o);
			}
		}
	}
}
