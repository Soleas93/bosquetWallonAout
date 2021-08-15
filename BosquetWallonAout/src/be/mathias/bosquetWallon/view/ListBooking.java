package be.mathias.bosquetWallon.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import be.mathias.bosquetWallon.model.data.BookingDao;
import be.mathias.bosquetWallon.model.data.DaoFactory;
import be.mathias.bosquetWallon.model.logic.Booking;
import be.mathias.bosquetWallon.model.logic.Organizer;
import be.mathias.bosquetWallon.model.logic.Person.PersonRole;

public class ListBooking extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5655694878000727888L;

	private static JPanel panel;
	
	private JPanel header_1;
	private JButton previousBtn;
	private JLabel currentMonthLbl;
	private JButton nextBtn_1;
	private JScrollPane scrollPane_1;
	private JPanel bookingListPanel;
	
	
	private JButton newBookingBtn;
	
	private PersonRole userRole;
	
	private LocalDate date = LocalDate.now();

	public ListBooking() {
		userRole = Main.getUser().getRole();
		
		panel = new JPanel();
		GridBagLayout gbl_listBooking = new GridBagLayout();
		gbl_listBooking.columnWidths = new int[]{0, 0, 0, 0};
		gbl_listBooking.rowHeights = new int[]{0, 0, 0, 5, 0};
		gbl_listBooking.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_listBooking.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_listBooking);
		
		header_1 = new JPanel();
		GridBagConstraints gbc_header_1 = new GridBagConstraints();
		gbc_header_1.insets = new Insets(0, 0, 5, 5);
		gbc_header_1.fill = GridBagConstraints.BOTH;
		gbc_header_1.gridx = 1;
		gbc_header_1.gridy = 0;
		panel.add(header_1, gbc_header_1);
		
		previousBtn = new JButton("Précedent");
		previousBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date = date.minusMonths(1);
				currentMonthLbl.setText(Main.getFrenchMonthOfDate(date));
				clearBookings();
				setBookings(getListBookings());
			}
		});
		header_1.add(previousBtn);
		
		currentMonthLbl = new JLabel(Main.getFrenchMonthOfDate(date));
		currentMonthLbl.setFont(new Font("Tahoma", Font.BOLD, 18));
		header_1.add(currentMonthLbl);
		
		nextBtn_1 = new JButton("Suivant");
		nextBtn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date = date.plusMonths(1);
				currentMonthLbl.setText(Main.getFrenchMonthOfDate(date));
				clearBookings();
				setBookings(getListBookings());
			}
		});
		header_1.add(nextBtn_1);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.anchor = GridBagConstraints.NORTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		panel.add(scrollPane_1, gbc_scrollPane_1);
		
		bookingListPanel = new JPanel();
		bookingListPanel.setLayout(new BoxLayout(bookingListPanel, BoxLayout.Y_AXIS));
		scrollPane_1.setViewportView(bookingListPanel);
		
		setBookings(getListBookings());
		
		newBookingBtn = new JButton("Nouvelle réservation");
		newBookingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.switchPane("bookPlanning");
			}
		});
		boolean isOrganizer = userRole.equals(PersonRole.Organizer);
		newBookingBtn.setVisible(isOrganizer);
		GridBagConstraints gbc_newBookingBtn = new GridBagConstraints();
		gbc_newBookingBtn.insets = new Insets(0, 0, 5, 5);
		gbc_newBookingBtn.gridx = 1;
		gbc_newBookingBtn.gridy = 2;
		panel.add(newBookingBtn, gbc_newBookingBtn);
	}
	
	public void setBookings(List<Booking> bookings) {
		
		if(bookings == null || bookings.isEmpty()) {
			JPanel booking = new JPanel();
			bookingListPanel.add(booking);
			GridBagLayout gbl_booking = new GridBagLayout();
			gbl_booking.columnWidths = new int[]{50, 50, 50, 50};
			gbl_booking.rowHeights = new int[]{14, 14, 14, 0};
			gbl_booking.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
			gbl_booking.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			booking.setLayout(gbl_booking);
			
			JLabel bookingLabel = new JLabel("Il n'existe aucune réservation pour cette date");
			bookingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
			gbc_lblNewLabel_4.gridwidth = 4;
			gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel_4.gridx = 0;
			gbc_lblNewLabel_4.gridy = 0;
			booking.add(bookingLabel, gbc_lblNewLabel_4);
			
			return;
		}
		
		for(Booking item : bookings) {
			JPanel booking = new JPanel();
			bookingListPanel.add(booking);
			GridBagLayout gbl_booking = new GridBagLayout();
			gbl_booking.columnWidths = new int[]{50, 50, 50, 50};
			gbl_booking.rowHeights = new int[]{14, 14, 14, 0};
			gbl_booking.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
			gbl_booking.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			booking.setLayout(gbl_booking);
			
			String bookingBegin = item.getPlanning().getBeginDate().format(Main.getDateFormat());
			String bookingEnd = item.getPlanning().getEndDate().format(Main.getDateFormat());

			JLabel bookingLabel = new JLabel("Réservation du " + bookingBegin + " au " + bookingEnd);
			bookingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
			gbc_lblNewLabel_4.gridwidth = 4;
			gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel_4.gridx = 0;
			gbc_lblNewLabel_4.gridy = 0;
			booking.add(bookingLabel, gbc_lblNewLabel_4);
			
			JLabel statusLabel = new JLabel("Statut : " + item.getStatus().toString());
			statusLabel.setAlignmentX(0.5f);
			GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
			gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_5.gridx = 0;
			gbc_lblNewLabel_5.gridy = 1;
			booking.add(statusLabel, gbc_lblNewLabel_5);
			
			JLabel depositLabel = new JLabel("Dépôt : " + item.getDeposit() + " €");
			depositLabel.setAlignmentX(0.5f);
			GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
			gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_6.gridx = 1;
			gbc_lblNewLabel_6.gridy = 1;
			booking.add(depositLabel, gbc_lblNewLabel_6);
			
			JLabel balanceLabel = new JLabel("Balance : "+ item.getBalance() + " €");
			balanceLabel.setAlignmentX(0.5f);
			GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
			gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_7.gridx = 2;
			gbc_lblNewLabel_7.gridy = 1;
			booking.add(balanceLabel, gbc_lblNewLabel_7);
			
			JLabel priceLabel = new JLabel("Prix : "+ item.getPrice() +" €");
			priceLabel.setAlignmentX(0.5f);
			GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
			gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel_8.gridx = 3;
			gbc_lblNewLabel_8.gridy = 1;
			booking.add(priceLabel, gbc_lblNewLabel_8);
			
			JButton updateBtn = new JButton("Modifier");
			updateBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					switch (userRole) {
					case Manager:
						//TODO : Modifier statut, balance et dépôt
						break;
					case Organizer:
						Main.setActualBooking(item);
						Main.setShowList();
						Main.switchPane("showList");
						break;
					default:
						throw new IllegalArgumentException("Unexpected value: " + userRole);
					}
				}
			});

			updateBtn.setAlignmentX(0.5f);
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.gridwidth = 4;
			gbc_btnNewButton.gridx = 0;
			gbc_btnNewButton.gridy = 2;
			booking.add(updateBtn, gbc_btnNewButton);
		}
		
	}
	
	public void clearBookings() {
			bookingListPanel.removeAll();
			bookingListPanel.revalidate();
			bookingListPanel.repaint();
	}
	
	public List<Booking> getListBookings(){
		switch (userRole) {
		case Manager:
			BookingDao bookingDao = (BookingDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetBookingDao();
			return bookingDao.findByMonth(date);
		case Organizer:
			if(((Organizer) Main.getUser()).getBookingList() == null)
				return null;
			return ((Organizer)Main.getUser()).getBookingList().stream()
				.filter(b -> b.getPlanning().getBeginDate().getMonth().equals(date.getMonth()) 
							|| b.getPlanning().getEndDate().getMonth().equals(date.getMonth()))
				.collect(Collectors.toList());
		default:
			throw new IllegalArgumentException("Unexpected value: " + userRole);
		}
	}
	
	public static JPanel getPanel() {
		return panel;
	}
	


	
	/*
	 * if(Main.getUser().getRole().equals(PersonRole.Manager))
	 */
}
