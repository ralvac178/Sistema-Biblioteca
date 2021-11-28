package View;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;

import Controller.BibliotecarioController;
import Controller.LibroController;
import Controller.PrestamoController;
import Controller.UsuarioController;
import Model.Bibliotecario;
import Model.Libro;
import Model.Prestamo;
import Model.Usuario;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.border.TitledBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Toolkit;

public class LoanBooks extends JFrame {

	private JPanel contentPane;
	private JTable table;
	static protected JTextField txtIdBook;
	static protected JTextField txtDniUser;
	private JComboBox cboBibliotecarios;
	private DefaultTableModel model;
	private JTextField txtIDPrest;
	private JDateChooser dtcFecha;
	private JButton btnDevolver;
	private JButton btnPrestar;

	PrestamoController pc = new PrestamoController();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanBooks frame = new LoanBooks();
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
	public LoanBooks() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoanBooks.class.getResource("/img/library.png")));
		setTitle("Prestamos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 598, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String book = leerLibro(1);
				String user = leerUsuarios();
				
				
				if(book != null || user != null) {
					
					
					ArrayList<Prestamo> prestamoArrayList = pc.listar();
					model.setRowCount(0);
					
					for (Prestamo prestamo : prestamoArrayList) {
						
						boolean findBook = prestamo.getIdLibro().equals(book);
						boolean findUser = prestamo.getDniUser().equals(user);
						if(findBook || findUser) {
							Object[] object = {prestamo.getIdPrest(), prestamo.getIdLibro(), prestamo.getDniUser(), prestamo.getIdBib(), prestamo.getFechaEntregaPrest()};
							model.addRow(object);
						}
					}
					
					if(model.getRowCount() == 0) aviso("No hay coincidencias de búsqueda");
				}
	
			}
		});
		btnBuscar.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnBuscar.setBounds(10, 224, 89, 23);
		contentPane.add(btnBuscar);
		
		btnDevolver = new JButton("Devolver");
		btnDevolver.setEnabled(false);
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idPrestamo = txtIDPrest.getText();
				String libro = leerLibro(1);
				
				
				Prestamo p = new Prestamo();
				p.setIdPrest(idPrestamo);
				p.setIdLibro(libro);
				
				int rs = pc.devolver(p);
				
				if(rs != 0) {
					aviso("El libro " + libro + " ha sido devuelto");
					listar();
					verificarLibroDisponible(); // Used to update prestar button
					btnDevolver.setEnabled(false);
				}
				else aviso("Error al devolver libro");
			}
		});
		btnDevolver.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnDevolver.setBounds(468, 224, 101, 23);
		contentPane.add(btnDevolver);
		
		btnPrestar = new JButton("Prestar");
		btnPrestar.setEnabled(false);
		btnPrestar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Read fields
				String libro = leerLibro(1);
				String dniUsuario = leerUsuarios();
				String idBibliotecario = (String) cboBibliotecarios.getSelectedItem();
				String fechaEntrega = leerFecha();
				
			
				//Valid values
				Prestamo p = null;
				if(libro != null && dniUsuario != null && idBibliotecario != null & fechaEntrega != null) {
					
					//Verify if book is loaned or not ... if is.. loan
					
					if(verificarLibroDisponible()) {
						String idPrestamo = pc.generarIDPretsamo();
						p = new Prestamo(idPrestamo, libro, dniUsuario, idBibliotecario, fechaEntrega);							
						int rs = pc.prestar(p);
						
						if(rs != 0) {
							aviso("El libro " + libro + " ha sido prestado");
							listar();
							generarTicket(p);
							verificarLibroDisponible(); // Used to update prestar button
							btnDevolver.setEnabled(false);
						}
						else aviso("Error al prestar libro");
					}											
									
				}else {
					aviso("Error al prestar libro");
				}
										
			}
		});
		btnPrestar.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnPrestar.setBounds(369, 224, 89, 23);
		contentPane.add(btnPrestar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 266, 559, 123);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				//Select item ... so we can get the loan id to return come back book
				int row = table.getSelectedRow();
				
				String idLoan = (String) model.getValueAt(row, 0);
				String idBook = (String) model.getValueAt(row, 1);
				String idUser = (String) model.getValueAt(row, 2);
				String idBib = (String) model.getValueAt(row, 3);
				String returnDate = (String) model.getValueAt(row, 4);
				Date date = null;

				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(returnDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				txtIDPrest.setText(idLoan);
				txtIdBook.setText(idBook);
				txtDniUser.setText(idUser);
				cboBibliotecarios.setSelectedItem(idBib);
				dtcFecha.setDate(date);
				
				boolean disponible = verificarLibroDisponible();
				if(!disponible) btnDevolver.setEnabled(true);
			}
		});
		model = new DefaultTableModel();
		
		table.setModel(model);

		model.addColumn("Cod. Préstamo");
		model.addColumn("Libro");
		model.addColumn("Usuario");
		model.addColumn("Bibliotecario");
		model.addColumn("Fecha Entrega");
		
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Pr\u00E9stamos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 37, 559, 176);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtIDPrest = new JTextField();
		txtIDPrest.setBounds(124, 30, 101, 20);
		panel.add(txtIDPrest);
		txtIDPrest.setEnabled(false);
		txtIDPrest.setColumns(10);
		
		JLabel lblCodPrstamo = new JLabel("ID Pr\u00E9stamo");
		lblCodPrstamo.setBounds(29, 31, 89, 14);
		panel.add(lblCodPrstamo);
		lblCodPrstamo.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JLabel btnSearchUser = new JLabel("");
		btnSearchUser.setBounds(230, 72, 32, 32);
		panel.add(btnSearchUser);
		btnSearchUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				UserList ul = new UserList();
				ul.setVisible(true);
			}
		});
		btnSearchUser.setIcon(new ImageIcon(LoanBooks.class.getResource("/img/lupe32.png")));
		txtDniUser = new JTextField();
		txtDniUser.setBounds(124, 77, 101, 20);
		panel.add(txtDniUser);
		txtDniUser.setColumns(10);
		
		JLabel lblDniUsuario = new JLabel("DNI Usuario");
		lblDniUsuario.setBounds(29, 78, 89, 14);
		panel.add(lblDniUsuario);
		lblDniUsuario.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JLabel btnSearchUser_1 = new JLabel("");
		btnSearchUser_1.setBounds(230, 120, 32, 32);
		panel.add(btnSearchUser_1);
		btnSearchUser_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				BooksList bl = new BooksList();
				bl.setVisible(true);
			}
		});
		btnSearchUser_1.setIcon(new ImageIcon(LoanBooks.class.getResource("/img/lupe32.png")));
		
		txtIdBook = new JTextField();
		txtIdBook.setBounds(124, 125, 101, 20);
		panel.add(txtIdBook);
		txtIdBook.setColumns(10);
		
		JLabel lblIdLibro = new JLabel("Id Libro");
		lblIdLibro.setBounds(29, 125, 73, 14);
		panel.add(lblIdLibro);
		lblIdLibro.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JLabel lblIdBibliotecario = new JLabel("ID Bibliotecario");
		lblIdBibliotecario.setBounds(294, 78, 122, 14);
		panel.add(lblIdBibliotecario);
		lblIdBibliotecario.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JLabel lblFechaDeEntrega = new JLabel("Fecha de Entrega");
		lblFechaDeEntrega.setBounds(293, 31, 145, 14);
		panel.add(lblFechaDeEntrega);
		lblFechaDeEntrega.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		dtcFecha = new JDateChooser();
		dtcFecha.setBounds(426, 30, 101, 20);
		panel.add(dtcFecha);
		
		cboBibliotecarios = new JComboBox();
		cboBibliotecarios.setBounds(426, 72, 101, 21);
		panel.add(cboBibliotecarios);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 582, 22);
		contentPane.add(menuBar);
		
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
		miCerrarSesion.setIcon(new ImageIcon(LoanBooks.class.getResource("/img/sesion.png")));
		mnNewMenu.add(miCerrarSesion);
		
		JMenuItem miSalir = new JMenuItem("Salir");
		miSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		miSalir.setIcon(new ImageIcon(LoanBooks.class.getResource("/img/exit.png")));
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
		miBib.setIcon(new ImageIcon(LoanBooks.class.getResource("/img/Bibliothecary.png")));
		mnNewMenu_1.add(miBib);
		
		JMenuItem miBook = new JMenuItem("Libros");
		miBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				BooksRegister booksRegister =  new BooksRegister();
				booksRegister.setVisible(true);
				dispose();
			}
		});
		miBook.setIcon(new ImageIcon(LoanBooks.class.getResource("/img/books.png")));
		mnNewMenu_1.add(miBook);
		
		JMenuItem miUser = new JMenuItem("Usuarios");
		miUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				UserRegister userRegister = new UserRegister();
				userRegister.setVisible(true);
				dispose();
			}
		});
		miUser.setIcon(new ImageIcon(LoanBooks.class.getResource("/img/user.png")));
		mnNewMenu_1.add(miUser);
		
		JMenu mnNewMenu_2 = new JMenu("Reportes");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Reportes");
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				Reports reports = new Reports();
				reports.setVisible(true);
			}
		});


		txtIdBook.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				boolean disponible = verificarLibroDisponible();
			}

			@Override  
			public void changedUpdate(DocumentEvent e) {
			   // boolean i = verificarLibroDisponible();
			  }
	
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				boolean disponible = verificarLibroDisponible();
			}
		});
		
		//setIdPrestamo();
		listarBibliotecarios();
		listar();
	}
	
	public void listarBibliotecarios() {
		BibliotecarioController bc = new BibliotecarioController();
		ArrayList<Bibliotecario> bibArrayList = bc.listar();
		for(Bibliotecario bibliotecario : bibArrayList) {
			cboBibliotecarios.addItem(bibliotecario.getIdBib());
		}
	}
	
	
	public String leerUsuarios() {
		UsuarioController uc = new UsuarioController();
		ArrayList<Usuario> userArrayList = uc.listar();
		for(Usuario user : userArrayList) {
			if(user.getDniUser().equals(txtDniUser.getText())) return txtDniUser.getText();
		}
		aviso("Usuario no encontrado");
		return null;
	}
	
	public String leerLibro(int i) {
		LibroController lc = new LibroController();
		ArrayList<Libro> libroArrayList = lc.listar("estado_placed_book","1");
		for(Libro libro : libroArrayList) {
			if(libro.getIdLibro().equals(txtIdBook.getText())) return txtIdBook.getText();
		}
		if(i == 1) aviso("Libro no encontrado");		
		return null;
	}
	
	String leerFecha() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").format(dtcFecha.getDate());
		}catch(Exception e) {
			aviso("Error de lectura de fecha");
			return null;
		}
		
	}
	

	public void listar() {
		ArrayList<Prestamo> prestamoArrayList = pc.listar();
		model.setRowCount(0);
		if(!prestamoArrayList.isEmpty()) {
			for(Prestamo prestamo : prestamoArrayList) {
				Object[] o = {prestamo.getIdPrest(), prestamo.getIdLibro(), prestamo.getDniUser(), prestamo.getIdBib(), prestamo.getFechaEntregaPrest()};
				model.addRow(o);
			}
		}
	}
	
	
	void aviso(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	

	boolean verificarLibroDisponible()
	{
		String idBook = leerLibro(2);
		if(idBook != null) {
			LibroController lc = new LibroController();
			ArrayList<Libro> libroArrayList = lc.listar("estado_loan_book","1");
			for(Libro libro : libroArrayList) {
				if(libro.getIdLibro().equals(idBook)) {
					btnPrestar.setEnabled(true);
					return true;		
				}
			}
		}
		btnPrestar.setEnabled(false);
		return false;
	}
	
	public void generarTicket(Prestamo loan) {
		
		String nameFile = "Ticket de Préstamo.pdf";
		try {
			
			Document d = new Document();
			
			FileOutputStream fos = new FileOutputStream(nameFile);
			
			PdfWriter pdfW = PdfWriter.getInstance(d, fos);
			
			d.open();
			
			//Printing Date
			Paragraph pDate  = new Paragraph("Fecha: " + new SimpleDateFormat().format(new Date()), FontFactory.getFont("arial",11) );
			pDate.setAlignment(Chunk.ALIGN_RIGHT);
			d.add(pDate);
			
			//Printing Title
			Paragraph p = new Paragraph("Ticket de Préstamo", FontFactory.getFont("arial",16));
			p.setAlignment(Chunk.ALIGN_CENTER);
			d.add(p);
			
			d.add(new Paragraph(" ")); // Line feed
			
			//Building table
			PdfPTable table = new PdfPTable(5);
			
			//Headers of table
			table.addCell("Préstamo");
			table.addCell("Libro");
			table.addCell("DNI Usuario");
			table.addCell("Entrega");
			table.addCell("Bibliotecario");

			//Values of table

			table.addCell(loan.getIdPrest());
			table.addCell(loan.getIdLibro());
			table.addCell(loan.getDniUser());
			table.addCell(loan.getFechaEntregaPrest());
			table.addCell(loan.getIdBib());

			
			
			d.add(table);
			
			d.close();
			
			Desktop.getDesktop().open(new File(nameFile));
			
			
		}catch(Exception er) {
			System.out.println("Error al crear archivo: " + er.getMessage());
		}
	}
}
