package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;

import be.mathias.bosquetWallon.model.logic.Planning;

public class PlanningDao extends Dao<Planning> {

	public PlanningDao(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Planning obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Planning obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Planning obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Planning find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
