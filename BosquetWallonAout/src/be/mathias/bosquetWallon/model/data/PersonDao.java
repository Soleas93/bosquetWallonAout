package be.mathias.bosquetWallon.model.data;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import be.mathias.bosquetWallon.model.logic.*;
import be.mathias.bosquetWallon.model.logic.Person.PersonRole;
import be.mathias.bosquetWallon.model.logic.Spectator.Gender;
import oracle.jdbc.OraclePreparedStatement;


public class PersonDao extends Dao<Person> {
	
	private static OrderDao orderDao = (OrderDao)OracleDaoFactory.GetFactory(DaoFactory.Type.Oracle).GetOrderDao();
	private static BookingDao bookingDao = (BookingDao)OracleDaoFactory.GetFactory(DaoFactory.Type.Oracle).GetBookingDao();

	public PersonDao(Connection conn) {
		super(conn);
	}
	
	@Override
	public boolean create(Person obj) {
		OraclePreparedStatement prepare = null;
		ResultSet result = null;
		
		int id = 0;
		
		String sql = "INSERT INTO BWA_Person(firstname, lastname, address, passwordhash, emailaddress, role) VALUES (?,?,?,?,?,?) " +
                "RETURNING id INTO ?";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setString(1, obj.getFirstName());
            prepare.setString(2, obj.getLastName());
            prepare.setString(3, obj.getAddress());
            prepare.setString(4, obj.getPasswordHash());
            prepare.setString(5, obj.getEmailAddress());
            prepare.setString(6, obj.getRole().toString());
            prepare.registerReturnParameter(7, Types.INTEGER);
            
            prepare.execute();
            result = prepare.getReturnResultSet();
            
            if(result.next()) {
            	id = result.getInt(1);
            	obj.setId(id); 
            	
            	
            	switch(obj.getRole()) {
        		case Spectator:
        			createSpectator(obj);
        			break;
        		case Artist:
        			createArtist(obj);
        			break;
        		case Manager:
        			createManager(obj);
        			break;
        		case Organizer:
        			createOrganizer(obj);
        			break;
        		default:
        			return false;
        		}
            	
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
	
	private boolean createSpectator(Person obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "INSERT INTO BWA_Spectator(id, phonenumber, gender, birthdate) VALUES (?,?,?,?)";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, obj.getId());
            prepare.setString(2, ((Spectator)obj).getPhoneNumber());
            prepare.setString(3, ((Spectator)obj).getGender().toString());
            Date sqlDate = Date.valueOf(((Spectator)obj).getBirthDate());
            prepare.setDate(4, sqlDate);
            
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

	private boolean createArtist(Person obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "INSERT INTO BWA_Artist(id, showname) VALUES (?,?)";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, obj.getId());
            prepare.setString(2, ((Artist)obj).getShowName());
            
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
	
	private boolean createManager(Person obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "INSERT INTO BWA_Manager(id, phonenumber) VALUES (?,?)";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, obj.getId());
            prepare.setString(2, ((Manager)obj).getPhoneNumber());
            
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
	
	private boolean createOrganizer(Person obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "INSERT INTO BWA_Organizer(id, phonenumber) VALUES (?,?)";
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, obj.getId());
            prepare.setString(2, ((Organizer)obj).getPhoneNumber());
            
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
	public boolean delete(Person obj) {
		// Supprimer En cascade /!\
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "DELETE FROM BWA_PERSON WHERE id = ? ";
		
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
	public boolean update(Person obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "update BWA_PERSON set "
				+ "FIRSTNAME = ?,LASTNAME = ?, ADDRESS = ?,PASSWORDHASH = ?,EMAILADDRESS = ?,ROLE = ? "
				+ "where ID = ?";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
			prepare.setString(1, obj.getFirstName());
            prepare.setString(2, obj.getLastName());
            prepare.setString(3, obj.getAddress());
            prepare.setString(4, obj.getPasswordHash());
            prepare.setString(5, obj.getEmailAddress());
            prepare.setString(6, obj.getRole().toString());
            prepare.setInt(7, obj.getId());
            int updated = prepare.executeUpdate();
            
            if(updated >= 1) {
            	switch(obj.getRole()) {
        		case Spectator:
        			updateSpectator((Spectator) obj);
        			break;
        		case Artist:
        			updateArtist((Artist) obj);
        			break;
        		case Manager:
        			updateManager((Manager) obj);
        			break;
        		case Organizer:
        			updateOrganizer((Organizer) obj);
        			break;
        		default:
        			return false;
        		}
            	prepare.close();
            	return true;
            }else
            	return false;
		}catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	private boolean updateSpectator(Spectator obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "update BWA_SPECTATOR set "
				+ "PHONENUMBER = ?,GENDER = ?,BIRTHDATE = ? "
				+ "where ID = ?";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setString(1, (obj).getPhoneNumber());
            prepare.setString(2, (obj).getGender().toString());
            Date sqlDate = Date.valueOf(obj.getBirthDate());
            prepare.setDate(3, sqlDate);
            prepare.setInt(4, obj.getId());
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
	
	private boolean updateArtist(Artist obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "update BWA_ARTIST set "
				+ "SHOWNAME = ? "
				+ "where ID = ?";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
			prepare.setString(1, (obj).getShowName());
            prepare.setInt(2, obj.getId());
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
	
	private boolean updateManager(Manager obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "update BWA_MANAGER set "
				+ "PHONENUMBER = ? "
				+ "where ID = ?";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setString(1, obj.getPhoneNumber());
            prepare.setInt(2, obj.getId());
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

	private boolean updateOrganizer(Organizer obj) {
		if(obj.getId() == 0)
			return false;
		
		OraclePreparedStatement prepare = null;
		
		String sql = "update BWA_ORGANIZER set "
				+ "PHONENUMBER = ? "
				+ "where ID = ?";
		
		try {
			prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setString(1, obj.getPhoneNumber());
            prepare.setInt(2, obj.getId());
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
	public Person find(int id) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_Person WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	String firstName = result.getString(2);
            	String lastName = result.getString(3);
            	String address = result.getString(4);
            	String password = result.getString(5);
            	String email = result.getString(6);
            	PersonRole role = PersonRole.valueOf(result.getString(7));
            	
            	Person person;
            	switch(role) {
        		case Spectator:
        			person = findSpectator(id, firstName, lastName, address, password, email);
        			break;
        		case Artist:
        			person = findArtist(id, firstName, lastName, address, password, email);
        			break;
        		case Manager:
        			person = findManager(id, firstName, lastName, address, password, email);
        			break;
        		case Organizer:
        			person = findOrganizer(id, firstName, lastName, address, password, email);
        			break;
        		default:
        			person = null;
        		}
                
                result.close();
                prepare.close();
                return person;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	private Spectator findSpectator(int id, String firstName, String lastName, String address, String password, String email) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_Spectator WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	String phoneNumber = result.getString(2);
            	Gender gender = Gender.valueOf(result.getString(3));
            	LocalDate birthDate = (result.getDate(4)).toLocalDate();
            	List<Order> orderList = orderDao.findBySpectatorId(id);
            	
            	Spectator spectator= new Spectator(id, firstName, lastName, address, password, email, phoneNumber, gender, birthDate, orderList);
                
                result.close();
                prepare.close();
                return spectator;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	private Artist findArtist(int id, String firstName, String lastName, String address, String password, String email) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_Artist WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	String showName = result.getString(2);
            	
            	Artist artist= new Artist(id, firstName, lastName, address, password, email, showName);
                
                result.close();
                prepare.close();
                return artist;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	private Manager findManager(int id, String firstName, String lastName, String address, String password, String email) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_Manager WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	String phoneNumber = result.getString(2);
            	
            	Manager manager = new Manager(id, firstName, lastName, address, password, email, phoneNumber);
                
                result.close();
                prepare.close();
                return manager;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	private Organizer findOrganizer(int id, String firstName, String lastName, String address, String password, String email) {
		if(id <= 0)
			return null;
		
		String sql = "SELECT * FROM BWA_Organizer WHERE id = ?";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, id);
            
            result = prepare.executeQuery();

            if(result.next()) {
            	String phoneNumber = result.getString(2);
            	List<Booking> bookingList = bookingDao.findByOrganizerId(id);
            	
            	Organizer organizer = new Organizer(id, firstName, lastName, address, password, email, phoneNumber, bookingList);
                
                result.close();
                prepare.close();
                return organizer;
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
	 * 		id of the parent Show, MUST be > 0
	 * @return
	 * 		null if error or empty; 
	 * 		List of Artist (1..n) if success
	 */
	public List<Artist> findArtistsByShowId(int showId){
		if(showId <= 0)
			return null;
		
		List<Artist> artists = new ArrayList<Artist>();
		
		String sql = "SELECT SA.id_BWA_Show AS id_show, P.id AS id_person, P.firstname, P.lastname, P.address, P.passwordhash, p.emailaddress, a.showname FROM BWA_SHOW_PLAYEDBY_ARTISTS SA "
				+ "INNER JOIN BWA_ARTIST A ON SA.id_BWA_Artist = A.id "
				+ "INNER JOIN BWA_PERSON P ON A.id = P.id "
				+ "WHERE SA.id_BWA_Show = ? ";

		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setInt(1, showId);
            result = prepare.executeQuery();

            while(result.next()) {
            	int id = result.getInt(2);
            	String firstName = result.getString(3);
            	String lastName = result.getString(4);
            	String address = result.getString(5);
            	String password = new String(result.getBytes(6), StandardCharsets.UTF_8);
            	String email = result.getString(7);
            	String showName = result.getString(8);

            	Artist artist = new Artist(id, firstName, lastName, address, password, email, showName);
            	artists.add(artist);
            }
            
            if(artists.isEmpty())
                return null;
                
            result.close();
            prepare.close();
            return artists;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public Person findOnConnection(String emailAddress, String passwordHash) {
		if(emailAddress.isBlank() || passwordHash.isBlank())
			return null;
		
		String sql = "SELECT id FROM BWA_Person WHERE ( emailAddress = ? AND passwordHash = ?)";
		
		OraclePreparedStatement prepare = null;
        ResultSet result = null;
		
		try {
            prepare = (OraclePreparedStatement) connect.prepareStatement(sql);
            prepare.setString(1, emailAddress);
            prepare.setString(2, passwordHash);
            result = prepare.executeQuery();

            if(result.next()) {
            	int id = result.getInt(1);
            	
            	Person person = find(id);
                
                result.close();
                prepare.close();
                return person;
            }else
                return null;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
}
