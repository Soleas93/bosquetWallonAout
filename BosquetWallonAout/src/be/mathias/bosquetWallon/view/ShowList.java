package be.mathias.bosquetWallon.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import be.mathias.bosquetWallon.model.data.DaoFactory;
import be.mathias.bosquetWallon.model.data.ShowDao;
import be.mathias.bosquetWallon.model.logic.Person.PersonRole;
import be.mathias.bosquetWallon.model.logic.Representation;
import be.mathias.bosquetWallon.model.logic.Show;

public class ShowList extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4962312084878055890L;
	private static JPanel panel;
	private static List<Show> shows = new ArrayList<Show>();
	private JScrollPane scrollPaneShow;
	private JPanel showListPanel; 
	
	private PersonRole userRole;
	private JButton addShowBtn;

	public ShowList() {
		userRole = Main.getUser().getRole();
		
		panel = new JPanel();
		panel.setBackground(new Color(176, 196, 222));
		GridBagLayout gbl_showList = new GridBagLayout();
		gbl_showList.columnWidths = new int[] {5, 200, 5};
		gbl_showList.rowHeights = new int[] {10, 20, 300, 10, 5};
		gbl_showList.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_showList.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0};
		panel.setLayout(gbl_showList);
		
		JLabel title = new JLabel("Spectacles");
		title.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_title = new GridBagConstraints();
		gbc_title.insets = new Insets(0, 0, 5, 5);
		gbc_title.gridx = 1;
		gbc_title.gridy = 1;
		panel.add(title, gbc_title);
		
		scrollPaneShow = new JScrollPane();
		scrollPaneShow.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPaneShow = new GridBagConstraints();
		gbc_scrollPaneShow.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneShow.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneShow.gridx = 1;
		gbc_scrollPaneShow.gridy = 2;
		panel.add(scrollPaneShow, gbc_scrollPaneShow);
		
		showListPanel = new JPanel();
		showListPanel.setLayout(new BoxLayout(showListPanel, BoxLayout.Y_AXIS));
		scrollPaneShow.setViewportView(showListPanel);
		
		addShowBtn = new JButton("Nouveau spectacle");
		boolean isOrganizer = userRole.equals(PersonRole.Organizer);
		addShowBtn.setEnabled(isOrganizer);
		addShowBtn.setVisible(isOrganizer);
		addShowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO : switchPane nouveau show
				//TODO : set Current Booking -> déja fait
				Main.switchPane("addShow");
			}
		});
		GridBagConstraints gbc_addShowBtn = new GridBagConstraints();
		gbc_addShowBtn.insets = new Insets(0, 0, 5, 5);
		gbc_addShowBtn.gridx = 1;
		gbc_addShowBtn.gridy = 3;
		panel.add(addShowBtn, gbc_addShowBtn);
		
		switch (userRole) {
		case Organizer: 
			shows = Main.getActualBooking().getPlanning().getShowList();
			break;
		case Spectator:
			ShowDao showDao = (ShowDao)DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetShowDao();
			shows = showDao.findFromNow();
			break;
		default:
			break;
		}
		clearShows();
		setShows(shows);
	}
	
	public void setShows(List<Show> shows) {
		if(shows == null || shows.isEmpty()) {
			JPanel show = new JPanel();
			GridBagLayout gbl_show = new GridBagLayout();
			gbl_show.columnWidths = new int[]{150, 316, 0};
			gbl_show.rowHeights = new int[]{14, 60, 0, 0, 0};
			gbl_show.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_show.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			show.setLayout(gbl_show);
			showListPanel.add(show, gbl_show);
			
			JLabel showTitle = new JLabel("Il n'existe aucun spectacle pour le moment.");
			showTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
			GridBagConstraints gbc_showTitle = new GridBagConstraints();
			gbc_showTitle.gridwidth = 2;
			gbc_showTitle.insets = new Insets(0, 0, 5, 0);
			gbc_showTitle.anchor = GridBagConstraints.NORTH;
			gbc_showTitle.gridx = 0;
			gbc_showTitle.gridy = 0;
			show.add(showTitle, gbc_showTitle);
			
			return;
		}
		
		for(Show item : shows) {
			
			JPanel show = new JPanel();
			GridBagLayout gbl_show = new GridBagLayout();
			gbl_show.columnWidths = new int[]{150, 316, 0};
			gbl_show.rowHeights = new int[]{14, 60, 0, 0, 0};
			gbl_show.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_show.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			show.setLayout(gbl_show);
			showListPanel.add(show, gbl_show);
			
			JLabel showTitle = new JLabel(item.getTitle());
			showTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
			GridBagConstraints gbc_showTitle = new GridBagConstraints();
			gbc_showTitle.gridwidth = 2;
			gbc_showTitle.insets = new Insets(0, 0, 5, 0);
			gbc_showTitle.anchor = GridBagConstraints.NORTH;
			gbc_showTitle.gridx = 0;
			gbc_showTitle.gridy = 0;
			show.add(showTitle, gbc_showTitle);
			
			JTextArea showDescription = new JTextArea();
			showDescription.setBackground(SystemColor.control);
			showDescription.setDisabledTextColor(Color.BLACK);
			showDescription.setEnabled(false);
			showDescription.setEditable(false);
			showDescription.setText(item.getDescription());
			GridBagConstraints gbc_showDescription = new GridBagConstraints();
			gbc_showDescription.insets = new Insets(0, 0, 5, 0);
			gbc_showDescription.fill = GridBagConstraints.BOTH;
			gbc_showDescription.gridx = 1;
			gbc_showDescription.gridy = 1;
			show.add(showDescription, gbc_showDescription);
			
			JPanel showCommands = new JPanel();
			GridBagConstraints gbc_showCommands = new GridBagConstraints();
			gbc_showCommands.gridwidth = 2;
			gbc_showCommands.insets = new Insets(0, 0, 5, 0);
			gbc_showCommands.fill = GridBagConstraints.HORIZONTAL;
			gbc_showCommands.gridx = 0;
			gbc_showCommands.gridy = 2;
			show.add(showCommands, gbc_showCommands);
			
			JButton updShow = new JButton("Modifier le spectacle");
			updShow.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//TODO
				}
			});
			updShow.setVisible(userRole == PersonRole.Organizer);
			updShow.setEnabled(userRole == PersonRole.Organizer);
			showCommands.add(updShow);
			
			//Checker si ok
			JScrollPane scrollPaneRepresentation = new JScrollPane();
			scrollPaneRepresentation.setVisible(false);
			scrollPaneRepresentation.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			GridBagConstraints gbc_scrollPaneRepresentation = new GridBagConstraints();
			gbc_scrollPaneRepresentation.gridwidth = 2;
			gbc_scrollPaneRepresentation.fill = GridBagConstraints.BOTH;
			gbc_scrollPaneRepresentation.gridx = 0;
			gbc_scrollPaneRepresentation.gridy = 3;
			show.add(scrollPaneRepresentation, gbc_scrollPaneRepresentation);
			
			JButton switchRep = new JButton("Afficher/Cacher les représentations");
			switchRep.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean newVisibility = !scrollPaneRepresentation.isVisible();
					scrollPaneRepresentation.setVisible(newVisibility);
					show.revalidate();
					show.repaint();
				}
			});
			showCommands.add(switchRep);
			
			JButton addRep = new JButton("Ajouter une représentation");
			addRep.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//TODO
				}
			});
			addRep.setVisible(userRole == PersonRole.Organizer);
			addRep.setEnabled(userRole == PersonRole.Organizer);
			showCommands.add(addRep);
			
			//AVANT : scrollPaneRepresentation ici
			
			JPanel representationList = new JPanel();
			scrollPaneRepresentation.setViewportView(representationList);
			representationList.setLayout(new BoxLayout(representationList, BoxLayout.Y_AXIS));
			
			if( item.getRepresentationList() == null || item.getRepresentationList().isEmpty()) {
				JPanel representation = new JPanel();
				representationList.add(representation);
				
				JLabel empty = new JLabel("Il n'existe pas de réprésentation disponible pour ce spectacle.");
				representation.add(empty);
				continue;
			}
			
			for(Representation rep : item.getRepresentationList() ) {
				JPanel representation = new JPanel();
				representationList.add(representation);
				
				String dateString = rep.getDate().format(Main.getDateFormat());
				String beginTime = rep.getBeginHour().format(Main.getTimeFormat());
				String endTime = rep.getEndHour().format(Main.getTimeFormat());
				JLabel date = new JLabel("Le " + dateString + " de " + beginTime + " à " + endTime);
				representation.add(date);
				
				JButton bookTickets = new JButton("Réserver");
				bookTickets.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//TODO
					}
				});
				representation.add(bookTickets);
				bookTickets.setVisible(userRole == PersonRole.Spectator);
				bookTickets.setEnabled(userRole == PersonRole.Spectator);
			}
			
			
		}
	}
	
	public void clearShows() {
		showListPanel.removeAll();
		showListPanel.revalidate();
		showListPanel.repaint();
	}
	
	public static JPanel getPanel() {
		return panel;
	}
}
