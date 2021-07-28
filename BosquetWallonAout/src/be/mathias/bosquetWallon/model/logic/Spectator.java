package be.mathias.bosquetWallon.model.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Spectator extends Person {
	
	public static enum Gender{
		Man,
		Woman
	}
	
	private String phoneNumber;
	private Gender gender;
	private LocalDate birthDate;
	private List<Order> orderList = new ArrayList<Order>();
}
