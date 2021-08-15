package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import be.mathias.bosquetWallon.model.logic.Planning;
import be.mathias.bosquetWallon.model.logic.Show;
import oracle.jdbc.OraclePreparedStatement;

public class PlanningDao extends Dao<Planning> {
	
	private static ShowDao showDao = (ShowDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetShowDao();

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
            
            Date sqlBeginDate = Date.valueOf(obj.getBeginDate());
            Date sqlEndDate = Date.valueOf(obj.getEndDate());
            prepare.setDate(2, sqlBeginDate);
            prepare.setDate(3, sqlEndDate);
            
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
		
		String sql = "update BWA_PLANNING set "
				+ "BEGINDATE = ?,ENDDATE = ? "
				+ "where ID = ?";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
			Date sqlBeginDate = Date.valueOf(obj.getBeginDate());
            Date sqlEndDate = Date.valueOf(obj.getEndDate());
            prepare.setDate(1, sqlBeginDate);
            prepare.setDate(2, sqlEndDate);
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
            	LocalDate beginDate = result.getDate(2).toLocalDate();
            	LocalDate endDate = result.getDate(3).toLocalDate();
            	
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
	
	
	public boolean isCompetitor(LocalDate begin, LocalDate end) {
		if(begin == null || end == null)
			return true;
		
		String sql = "SELECT COUNT(DISTINCT id) FROM BWA_Planning "
				+ "WHERE (beginDate BETWEEN ? AND ?) OR (endDate BETWEEN ? AND ?) ";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setDate(1,  Date.valueOf(begin));
            prepare.setDate(2,  Date.valueOf(end.minusDays(1)));
            prepare.setDate(3,  Date.valueOf(begin.plusDays(1)));
            prepare.setDate(4,  Date.valueOf(end));
            
            result = prepare.executeQuery();

            if(result.next()) {
            	int count = result.getInt(1);
            	
            	boolean competitor = count > 0;
                
                result.close();
                prepare.close();
                return competitor;
            }else
                return false;
		}catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
	}
	
	public List<Integer> bookedDate(LocalDate month){
		if(month == null)
			return null;
		
		String sql = "SELECT beginDate, endDate FROM BWA_Planning "
				+ "WHERE (beginDate BETWEEN ? AND ?) OR (endDate BETWEEN ? AND ?) ";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
        
        LocalDate firstday = month.withDayOfMonth(1);
        LocalDate lastday = month.withDayOfMonth(month.lengthOfMonth());
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setDate(1,  Date.valueOf(firstday));
            prepare.setDate(2,  Date.valueOf(lastday));
            prepare.setDate(3,  Date.valueOf(firstday));
            prepare.setDate(4,  Date.valueOf(lastday));
            
            result = prepare.executeQuery();

            List<Integer> daysBooked = new ArrayList<Integer>();
            while(result.next()) {
            	LocalDate beginDate = result.getDate(1).toLocalDate();
            	LocalDate endDate = result.getDate(2).toLocalDate();
            	
            	if(beginDate.isBefore(firstday))
            		beginDate = firstday;
            	if(endDate.isAfter(lastday))
            		endDate = lastday;
            	
            	while(beginDate.isBefore(endDate)) {
            		daysBooked.add(beginDate.getDayOfMonth());
            		beginDate = beginDate.plusDays(1);
            	}                
            }
            if(daysBooked == null || daysBooked.isEmpty())
            	return null;
            
            result.close();
            prepare.close();
            return daysBooked;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}

}
