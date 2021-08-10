package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;

import be.mathias.bosquetWallon.model.logic.Booking;
import be.mathias.bosquetWallon.model.logic.Category;
import be.mathias.bosquetWallon.model.logic.Configuration;
import be.mathias.bosquetWallon.model.logic.Order;
import be.mathias.bosquetWallon.model.logic.Person;
import be.mathias.bosquetWallon.model.logic.Planning;
import be.mathias.bosquetWallon.model.logic.Representation;
import be.mathias.bosquetWallon.model.logic.Show;
import be.mathias.bosquetWallon.model.logic.Ticket;

public class OracleDaoFactory extends DaoFactory{

private static final Connection connection = DriverOracle.getInstance();
	
	public static final Connection Connect() {
		return connection;
	}

	@Override
	public Dao<Person> GetPersonDao() {
		return new PersonDao(connection);
	}

	@Override
	public Dao<Order> GetOrderDao() {
		return new OrderDao(connection);
	}


	@Override
	public Dao<Ticket> GetTicketDao() {
		return new TicketDao(connection);
	}

	@Override
	public Dao<Show> GetShowDao() {
		return new ShowDao(connection);
	}
	
	@Override
	public Dao<Category> GetCategoryDao() {
		return new CategoryDao(connection);
	}

	@Override
	public Dao<Representation> GetRepresentationDao() {
		return new RepresentationDao(connection);
	}

	@Override
	public Dao<Booking> GetBookingDao() {
		return new BookingDao(connection);
	}

	@Override
	public Dao<Planning> GetPlanningDao() {
		return new PlanningDao(connection);
	}

	@Override
	public Dao<Configuration> GetConfigurationDao() {
		return new ConfigurationDao(connection);
	}
}
