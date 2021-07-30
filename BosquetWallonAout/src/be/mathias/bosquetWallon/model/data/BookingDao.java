package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;

import be.mathias.bosquetWallon.model.logic.Booking;

public class BookingDao extends Dao<Booking> {

	public BookingDao(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Booking obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Booking obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Booking obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Booking find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
