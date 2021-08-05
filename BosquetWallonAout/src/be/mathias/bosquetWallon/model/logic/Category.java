package be.mathias.bosquetWallon.model.logic;

public class Category {
	public static enum Type{
		Standing, 	//Debout (Debout)
		Diamond,	//Diamant (Cirque)
		Gold,		//Or (Concert, Cirque)
		Silver,		//Argent (Concert, Cirque)
		Bronze		//Bronze (Concert, Cirque)
	}
	
	private int id = 0;
	private Type type = null;
	private double price = 0;
	private int availableTickets = 0;
	private int maximumTickets = 0; //Total available tickets depending of Configuration of the room (owner) and type of the seats
	
	
	//Depuis db
	public Category(int id, Type type, double price, int availableTickets, int maximumTickets) {
		setId(id);
		setType(type);
		setPrice(price);
		
		if(id != 0) {
			this.maximumTickets = maximumTickets;
			setAvailableTickets(availableTickets);
		}else
			setAvailableTickets(maximumTickets);		
	}
	
	//Depuis Config
	public Category(Type type, double price, int availableTickets, Configuration.Type confType) {
		this(0, type, price, 0, 0);
		setMaximumTickets(confType);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if(this.id == 0 && id > 0)
			this.id = id;
	}
	
	public int getAvailableTickets() {
		return availableTickets;
	}

	public void setAvailableTickets(int availableTickets) {
		this.availableTickets = availableTickets;
	}

	public int getMaximumTickets() {
		return maximumTickets;
	}

	private void setMaximumTickets(Configuration.Type confType) {
		switch(confType) {
		case Concert :
			this.maximumTickets = switch(type) {
				case Gold -> 500;
				case Silver -> 1500;
				case Bronze -> 3000;
				default -> 0;
			};
			break;
			
		case Circus :
			this.maximumTickets = switch(type) {
				case Diamond -> 1000;
				case Gold -> 2000;
				case Silver -> 1500;
				case Bronze -> 1500;
				default -> 0;
			};
			break;
			
		case Standing :
			this.maximumTickets = switch(type) {
				case Standing -> 8000;
				default -> 0;
			};
			break;
			
		default :
			this.maximumTickets = 0;	
		}
	}
	
	public Type getType() {
		return type;
	}

	private void setType(Type type) {
		this.type = type;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
