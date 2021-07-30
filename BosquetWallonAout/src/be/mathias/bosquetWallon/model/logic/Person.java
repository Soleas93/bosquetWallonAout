package be.mathias.bosquetWallon.model.logic;

public abstract class Person {
	
	public static enum PersonRole {
		Spectator,
		Artist,
		Manager,
		Organizer
	}
	
	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private String passwordHash;
	private String emailAddress;
	private PersonRole role;
	
	public PersonRole GetRole() {
		return role;
	}
	
}
