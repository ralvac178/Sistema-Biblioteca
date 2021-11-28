package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Controller.BibliotecarioController;
import Controller.LibroController;
import Controller.PrestamoController;
import Controller.UsuarioController;
import Model.Bibliotecario;
import Model.Libro;
import Model.Prestamo;
import Model.Usuario;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;

public class Reports extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reports frame = new Reports();
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
	public Reports() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Reports.class.getResource("/img/library.jpg")));
		setTitle("Gesti\u00F3n de Reportes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnUsers = new JButton("Usuarios");
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nameFile = "Reporte Usuarios.pdf";
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
					Paragraph p = new Paragraph("Reporte de Usuarios", FontFactory.getFont("arial",16));
					p.setAlignment(Chunk.ALIGN_CENTER);
					d.add(p);
					
					d.add(new Paragraph(" ")); // Line feed
					
					//Building table
					PdfPTable table = new PdfPTable(4);
					UsuarioController uc = new UsuarioController();
					ArrayList<Usuario> userArrayList = uc.listar();
					
					//Headers of table
					table.addCell("DNI");
					table.addCell("Nombres");
					table.addCell("Apellidos");
					table.addCell("Teléfono");

					//Values of table
					for(Usuario user : userArrayList) {
						table.addCell(user.getDniUser());
						table.addCell(user.getNomUser());
						table.addCell(user.getApellUser());
						table.addCell(user.getTelfUser());
					}
					
					
					d.add(table);
					
					d.close();
					
					Desktop.getDesktop().open(new File(nameFile));
					
					
				}catch(Exception er) {
					System.out.println("Error al crear archivo: " + er.getMessage());
				}
			}
		});
		btnUsers.setBounds(244, 63, 89, 23);
		contentPane.add(btnUsers);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Mostrar Reportes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(36, 27, 357, 158);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnBooks = new JButton("Libros");
		btnBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nameFile = "Reporte Libros.pdf";
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
					Paragraph p = new Paragraph("Reporte de Libros", FontFactory.getFont("arial",16));
					p.setAlignment(Chunk.ALIGN_CENTER);
					d.add(p);
					
					d.add(new Paragraph(" ")); // Line feed
					
					//Building table
					PdfPTable table = new PdfPTable(6);
					LibroController lc = new LibroController();
					ArrayList<Libro> libroArrayList = lc.listar("estado_loan_book","1");
					
					//Headers of table
					table.addCell("ID");
					table.addCell("Título");
					table.addCell("Autor");
					table.addCell("Categoría");
					table.addCell("País");
					table.addCell("Año");
					//Values of table
					for(Libro libro : libroArrayList) {
						table.addCell(libro.getIdLibro());
						table.addCell(libro.getTitLibro());
						table.addCell(libro.getAutorLibro());
						table.addCell(libro.getCatLibro());
						table.addCell(libro.getPaisLibro());
						table.addCell(libro.getAñoLibro());
					}
					
					
					d.add(table);
					
					
					d.close();
					
					Desktop.getDesktop().open(new File(nameFile));
					
					
				}catch(Exception er) {
					System.out.println("Error al crear archivo: " + er.getMessage());
				}
			}
		});
		btnBooks.setBounds(58, 104, 89, 23);
		panel.add(btnBooks);
		
		JButton btnLoan = new JButton("Pr\u00E9stamos");
		btnLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nameFile = "Reporte Préstamos.pdf";
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
					Paragraph p = new Paragraph("Reporte de Préstamos", FontFactory.getFont("arial",16));
					p.setAlignment(Chunk.ALIGN_CENTER);
					d.add(p);
					
					d.add(new Paragraph(" ")); // Line feed
					
					//Building table
					PdfPTable table = new PdfPTable(6);
					PrestamoController pc = new PrestamoController();
					ArrayList<Prestamo> loanArrayList = pc.listar();
					
					//Headers of table
					table.addCell("Préstamo");
					table.addCell("Libro");
					table.addCell("DNI Usuario");
					table.addCell("Préstamo");
					table.addCell("Entrega");
					table.addCell("Bibliotecario");

					//Values of table
					for(Prestamo loan : loanArrayList) {
						table.addCell(loan.getIdPrest());
						table.addCell(loan.getIdLibro());
						table.addCell(loan.getDniUser());
						table.addCell(loan.getFechaPrest());
						table.addCell(loan.getFechaEntregaPrest());
						table.addCell(loan.getIdBib());
					}
					
					
					d.add(table);
					
					d.close();
					
					Desktop.getDesktop().open(new File(nameFile));
					
					
				}catch(Exception er) {
					System.out.println("Error al crear archivo: " + er.getMessage());
				}
			}
		});
		btnLoan.setBounds(197, 104, 110, 23);
		panel.add(btnLoan);
		
		JButton btnBib = new JButton("Bibliotecarios");
		btnBib.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nameFile = "Reporte Bibliotecarios.pdf";
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
					Paragraph p = new Paragraph("Reporte de Bibliotecarios", FontFactory.getFont("arial",16));
					p.setAlignment(Chunk.ALIGN_CENTER);
					d.add(p);
					
					d.add(new Paragraph(" ")); // Line feed
					
					//Building table
					PdfPTable table = new PdfPTable(3);
					BibliotecarioController bc = new BibliotecarioController();
					ArrayList<Bibliotecario> bibArrayList = bc.listar();
					
					//Headers of table
					table.addCell("ID");
					table.addCell("Nombres");
					table.addCell("Apellidos");
					//Values of table
					for(Bibliotecario bib : bibArrayList) {
						table.addCell(bib.getIdBib());
						table.addCell(bib.getNomBib());
						table.addCell(bib.getApellBib());
					}
					
					
					d.add(table);
					d.close();
					
					Desktop.getDesktop().open(new File(nameFile));
					
					
				}catch(Exception er) {
					System.out.println("Error al crear archivo: " + er.getMessage());
				}
				
			}
		});
		btnBib.setBounds(42, 36, 120, 23);
		panel.add(btnBib);
	}
	
	
}
