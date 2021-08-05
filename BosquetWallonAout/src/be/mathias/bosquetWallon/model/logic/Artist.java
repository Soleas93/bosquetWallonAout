package be.mathias.bosquetWallon.model.logic;

public class Artist extends Person {
	private String showName;

	public Artist(int id, String firstName, String lastName, String address, String password, String email, String showName) {
		super(id, firstName, lastName, address, password, email, PersonRole.Artist);
		setShowName(showName);
	}

	public Artist(String firstName, String lastName, String address, String password, String email, String showName) {
		this(0, firstName, lastName, address, password, email, showName);
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
	
	
}
