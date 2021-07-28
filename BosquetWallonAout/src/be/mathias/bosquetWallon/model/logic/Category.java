package be.mathias.bosquetWallon.model.logic;

public class Category {
	public static enum Type{
		Standing, 	//Debout (Debout)
		Diamond,	//Diamant (Cirque)
		Gold,		//Or (Concert, Cirque)
		Silver,		//Argent (Concert, Cirque)
		Bronze		//Bronze (Concert, Cirque)
	}
	
	private int id;
	private Type type = null;
	private double price = 0;
	private int availableTickets = 0;
	private int maximumTickets = 0;
}
