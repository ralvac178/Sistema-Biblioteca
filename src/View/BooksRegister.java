package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.time.Year;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controller.LibroController;
import Model.Libro;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class BooksRegister extends JFrame {

	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtTitulo;
	private JTextField txtAño;
	private JTextField txtPais;
	private JTable table;
	private JTextField txtAutor;
	private JComboBox cboCategoria;
	
	DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BooksRegister frame = new BooksRegister();
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
	public BooksRegister() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BooksRegister.class.getResource("/img/library.png")));
		
		setTitle("Registro de Libros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 558, 508);
		
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
		miCerrarSesion.setIcon(new ImageIcon(BooksRegister.class.getResource("/img/sesion.png")));
		mnNewMenu.add(miCerrarSesion);
		
		JMenuItem miSalir = new JMenuItem("Salir");
		miSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		miSalir.setIcon(new ImageIcon(BooksRegister.class.getResource("/img/exit.png")));
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
		miBib.setIcon(new ImageIcon(BooksRegister.class.getResource("/img/Bibliothecary.png")));
		mnNewMenu_1.add(miBib);
		
		JMenuItem miPrest = new JMenuItem("Pr\u00E9stamos");
		miPrest.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				LoanBooks loanBooks = new LoanBooks();
				loanBooks.setVisible(true);
				dispose();
			}
		});
		miPrest.setIcon(new ImageIcon(BooksRegister.class.getResource("/img/loan.png")));
		mnNewMenu_1.add(miPrest);
		
		JMenuItem miUser = new JMenuItem("Usuarios");
		miUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				UserRegister userRegister = new UserRegister();
				userRegister.setVisible(true);
				dispose();
			}
		});
		miUser.setIcon(new ImageIcon(BooksRegister.class.getResource("/img/user.png")));
		mnNewMenu_1.add(miUser);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//setIdBook();
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Get Controller
				LibroController bc = new LibroController();
				
				//Read Fields
				String id = bc.generarID();
				String title = leerTitulo();
				String author = leerAutor();
				String country = leerPais();
				String year = leerAño();
				String category = (String)cboCategoria.getSelectedItem();
				
				//Validation
				boolean validation = id != null && title != null && author != null && country != null && year != null;
				
				if(validation) {
					Libro book = new Libro(id, title, author, category, year, country);
					
					int rs = bc.agregar(book);
					if(rs != 0) {
						aviso("Libro ingresado");
					}else aviso("Error al ingresar libro");
				}else aviso("Datos no válidos");
				
				listar();
			}
		});
		btnAgregar.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnAgregar.setBounds(53, 229, 99, 23);
		contentPane.add(btnAgregar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Read Fields
				String id = leerId();
				String title = leerTitulo();
				String author = leerAutor();
				String country = leerPais();
				String year = leerAño();
				String category = (String)cboCategoria.getSelectedItem();
				
				//Validation
				boolean validation = id != null && title != null && author != null && country != null && year != null;
				
				if(validation) {
					Libro book = new Libro(id, title, author, category, year, country);
					LibroController bc = new LibroController();
					int rs = bc.modificar(book);
					if(rs != 0) {
						aviso("Libro modificado");
					}else aviso("Error al modificar libro");
				}else aviso("Datos no válidos");
				
				listar();
			}
		});
		btnModificar.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnModificar.setBounds(219, 229, 106, 23);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Read Fields
				String id = leerId();

				//Validation
				boolean validation = id != null;
				
				if(validation) {
					Libro book = new Libro();
					book.setIdLibro(id);
					LibroController bc = new LibroController();
					int rs = bc.quitar(book);
					if(rs != 0) {
						aviso("Libro eliminado");
					}else aviso("Error al eliminar libro");
				}else aviso("Datos no válidos");
				
				listar();
			}
		});
		btnEliminar.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnEliminar.setBounds(385, 229, 99, 23);
		contentPane.add(btnEliminar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 275, 521, 161);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				int raw = table.getSelectedRow();
				
				String code = (String)table.getValueAt(raw, 0);
				String title = (String)table.getValueAt(raw, 1);
				String author = (String)table.getValueAt(raw, 2);
				String category = (String)table.getValueAt(raw, 3);
				String country = (String)table.getValueAt(raw, 4);
				String year = (String)table.getValueAt(raw, 5);
				
				txtId.setText(code);
				txtTitulo.setText(title);
				txtPais.setText(country);
				txtAutor.setText(author);
				cboCategoria.setSelectedItem(category);
				txtAño.setText(year);
			}
		});
		
		model = new DefaultTableModel();
		
		table.setModel(model);
		model.addColumn("ID");
		model.addColumn("Título");
		model.addColumn("Autor");
		model.addColumn("Categoría");
		model.addColumn("País");
		model.addColumn("Año");		
		
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registro de Libros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 22, 521, 183);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtId = new JTextField();
		txtId.setBounds(110, 37, 86, 20);
		panel.add(txtId);
		txtId.setEnabled(false);
		txtId.setColumns(10);
		
		JLabel lblIdLibro = new JLabel("Id Libro");
		lblIdLibro.setBounds(26, 38, 74, 14);
		panel.add(lblIdLibro);
		lblIdLibro.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setBounds(26, 88, 64, 14);
		panel.add(lblTitulo);
		lblTitulo.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(110, 87, 114, 20);
		panel.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setBounds(267, 38, 46, 14);
		panel.add(lblAutor);
		lblAutor.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		txtAutor = new JTextField();
		txtAutor.setBounds(323, 37, 114, 20);
		panel.add(txtAutor);
		txtAutor.setColumns(10);
		
		txtAño = new JTextField();
		txtAño.setBounds(323, 87, 114, 20);
		panel.add(txtAño);
		txtAño.setColumns(10);
		
		JLabel lblAo = new JLabel("A\u00F1o");
		lblAo.setBounds(267, 88, 46, 14);
		panel.add(lblAo);
		lblAo.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		txtPais = new JTextField();
		txtPais.setBounds(323, 138, 114, 20);
		panel.add(txtPais);
		txtPais.setColumns(10);
		
		JLabel lblPais = new JLabel("Pais");
		lblPais.setBounds(267, 139, 46, 14);
		panel.add(lblPais);
		lblPais.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		cboCategoria = new JComboBox();
		cboCategoria.setBounds(110, 135, 114, 22);
		panel.add(cboCategoria);
		cboCategoria.setModel(new DefaultComboBoxModel(new String[] {"Arte", "Ciencias", "Ciencia Ficci\u00F3n", "Comedia", "Historia", "Ensayo", "Religión"}));
		cboCategoria.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(26, 139, 86, 14);
		panel.add(lblCategoria);
		lblCategoria.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		listar();
	}

	public String leerId() {
		String id = txtId.getText();
		if(id.length() == 5 && id.matches("[L][0-9]+")) return id;
		else {
			aviso("Error al ingresar ID");
			return null;
		}
	}
	
	public String leerTitulo() {
		String titulo = txtTitulo.getText();
		if(titulo.length() <= 32 && titulo.matches("[a-zA-Z0-9\s]+")) return titulo;
		else {
			aviso("Error al ingresar Titulo");
			return null;
		}
	}
	
	public String leerAutor() {
		String autor = txtAutor.getText();
		if(autor.length() <= 32 && autor.matches("[a-zA-Z0-9\s]+")) return autor;
		else {
			aviso("Error al ingresar Autor");
			return null;
		}
	}
	
	public String leerPais() {
		String pais = txtPais.getText();
		if(pais.length() <= 16 && pais.matches("[a-zA-Z0-9\s]+")) return pais;
		else {
			aviso("Error al ingresar País");
			return null;
		}
	}
	
	public String leerAño() {
		String añoText = txtAño.getText();
		try {
			int año = Integer.parseInt(añoText);
			if(año <= Year.now().getValue() && añoText.matches("^[1-2]\\d{3}$")) {
				return añoText;
			}
			else {
				aviso("Error al ingresar Año");
				return null;
			}
		}catch(Exception e) {
			aviso("Error al ingresar Año");
		}
		return null;

	}
	
	void aviso(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	public void listar() {
		LibroController lc = new LibroController();
		ArrayList<Libro> libroArrayList = lc.listar("estado_placed_book","1");
		model.setRowCount(0);
		if(!libroArrayList.isEmpty()) {
			for(Libro libro : libroArrayList) {
				Object[] o = {libro.getIdLibro(), libro.getTitLibro(), libro.getAutorLibro(), libro.getCatLibro(), libro.getPaisLibro(), libro.getAñoLibro().substring(0, 4)};
				model.addRow(o);
			}
		}
	}
	
}
