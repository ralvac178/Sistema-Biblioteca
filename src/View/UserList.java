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

import Controller.UsuarioController;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserList extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private DefaultTableModel model;
	private JTable table;
	private JTextField txtFiltro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UserList dialog = new UserList();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public UserList() {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 53, 412, 169);
			contentPanel.add(scrollPane);
			{
				table = new JTable();
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						
					
						
					}
				});
				model = new DefaultTableModel();
				model.addColumn("DNI");
				model.addColumn("Nombres");
				model.addColumn("Apellidos");
				model.addColumn("Edad");
				model.addColumn("Telefono");
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
		
		txtFiltro = new JTextField();
		
		txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
				String input = txtFiltro.getText();
				listar(input);
			}

			@Override  
			public void changedUpdate(DocumentEvent e) {
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
				String input = txtFiltro.getText();
				listar(input);
			}
	});
		
		txtFiltro.setBounds(69, 18, 96, 19);
		contentPanel.add(txtFiltro);
		txtFiltro.setColumns(10);

		listar();
		
		//contentPanel.setView
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						//Select an Item
						int row = table.getSelectedRow();
						
						if(row != -1) {
							String dniUser = (String) model.getValueAt(row, 0);
							
							LoanBooks.txtDniUser.setText(dniUser);	
							dispose();
						}else {
							JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila");
						}
																
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
	}
	
	public void listar() {
		UsuarioController uc = new UsuarioController();
		ArrayList<Usuario> userArrayList = uc.listar();
		model.setRowCount(0);
		if(userArrayList != null) {
			for (Usuario usuario : userArrayList) {
				Object[] object = {usuario.getDniUser(), usuario.getNomUser(), usuario.getApellUser(), usuario.getEdadUser(), usuario.getTelfUser()};
				model.addRow(object);
			}
		}	
	}
	
	public void listar(String filtro) {
		UsuarioController uc = new UsuarioController();
		ArrayList<Usuario> userArrayList = uc.listar();
		model.setRowCount(0);
		if(!userArrayList.isEmpty()) {
			for (Usuario usuario : userArrayList) {
				Object[] object = {usuario.getDniUser(), usuario.getNomUser(), usuario.getApellUser(), usuario.getEdadUser(), usuario.getTelfUser()};
				if(usuario.getDniUser().contains(filtro) || usuario.getNomUser().contains(filtro) || usuario.getApellUser().contains(filtro)
						 || String.valueOf(usuario.getEdadUser()).contains(filtro) || usuario.getTelfUser().contains(filtro)) {
					model.addRow(object);
				
				}
			}
		}	
	}
}
