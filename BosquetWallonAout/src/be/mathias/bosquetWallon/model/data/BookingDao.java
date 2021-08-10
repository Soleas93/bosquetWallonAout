package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import be.mathias.bosquetWallon.model.logic.Booking;
import be.mathias.bosquetWallon.model.logic.Planning;
import oracle.jdbc.OraclePreparedStatement;

public class BookingDao extends Dao<Booking> {
	PlanningDao planningDao = (PlanningDao) OracleDaoFactory.GetFactory(DaoFactory.Type.Oracle).GetPlanningDao();

	public BookingDao(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Booking obj) {
		if(obj.getParentId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		ResultSet result = null;
		
		int id = 0;
		
		String sql = "INSERT INTO BWA_Booking(deposit, balance, status, price, id_BWA_Organizer) VALUES (?,?,?,?,?) " +
                "RETURNING id INTO ?";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setFloat(1, (float)obj.getDeposit());
            prepare.setFloat(2, (float)obj.getBalance());
            prepare.setString(3, obj.getStatus().toString());
            prepare.setFloat(4, (float)obj.getPrice());
            prepare.setInt(5, obj.getParentId());
            prepare.registerReturnParameter(6, Types.INTEGER);
            
            prepare.execute();
            result = prepare.getReturnResultSet();
            
            if(result.next()) {
            	id = result.getInt(1);
            	obj.setId(id);            	
            	result.close();
            	prepare.close();
            	return true;
            }else
            	return false;
		}catch (SQLException e) {
            e.printStackTrace();
            return false;
		}
	}

	@Override
	public boolean delete(Booking obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "DELETE FROM BWA_Booking WHERE id = ? ";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, obj.getId());
            int deleted = prepare.executeUpdate();
            
            if(deleted >= 1) {        
            	prepare.close();
            	return true;
            }else
            	return false;
		}catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean update(Booking obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "update BWA_BOOKING set"
				+ "ID_BWA_ORGANIZER = ?,STATUS = ?,PRICE = ?,DEPOSIT = ?,BALANCE = ?"
				+ "where ID = ?";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, obj.getParentId());
            prepare.setString(2, obj.getStatus().toString());
            prepare.setFloat(3, (float)obj.getPrice());
			prepare.setFloat(4, (float)obj.getDeposit());
            prepare.setFloat(5, (float)obj.getBalance());
            prepare.setInt(6, obj.getId());
            int updated = prepare.executeUpdate();
            
            if(updated >= 1) {            	
            	prepare.close();
            	return true;
            }else
            	return false;
		}catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public Booking find(int id) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_Booking WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	//deposit, balance, status, price
            	double deposit = (double) result.getFloat(2);
            	double balance = (double) result.getFloat(3);
            	Booking.State status = Booking.State.valueOf(result.getString(4));
            	double price = (double) result.getInt(5);
            	int organizerId = result.getInt(6);
            	
            	Planning planning = planningDao.find(id);
            	
            	Booking booking = new Booking(id, deposit, balance, status, price, planning);
            	booking.setParentId(organizerId);
                
                result.close();
                prepare.close();
                return booking;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	/**
	 * 
	 * @param organizerId
	 * 		id of the parent Organizer, MUST be > 0
	 * @return
	 * 		null if error or empty; 
	 * 		List of Booking (1..n) if success
	 */
	public List<Booking> findByOrganizerId(int organizerId) {
		if(organizerId <= 0)
			return null;
		
		List<Booking> bookings  = new ArrayList<Booking>();
		
		String sql = "SELECT * FROM BWA_Booking WHERE id_BWA_Organizer = ?";

		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, organizerId);
            result = prepare.executeQuery();

            while(result.next()) {
            	int id = result.getInt(1);
            	double deposit = (double) result.getFloat(2);
            	double balance = (double) result.getFloat(3);
            	Booking.State status = Booking.State.valueOf(result.getString(4));
            	double price = (double) result.getInt(5);
            	
            	Planning planning = planningDao.find(id);
            	
            	Booking booking = new Booking(id, deposit, balance, status, price, planning);
            	booking.setParentId(organizerId);
            	
            	bookings.add(booking);
            }
            
            if(bookings.isEmpty())
                return null;
                
            result.close();
            prepare.close();
            return bookings;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}

}
