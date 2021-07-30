package be.mathias.bosquetWallon.model.logic;

public class Booking {
	
	public static enum State {
		Canceled,
		Pending,
		Paid
	}
	
	private int id;
	private double deposit = 0;
	private double balance = 0;
	private State status = State.Pending;
	private double price = 0;
	private Planning planning;
}
