package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import Controller.LibroController;
import Controller.UsuarioController;
import Model.Libro;
import Model.Usuario;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BooksList extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private DefaultTableModel model;
	private JTable table;
	private JTextField txtFilter;
	private JComboBox cboEstate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BooksList dialog = new BooksList();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BooksList() {
		setResizable(false);
		setBounds(100, 100, 488, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 53, 446, 169);
			contentPanel.add(scrollPane);
			{
				table = new JTable();	
				model = new DefaultTableModel();
				model.addColumn("ID");
				model.addColumn("Título");
				model.addColumn("Autor");
				model.addColumn("Categoría");
				model.addColumn("País");
				model.addColumn("Año");	
				
				table.setModel(model);

				scrollPane.setViewportView(table);
			}
			
		}
		{
			JLabel lblNewLabel = new JLabel("Filtro");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel.setBounds(30, 20, 45, 13);
			contentPanel.add(lblNewLabel);
		}
		
			txtFilter = new JTextField();
		
			txtFilter.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
					String input = txtFilter.getText();
					
					//Ask if the book is loaned or not
					String availability = (String) cboEstate.getSelectedItem();
					
					switch(availability) {
						case "Disponible": listar("1", input); break;
						case "Prestado": listar("2", input); break;
					}
				}

				@Override  
				public void changedUpdate(DocumentEvent e) {
				}
	
				@Override
				public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
					String input = txtFilter.getText();
					
					//Ask if the book is loaned or not
					String availability = (String) cboEstate.getSelectedItem();
					
					switch(availability) {
						case "Disponible": listar("1", input); break;
						case "Prestado": listar("2", input); break;
					}
				}
		});
		
		txtFilter.setBounds(69, 18, 96, 19);
		contentPanel.add(txtFilter);
		txtFilter.setColumns(10);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEstado.setBounds(199, 20, 45, 13);
		contentPanel.add(lblEstado);
		
		cboEstate = new JComboBox();
		cboEstate.setModel(new DefaultComboBoxModel(new String[] {"Disponible", "Prestado"}));
		cboEstate.setBounds(254, 16, 96, 22);
		contentPanel.add(cboEstate);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Ask if the book is loaned or not
				String availability = (String) cboEstate.getSelectedItem();
				
				switch(availability) {
					case "Disponible": listar("1"); break;
					case "Prestado": listar("2"); break;
				}
			}
		});
		btnBuscar.setBounds(380, 16, 76, 23);
		contentPanel.add(btnBuscar);

		listar("1");
		
		//contentPanel.setView
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						int row = table.getSelectedRow();
						
						if(row != -1) {
							String bookID = (String) model.getValueAt(row, 0);
							LoanBooks.txtIdBook.setText(bookID);
							dispose();
							
						}else JOptionPane.showMessageDialog(null, "No ha seleccionado ningún libro");
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}

	}
	
	public void listar(String estado, String filtro) {
		LibroController lc = new LibroController();
		ArrayList<Libro> booksArrayList = lc.listar("estado_loan_book",estado);
		model.setRowCount(0);
		if(booksArrayList != null) {
			for (Libro libro : booksArrayList) {
				Object[] object = {libro.getIdLibro(), libro.getTitLibro(), libro.getAutorLibro(), libro.getCatLibro(), libro.getPaisLibro(), libro.getAñoLibro().substring(0, 4)};

					if(libro.getIdLibro().contains(filtro) || libro.getTitLibro().contains(filtro) || libro.getAutorLibro().contains(filtro)
							 || libro.getCatLibro().contains(filtro) || libro.getPaisLibro().contains(filtro) || libro.getAñoLibro().contains(filtro)) {
						model.addRow(object);
					
					}
				
			}
		}	
	}
	
	public void listar(String estado) {
		LibroController lc = new LibroController();
		ArrayList<Libro> booksArrayList = lc.listar("estado_placed_book = 1 AND estado_loan_book",estado);
		model.setRowCount(0);
		if(!booksArrayList.isEmpty()) {
			for (Libro libro : booksArrayList) {
				Object[] object = {libro.getIdLibro(), libro.getTitLibro(), libro.getAutorLibro(), libro.getCatLibro(), libro.getPaisLibro(), libro.getAñoLibro().substring(0, 4)};
				model.addRow(object);
			}
		}
		
	}
}
