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

import Controller.LibroController;
import Controller.UsuarioController;
import Model.Usuario;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.border.TitledBorder;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;

public class UserRegister extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtTelefono;
	private JTextField txtDni;
	private JTextField txtEdad;
	private JTable table;
	
	DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserRegister frame = new UserRegister();
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
	public UserRegister() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserRegister.class.getResource("/img/library.png")));
		setTitle("Registro de Usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 511);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Inicio");
		menuBar.add(mnNewMenu);
		
		JMenuItem miCerraSesion = new JMenuItem("Logout");
		miCerraSesion.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Login login = new Login();
				login.setVisible(true);
				dispose();
			}
		});
		miCerraSesion.setIcon(new ImageIcon(UserRegister.class.getResource("/img/sesion.png")));
		mnNewMenu.add(miCerraSesion);
		
		JMenuItem miSalir = new JMenuItem("Salir");
		miSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		miSalir.setIcon(new ImageIcon(UserRegister.class.getResource("/img/exit.png")));
		mnNewMenu.add(miSalir);
		
		JMenu mnNewMenu_1 = new JMenu("Registros");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem miBib = new JMenuItem("Bibliotecarios");
		miBib.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				BibRegister bibRegister = new BibRegister();
				bibRegister.setVisible(true);
				dispose();
			}
		});
		miBib.setIcon(new ImageIcon(UserRegister.class.getResource("/img/Bibliothecary.png")));
		mnNewMenu_1.add(miBib);
		
		JMenuItem miBook = new JMenuItem("Libros");
		miBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				BooksRegister booksRegister = new BooksRegister();
				booksRegister.setVisible(true);
				dispose();
			}
		});
		miBook.setIcon(new ImageIcon(UserRegister.class.getResource("/img/books.png")));
		mnNewMenu_1.add(miBook);
		
		JMenuItem miPrest = new JMenuItem("Pr\u00E9stamos");
		miPrest.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				LoanBooks loanBooks = new LoanBooks();
				loanBooks.setVisible(true);
				dispose();
			}
		});
		miPrest.setIcon(new ImageIcon(UserRegister.class.getResource("/img/loan.png")));
		mnNewMenu_1.add(miPrest);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Read Fields
				String nombres = leerNombres();
				String apellidos = leerApellidos();
				String dni = leerDNI();
				int edad = leerEdad();
				String telefono = leerTelefono();
				
				//Validation
				boolean validation = nombres != null && apellidos != null && dni != null && edad != -1 && telefono != null;
				
				if(validation) {
					Usuario user = new Usuario(dni, nombres, apellidos, telefono, edad);
					UsuarioController lc = new UsuarioController();
					int rs = lc.agregar(user);
					if(rs != 0) {
						aviso("Usuario ingresado");
					}else aviso("Error al ingresar Usuario");
				}else aviso("Datos no válidos");
				
				listar();
				
			}
		});
		btnAgregar.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnAgregar.setBounds(112, 215, 89, 23);
		contentPane.add(btnAgregar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Read Fields
				String dni = leerDNI();

				//Validation
				boolean validation = dni != null;
				
				if(validation) {
					Usuario user = new Usuario();
					user.setDniUser(dni);
					UsuarioController uc = new UsuarioController();
					int rs = uc.quitar(user);
					if(rs != 0) {
						aviso("Usuario eliminado");
					}else aviso("Error al eliminar usuario");
				}else aviso("Datos no válidos");
				
				listar();
			}
		});
		btnEliminar.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnEliminar.setBounds(397, 215, 89, 23);
		contentPane.add(btnEliminar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Read Fields
				String nombres = leerNombres();
				String apellidos = leerApellidos();
				String dni = leerDNI();
				int edad = leerEdad();
				String telefono = leerTelefono();
				
				//Validation
				boolean validation = nombres != null && apellidos != null && dni != null && edad != -1 && telefono != null;
				
				if(validation) {
					Usuario user = new Usuario(dni, nombres, apellidos, telefono, edad);
					UsuarioController lc = new UsuarioController();
					int rs = lc.modificar(user);
					if(rs != 0) {
						aviso("Usuario modificado");
					}else aviso("Error al modificar Usuario");
				}else aviso("Datos no válidos");
				
				listar();
				
				
			}
		});
		btnModificar.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnModificar.setBounds(258, 215, 89, 23);
		contentPane.add(btnModificar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 258, 589, 181);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				int raw = table.getSelectedRow();
				
				String dni = (String) table.getValueAt(raw, 0);
				String nombres = (String) table.getValueAt(raw, 1);
				String apellidos = (String) table.getValueAt(raw, 2);
				int edad = (int) table.getValueAt(raw, 3);
				String tlf = (String) table.getValueAt(raw, 4);
				
				
				txtDni.setText(dni);
				txtNombre.setText(nombres);
				txtApellidos.setText(apellidos);
				txtEdad.setText(String.valueOf(edad));
				txtTelefono.setText(tlf);
				
			}
		});
		model = new DefaultTableModel();
		
		table.setModel(model);

		model.addColumn("DNI");
		model.addColumn("Nombres");
		model.addColumn("Apellidos");
		model.addColumn("Edad");
		model.addColumn("Telefono");

		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registro de Usuarios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 589, 184);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtDni = new JTextField();
		txtDni.setBounds(107, 33, 125, 21);
		panel.add(txtDni);
		txtDni.setColumns(10);
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(37, 30, 75, 25);
		panel.add(lblDni);
		lblDni.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		txtNombre = new JTextField();
		txtNombre.setBounds(107, 87, 125, 21);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNombres = new JLabel("Nombres");
		lblNombres.setBounds(37, 85, 75, 25);
		panel.add(lblNombres);
		lblNombres.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		txtApellidos = new JTextField();
		txtApellidos.setBounds(107, 141, 125, 21);
		panel.add(txtApellidos);
		txtApellidos.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(37, 140, 75, 25);
		panel.add(lblApellidos);
		lblApellidos.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(370, 33, 125, 20);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(285, 29, 75, 25);
		panel.add(lblTelefono);
		lblTelefono.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		txtEdad = new JTextField();
		txtEdad.setBounds(370, 87, 125, 21);
		panel.add(txtEdad);
		txtEdad.setColumns(10);
		
		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setBounds(285, 83, 62, 25);
		panel.add(lblEdad);
		lblEdad.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
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
	
	public String leerDNI() {
		String dni = txtDni.getText();
		if(dni.length() == 8 && dni.matches("[0-9]+")) return dni;
		else {
			aviso("Error al ingresar DNI");
			return null;
		}
	}
	
	public String leerTelefono() {
		String telefono = txtTelefono.getText();
		if(telefono.length() == 9 && telefono.matches("[0-9]+")) return telefono;
		else {
			aviso("Error al ingresar teléfono");
			return null;
		}
	}
	
	public int leerEdad() {
		try {
			return Integer.parseInt(txtEdad.getText());
		}catch(Exception e) {
			aviso("Error al ingresar edad");
			return -1;
		}
	}
	
	void aviso(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	public void listar() {
		UsuarioController uc = new UsuarioController();
		ArrayList<Usuario> usuarioArrayList = uc.listar();
		model.setRowCount(0);
		if(!usuarioArrayList.isEmpty()) {
			for(Usuario user : usuarioArrayList) {
				Object[] o = {user.getDniUser(), user.getNomUser(), user.getApellUser(), user.getEdadUser(), user.getTelfUser()};
				model.addRow(o);
			}
		}
	}
}
