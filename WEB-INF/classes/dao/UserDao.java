/**
 * 
 */
package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom2.JDOMException;

import com.mysql.cj.exceptions.RSAException;

import model.User;
import utils.StringUtils;

/**
 * @author Filippo Topinelli
 *
 */
public class UserDao extends DAO {
	
	private static final String SQL_INSERT = "INSERT INTO user (username, first_name, last_name, email, cellphone, birth_date, id_role, date_access, date_insert, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_DATE_ACCESS = "UPDATE user SET date_access=? WHERE id=?";
	private static final String SQL_UPDATE = "UPDATE user SET username=?, first_name=?, last_name=?, email=?, cellphone=?, birth_date=?, id_role=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM user WHERE id=?";
	private static final String SQL_GET_ALL = "SELECT * FROM user";
	private static final String SQL_GET_BY_ID = "SELECT * FROM user where id=?";
	private static final String SQL_GET_BY_USER_AND_PASSWORD = "SELECT * FROM user where username=? and password=?";
	private static final String SQL_GET_STUDENTS = "SELECT u.* FROM user u JOIN users_roles ur ON u.Id = ur.Id WHERE ur.role_name = 'Studente'";
	private static final String SQL_GET_CURRENT_ROLES_OF_USER = "SELECT role_name FROM users_roles WHERE finish_assignment > current_date OR finish_assignment IS NULL AND Id=?";
	private static final String SQL_INSERT_ROLES_OF_USER = "INSERT INTO users_roles(Id, role_name, start_assignment, finish_assignment) VALUES(?, ?, ?, ?)";
	private static final String SQL_DELETE_ROLES_OF_USER = "DELETE FROM users_roles WHERE Id = ?";
	private static final String GET_ID_LAST_USER = "SELECT MAX(id) AS highest_id FROM user";
			
	/**
	 * @param xml
	 * @throws ClassNotFoundException
	 * @throws JDOMException
	 * @throws IOException
	 * @throws SQLException
	 */
	public UserDao(String xml,int id_utente_sessione) throws ClassNotFoundException, JDOMException, IOException, SQLException {
    	
        super(xml, id_utente_sessione);
    }
	
	public User getByUserAndPassword(String username, String password) throws Exception {
		User user = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			this.conn = this.getConnection();
			
			st = this.conn.prepareStatement(SQL_GET_BY_USER_AND_PASSWORD);
			
			st.setString(1, username);
			st.setString(2, password);
			
			rs = st.executeQuery();
			System.out.println("\n\n" + st);
			System.out.println(rs);
			
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setCellphone(rs.getString("cellphone"));
				user.setBirth_date(rs.getDate("birth_date"));
				user.setIdRole(rs.getInt("id_role"));
				user.setDate_access(rs.getDate("date_access"));
				user.setDate_insert(rs.getDate("date_insert"));
				user.setPassword(rs.getString("password"));
			}
			
		}catch (Exception e) {
			printException(e);
			
			user = null;
			
			throw new Exception(e);
		}
		
		return user;
	}
	
	public User getById(int id) throws Exception {
		User user = new User();
		
		try {
			this.conn = this.getConnection();
			
			PreparedStatement st = this.conn.prepareStatement(SQL_GET_BY_ID);
			
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {

				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setCellphone(rs.getString("cellphone"));
				user.setBirth_date(rs.getDate("birth_date"));
				user.setIdRole(rs.getInt("id_role"));
				user.setDate_access(rs.getDate("date_access"));
				user.setDate_insert(rs.getDate("date_insert"));
				user.setPassword(rs.getString("password"));
			}
		}catch (Exception e) {
			printException(e);
			
			throw new Exception(e);
		}
		
		return user;
	}
	
	public List<User> getAll(String username) throws Exception {
		List<User> listUsers = new ArrayList<User>();
		
		this.conn = this.getConnection();
		
		try {
			Statement st = this.conn.createStatement();
			
			ResultSet rs = st.executeQuery(SQL_GET_ALL);
			
			while (rs.next()) {
				User temp = new User();
				temp.setId(rs.getInt("id"));
				temp.setUsername(rs.getString("username"));
				temp.setFirst_name(rs.getString("first_name"));
				temp.setLast_name(rs.getString("last_name"));
				temp.setEmail(rs.getString("email"));
				temp.setCellphone(rs.getString("cellphone"));
				temp.setBirth_date(rs.getDate("birth_date"));
				temp.setIdRole(rs.getInt("id_role"));
				temp.setDate_access(rs.getDate("date_access"));
				temp.setDate_insert(rs.getDate("date_insert"));
				temp.setPassword(rs.getString("password"));
				
				if(username == null || username.isEmpty() || username.equals(temp.getUsername()) ) {
					listUsers.add(temp);
				}

			}
		}catch (Exception e) {
			printException(e);
			
			throw new Exception(e);
		}

		
		return listUsers;
	}

	public void insertUser(User user) throws SQLException, ClassNotFoundException, JDOMException, IOException 
	{
		
	//"INSERT INTO user (username, first_name, last_name, email, cellphone, birth_date, id_role, date_access, date_insert, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		this.conn = this.getConnection();
		//Preparo la query
		try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getFirst_name());
			preparedStatement.setString(3, user.getLast_name());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getCellphone());
			preparedStatement.setDate(6, new java.sql.Date(user.getBirth_date().getTime()));
			preparedStatement.setInt(7, user.getIdRole());
			preparedStatement.setDate(8, java.sql.Date.valueOf(LocalDate.now()));
			preparedStatement.setDate(9, java.sql.Date.valueOf(LocalDate.now()));
			preparedStatement.setString(10, user.getPassword());
			
			System.out.println(preparedStatement);
			
			// Eseguo la query
			preparedStatement.executeUpdate();
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
		    if (generatedKeys.next()) {
		        Integer id = generatedKeys.getInt(1);
		    }
		    this.salvaLogs(preparedStatement);
		} catch (SQLException e) {
			// processo la sql exception
			printException(e);
			
			throw new SQLException(e);
		}

	}
	
	public void deleteUser(int user_id) throws SQLException, ClassNotFoundException, JDOMException, IOException {
		
		this.conn = this.getConnection();
		
		//Preparo la query
		try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_DELETE)) {

			preparedStatement.setInt(1, user_id);

			System.out.println(preparedStatement);
			

			preparedStatement.executeUpdate();
			this.salvaLogs(preparedStatement);
		} catch (SQLException e) {
			// processo la sql exception
			printException(e);
			
			throw new SQLException(e);
		}
	}
	
	public void updateUser(User user) throws SQLException, ClassNotFoundException, JDOMException, IOException {
		this.conn = this.getConnection();
		
		//Preparo la query
		try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_UPDATE)) {

			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getFirst_name());
			preparedStatement.setString(3, user.getLast_name());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getCellphone());
			preparedStatement.setDate(6, new java.sql.Date(user.getBirth_date().getTime()));
			preparedStatement.setInt(7, user.getIdRole());
			preparedStatement.setInt(8, user.getId());


			System.out.println(preparedStatement);
			

			preparedStatement.executeUpdate();

			this.salvaLogs(preparedStatement);
		} catch (SQLException e) {
			// processo la sql exception
			printException(e);
			
			throw new SQLException(e);
		}
	}
	
	public void updateUser4DateAccess(User user) throws SQLException, ClassNotFoundException, JDOMException, IOException {
		this.conn = this.getConnection();
		
		//Preparo la query
		try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_UPDATE_DATE_ACCESS)) {

			preparedStatement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			preparedStatement.setInt(2, user.getId());


			System.out.println(preparedStatement);
			

			preparedStatement.executeUpdate();
			this.salvaLogs(preparedStatement);
		} catch (SQLException e) {
			// processo la sql exception
			printException(e);
			
			throw new SQLException(e);
		}
	}
	

	public int getIdLastUser() throws SQLException, ClassNotFoundException, JDOMException, IOException {
		this.conn = this.getConnection();
		Statement st = this.conn.createStatement();
		
		try {
			int a=0;
			ResultSet rs = st.executeQuery(GET_ID_LAST_USER);
			if (rs.next()) {
				a = rs.getInt("highest_id");
			}
			
			return a;
			
		}catch (SQLException e) {
			// processo la sql exception
			printException(e);
			
			throw new SQLException(e);
		}
		
	
	}
}



	

