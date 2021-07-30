package be.mathias.bosquetWallon.model.data;

import javax.swing.JOptionPane;

import be.mathias.bosquetWallon.model.logic.*;

public abstract class DaoFactory {
	
	public static enum Type{
		Oracle,
		Access
	}
	
	public static DaoFactory GetFactory(DaoFactory.Type type){
		switch(type){
		case Oracle:
			return new OracleDaoFactory();
		case Access:
			JOptionPane.showMessageDialog(null, "La base de donnée Access n'est pas implémentée.");
			return null;
		default:
			return null;
		}
	}
	
	public abstract Dao<Person> GetPersonDao(); //Works for : Spectator, Artist, Manager and Organizer
	public abstract Dao<Order> GetOrderDao();
	public abstract Dao<Ticket> GetTicketDao();
	public abstract Dao<Show> GetShowDao(); // Directly load : (1)Configuration and (1..n)Category
	public abstract Dao<Representation> GetRepresentationDao();
	public abstract Dao<Booking> GetBookingDao();
	public abstract Dao<Planning> GetPlanningDao();
}
