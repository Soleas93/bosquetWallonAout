package be.mathias.bosquetWallon.model.logic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Show {

	private int id;
	private String title;
	private String Description;
	private BufferedImage image;
	private int ticketPerPerson;
	private List<Artist> artistList = new ArrayList<Artist>();
	private List<Representation> representationList = new ArrayList<Representation>();
	private Configuration configuration;
}
