package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;


public abstract class Dao<T> {
	protected Connection connect = null;
	
	public Dao(Connection conn) {
		this.connect = conn;
	}
	
	public abstract boolean create(T obj);
	
	public abstract boolean delete(T obj);
	
	public abstract boolean update(T obj);
	
	public abstract T find(int id);
}
