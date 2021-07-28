package be.mathias.bosquetWallon.model.logic;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Person {
	private String phoneNumber;
	private List<Planning> planningList = new ArrayList<Planning>();
	// ??? Link to Planning ??? quelle cardinalité ? => 1 objet planning par réservation, donc #0..*
}
