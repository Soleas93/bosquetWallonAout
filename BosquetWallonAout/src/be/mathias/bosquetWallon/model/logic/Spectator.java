package be.mathias.bosquetWallon.model.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class Spectator extends Person {
	
	public static enum Gender{
		Man,
		Woman
	}
	
	private String phoneNumber;
	private Gender gender;
	private LocalDate birthDate;
	private List<Order> orderList;
	
	/**
	 * Import from database
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param password
	 * @param email
	 * @param phoneNumber
	 * @param gender
	 * @param birthDate
	 * @param orderList
	 */
	public Spectator(int id, String firstName, String lastName, String address, String password,  String email, String phoneNumber, Gender gender,LocalDate birthDate, @Nullable List<Order> orderList) {
		super(id, firstName, lastName, address, password, email, PersonRole.Spectator);
		
		setPhoneNumber(phoneNumber);
		setGender(gender);
		setBirthDate(birthDate);
		setOrderList(orderList);
	}
	
	/**
	 * New account
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param password
	 * @param email
	 * @param phoneNumber
	 * @param gender
	 * @param birthDate
	 */
	public Spectator(String firstName, String lastName, String address, String password, String email, String phoneNumber, Gender gender, LocalDate birthDate) {
		this(0, firstName, lastName, address, password, email, phoneNumber, gender, birthDate, null);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public List<Order> getOrderList() {
		return orderList;
	}
	
	private void setOrderList(@Nullable List<Order> orderList) {
		if(orderList != null)
			this.orderList = orderList;
		else
			this.orderList = new ArrayList<Order>();
	}
	
	public void addOrder(Order order) {
		if(order != null)
			this.orderList.add(order);
	}
	
	public void addOrders(@NonNull Collection<? extends Order> orders) {
		this.orderList.addAll(orders);
	}
}
