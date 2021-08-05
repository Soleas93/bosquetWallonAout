package be.mathias.bosquetWallon.model.logic;


public class Manager extends Person {
	private String phoneNumber;
	//private List<Planning> planningList = new ArrayList<Planning>();
	// ??? Link to Planning ??? quelle cardinalité ? => 1 objet planning par réservation, donc #0..*
	//PAS BESOIN : Un manager, quel qu'il soit, a accès à tous les plannings
	
	public Manager(int id, String firstName, String lastName, String address, String password, String email, String phoneNumber) {
		super(id, firstName, lastName, address, password, email, PersonRole.Manager);
	}

	public Manager(String firstName, String lastName, String address, String password, String email, String phoneNumber) {
		this(0, firstName, lastName, address, password, email, phoneNumber);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
