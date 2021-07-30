package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;

import be.mathias.bosquetWallon.model.logic.Ticket;

public class TicketDao extends Dao<Ticket> {

	public TicketDao(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Ticket obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Ticket obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Ticket obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Ticket find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
