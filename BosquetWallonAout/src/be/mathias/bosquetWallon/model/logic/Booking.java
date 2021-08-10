package be.mathias.bosquetWallon.model.logic;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;

public class Booking {
	
	public static enum State {
		Canceled,
		Pending,
		Paid
	}
	
	private int id = 0;
	private double deposit = 0; // Déjà versé
	private double balance = 0;	// Reste a payer : prix - depot
	private State status = State.Pending;
	private double price = 0;
	private Planning planning;
	
	//db only
	private int parentId = 0;
	
	public Booking(int id, double deposit, double balance, State status, double price, Planning planning) {
		setId(id);
		setStatus(status);
		setPlanning(planning);

		if(id != 0) {
			this.price = price;
			this.deposit = deposit;
			this.balance = balance;
		}
		else {
			setPrice();
			setDeposit(deposit);
		}
	}
	
	public Booking(Planning planning) {
		this(0, 0, 0, State.Pending, 0, planning);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if(this.id == 0 && id > 0)
			this.id = id;
	}
	
	public int getParentId() {
		return parentId;
	}
	
	public void setParentId(int organizerId) {
		this.parentId = organizerId;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		if(deposit <= 0)
			return;
		
		this.deposit = deposit;
		setBalance();
	}

	public double getBalance() {
		return balance;
	}

	private void setBalance() {
		if(deposit >= 0)
			this.balance = this.price - this.deposit;
		else
			this.balance = this.price;
	}

	public State getStatus() {
		return status;
	}

	public void setStatus(State status) {
		this.status = status;
	}

	public double getPrice() {
		return price;
	}

	private void setPrice() {
		double total = 0;
		
		int numOfDays = (int) planning.getBeginDate().until(planning.getEndDate(), ChronoUnit.DAYS );
		DayOfWeek baseDay = planning.getBeginDate().getDayOfWeek();
		
		for(int i = 0; i < numOfDays; i++) {
			DayOfWeek day = baseDay.plus(i);
			switch(day) {
				case FRIDAY, SATURDAY :
					total += 4500;
				default :
					total += 3000;
			}
		}
		// 2j = -5%, 3j = -10%, 7j = -20%, 15 = -30%
		double discount = numOfDays >= 15 ? 0.3: numOfDays >= 7 ? 0.2 : numOfDays >= 3 ? 0.1 : numOfDays >= 2 ? 0.05 : 0;

		this.price = total * (double)(1.0-discount);
	}

	public Planning getPlanning() {
		return planning;
	}

	private void setPlanning(Planning planning) {
		if(planning != null)
			this.planning = planning;
	}
	
}
