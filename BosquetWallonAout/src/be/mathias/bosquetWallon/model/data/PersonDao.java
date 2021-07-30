package be.mathias.bosquetWallon.model.data;
import java.sql.Connection;

import be.mathias.bosquetWallon.model.logic.*;


public class PersonDao extends Dao<Person> {

	public PersonDao(Connection conn) {
		super(conn);
	}
	
	@Override
	public boolean create(Person obj) {
		//Prepare create person
		
		//Prepare create child relation
		switch(obj.GetRole()) {
		case Spectator:
			
			break;
		case Artist:
			break;
		case Manager:
			break;
		case Organizer:
			break;
		default:
			return false;
		}
		
		//Execute Query
		return false;
	}

	@Override
	public boolean delete(Person obj) {
		// Supprimer En cascade /!\
		return false;
	}

	@Override
	public boolean update(Person obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Person find(int id) {
		//find by id, WITH link
		return null;
	}
	
}
