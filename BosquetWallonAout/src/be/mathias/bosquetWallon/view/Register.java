package be.mathias.bosquetWallon.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import be.mathias.bosquetWallon.model.data.DaoFactory;
import be.mathias.bosquetWallon.model.data.PersonDao;
import be.mathias.bosquetWallon.model.logic.Organizer;
import be.mathias.bosquetWallon.model.logic.Person;
import be.mathias.bosquetWallon.model.logic.Person.PersonRole;
import be.mathias.bosquetWallon.model.logic.Spectator;
import be.mathias.bosquetWallon.model.logic.Spectator.Gender;

public class Register extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1335445877061059701L;

	private static JPanel panel;
	
	private JPasswordField passwordField;
	private JTextField emailField;
	private JTextField firstNameField;
	private JTextField lastNameField;
	
	private JRadioButton rdSpectator;
	private JRadioButton rdOrganizer;
	private JTextField orgPhoneField;
	private JLayeredPane lPane_userRole;
	
	private JRadioButton rdSpeMan;
	private JRadioButton rdSpeWoman;
	private JFormattedTextField speBirthDateField;
	private JTextField spePhoneField;
	
	private PersonRole role = PersonRole.Spectator;
	private Spectator.Gender gender = Gender.Man;
	private JTextArea addressField;
	
	private Register() {
		panel = new JPanel();
		
		GridBagLayout gbl_register = new GridBagLayout();
		gbl_register.columnWidths = new int[]{0, 350, 350, 0, 0};
		gbl_register.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_register.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_register.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_register);
		
		JLabel title = new JLabel("Nouvel utilisateur");
		title.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_title = new GridBagConstraints();
		gbc_title.gridwidth = 4;
		gbc_title.insets = new Insets(0, 0, 5, 0);
		gbc_title.gridx = 0;
		gbc_title.gridy = 0;
		panel.add(title, gbc_title);
		
		JLabel lbFirstname = new JLabel("Prénom");
		GridBagConstraints gbc_lbFirstname = new GridBagConstraints();
		gbc_lbFirstname.gridwidth = 2;
		gbc_lbFirstname.insets = new Insets(0, 0, 5, 5);
		gbc_lbFirstname.gridx = 1;
		gbc_lbFirstname.gridy = 1;
		panel.add(lbFirstname, gbc_lbFirstname);
		
		firstNameField = new JTextField();
		firstNameField.setColumns(25);
		GridBagConstraints gbc_firstNameField = new GridBagConstraints();
		gbc_firstNameField.gridwidth = 2;
		gbc_firstNameField.insets = new Insets(0, 0, 5, 5);
		gbc_firstNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_firstNameField.gridx = 1;
		gbc_firstNameField.gridy = 2;
		panel.add(firstNameField, gbc_firstNameField);
		
		JLabel lbName = new JLabel("Nom");
		GridBagConstraints gbc_lbName = new GridBagConstraints();
		gbc_lbName.gridwidth = 2;
		gbc_lbName.insets = new Insets(0, 0, 5, 5);
		gbc_lbName.gridx = 1;
		gbc_lbName.gridy = 3;
		panel.add(lbName, gbc_lbName);
		
		lastNameField = new JTextField();
		lastNameField.setColumns(25);
		GridBagConstraints gbc_lastNameField = new GridBagConstraints();
		gbc_lastNameField.gridwidth = 2;
		gbc_lastNameField.insets = new Insets(0, 0, 5, 5);
		gbc_lastNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastNameField.gridx = 1;
		gbc_lastNameField.gridy = 4;
		panel.add(lastNameField, gbc_lastNameField);
		
		JLabel lblNewLabel_4 = new JLabel("Email");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.gridwidth = 2;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 5;
		panel.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		emailField = new JTextField();
		emailField.setColumns(25);
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.gridwidth = 2;
		gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailField.insets = new Insets(0, 0, 5, 5);
		gbc_emailField.gridx = 1;
		gbc_emailField.gridy = 6;
		panel.add(emailField, gbc_emailField);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 4;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 7;
		panel.add(separator, gbc_separator);
		
		JLabel lblNewLabel_3 = new JLabel("Mot de passe");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.gridwidth = 2;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 8;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(25);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 2;
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 9;
		panel.add(passwordField, gbc_passwordField);
		
		JLabel lbAddress = new JLabel("Adresse");
		GridBagConstraints gbc_lbAddress = new GridBagConstraints();
		gbc_lbAddress.gridwidth = 2;
		gbc_lbAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lbAddress.gridx = 1;
		gbc_lbAddress.gridy = 10;
		panel.add(lbAddress, gbc_lbAddress);
		
		addressField = new JTextArea();
		addressField.setTabSize(4);
		addressField.setRows(3);
		addressField.setColumns(25);
		addressField.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_addressField = new GridBagConstraints();
		gbc_addressField.gridwidth = 2;
		gbc_addressField.insets = new Insets(0, 0, 5, 5);
		gbc_addressField.fill = GridBagConstraints.BOTH;
		gbc_addressField.gridx = 1;
		gbc_addressField.gridy = 11;
		panel.add(addressField, gbc_addressField);
		
		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.gridwidth = 4;
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 12;
		panel.add(separator_1, gbc_separator_1);
		
		JLabel lbType = new JLabel("Type");
		GridBagConstraints gbc_lbType = new GridBagConstraints();
		gbc_lbType.gridwidth = 2;
		gbc_lbType.insets = new Insets(0, 0, 5, 5);
		gbc_lbType.gridx = 1;
		gbc_lbType.gridy = 13;
		panel.add(lbType, gbc_lbType);
		
		rdSpectator = new JRadioButton("Spectateur");
		rdSpectator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdSpectator.isSelected()) {
					role = PersonRole.Spectator;
					rdOrganizer.setSelected(false);
					CardLayout cards = (CardLayout) lPane_userRole.getLayout();
					cards.show(lPane_userRole, "spectator" );
				}
			}
		});
		rdSpectator.setSelected(true);
		GridBagConstraints gbc_rdSpectator = new GridBagConstraints();
		gbc_rdSpectator.insets = new Insets(0, 0, 5, 5);
		gbc_rdSpectator.gridx = 1;
		gbc_rdSpectator.gridy = 14;
		panel.add(rdSpectator, gbc_rdSpectator);
		
		rdOrganizer = new JRadioButton("Organisateur");
		rdOrganizer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdOrganizer.isSelected()) {
					role = PersonRole.Organizer;
					rdSpectator.setSelected(false);
					CardLayout cards = (CardLayout) lPane_userRole.getLayout();
					cards.show(lPane_userRole, "organizer" );
				}
			}
		});
		GridBagConstraints gbc_rdOrganizer = new GridBagConstraints();
		gbc_rdOrganizer.insets = new Insets(0, 0, 5, 5);
		gbc_rdOrganizer.gridx = 2;
		gbc_rdOrganizer.gridy = 14;
		panel.add(rdOrganizer, gbc_rdOrganizer);
		
		lPane_userRole = new JLayeredPane();
		GridBagConstraints gbc_lPane_userRole = new GridBagConstraints();
		gbc_lPane_userRole.gridwidth = 2;
		gbc_lPane_userRole.insets = new Insets(0, 0, 5, 5);
		gbc_lPane_userRole.fill = GridBagConstraints.BOTH;
		gbc_lPane_userRole.gridx = 1;
		gbc_lPane_userRole.gridy = 15;
		panel.add(lPane_userRole, gbc_lPane_userRole);
		lPane_userRole.setLayout(new CardLayout(0, 0));
		
		JPanel spectator = new JPanel();
		lPane_userRole.add(spectator, "spectator");
		GridBagLayout gbl_spectator = new GridBagLayout();
		gbl_spectator.columnWidths = new int[] {138, 110, 110, 0, 30};
		gbl_spectator.rowHeights = new int[] {34, 34, 34, 10};
		gbl_spectator.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_spectator.rowWeights = new double[]{0.0, 0.0, 0.0};
		spectator.setLayout(gbl_spectator);
		
		JLabel lblNewLabel = new JLabel("Numéro de téléphone");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		spectator.add(lblNewLabel, gbc_lblNewLabel);
		
		spePhoneField = new JTextField();
		GridBagConstraints gbc_spePhoneField = new GridBagConstraints();
		gbc_spePhoneField.fill = GridBagConstraints.HORIZONTAL;
		gbc_spePhoneField.gridwidth = 2;
		gbc_spePhoneField.insets = new Insets(0, 0, 5, 5);
		gbc_spePhoneField.gridx = 1;
		gbc_spePhoneField.gridy = 0;
		spectator.add(spePhoneField, gbc_spePhoneField);
		spePhoneField.setColumns(10);
		
		JLabel lblDateDeNaissance = new JLabel("Date de naissance");
		GridBagConstraints gbc_lblDateDeNaissance = new GridBagConstraints();
		gbc_lblDateDeNaissance.anchor = GridBagConstraints.EAST;
		gbc_lblDateDeNaissance.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateDeNaissance.gridx = 0;
		gbc_lblDateDeNaissance.gridy = 1;
		spectator.add(lblDateDeNaissance, gbc_lblDateDeNaissance);
		
		speBirthDateField = new JFormattedTextField((Format) null);
		speBirthDateField.setToolTipText("jj/mm/aaaa");
		speBirthDateField.setText("01/01/1900");
		speBirthDateField.setColumns(25);
		GridBagConstraints gbc_speBirthDateField = new GridBagConstraints();
		gbc_speBirthDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_speBirthDateField.gridwidth = 2;
		gbc_speBirthDateField.insets = new Insets(0, 0, 5, 5);
		gbc_speBirthDateField.gridx = 1;
		gbc_speBirthDateField.gridy = 1;
		spectator.add(speBirthDateField, gbc_speBirthDateField);
		
		JLabel lblNewLabel_1 = new JLabel("Genre");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		spectator.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		rdSpeMan = new JRadioButton("Homme");
		rdSpeMan.setSelected(true);
		rdSpeMan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdSpeMan.isSelected()) {
					rdSpeWoman.setSelected(false);
					gender = Gender.Man;
				}
			}
		});
		GridBagConstraints gbc_rdSpeMan = new GridBagConstraints();
		gbc_rdSpeMan.insets = new Insets(0, 0, 5, 5);
		gbc_rdSpeMan.gridx = 1;
		gbc_rdSpeMan.gridy = 2;
		spectator.add(rdSpeMan, gbc_rdSpeMan);
		
		rdSpeWoman = new JRadioButton("Femme");
		rdSpeWoman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdSpeWoman.isSelected()) {
					rdSpeMan.setSelected(false);
					gender = Gender.Woman;
				}
			}
		});
		GridBagConstraints gbc_rdSpeWoman = new GridBagConstraints();
		gbc_rdSpeWoman.insets = new Insets(0, 0, 5, 5);
		gbc_rdSpeWoman.gridx = 2;
		gbc_rdSpeWoman.gridy = 2;
		spectator.add(rdSpeWoman, gbc_rdSpeWoman);
		
		JPanel organizer = new JPanel();
		lPane_userRole.add(organizer, "organizer");
		GridBagLayout gbl_organizer = new GridBagLayout();
		gbl_organizer.columnWidths = new int[] {138, 220, 30};
		gbl_organizer.rowHeights = new int[]{35, 35, 0};
		gbl_organizer.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_organizer.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		organizer.setLayout(gbl_organizer);
		
		JLabel lbl_Org_phone = new JLabel("Numéro de téléphone");
		GridBagConstraints gbc_lbl_Org_phone = new GridBagConstraints();
		gbc_lbl_Org_phone.anchor = GridBagConstraints.EAST;
		gbc_lbl_Org_phone.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Org_phone.gridx = 0;
		gbc_lbl_Org_phone.gridy = 0;
		organizer.add(lbl_Org_phone, gbc_lbl_Org_phone);
		
		orgPhoneField = new JTextField();
		orgPhoneField.setColumns(25);
		GridBagConstraints gbc_orgPhoneField = new GridBagConstraints();
		gbc_orgPhoneField.insets = new Insets(0, 0, 5, 5);
		gbc_orgPhoneField.gridx = 1;
		gbc_orgPhoneField.gridy = 0;
		organizer.add(orgPhoneField, gbc_orgPhoneField);
		
		JButton btnValidate = new JButton("Inscription");
		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
		GridBagConstraints gbc_btnValidate = new GridBagConstraints();
		gbc_btnValidate.anchor = GridBagConstraints.NORTH;
		gbc_btnValidate.gridwidth = 4;
		gbc_btnValidate.gridx = 0;
		gbc_btnValidate.gridy = 16;
		panel.add(btnValidate, gbc_btnValidate);
	}
	
	public static JPanel get() {
		if(panel == null)
			new Register();
		return panel;
	}
	
	private void register() {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String address = addressField.getText();
		String password = String.valueOf(passwordField.getPassword());
		String email = emailField.getText();
		String phoneNumber;
		String birthDate = null;
		if(role == PersonRole.Spectator) {
			phoneNumber = spePhoneField.getText();
			birthDate = speBirthDateField.getText();
		}else
			phoneNumber = orgPhoneField.getText();
		
		boolean valid = !firstName.isBlank() && !lastName.isBlank() && !address.isBlank() && !password.isBlank() && !email.isBlank() && Main.checkEmail(email) && !phoneNumber.isBlank();
		if(role == PersonRole.Spectator)
			valid = valid && !birthDate.isBlank() && Main.checkDate(birthDate);
		
		if(!valid) {
			JOptionPane.showMessageDialog(null, "Le formulaire n'est pas valide :\n"
					+ "- Tous les champs sont obligatoires\n"
					+ "- L'email et éventuellement la date doivent être corrects");
			return;
		}
		
		Person user;
		if(role == PersonRole.Spectator) {
			LocalDate bDate;
			try {
				bDate = LocalDate.parse(birthDate, Main.getDateFormat());
			}catch(DateTimeParseException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "La date n'est pas dans un format correct : dd/MM/yyyy");
				return;
			}
			
			user = new Spectator(firstName, lastName, address, password, email, phoneNumber, gender, bDate);
		}else {
			user = new Organizer(firstName, lastName, address, password, email, phoneNumber);
		}
			
		PersonDao personDao = (PersonDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetPersonDao();
		personDao.create(user);
		Main.setUser(user);
		
		Main.switchPane("connection");
	}

}
