package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;

import be.mathias.bosquetWallon.model.logic.Show;

public class ShowDao extends Dao<Show> {

	public ShowDao(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Show obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Show obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Show obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Show find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
