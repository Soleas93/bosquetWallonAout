package be.mathias.bosquetWallon.model.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import be.mathias.bosquetWallon.model.logic.Order;
import be.mathias.bosquetWallon.model.logic.Ticket;
import oracle.jdbc.OraclePreparedStatement;

public class OrderDao extends Dao<Order> {
	
	private static TicketDao ticketDao = (TicketDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetTicketDao();
	
	public OrderDao(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Order obj) {
		
		if(obj.getParentId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		int id = 0;
		
		String sql = "INSERT INTO BWA_ORDER(paymentMethod, deliveryMethod, total, id_BWA_Spectator) VALUES (?,?,?,?) " +
                "RETURNING id INTO ?";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setString(1, obj.getPaymentMethod().toString());
            prepare.setString(2, obj.getDeliveryMethod().toString());
            prepare.setFloat(3, (float) obj.getTotal());
            prepare.setInt(4, obj.getParentId());
            prepare.registerReturnParameter(5, Types.INTEGER);
            prepare.execute();
            result = prepare.getReturnResultSet();
            
            if(result.next()) {
            	id = result.getInt(1);
            	obj.setId(id);
            	
            	//TODO : Create tickets
            	
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
	public boolean delete(Order obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "DELETE FROM BWA_ORDER WHERE id = ? ";
		
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
	public boolean update(Order obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "UPDATE BWA_ORDER " +
				"SET paymentMethod = ?, deliveryMethod = ?, total = ? " +
				"WHERE id = ? ";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setString(1, obj.getPaymentMethod().toString());
            prepare.setString(2, obj.getDeliveryMethod().toString());
            prepare.setFloat(3, (float) obj.getTotal());
            prepare.setInt(4, obj.getId());
            int updated = prepare.executeUpdate();
            
            if(updated >= 1) {            	
            	//TODO : update tickets
            	
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
	public Order find(int id) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_ORDER WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	Order.PaymentMethod paymentMethod = Order.PaymentMethod.valueOf(result.getString(2));
            	Order.DeliveryMethod deliveryMethod = Order.DeliveryMethod.valueOf(result.getString(3));
            	double total = (double) result.getFloat(4);
            	
            	List<Ticket> tickets = ticketDao.findByOrderId(id);
            	
                Order order = new Order(id, paymentMethod, deliveryMethod, total, tickets);
                
                result.close();
                prepare.close();
                return order;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	/**
	 * 
	 * @param spectatorId
	 * 		id of the parent Spectator, MUST be > 0
	 * @return
	 * 		null if error or empty; 
	 * 		List of Order (1..n) if success
	 */
	public List<Order> findBySpectatorId(int spectatorId) {
		if(spectatorId <= 0)
			return null;
		
		List<Order> orders  = new ArrayList<Order>();
		
		String sql = "SELECT * FROM BWA_ORDER WHERE id_BWA_Spectator = ?";

		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, spectatorId);
            result = prepare.executeQuery();

            while(result.next()) {
            	int id = result.getInt(1);
            	Order.PaymentMethod paymentMethod = Order.PaymentMethod.valueOf(result.getString(2));
            	Order.DeliveryMethod deliveryMethod = Order.DeliveryMethod.valueOf(result.getString(3));
            	double total = (double) result.getFloat(4);
            	
            	List<Ticket> tickets = ticketDao.findByOrderId(id);
            	
            	Order order = new Order(id, paymentMethod, deliveryMethod, total, tickets);
            	order.setParentId(spectatorId);
            	
            	orders.add(order);
            }
            
            if(orders.isEmpty())
                return null;
                
            result.close();
            prepare.close();
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}

}
