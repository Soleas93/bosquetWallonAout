package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.mathias.bosquetWallon.model.logic.Category;
import be.mathias.bosquetWallon.model.logic.Ticket;
import be.mathias.bosquetWallon.model.logic.Representation;
import oracle.jdbc.OraclePreparedStatement;

public class TicketDao extends Dao<Ticket> {
	
	private static Dao<Representation> representationDao = DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetRepresentationDao();

	public TicketDao(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Ticket obj) {
		if(obj.getParentId() == 0 || obj.getRepresentation().getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		int id = 0;
		
		String sql = "INSERT INTO BWA_TICKET(price, id_BWA_Order, id_BWA_Representation, type) VALUES (?,?,?,?) " +
                "RETURNING id INTO ?";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setFloat(1, (float) obj.getPrice());
            prepare.setInt(2, obj.getParentId());
            prepare.setInt(3, obj.getRepresentation().getId());
            prepare.setString(4, obj.getType().toString());
            prepare.registerReturnParameter(5, Types.INTEGER);
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
	public boolean delete(Ticket obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "DELETE FROM BWA_TICKET WHERE id = ? ";
		
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
	public boolean update(Ticket obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "update BWA_TICKET set "
				+ "ID_BWA_REPRESENTATION = ?,ID_BWA_ORDER = ?,PRICE = ?,TYPE = ? "
				+ "where ID = ?";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
			prepare.setInt(1, obj.getRepresentation().getId());
            prepare.setInt(2, obj.getParentId());
			prepare.setFloat(3, (float) obj.getPrice());
            prepare.setString(4, obj.getType().toString());
            prepare.setInt(5, obj.getId());
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
	public Ticket find(int id) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_TICKET WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	double price = (double) result.getFloat(2);
            	int orderId = result.getInt(3);
            	int idRepresentation = result.getInt(4);
            	Category.Type catType = Category.Type.valueOf(result.getString(5));
            	
            	Representation representation = representationDao.find(idRepresentation);
            	
            	Ticket ticket = new Ticket(id, representation , catType, price);
            	ticket.setParentId(orderId);
                
                result.close();
                prepare.close();
                return ticket;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	/**
	 * 
	 * @param orderId
	 * 		id of the parent Order, MUST be > 0
	 * @return
	 * 		null if error or empty; 
	 * 		List of Tickets (1..n) if success
	 */
	public List<Ticket> findByOrderId(int orderId){
		if(orderId <= 0)
			return null;
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		
		String sql = "SELECT * FROM BWA_TICKET WHERE id_BWA_Order = ?";

		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, orderId);
            result = prepare.executeQuery();

            while(result.next()) {
            	int id = result.getInt(1);
            	double price = (double) result.getFloat(2);
            	int idRepresentation = result.getInt(4);
            	Category.Type catType = Category.Type.valueOf(result.getString(5));
            	
            	Representation representation = representationDao.find(idRepresentation);
            	
            	Ticket ticket = new Ticket(id, representation , catType, price);
            	ticket.setParentId(orderId);
            	
                tickets.add(ticket);
            }
            if(tickets.isEmpty()) {
                return null;
            }

            result.close();
            prepare.close();
            return tickets;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	
	
	public Map<Category.Type, Integer> countByRepresentation(int representationId){
		//TODO :pas sur que celle-là soit nécessaire, à voir plus tard
		return null;
	}

}
