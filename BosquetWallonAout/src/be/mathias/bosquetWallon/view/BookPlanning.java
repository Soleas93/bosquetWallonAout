package be.mathias.bosquetWallon.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import be.mathias.bosquetWallon.model.data.BookingDao;
import be.mathias.bosquetWallon.model.data.DaoFactory;
import be.mathias.bosquetWallon.model.data.PlanningDao;
import be.mathias.bosquetWallon.model.logic.Booking;
import be.mathias.bosquetWallon.model.logic.Organizer;
import be.mathias.bosquetWallon.model.logic.Planning;

public class BookPlanning extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5358761480430580171L;
	
	private static JPanel panel;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel header;
	private JButton previousBtn;
	private JLabel currentMonthLbl;
	private JButton nextBtn;
	private JPanel bookingForm;
	private JPanel beginDatePanel;
	private JPanel endDatePanel;
	private JLabel lblNewLabel_2;
	private JTextField beginDateField;
	private JLabel lblNewLabel_3;
	private JTextField endDateField;
	private JPanel informations;
	private JLabel info1;
	private JLabel info2;
	private JPanel pricePanel;
	private JLabel priceLabel;
	private JLabel priceValueLabel;
	private JButton bookBtn;
	private JPanel calendarHeader;
	private JLabel dispoLbl;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	private static LocalDate date = LocalDate.now();
	private JButton calculateBtn;
	private boolean validForm = false;
	
	private LocalDate beginDate;
	private LocalDate endDate;
	
	PlanningDao planningDao = (PlanningDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetPlanningDao();
	
	private BookPlanning() {
		panel = new JPanel();
		GridBagLayout gbl_bookPlanning = new GridBagLayout();
		gbl_bookPlanning.columnWidths = new int[]{10, 0, 10, 0};
		gbl_bookPlanning.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_bookPlanning.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_bookPlanning.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_bookPlanning);
		
		header = new JPanel();
		GridBagConstraints gbc_header = new GridBagConstraints();
		gbc_header.gridwidth = 3;
		gbc_header.insets = new Insets(0, 0, 5, 0);
		gbc_header.fill = GridBagConstraints.BOTH;
		gbc_header.gridx = 0;
		gbc_header.gridy = 0;
		panel.add(header, gbc_header);
		
		previousBtn = new JButton("Précedent");
		previousBtn.setEnabled(false);
		previousBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date = date.minusMonths(1);
				setPlanningTableModel(date);
				currentMonthLbl.setText(Main.getFrenchMonthOfDate(date));
				LocalDate previousMonth = date.minusMonths(1);
				if(previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()).isBefore(LocalDate.now()))
					previousBtn.setEnabled(false);
			}
		});
		header.add(previousBtn);
		
		currentMonthLbl = new JLabel(Main.getFrenchMonthOfDate(date));
		currentMonthLbl.setFont(new Font("Tahoma", Font.BOLD, 18));
		header.add(currentMonthLbl);
		
		nextBtn = new JButton("Suivant");
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date = date.plusMonths(1);
				setPlanningTableModel(date);
				currentMonthLbl.setText(Main.getFrenchMonthOfDate(date));
				previousBtn.setEnabled(true);
			}
		});
		header.add(nextBtn);
		
		calendarHeader = new JPanel();
		GridBagConstraints gbc_calendarHeader = new GridBagConstraints();
		gbc_calendarHeader.insets = new Insets(0, 0, 5, 5);
		gbc_calendarHeader.fill = GridBagConstraints.BOTH;
		gbc_calendarHeader.gridx = 1;
		gbc_calendarHeader.gridy = 1;
		panel.add(calendarHeader, gbc_calendarHeader);
		calendarHeader.setLayout(new BoxLayout(calendarHeader, BoxLayout.Y_AXIS));
		
		dispoLbl = new JLabel("Disponibilités");
		dispoLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		dispoLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		calendarHeader.add(dispoLbl);
		
		lblNewLabel = new JLabel("Un numéro manquant indique que la salle est indisponible A PARTIR de cette date.");
		lblNewLabel.setAlignmentX(0.5f);
		calendarHeader.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Il est néanmoins possible de reserver JUSQU'A cette date si le(s) jour(s) précédent(s) est/sont disponible(s).");
		lblNewLabel_1.setAlignmentX(0.5f);
		calendarHeader.add(lblNewLabel_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.anchor = GridBagConstraints.NORTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		
		table.setPreferredScrollableViewportSize(new Dimension(385, 96));
		table.setAutoscrolls(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
		table.setEnabled(false);
		table.setRowSelectionAllowed(false);
		setPlanningTableModel(date);
		
		informations = new JPanel();
		GridBagConstraints gbc_informations = new GridBagConstraints();
		gbc_informations.insets = new Insets(0, 0, 5, 5);
		gbc_informations.fill = GridBagConstraints.VERTICAL;
		gbc_informations.gridx = 1;
		gbc_informations.gridy = 3;
		panel.add(informations, gbc_informations);
		informations.setLayout(new BoxLayout(informations, BoxLayout.Y_AXIS));
		
		info1 = new JLabel("Les réservations se font de midi à midi");
		info1.setAlignmentX(Component.CENTER_ALIGNMENT);
		informations.add(info1);
		
		info2 = new JLabel("Un prix dégressif est appliqué selon la durée de la réservation");
		info2.setAlignmentX(Component.CENTER_ALIGNMENT);
		informations.add(info2);
		
		bookingForm = new JPanel();
		GridBagConstraints gbc_bookingForm = new GridBagConstraints();
		gbc_bookingForm.insets = new Insets(0, 0, 5, 5);
		gbc_bookingForm.fill = GridBagConstraints.BOTH;
		gbc_bookingForm.gridx = 1;
		gbc_bookingForm.gridy = 4;
		panel.add(bookingForm, gbc_bookingForm);
		bookingForm.setLayout(new BoxLayout(bookingForm, BoxLayout.Y_AXIS));
		
		beginDatePanel = new JPanel();
		bookingForm.add(beginDatePanel);
		
		lblNewLabel_2 = new JLabel("Date de début");
		beginDatePanel.add(lblNewLabel_2);
		
		beginDateField = new JTextField();
		beginDateField.setToolTipText("jj/mm/aaaa");
		beginDateField.setText("03/09/2021");
		beginDatePanel.add(beginDateField);
		beginDateField.setColumns(10);
		
		endDatePanel = new JPanel();
		bookingForm.add(endDatePanel);
		
		lblNewLabel_3 = new JLabel("Date de fin");
		endDatePanel.add(lblNewLabel_3);
		
		endDateField = new JTextField();
		endDateField.setToolTipText("jj/mm/aaaa");
		endDateField.setText("04/09/2021");
		endDatePanel.add(endDateField);
		endDateField.setColumns(10);
		
		pricePanel = new JPanel();
		bookingForm.add(pricePanel);
		
		priceLabel = new JLabel("Prix total : ");
		pricePanel.add(priceLabel);
		
		priceValueLabel = new JLabel("0 €");
		pricePanel.add(priceValueLabel);
		
		calculateBtn = new JButton("Calculer");
		calculateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bDate = beginDateField.getText();
				String eDate = endDateField.getText();
				boolean validDates = Main.checkDate(bDate) && Main.checkDate(eDate); //parse work on 30/02/2021 so we do an additional verification
				boolean isAvailable = false;
				if(validDates) {
					try {
						LocalDate beDate = LocalDate.parse(bDate, Main.getDateFormat());
						LocalDate enDate = LocalDate.parse(eDate, Main.getDateFormat());
						isAvailable = !planningDao.isCompetitor(beDate, enDate);
						if(beDate.isAfter(LocalDate.now()) && enDate.isAfter(beDate) && isAvailable){
							priceLabel.setText("Prix total : ");
							priceValueLabel.setText(Booking.getPrice(beDate, enDate) + " €");
							validForm = true;
							beginDate = beDate;
							endDate = enDate;
						}else {
							priceLabel.setText("Erreur : ");
							priceValueLabel.setText("les dates ne sont pas correctes");
							validForm = false;
						}
						
					}catch(DateTimeParseException er) {
						er.printStackTrace();
						validDates = false;
					}
				}
				if(!validDates) {
					priceLabel.setText("Erreur : ");
					priceValueLabel.setText("les dates ne sont pas correctes");
					validForm = false;
				}else if(!isAvailable) {
					priceLabel.setText("Erreur : ");
					priceValueLabel.setText("les dates choisies ne sont pas disponibles.");
					validForm = false;
				}
				bookBtn.setEnabled(validForm);
			}
		});
		pricePanel.add(calculateBtn);
		
		bookBtn = new JButton("Réserver");
		bookBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookBtn.setEnabled(false);
				
				Planning planning = new Planning(beginDate, endDate);
				Booking booking = new Booking(planning);
				booking.setParentId(Main.getUser().getId());
				
				BookingDao bookingDao = (BookingDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetBookingDao();
				bookingDao.create(booking);
				
				planning.setId(booking.getId());
				planningDao.create(planning);
				
				((Organizer)Main.getUser()).addBooking(booking);
								
				Main.setActualBooking(booking);
				Main.setShowList();
				Main.switchPane("showList");
			}
		});
		bookBtn.setEnabled(false);
		GridBagConstraints gbc_bookBtn = new GridBagConstraints();
		gbc_bookBtn.insets = new Insets(0, 0, 0, 5);
		gbc_bookBtn.gridx = 1;
		gbc_bookBtn.gridy = 5;
		panel.add(bookBtn, gbc_bookBtn);
	}
	
	public static JPanel get() {
		if(panel == null)
			new BookPlanning();
		return panel;
	}
	
	public void setPlanningTableModel(LocalDate date) {
		int firstDayOfWeek = date.withDayOfMonth(1).getDayOfWeek().getValue();
		int numOfDays = date.lengthOfMonth();
		int numOfRows = (int) Math.ceil( (double)(numOfDays + firstDayOfWeek / 7) );
		
		Object[][] calendar = new Object[numOfRows][7];
		int i = 1;
		
		for(int j = 0; j < numOfRows; j++) {
			for(int k = 0; k <7 ; k++) {
				if(i > numOfDays) {
					j = numOfRows;
					k = 7;
					continue;
				}
				if(j == 0 && k < (firstDayOfWeek - 1))
					calendar[j][k] = null;
				else if(date.withDayOfMonth(i).isBefore(LocalDate.now().plusDays(1))) {
					calendar[j][k] = null;
					i++;
				}else
					calendar[j][k] = i++;
			}	
		}
		
		List<Integer> daysBooked = planningDao.bookedDate(date);
		if(daysBooked != null) {
			for(int n : daysBooked) {
				int x = n-1;
				int col = (x + firstDayOfWeek - 1) % 7;
				int lig = (int) Math.ceil((double)((x + firstDayOfWeek - 1) / 7));
				calendar[lig][col] = null;
			}
		}

		DefaultTableModel model = new DefaultTableModel( calendar,  new String [] {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"} ) {
			private static final long serialVersionUID = -5437156447181410155L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		table.setModel(model);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
		 TableColumnModel tableModel = table.getColumnModel();

        for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++)
        {
            tableModel.getColumn(columnIndex).setCellRenderer(centerRenderer);
            tableModel.getColumn(columnIndex).setResizable(false);
            tableModel.getColumn(columnIndex).setPreferredWidth(55);
            tableModel.getColumn(columnIndex).setMinWidth(55);
        }
        
        
	}

}
