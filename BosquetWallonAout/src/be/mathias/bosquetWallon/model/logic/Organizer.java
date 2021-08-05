package be.mathias.bosquetWallon.model.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class Organizer extends Person {
	private String phoneNumber;
	private List<Booking> bookingList;
	
	public Organizer(int id, String firstName, String lastName, String address, String password, String email, String phoneNumber, @Nullable List<Booking> bookingList) {
		super(id, firstName, lastName, address, password, email, PersonRole.Organizer);
		
		setPhoneNumber(phoneNumber);
		setBookingList(bookingList);
	}

	public Organizer(String firstName, String lastName, String address, String password, String email, String phoneNumber) {
		this(0, firstName, lastName, address, password, email, phoneNumber, null);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public List<Booking> getBookingList() {
		return bookingList;
	}
	
	private void setBookingList(@Nullable List<Booking> bookingList) {
		if(bookingList != null)
			this.bookingList = bookingList;
		else
			new ArrayList<Booking>();
	}
	
	public void addBooking(Booking booking) {
		if(booking != null)
			this.bookingList.add(booking);
	}
	
	public void addBookings(@NonNull Collection<? extends Booking> bookings) {
		this.bookingList.addAll(bookings);
	}
}
