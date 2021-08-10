package be.mathias.bosquetWallon.model.logic;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;

public class Configuration {
	public static enum Type{
		Standing, 	//Debout : 8000 places
		Concert,	//Concert : 5000 places : 500 or, 1500 argent, 3000 bronze
		Circus		//Cirque : 6000 places : 1000 diamant, 2000 or, 1500 argent, 1500 bronze
	}
	
	private static final EnumSet<Category.Type> STANDING = EnumSet.of(Category.Type.Standing);
	private static final EnumSet<Category.Type> CONCERT = EnumSet.of(Category.Type.Gold, Category.Type.Silver, Category.Type.Bronze);
	private static final EnumSet<Category.Type> CIRCUS = EnumSet.of(Category.Type.Diamond, Category.Type.Gold, Category.Type.Silver, Category.Type.Bronze);
	
	private static final Map<Type, EnumSet<Category.Type>> CategoriesPerType = new HashMap<Type, EnumSet<Category.Type>>(){
		private static final long serialVersionUID = 1L;
	{
		put(Type.Standing, STANDING);
		put(Type.Concert, CONCERT);
		put(Type.Circus, CIRCUS);
	}};
	
	private int id = 0; //same id as parent Show
	private Type type = null;
	private String description;
	private List<Category> categoryList;
	
	//CTOR : Une fois Config effectuée , Plus de modifications du type si déjà reservations !!!	
	//depuis db : categoryList est importé !
	public Configuration(int id, Type type, String description, List<Category> categoryList) {
		setId(id);
		setType(type);
		setDescription(description);
		setCategoryList(categoryList);
	}
	
	//depuis Show : categoryList est construit ! //Soit MAJ dynamique de la vue, soit prix et available tickets à 0 par défaut
	public Configuration( Type type, String description) {
		this(0, type, description, null);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		if(this.id == 0 && id > 0)
			this.id = id;
	}
	
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}
	
	public Category getCategory(Category.Type type){
		Category category = categoryList.stream()
				.filter(c -> c.getType().equals(type))
				.findAny()
				.orElse(null);
		return category;
	}

	private void setCategoryList(@Nullable List<Category> categoryList) {
		if(categoryList != null)
			this.categoryList = categoryList;
		else {
			this.categoryList = new ArrayList<Category>();
			for(Category.Type caType : CategoriesPerType.get(type)) {
				Category category = new Category(caType, 0, 0, type);
				this.categoryList.add(category);
			}
		}
			
	}

	
}
