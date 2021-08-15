package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import be.mathias.bosquetWallon.model.logic.Category;
import be.mathias.bosquetWallon.model.logic.Configuration;
import oracle.jdbc.OraclePreparedStatement;

public class ConfigurationDao extends Dao<Configuration> {
	
	private static CategoryDao categoryDao = (CategoryDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetCategoryDao();

	public ConfigurationDao(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Configuration obj) {
		//l'id ne peut être à 0, il doit correspondre à l'id du Show parent
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "INSERT INTO BWA_Configuration(id, type, description) VALUES (?,?,?) ";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, obj.getId());
            prepare.setString(2, obj.getType().toString());
            prepare.setString(3, obj.getDescription());
            
            int created = prepare.executeUpdate();
            
            if(created >= 1) {
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
	public boolean delete(Configuration obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "DELETE FROM BWA_Configuration WHERE id = ? ";
		
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
	public boolean update(Configuration obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "update BWA_CONFIGURATION set "
				+ "DESCRIPTION = ?,TYPE = ? "
				+ "where ID = ?";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setString(1, obj.getDescription());
            prepare.setString(2, obj.getType().toString());
			prepare.setInt(3, obj.getId());
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
	public Configuration find(int id) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_Configuration WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	Configuration.Type type = Configuration.Type.valueOf(result.getString(2));
            	String description = result.getString(3);
            	List<Category> categories = categoryDao.findByConfigurationId(id);
            	
            	Configuration configuration = new Configuration(id, type , description, categories);
                
                result.close();
                prepare.close();
                return configuration;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}

}
