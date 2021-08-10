package be.mathias.bosquetWallon.model.data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import be.mathias.bosquetWallon.model.logic.Artist;
import be.mathias.bosquetWallon.model.logic.Configuration;
import be.mathias.bosquetWallon.model.logic.Representation;
import be.mathias.bosquetWallon.model.logic.Show;
import oracle.jdbc.OraclePreparedStatement;

public class ShowDao extends Dao<Show> {
	
	RepresentationDao representationDao = (RepresentationDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetRepresentationDao();
	PersonDao artistDao = (PersonDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetPersonDao();
	ConfigurationDao configurationDaoDao = (ConfigurationDao) DaoFactory.GetFactory(DaoFactory.Type.Oracle).GetConfigurationDao();

	public ShowDao(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Show obj) {
		if(obj.getParentId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		int id = 0;
		
		String sql = "INSERT INTO BWA_Show(title, image, ticketperperson, id_bwa_planning, description) VALUES (?,?,?,?,?) " +
                "RETURNING id INTO ?";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setString(1, obj.getTitle());
            
            try {
            	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            	ImageIO.write(obj.getImage(), "png", baos);
            	InputStream is = new ByteArrayInputStream(baos.toByteArray());
            	prepare.setBlob(2, is);
            }catch (IOException e) {
				e.printStackTrace();
			}
            prepare.setInt(3, obj.getTicketPerPerson());
            prepare.setInt(4, obj.getParentId());
            prepare.setString(5, obj.getDescription());
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
	public boolean delete(Show obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "DELETE FROM BWA_SHOW WHERE id = ? ";
		
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
	public boolean update(Show obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "update BWA_SHOW set"
				+ "IMAGE = ?,DESCRIPTION = ?,ID_BWA_PLANNING = ?,TITLE = ?,TICKETPERPERSON = ?"
				+ "where ID = ?;";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
			try {
            	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            	ImageIO.write(obj.getImage(), "png", baos);
            	InputStream is = new ByteArrayInputStream(baos.toByteArray());
            	prepare.setBlob(1, is);
            }catch (IOException e) {
				e.printStackTrace();
			}
            prepare.setString(2, obj.getDescription());
            prepare.setInt(3, obj.getParentId());
            prepare.setString(4, obj.getTitle());
            prepare.setInt(5, obj.getTicketPerPerson());
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
	public Show find(int id) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_Show WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	String title = result.getString(2);
            	BufferedImage image = null;
				try {
					image = ImageIO.read(result.getBlob(3).getBinaryStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
            	int ticketPerPerson = result.getInt(4);
            	int planningId = result.getInt(5);
            	String description = result.getString(6);

            	List<Representation> representationList = representationDao.findByShow(id);
            	List<Artist> artistList = artistDao.findArtistsByShowId(id);
            	Configuration configuration = configurationDaoDao.find(id);
            	
            	Show show = new Show(id, title, description, image, ticketPerPerson, artistList, representationList, configuration);
            	for(Representation representation : representationList)
                    		representation.setShow(show);
            	show.setParentId(planningId);
                
                result.close();
                prepare.close();
                return show;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	/**
	 * 
	 * @param planningId
	 * 		id of the parent Planning, MUST be > 0
	 * @return
	 * 		null if error or empty; 
	 * 		List of Show (1..n) if success
	 */
	public List<Show> findByPlanningId(int planningId){
		if(planningId <= 0)
			return null;
		
		List<Show> shows = new ArrayList<Show>();
		
		String sql = "SELECT * FROM BWA_SHOW WHERE id_BWA_Planning = ?";

		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, planningId);
            result = prepare.executeQuery();

            while(result.next()) {
            	int id = result.getInt(1);
            	String title = result.getString(2);
            	BufferedImage image = null;
				try {
					image = ImageIO.read(result.getBlob(3).getBinaryStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
            	int ticketPerPerson = result.getInt(4);
            	String description = result.getString(6);

            	List<Representation> representationList = representationDao.findByShow(id);
            	List<Artist> artistList = artistDao.findArtistsByShowId(id);
            	Configuration configuration = configurationDaoDao.find(id);
            	
            	Show show = new Show(id, title, description, image, ticketPerPerson, artistList, representationList, configuration);
            	for(Representation representation : representationList)
                    		representation.setShow(show);
            	show.setParentId(planningId);
            	
            	shows.add(show);
            }
            
            if(shows.isEmpty())
                return null;
                
            result.close();
            prepare.close();
            return shows;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}

}
