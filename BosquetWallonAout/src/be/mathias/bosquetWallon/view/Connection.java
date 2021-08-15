package be.mathias.bosquetWallon.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import be.mathias.bosquetWallon.model.data.DaoFactory;
import be.mathias.bosquetWallon.model.data.PersonDao;
import be.mathias.bosquetWallon.model.logic.Person;

public class Connection extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3765529209476228665L;
	private static JPanel panel;
	private JTextField emailField;
	private JPasswordField passwordField;
	
	private Connection() {
		panel = new JPanel();
		GridBagLayout gbl_connection = new GridBagLayout();
		gbl_connection.columnWidths = new int[] {30, 90, 130, 130, 130, 30};
		gbl_connection.rowHeights = new int[]{0, 0, 10, 0, 0, 10, 0, 0, 0};
		gbl_connection.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		gbl_connection.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_connection);
		
		JLabel lbl_connexion = new JLabel("Connexion");
		lbl_connexion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lbl_connexion = new GridBagConstraints();
		gbc_lbl_connexion.gridwidth = 4;
		gbc_lbl_connexion.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_connexion.gridx = 1;
		gbc_lbl_connexion.gridy = 1;
		panel.add(lbl_connexion, gbc_lbl_connexion);
		
		JLabel lbl_email = new JLabel("Email");
		GridBagConstraints gbc_lbl_email = new GridBagConstraints();
		gbc_lbl_email.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_email.gridx = 1;
		gbc_lbl_email.gridy = 3;
		panel.add(lbl_email, gbc_lbl_email);
		
		emailField = new JTextField();
		emailField.setColumns(25);
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.gridwidth = 3;
		gbc_emailField.insets = new Insets(0, 0, 5, 5);
		gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailField.gridx = 2;
		gbc_emailField.gridy = 3;
		panel.add(emailField, gbc_emailField);
		
		JLabel lbl_mdp = new JLabel("Mot de passe");
		GridBagConstraints gbc_lbl_mdp = new GridBagConstraints();
		gbc_lbl_mdp.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_mdp.gridx = 1;
		gbc_lbl_mdp.gridy = 4;
		panel.add(lbl_mdp, gbc_lbl_mdp);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(25);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 4;
		panel.add(passwordField, gbc_passwordField);
		
		JButton btn_inscription = new JButton("Inscription");
		btn_inscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.switchPane("register");
			}
		});
		GridBagConstraints gbc_btn_inscription = new GridBagConstraints();
		gbc_btn_inscription.gridwidth = 2;
		gbc_btn_inscription.insets = new Insets(0, 0, 5, 5);
		gbc_btn_inscription.gridx = 1;
		gbc_btn_inscription.gridy = 6;
		panel.add(btn_inscription, gbc_btn_inscription);
		
		JButton btn_connexion = new JButton("Connexion");
		btn_connexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		GridBagConstraints gbc_btn_connexion = new GridBagConstraints();
		gbc_btn_connexion.gridwidth = 2;
		gbc_btn_connexion.insets = new Insets(0, 0, 5, 5);
		gbc_btn_connexion.gridx = 3;
		gbc_btn_connexion.gridy = 6;
		panel.add(btn_connexion, gbc_btn_connexion);
	}
	
	public static JPanel get() {
		if(panel == null)
			new Connection();
		return panel;
	}
	
	private void connect() {
		if(emailField.getText().isBlank() || passwordField.getPassword().length == 0) {
			JOptionPane.showMessageDialog(null, "Erreur, veuillez réessayer.");
			return;
			}
		
		String emailAddress = emailField.getText();
		String password = String.valueOf(passwordField.getPassword());
		String passwordHash = Person.HashPassword(password);
		
		PersonDao personDao = (PersonDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetPersonDao();
		Person user = personDao.findOnConnection(emailAddress, passwordHash);
		
		if(user != null) {
			Main.setUser(user);
			switch (user.getRole()) {
			case Spectator:
				Main.setShowList();
				Main.switchPane("showList");
				break;
			case Organizer:
				Main.setListBooking();
				Main.switchPane("listBooking");
				break;
			case Manager:
				Main.setListBooking();
				Main.switchPane("listBooking");
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + user.getRole());
			}
		}else
			JOptionPane.showMessageDialog(null, "Erreur, veuillez réessayer.");
	}
}
