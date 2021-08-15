package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import be.mathias.bosquetWallon.model.logic.Category;
import oracle.jdbc.OraclePreparedStatement;

public class CategoryDao extends Dao<Category> {

	public CategoryDao(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Category obj) {
		if(obj.getParentId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		int id = 0;
		
		String sql = "INSERT INTO BWA_Category(type, price, availabletickets, maximumtickets, id_bwa_configuration) VALUES (?,?,?,?,?) " +
                "RETURNING id INTO ?";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setString(1, obj.getType().toString());
            prepare.setFloat(2, (float) obj.getPrice());
            prepare.setInt(3, obj.getAvailableTickets());
            prepare.setInt(4, obj.getMaximumTickets());
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
	public boolean delete(Category obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "DELETE FROM BWA_CATEGORY WHERE id = ? ";
		
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
	public boolean update(Category obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "update BWA_CATEGORY set "
				+ "AVAILABLETICKETS = ?,ID_BWA_CONFIGURATION = ?,PRICE = ?,MAXIMUMTICKETS = ?,TYPE = ? "
				+ "where ID = ?";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, obj.getAvailableTickets());
            prepare.setInt(2, obj.getParentId());
            prepare.setFloat(3, (float) obj.getPrice());
            prepare.setInt(4, obj.getMaximumTickets());
			prepare.setString(5, obj.getType().toString());
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
	public Category find(int id) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_Category WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	Category.Type catType = Category.Type.valueOf(result.getString(2));
            	double price = (double) result.getFloat(3);
            	int availableTickets = result.getInt(4);
            	int maximumTickets = result.getInt(5);
            	int configurationId = result.getInt(6);
            	
            	Category category = new Category(id, catType, price, availableTickets, maximumTickets);
            	category.setParentId(configurationId);
                
                result.close();
                prepare.close();
                return category;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	
	/**
	 * 
	 * @param configurationId
	 * 		id of the parent Configuration, MUST be > 0
	 * @return
	 * 		null if error or empty; 
	 * 		List of Category (1..n) if success
	 */
	public List<Category> findByConfigurationId(int configurationId){
		if(configurationId <= 0)
			return null;
		
		List<Category> categories = new ArrayList<Category>();
		
		String sql = "SELECT * FROM BWA_CATEGORY WHERE ID_BWA_CONFIGURATION = ?";

		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, configurationId);
            result = prepare.executeQuery();

            while(result.next()){
            	int id = result.getInt(1);
            	Category.Type catType = Category.Type.valueOf(result.getString(2));
            	double price = (double) result.getFloat(3);
            	int availableTickets = result.getInt(4);
            	int maximumTickets = result.getInt(5);
            	
            	Category category = new Category(id, catType, price, availableTickets, maximumTickets);
            	category.setParentId(configurationId);
            	
                categories.add(category);
            }
            if(categories.isEmpty())
                return null;

            result.close();
            prepare.close();
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}

}
