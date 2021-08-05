package be.mathias.bosquetWallon.model.logic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class Show {

	private int id = 0;
	private String title;
	private String Description;
	private BufferedImage image;
	private int ticketPerPerson;
	private List<Artist> artistList;
	private List<Representation> representationList;
	private Configuration configuration;
	
	//From db
	public Show(int id, String title, String description, BufferedImage image, int ticketPerPerson,
			List<Artist> artistList, List<Representation> representationList, Configuration configuration) {
		setId(id);
		setTitle(title);
		setDescription(description);
		setImage(image);
		setTicketPerPerson(ticketPerPerson);
		setArtistList(artistList);
		setRepresentationList(representationList);
		
		if(id != 0)
			setConfiguration(configuration);
	}
	
	//From planning
	public Show(String title, String description, BufferedImage image, int ticketPerPerson,
			List<Artist> artistList, List<Representation> representationList, Configuration.Type confType) {
		this(0, title, description, image, ticketPerPerson, artistList, representationList, null);
		
		Configuration config = new Configuration(confType, description);
		setConfiguration(config);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if(this.id == 0 && id > 0)
			this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getTicketPerPerson() {
		return ticketPerPerson;
	}

	public void setTicketPerPerson(int ticketPerPerson) {
		this.ticketPerPerson = ticketPerPerson;
	}

	public List<Artist> getArtistList() {
		return artistList;
	}

	private void setArtistList(@Nullable List<Artist> artistList) {
		if(artistList != null)
			this.artistList = artistList;
		else
		 this.artistList = new ArrayList<Artist>();
	}
	
	public void addArtist(Artist artist) {
		if(artist != null)
			this.artistList.add(artist);
	}
	
	public void addArtists(@NonNull Collection<? extends Artist> artists) {
		this.artistList.addAll(artists);
	}

	public List<Representation> getRepresentationList() {
		return representationList;
	}

	private void setRepresentationList(@Nullable List<Representation> representationList) {
		if(representationList != null)
			this.representationList = representationList;
		else
			this.representationList = new ArrayList<Representation>();
	}
	
	public void addRepresentation(Representation representation) {
		if(representation != null)
			this.representationList.add(representation);
	}
	
	public void addRepresentations(@NonNull Collection<? extends Representation> representations) {
		this.representationList.addAll(representations);
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}

	private void setConfiguration(@Nullable Configuration configuration) {
		if(configuration != null)
			this.configuration = configuration;
	}

}
