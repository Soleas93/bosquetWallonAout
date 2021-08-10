package be.mathias.bosquetWallon.model.logic;

public class Ticket {
	private int id = 0;
	private double price = 0;
	private Representation representation;
	private Category.Type type;
	
	//db only
	private int parentId = 0;
	
	public Ticket(int id, Representation representation, Category.Type type, double price) {
		setId(id);
		setRepresentation(representation);
		setType(type);
		
		//si id > 0, on récupère le prix précédemment enregistré dans la db, pas de la catégorie.
		if(id > 0)
			this.price = price;
		else
			setPrice();	
	}
	
	public Ticket(Representation representation, Category.Type type) {
		this(0, representation, type, 0);
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
	
	public void setParentId(int orderId) {
		this.parentId = orderId;
	}


	public Representation getRepresentation() {
		return representation;
	}

	private void setRepresentation(Representation representation) {
		this.representation = representation;
	}
	
	
	public Category.Type getType() {
		return type;
	}

	private void setType(Category.Type type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	private void setPrice() {
			this.price = this.representation.getShow().getConfiguration().getCategory(this.type).getPrice();
	}
	
	
}
