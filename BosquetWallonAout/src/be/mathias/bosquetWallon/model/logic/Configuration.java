package be.mathias.bosquetWallon.model.logic;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
	public static enum Type{
		Standing, 	//Debout : 8000 places
		Concert,	//Concert : 5000 places : 500 or, 1500 argent, 3000 bronze
		Circus		//Cirque : 6000 places : 1000 diamant, 2000 or, 1500 argent, 1500 bronze
	}
	
	private int id;
	private Type type = null;
	private String description;
	private List<Category> categoryList = new ArrayList<Category>();
	
}
