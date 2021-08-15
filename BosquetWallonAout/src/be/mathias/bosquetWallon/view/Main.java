package be.mathias.bosquetWallon.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import be.mathias.bosquetWallon.model.logic.Booking;
import be.mathias.bosquetWallon.model.logic.Person;
import be.mathias.bosquetWallon.model.logic.Show;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.regex.Pattern;


import java.awt.Insets;

public class Main {

	private JFrame frmBosquetWallon;

	private static JLayeredPane mainPane;
	
	private static JPanel connection = Connection.get();
	private static JPanel register = Register.get();
	private static JPanel showList;
	private static JPanel listBooking;
	private static JPanel bookPlanning = BookPlanning.get();
	private JPanel addShow = AddShow.get();
	
	private static Person user = null;
	private static Show actualShow = null;
	private static Booking actualBooking = null;

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
	//https://stackoverflow.com/a/26972181
	private static final Pattern DATE_PATTERN = Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
	
	private JPanel addRepresentation;
	private JLabel lblNewLabel_13;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmBosquetWallon.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBosquetWallon = new JFrame();
		frmBosquetWallon.setTitle("Bosquet Wallon");
		frmBosquetWallon.setMinimumSize(new Dimension(800, 600));
		frmBosquetWallon.setSize(new Dimension(800, 600));
		frmBosquetWallon.setBounds(100, 100, 450, 300);
		frmBosquetWallon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmBosquetWallon.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{784, 0};
		gridBagLayout.rowHeights = new int[]{539, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frmBosquetWallon.getContentPane().setLayout(gridBagLayout);
		
		mainPane = new JLayeredPane();
		GridBagConstraints gbc_mainPane = new GridBagConstraints();
		gbc_mainPane.fill = GridBagConstraints.BOTH;
		gbc_mainPane.gridx = 0;
		gbc_mainPane.gridy = 0;
		frmBosquetWallon.getContentPane().add(mainPane, gbc_mainPane);
		mainPane.setLayout(new CardLayout(0, 0));
		
		//pane connection
		mainPane.add(connection, "connection");
		mainPane.add(register, "register");
		mainPane.add(bookPlanning, "bookPlanning");
		mainPane.add(addShow, "addShow");
		
		
		
		addRepresentation = new JPanel();
		mainPane.add(addRepresentation, "name_234100229561200");
		GridBagLayout gbl_addRepresentation = new GridBagLayout();
		gbl_addRepresentation.columnWidths = new int[]{10, 46, 10, 0};
		gbl_addRepresentation.rowHeights = new int[]{14, 0};
		gbl_addRepresentation.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_addRepresentation.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		addRepresentation.setLayout(gbl_addRepresentation);
		
		lblNewLabel_13 = new JLabel("Nouvelle représentation");
		GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
		gbc_lblNewLabel_13.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_13.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_13.gridx = 1;
		gbc_lblNewLabel_13.gridy = 0;
		addRepresentation.add(lblNewLabel_13, gbc_lblNewLabel_13);
		
		
		//set default pane
		switchPane("connection");
		//switchPane("bookPlanning");
		
	}
	
	public static void switchPane(String paneName) {
		CardLayout cards = (CardLayout) mainPane.getLayout();
		cards.show(mainPane, paneName );
	}
	
	public static void setUser(Person user) {
		Main.user = user;
	}
	
	public static Person getUser() {
		return Main.user;
	}
	
	
	public static boolean checkDate(String date) {
		return DATE_PATTERN.matcher(date).matches();
	}
	
	public static boolean checkEmail(String email) {
		return EMAIL_PATTERN.matcher(email).matches();
	}

	public static DateTimeFormatter getDateFormat() {
		return DATE_FORMAT;
	}

	public static DateTimeFormatter getTimeFormat() {
		return TIME_FORMAT;
	}

	public static Booking getActualBooking() {
		return actualBooking;
	}

	public static void setActualBooking(Booking actualBooking) {
		Main.actualBooking = actualBooking;
	}

	public static Show getActualShow() {
		return actualShow;
	}

	public static void setActualShow(Show actualShow) {
		Main.actualShow = actualShow;
	}

	public static void setShowList() {
		new ShowList();
		Main.showList = ShowList.getPanel();
		mainPane.add(showList, "showList");
	}
	
	public static String getFrenchMonthOfDate(LocalDate date) {
		return "  " + date.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE)+ " " + date.getYear() + "  ";
	}

	public static void setListBooking() {
		new ListBooking();
		Main.listBooking = ListBooking.getPanel();
		mainPane.add(listBooking, "listBooking");
	}
}
