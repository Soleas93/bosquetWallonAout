package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import be.mathias.bosquetWallon.model.logic.Planning;
import be.mathias.bosquetWallon.model.logic.Show;
import oracle.jdbc.OraclePreparedStatement;

public class PlanningDao extends Dao<Planning> {
	
	ShowDao showDao = (ShowDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetShowDao();

	public PlanningDao(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Planning obj) {
		//l'id ne peut pas être à 0, il doit correspondre à l'id du Booking parent
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "INSERT INTO BWA_Planning(id, begindate, enddate) VALUES (?,?,?) ";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, obj.getId());
            prepare.setObject(2, obj.getBeginDate());
            prepare.setObject(3, obj.getEndDate());
            
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
	public boolean delete(Planning obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "DELETE FROM BWA_PLANNING WHERE id = ? ";
		
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
	public boolean update(Planning obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "update BWA_PLANNING set"
				+ "BEGINDATE = ?,ENDDATE = ?"
				+ "where ID = ?";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setObject(1, obj.getBeginDate());
            prepare.setObject(2, obj.getEndDate());
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
	public Planning find(int id) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_Planning WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	LocalDate beginDate = (LocalDate) result.getObject(2);
            	LocalDate endDate = (LocalDate) result.getObject(3);
            	
            	List<Show> showList = showDao.findByPlanningId(id);
            	
                Planning planning = new Planning(id, beginDate, endDate, showList);
                
                result.close();
                prepare.close();
                return planning;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}

}
