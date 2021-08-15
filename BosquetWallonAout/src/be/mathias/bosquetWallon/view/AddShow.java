package be.mathias.bosquetWallon.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;


import be.mathias.bosquetWallon.model.logic.Artist;
import be.mathias.bosquetWallon.model.logic.Configuration;
import be.mathias.bosquetWallon.model.logic.Planning;
import be.mathias.bosquetWallon.model.logic.Configuration.Type;
import be.mathias.bosquetWallon.model.logic.Representation;
import be.mathias.bosquetWallon.model.logic.Show;

public class AddShow extends JPanel {
	
	private static final long serialVersionUID = 2585971049072625140L;
	
	private JLabel lblNewLabel_9;
	private JPanel addShowForm;
	private JPanel titrePanel;
	private JPanel descriptionPanel;
	private JPanel ticketPerPersonPanel;
	private JPanel artistPanel;
	private JPanel artistListPanel;
	private JScrollPane artistList;
	private JLabel lblNewLabel_10;
	private JTextField titleField;
	private JLabel lblNewLabel_11;
	private JLabel lblNewLabel_12;
	private JTextField ticketPerPersonField;
	private JTextArea descriptionField;
	
	private JPanel configurationPanel;
	private JRadioButton standingRd;
	private JRadioButton concertRd;
	private JRadioButton circusRd;
	private JLabel configTypeLbl;
	private JTextArea configDescription;
	private JPanel categoriesPanel;

	private JButton validateBtn;
	
	private JPanel representationPanel;
	private JPanel representationListPanel;
	private JScrollPane representationList;
	
	private static JPanel panel;
	
	private Configuration.Type configType = Type.Standing;
	private List<Artist> artists = new ArrayList<Artist>();
	private List<JTextField> categoryPriceFields = new ArrayList<JTextField>();
	private List<Object[]> representationDataList = new ArrayList<Object[]>();

	private AddShow() {
		panel = new JPanel();
		
		GridBagLayout gbl_addShow = new GridBagLayout();
		gbl_addShow.columnWidths = new int[]{4, 0, 4, 0};
		gbl_addShow.rowHeights = new int[]{0, 0, 5, 0};
		gbl_addShow.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_addShow.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_addShow);
		
		lblNewLabel_9 = new JLabel("Nouveau spectacle");
		lblNewLabel_9.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 1;
		gbc_lblNewLabel_9.gridy = 0;
		panel.add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		addShowForm = new JPanel();
		GridBagConstraints gbc_addShowForm = new GridBagConstraints();
		gbc_addShowForm.fill = GridBagConstraints.BOTH;
		gbc_addShowForm.insets = new Insets(0, 0, 5, 5);
		gbc_addShowForm.gridx = 1;
		gbc_addShowForm.gridy = 1;
		panel.add(addShowForm, gbc_addShowForm);
		GridBagLayout gbl_addShowForm = new GridBagLayout();
		gbl_addShowForm.columnWidths = new int[]{719, 0};
		gbl_addShowForm.rowHeights = new int[]{34, 36, 34, 100, 104, 100, 23, 0};
		gbl_addShowForm.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_addShowForm.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		addShowForm.setLayout(gbl_addShowForm);
		
		titrePanel = new JPanel();
		GridBagConstraints gbc_titrePanel = new GridBagConstraints();
		gbc_titrePanel.anchor = GridBagConstraints.NORTH;
		gbc_titrePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_titrePanel.insets = new Insets(0, 0, 5, 0);
		gbc_titrePanel.gridx = 0;
		gbc_titrePanel.gridy = 0;
		addShowForm.add(titrePanel, gbc_titrePanel);
		titrePanel.setLayout(new BoxLayout(titrePanel, BoxLayout.Y_AXIS));
		
		lblNewLabel_10 = new JLabel("Titre");
		lblNewLabel_10.setAlignmentX(Component.CENTER_ALIGNMENT);
		titrePanel.add(lblNewLabel_10);
		
		titleField = new JTextField();
		titrePanel.add(titleField);
		titleField.setColumns(50);
		
		descriptionPanel = new JPanel();
		GridBagConstraints gbc_descriptionPanel = new GridBagConstraints();
		gbc_descriptionPanel.anchor = GridBagConstraints.NORTH;
		gbc_descriptionPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_descriptionPanel.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionPanel.gridx = 0;
		gbc_descriptionPanel.gridy = 1;
		addShowForm.add(descriptionPanel, gbc_descriptionPanel);
		descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
		
		lblNewLabel_11 = new JLabel("Description");
		lblNewLabel_11.setAlignmentX(Component.CENTER_ALIGNMENT);
		descriptionPanel.add(lblNewLabel_11);
		
		descriptionField = new JTextArea();
		descriptionPanel.add(descriptionField);
		
		ticketPerPersonPanel = new JPanel();
		GridBagConstraints gbc_ticketPerPersonPanel = new GridBagConstraints();
		gbc_ticketPerPersonPanel.anchor = GridBagConstraints.NORTH;
		gbc_ticketPerPersonPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_ticketPerPersonPanel.insets = new Insets(0, 0, 5, 0);
		gbc_ticketPerPersonPanel.gridx = 0;
		gbc_ticketPerPersonPanel.gridy = 2;
		addShowForm.add(ticketPerPersonPanel, gbc_ticketPerPersonPanel);
		ticketPerPersonPanel.setLayout(new BoxLayout(ticketPerPersonPanel, BoxLayout.Y_AXIS));
		
		lblNewLabel_12 = new JLabel("Ticket Max. par personne (0 pour annuler)");
		lblNewLabel_12.setAlignmentX(Component.CENTER_ALIGNMENT);
		ticketPerPersonPanel.add(lblNewLabel_12);
		
		ticketPerPersonField = new JTextField();
		ticketPerPersonField.setColumns(10);
		ticketPerPersonPanel.add(ticketPerPersonField);
		
		artistPanel = new JPanel();
		GridBagConstraints gbc_artistPanel = new GridBagConstraints();
		gbc_artistPanel.fill = GridBagConstraints.BOTH;
		gbc_artistPanel.insets = new Insets(0, 0, 5, 0);
		gbc_artistPanel.gridx = 0;
		gbc_artistPanel.gridy = 3;
		addShowForm.add(artistPanel, gbc_artistPanel);
		GridBagLayout gbl_artistPanel = new GridBagLayout();
		gbl_artistPanel.columnWidths = new int[]{719, 0};
		gbl_artistPanel.rowHeights = new int[]{50, 0};
		gbl_artistPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_artistPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		artistPanel.setLayout(gbl_artistPanel);
		
		artistList = new JScrollPane();
		artistList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_artistList = new GridBagConstraints();
		gbc_artistList.fill = GridBagConstraints.BOTH;
		gbc_artistList.gridx = 0;
		gbc_artistList.gridy = 0;
		artistPanel.add(artistList, gbc_artistList);
		
		artistListPanel = new JPanel();
		artistListPanel.setLayout(new BoxLayout(artistListPanel, BoxLayout.Y_AXIS));
		artistList.setViewportView(artistListPanel);
		
		addArtist();
		
		configurationPanel = new JPanel();
		GridBagConstraints gbc_configurationPanel = new GridBagConstraints();
		gbc_configurationPanel.anchor = GridBagConstraints.NORTH;
		gbc_configurationPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_configurationPanel.insets = new Insets(0, 0, 5, 0);
		gbc_configurationPanel.gridx = 0;
		gbc_configurationPanel.gridy = 4;
		addShowForm.add(configurationPanel, gbc_configurationPanel);
		GridBagLayout gbl_configurationPanel = new GridBagLayout();
		gbl_configurationPanel.columnWidths = new int[]{109, 109, 10, 0};
		gbl_configurationPanel.rowHeights = new int[]{0, 23, 0, 0, 0};
		gbl_configurationPanel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_configurationPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		configurationPanel.setLayout(gbl_configurationPanel);
		
		configTypeLbl = new JLabel("Type de configuration");
		GridBagConstraints gbc_configTypeLbl = new GridBagConstraints();
		gbc_configTypeLbl.gridwidth = 3;
		gbc_configTypeLbl.insets = new Insets(0, 0, 5, 0);
		gbc_configTypeLbl.gridx = 0;
		gbc_configTypeLbl.gridy = 0;
		configurationPanel.add(configTypeLbl, gbc_configTypeLbl);
		
		standingRd = new JRadioButton("Debout");
		standingRd.setSelected(true);
		standingRd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(standingRd.isSelected()) {
					concertRd.setSelected(false);
					circusRd.setSelected(false);
					configDescription.setText("Pas de places assises.\n"
							+ "Peut accueilir 8000 personnes.\n");
					configType = Type.Standing;
					setCategories(Configuration.Type.Standing);
				}
			}
		});
		GridBagConstraints gbc_standingRd = new GridBagConstraints();
		gbc_standingRd.insets = new Insets(0, 0, 5, 5);
		gbc_standingRd.gridx = 0;
		gbc_standingRd.gridy = 1;
		configurationPanel.add(standingRd, gbc_standingRd);
		
		concertRd = new JRadioButton("Concert");
		concertRd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(concertRd.isSelected()) {
					standingRd.setSelected(false);
					circusRd.setSelected(false);
					configDescription.setText("5000 places.\n"
							+ "3000 places bronze, 1500 places argent, 500 places or.\n");
					configType = Type.Concert;
					setCategories(Configuration.Type.Concert);
				}
			}
		});
		GridBagConstraints gbc_concertRd = new GridBagConstraints();
		gbc_concertRd.insets = new Insets(0, 0, 5, 5);
		gbc_concertRd.gridx = 1;
		gbc_concertRd.gridy = 1;
		configurationPanel.add(concertRd, gbc_concertRd);
		
		circusRd = new JRadioButton("Cirque");
		circusRd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(circusRd.isSelected()) {
					standingRd.setSelected(false);
					concertRd.setSelected(false);
					configDescription.setText("6000 places.\n"
							+ "1500 places bronze, 1500 places argent, 2000 places or et 1000 places diamant.\n");
					configType = Type.Circus;
					setCategories(Configuration.Type.Circus);
				}
			}
		});
		GridBagConstraints gbc_circusRd = new GridBagConstraints();
		gbc_circusRd.insets = new Insets(0, 0, 5, 0);
		gbc_circusRd.gridx = 2;
		gbc_circusRd.gridy = 1;
		configurationPanel.add(circusRd, gbc_circusRd);
		
		configDescription = new JTextArea();
		configDescription.setMargin(new Insets(2, 10, 2, 10));
		configDescription.setDisabledTextColor(Color.BLACK);
		configDescription.setBackground(UIManager.getColor("Button.background"));
		configDescription.setText("Description de la configuration");
		configDescription.setEditable(false);
		configDescription.setEnabled(false);
		GridBagConstraints gbc_configDescription = new GridBagConstraints();
		gbc_configDescription.insets = new Insets(0, 0, 5, 0);
		gbc_configDescription.gridwidth = 3;
		gbc_configDescription.fill = GridBagConstraints.VERTICAL;
		gbc_configDescription.gridx = 0;
		gbc_configDescription.gridy = 2;
		configurationPanel.add(configDescription, gbc_configDescription);
		
		
		categoriesPanel = new JPanel();
		GridBagConstraints gbc_categoriesPanel = new GridBagConstraints();
		gbc_categoriesPanel.gridwidth = 3;
		gbc_categoriesPanel.insets = new Insets(0, 0, 0, 5);
		gbc_categoriesPanel.fill = GridBagConstraints.BOTH;
		gbc_categoriesPanel.gridx = 0;
		gbc_categoriesPanel.gridy = 3;
		configurationPanel.add(categoriesPanel, gbc_categoriesPanel);
		categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS));
		
		setCategories(Type.Standing);
		
		representationPanel = new JPanel();
		representationPanel.setPreferredSize(new Dimension(350, 50));
		GridBagConstraints gbc_representationPanel = new GridBagConstraints();
		gbc_representationPanel.fill = GridBagConstraints.BOTH;
		gbc_representationPanel.insets = new Insets(0, 0, 5, 0);
		gbc_representationPanel.gridx = 0;
		gbc_representationPanel.gridy = 5;
		addShowForm.add(representationPanel, gbc_representationPanel);
		GridBagLayout gbl_representationPanel = new GridBagLayout();
		gbl_representationPanel.columnWidths = new int[]{681, 0};
		gbl_representationPanel.rowHeights = new int[]{50, 0};
		gbl_representationPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_representationPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		representationPanel.setLayout(gbl_representationPanel);
		
		representationList = new JScrollPane();
		representationList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_representationList = new GridBagConstraints();
		gbc_representationList.fill = GridBagConstraints.BOTH;
		gbc_representationList.gridx = 0;
		gbc_representationList.gridy = 0;
		representationPanel.add(representationList, gbc_representationList);
		
		representationListPanel = new JPanel();
		representationListPanel.setLayout(new BoxLayout(representationListPanel, BoxLayout.Y_AXIS));
		representationList.setViewportView(representationListPanel);
		
		addRepresentation();
		
		
		validateBtn = new JButton("Valider");
		validateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateForm();
			}
		});
		validateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_validateBtn = new GridBagConstraints();
		gbc_validateBtn.anchor = GridBagConstraints.SOUTH;
		gbc_validateBtn.gridx = 0;
		gbc_validateBtn.gridy = 6;
		addShowForm.add(validateBtn, gbc_validateBtn);
	}
	
	private void addArtist() {
		JPanel artist = new JPanel();
		artistListPanel.add(artist);
		GridBagLayout gbl_artist = new GridBagLayout();
		gbl_artist.columnWidths = new int[]{10, 67, 86, 111, 81, 10, 0};
		gbl_artist.rowHeights = new int[]{23, 0};
		gbl_artist.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_artist.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		artist.setLayout(gbl_artist);
		
		JLabel artistNameLbl = new JLabel("Nom de scène");
		GridBagConstraints gbc_artistNameLbl = new GridBagConstraints();
		gbc_artistNameLbl.anchor = GridBagConstraints.EAST;
		gbc_artistNameLbl.insets = new Insets(0, 0, 0, 5);
		gbc_artistNameLbl.gridx = 1;
		gbc_artistNameLbl.gridy = 0;
		artist.add(artistNameLbl, gbc_artistNameLbl);
		
		JTextField textField_3 = new JTextField();
		textField_3.setColumns(10);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.insets = new Insets(0, 0, 0, 5);
		gbc_textField_3.gridx = 2;
		gbc_textField_3.gridy = 0;
		artist.add(textField_3, gbc_textField_3);
		
		JButton removeArtistBtn = new JButton("Supprimer");
		
		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String showName = textField_3.getText();
				if(showName.isBlank())
					return;
				
				textField_3.setEditable(false);
				Artist newArtist = new Artist(" ", " ", " ", " ", " ", showName);
				artists.add(newArtist);
				btnNewButton.setEnabled(false);
				removeArtistBtn.setEnabled(true);
				addArtist();
				artistListPanel.revalidate();
				artistListPanel.repaint();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 0;
		artist.add(btnNewButton, gbc_btnNewButton);
		
		removeArtistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String showName = textField_3.getText();
				artists.removeIf(a -> a.getShowName().equals(showName));
				artistListPanel.remove(artist);
				artistListPanel.revalidate();
				artistListPanel.repaint();
			}
		});
		removeArtistBtn.setEnabled(false);
		GridBagConstraints gbc_removeArtistBtn = new GridBagConstraints();
		gbc_removeArtistBtn.insets = new Insets(0, 0, 0, 5);
		gbc_removeArtistBtn.gridx = 4;
		gbc_removeArtistBtn.gridy = 0;
		artist.add(removeArtistBtn, gbc_removeArtistBtn);
	}

	private void setCategories(Configuration.Type type) {
		categoriesPanel.removeAll();
		categoryPriceFields.clear();
		switch (type) {
		case Standing:
			addCategory("Debout", 8000);
			break;
		case Concert:
			addCategory("Bronze", 3000);
			addCategory("Argent", 1500);
			addCategory("Or", 500);
			break;
		case Circus:
			addCategory("Bronze", 1500);
			addCategory("Argent", 1500);
			addCategory("Or", 2000);
			addCategory("Diamant", 1000);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
		categoriesPanel.repaint();
		categoriesPanel.revalidate();
	}
	
	private void addCategory(String name, int seats) {
				JPanel category = new JPanel();
				categoriesPanel.add(category);
				
				JLabel catNameLbl = new JLabel("Catégorie " + name);
				category.add(catNameLbl);
				
				JLabel maximumTicketsLbl = new JLabel(seats + " places");
				category.add(maximumTicketsLbl);
				
				JLabel priceLbl = new JLabel("Prix :");
				category.add(priceLbl);
				
				JTextField categoryPriceField = new JTextField();
				category.add(categoryPriceField);
				categoryPriceField.setColumns(10);
				
				categoryPriceFields.add(categoryPriceField);
	}
	
	private void addRepresentation() {
		JPanel representation = new JPanel();
		representationListPanel.add(representation);
		GridBagLayout gbl_representation = new GridBagLayout();
		gbl_representation.columnWidths = new int[]{30, 30, 30, 30, 30, 30, 0};
		gbl_representation.rowHeights = new int[]{23, 0, 0};
		gbl_representation.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_representation.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		representation.setLayout(gbl_representation);
		
		JLabel dateLbl = new JLabel("Date : ");
		GridBagConstraints gbc_dateLbl = new GridBagConstraints();
		gbc_dateLbl.anchor = GridBagConstraints.EAST;
		gbc_dateLbl.insets = new Insets(0, 0, 5, 5);
		gbc_dateLbl.gridx = 0;
		gbc_dateLbl.gridy = 0;
		representation.add(dateLbl, gbc_dateLbl);
		
		JTextField dateField = new JTextField();
		dateField.setText("dd/MM/aaaa");
		dateField.setToolTipText("dd/MM/aaaa");
		dateField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		representation.add(dateField, gbc_textField);
		
		JLabel beginHourLbl = new JLabel("Heure de début :");
		GridBagConstraints gbc_beginHourLbl = new GridBagConstraints();
		gbc_beginHourLbl.anchor = GridBagConstraints.EAST;
		gbc_beginHourLbl.insets = new Insets(0, 0, 5, 5);
		gbc_beginHourLbl.gridx = 2;
		gbc_beginHourLbl.gridy = 0;
		representation.add(beginHourLbl, gbc_beginHourLbl);
		
		JTextField beginHourField = new JTextField();
		beginHourField.setText("hh:mm");
		beginHourField.setToolTipText("hh:mm");
		beginHourField.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 0;
		representation.add(beginHourField, gbc_textField_1);
		
		JLabel lblNewLabel = new JLabel("Heure de fin : ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 4;
		gbc_lblNewLabel.gridy = 0;
		representation.add(lblNewLabel, gbc_lblNewLabel);
		
		JTextField endHourField = new JTextField();
		endHourField.setText("hh:mm");
		endHourField.setToolTipText("hh:mm");
		endHourField.setColumns(10);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.gridx = 5;
		gbc_textField_2.gridy = 0;
		representation.add(endHourField, gbc_textField_2);
		
		JButton deleteRepresentation = new JButton("Supprimer");
		
		JButton addUpdRepresentationBtn = new JButton("Ajouter");
		addUpdRepresentationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dateString = dateField.getText();
				String beginHourString = beginHourField.getText();
				String endHourString = endHourField.getText();
				if(dateString.isBlank() || beginHourString.isBlank() || endHourString.isBlank())
					return;
				
				LocalDate date;
				LocalTime beginHour;
				LocalTime endHour;
				try {
					date = LocalDate.parse(dateString, Main.getDateFormat());
					beginHour = LocalTime.parse(beginHourString, Main.getTimeFormat());
					endHour = LocalTime.parse(endHourString, Main.getTimeFormat());
				}catch(DateTimeParseException er) {
					er.printStackTrace();
					JOptionPane.showMessageDialog(null, "La date ou une des heures est dans un format incorrect.");
					return;
				}
				
				if(!beginHour.isBefore(endHour)) {
					JOptionPane.showMessageDialog(null, "L'heure de fin doit se situer après l'heure de début ! Et inversément...");
					return;
				}
					
				
				Planning parentPlanning = Main.getActualBooking().getPlanning();
				//is date in the planning ?
				boolean isInPlanning = !date.isBefore(parentPlanning.getBeginDate()) 
						&& !date.isAfter(parentPlanning.getEndDate())
						&& ( !date.isEqual(parentPlanning.getBeginDate()) || !beginHour.isBefore(LocalTime.NOON) )
						&& ( !date.isEqual(parentPlanning.getEndDate()) || !endHour.isAfter(LocalTime.NOON) );
				
				boolean competitor = false;
				
				//is time slot still available ?
				if(isInPlanning  && parentPlanning.getShowList() != null && !parentPlanning.getShowList().isEmpty()) {
					for(Show show : parentPlanning.getShowList()) {
						if(show.getRepresentationList() == null || show.getRepresentationList().isEmpty())
							continue;
						
						competitor = show.getRepresentationList().stream()
												.anyMatch(rep -> date.isEqual(rep.getDate()) &&(
													(beginHour.isAfter(rep.getBeginHour()) && beginHour.isBefore(rep.getEndHour()))
													|| (endHour.isAfter(rep.getBeginHour()) && endHour.isBefore(rep.getEndHour())) 
													|| beginHour.equals(rep.getBeginHour()) || endHour.equals(rep.getEndHour()) 
													)
												);
						if(competitor)
							break;
					}
				}
				
				
				boolean selfCompetitor = false;
				if(!representationDataList.isEmpty()) {
					for(Object[] data : representationDataList) {
						selfCompetitor = ((LocalDate)data[0]).isEqual(date) && (
												((LocalTime)data[1]).isAfter(beginHour) && ((LocalTime)data[1]).isBefore(endHour) 
												|| ( ((LocalTime)data[2]).isAfter(beginHour) && ((LocalTime)data[2]).isBefore(endHour) )
												|| ((LocalTime)data[1]).equals(beginHour) || ((LocalTime)data[2]).equals(endHour) 
												);
						if(selfCompetitor)
							break;
					}
				}
					
				if(!isInPlanning) {
					JOptionPane.showMessageDialog(null, "La représentation sort des limites du planning !");
					return;
				}
					
				if(competitor || selfCompetitor) {
					JOptionPane.showMessageDialog(null, "Une autre représentation chevauche déjà ces heures.");
					return;
				}
				
				Object[] repData =  new Object[]{ date, beginHour, endHour};
				representationDataList.add(repData);
				
				dateField.setEditable(false);
				beginHourField.setEditable(false);
				endHourField.setEditable(false);
				
				addUpdRepresentationBtn.setEnabled(false);
				deleteRepresentation.setEnabled(true);
			
				addRepresentation();
			}
		});
		GridBagConstraints gbc_addUpdRepresentationBtn = new GridBagConstraints();
		gbc_addUpdRepresentationBtn.gridwidth = 3;
		gbc_addUpdRepresentationBtn.anchor = GridBagConstraints.NORTH;
		gbc_addUpdRepresentationBtn.insets = new Insets(0, 0, 0, 5);
		gbc_addUpdRepresentationBtn.gridx = 0;
		gbc_addUpdRepresentationBtn.gridy = 1;
		representation.add(addUpdRepresentationBtn, gbc_addUpdRepresentationBtn);
		
		
		GridBagConstraints gbc_deleteRepresentation = new GridBagConstraints();
		gbc_deleteRepresentation.gridwidth = 3;
		gbc_deleteRepresentation.insets = new Insets(0, 0, 0, 5);
		gbc_deleteRepresentation.anchor = GridBagConstraints.NORTH;
		gbc_deleteRepresentation.gridx = 3;
		gbc_deleteRepresentation.gridy = 1;
		representation.add(deleteRepresentation, gbc_deleteRepresentation);
		
		representationListPanel.revalidate();
		representationListPanel.repaint();
	}
	
	public static JPanel get() {
		if(panel == null)
			new AddShow();
		
		return panel;
	}
	
	private void validateForm() {
		//TODO 
		/*
		titleField;
		descriptionField;
		ticketPerPersonField;
		artists;
		configType;
		categoryPriceFields;
		representationDataList;*/
		
		//creer Show
		//TODO : DAO Lien entre show et artistes ?
		
	}

}
