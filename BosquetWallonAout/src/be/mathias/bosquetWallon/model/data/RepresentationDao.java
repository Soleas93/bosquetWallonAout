package be.mathias.bosquetWallon.model.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import be.mathias.bosquetWallon.model.logic.Representation;
import be.mathias.bosquetWallon.model.logic.Show;
import oracle.jdbc.OraclePreparedStatement;

public class RepresentationDao extends Dao<Representation> {
	
	private static ShowDao showDao = (ShowDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetShowDao();

	public RepresentationDao(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Representation obj) {
		if(obj.getShow().getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		int id = 0;
		
		String sql = "INSERT INTO BWA_Representation(daydate, beginhour, endhour, id_bwa_show) VALUES (?,?,?,?) " +
                "RETURNING id INTO ?";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            
            Date sqlDate = Date.valueOf(obj.getDate());
			Time sqlBeginHour = Time.valueOf(obj.getBeginHour());
            Time sqlEndHour = Time.valueOf(obj.getEndHour());
            prepare.setDate(1, sqlDate);
            prepare.setTime(2, sqlBeginHour);
            prepare.setTime(3, sqlEndHour);
            prepare.setInt(4, obj.getShow().getId());
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
	public boolean delete(Representation obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "DELETE FROM BWA_Representation WHERE id = ? ";
		
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
	public boolean update(Representation obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "update BWA_REPRESENTATION set "
				+ "DAYDATE = ?,BEGINHOUR = ?,ID_BWA_SHOW = ?,ENDHOUR = ? "
				+ "where ID = ?";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
			Date sqlDate = Date.valueOf(obj.getDate());
			Time sqlBeginHour = Time.valueOf(obj.getBeginHour());
            Time sqlEndHour = Time.valueOf(obj.getEndHour());
            prepare.setDate(1, sqlDate);
            prepare.setTime(2, sqlBeginHour);
            prepare.setInt(3, obj.getShow().getId());
            prepare.setTime(4, sqlEndHour);
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
	public Representation find(int id) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_Representation WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	int showId = result.getInt(5);
            	
            	Show show = showDao.find(showId);
                
            	Representation representation = show.getRepresentationList().stream()
            			.filter(r -> ((Integer)r.getId()).equals((Integer)id))
        				.findAny()
        				.orElse(null);
                
                result.close();
                prepare.close();
                return representation;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	/**
	 * 
	 * @param showId
	 * 		id of the parent Configuration, MUST be > 0
	 * @return
	 * 		null if error or empty; 
	 * 		List of Category (1..n) if success
	 */
	public List<Representation> findByShow(int showId){
		if(showId <= 0)
			return null;
		
		List<Representation> representations = new ArrayList<Representation>();
		
		String sql = "SELECT * FROM BWA_Representation WHERE ID_BWA_SHOW = ?";

		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, showId);
            result = prepare.executeQuery();

            while(result.next()){
            	int id = result.getInt(1);
            	LocalDate date = result.getDate(2).toLocalDate();
            	LocalTime beginHour = result.getTime(3).toLocalTime();
            	LocalTime endHour = result.getTime(4).toLocalTime();
            	
                Representation representation = new Representation(id, date, beginHour, endHour, null);
            	
                representations.add(representation);
            }
            if(representations.isEmpty())
                return null;

            result.close();
            prepare.close();
            return representations;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}

}
