package dao;

//ggggggggggg

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.jdom2.JDOMException;

import model.LogsUsers;
import utils.Config;



/**
 * Classe DAO di accesso ai dati
 * 
 * @author ste
 *
 */
public class DAO {

	protected Connection conn;
	protected Config cfg;
	private static final String SQL_INSERT = "INSERT INTO logs (id_utente, creation_log, action) VALUES (?, CURRENT_TIMESTAMP, ?)";
	private int id_utente_sessione = 0;

	


	/**
	 * Costruttore della classe di accesso ai dati
	 * 
	 * @throws ClassNotFoundException
	 * @throws JDOMException
	 * @throws IOException
	 * @throws SQLException
	 */
	public DAO(String xml, int id_utente_sessione) throws ClassNotFoundException, JDOMException, IOException, SQLException {
		this.cfg = new Config(xml);
		this.id_utente_sessione = id_utente_sessione;

	}
	
	
	/**
	 * metodo creato per utilizzare la stessa connessione passata da input
	 * @param conn
	 */
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	protected void closeConnection() throws SQLException {
		if(this.conn != null && !this.conn.isClosed()) {
			this.conn.close();
		}
	}

	/**
	 * Legge dal file di configurazione la stringa di connessione e imposta la
	 * connessione ai dati
	 * 
	 * @return la connessione al DB
	 * @throws JDOMException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	protected Connection getConnection()
			throws JDOMException, IOException, ClassNotFoundException, SQLException {

		this.cfg.loadConfig();

		Class.forName(this.cfg.getDriver());// leggo driver

		return DriverManager.getConnection(this.cfg.getDbUrl(), this.cfg.getUser(), this.cfg.getPassword());
	}
	
	//salva su logs
	protected void salvaLogs(PreparedStatement executedStatement) throws ClassNotFoundException, JDOMException, IOException, SQLException {
		 this.conn = this.getConnection();
	        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
	            preparedStatement.setInt(1, id_utente_sessione);
	            preparedStatement.setString(2,  String.valueOf( executedStatement));
	            preparedStatement.executeUpdate();
	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                Integer id = generatedKeys.getInt(1);
	            }
	        } catch (SQLException e) {
	            printException(e);
	            throw new SQLException(e);
	        }
		
		
	}
	
	/**
	 * Gestisco l�eccezione sql stampando l'errore a console
	 * @param ex l'eccezione sollevata dal metodo
	 */
	protected void printException(Exception ex) {
		ex.printStackTrace();
		if (ex instanceof SQLException) {
			
			System.err.println("SQLState: " + ((SQLException) ex).getSQLState());
			System.err.println("Error Code: " + ((SQLException) ex).getErrorCode());
			System.err.println("Message: " + ex.getMessage());
			Throwable t = ex.getCause();
			while (t != null) {
				System.out.println("Cause: " + t);
				t = t.getCause();
			}
		}
	}
	
	
}
