package be.mathias.bosquetWallon.model.logic;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Person {
	
	public static enum PersonRole {
		Spectator,
		Artist,
		Manager,
		Organizer
	}
	
	private int id = 0;
	private String firstName;
	private String lastName;
	private String address;
	private String passwordHash;
	private String emailAddress;
	private PersonRole role;
	
	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param password
	 * @param email
	 * @param role
	 * @param fromDb
	 */
	public Person(int id, String firstName, String lastName, String address, String password, String email, PersonRole role) {
		if(id > 0)		
			passwordHash = password;
		else 
			setPassword(password);
		
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setAddress(address);
		setEmailAddress(email);
		setRole(role);
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param password
	 * @param email
	 * @param role
	 */
	public Person(String firstName, String lastName, String address, String password, String email, PersonRole role) {
		this(0, firstName, lastName, address, password, email, role);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		//Only allow id update once for DAO
		if(this.id == 0 && id > 0)
			this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPasswordHash() {
		return passwordHash;
	}
	
	protected void setPassword(String password){
		this.passwordHash = HashPassword(password);
	}

	public boolean ChangePassword(String oldPassword, String newPassword) {
		if(!CheckPassword(oldPassword))
			return false;
		setPassword(newPassword);
		return true;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	private void setRole(PersonRole role) {
		this.role = role;
	}

	public PersonRole getRole() {
		return role;
	}
	
	public static String HashPassword(String password) {
		try{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return new String(digest.digest(password.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
		}
		catch(NoSuchAlgorithmException e){
			System.out.print("Error with HashPassword from Person class : " + e);
			return null;
		}
		
	}
	
	private boolean CheckPassword(String password) {
		if(HashPassword(password).equals(passwordHash))
			return true;
		return false;
	}
	
}
