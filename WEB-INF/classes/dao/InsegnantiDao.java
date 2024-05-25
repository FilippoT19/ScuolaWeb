package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import model.Insegnanti;

public class InsegnantiDao extends DAO {

	private static final String SQL_INSERT = "INSERT INTO insegnanti (id_insegnante, id_utente) VALUES (?, ?)";
	private static final String SQL_UPDATE = "UPDATE insegnanti SET id_utente=? WHERE id_insegnante=?";
	private static final String SQL_DELETE = "DELETE FROM insegnanti WHERE id_insegnante=?";
	private static final String SQL_GET_ALL = "SELECT * FROM insegnanti";
	private static final String SQL_GET_BY_ID = "SELECT * FROM insegnanti WHERE id_insegnante=?";
	private static final String SQL_GET_BY_ID_UTENTE = "SELECT * FROM insegnanti WHERE id_utente = ?";
	private static final String GET_ID_LAST_INSEGNANTE = "SELECT MAX(id_insegnante) AS highest_id FROM insegnanti";

	public InsegnantiDao(String xml,int id_utente_sessione) throws ClassNotFoundException, JDOMException, IOException, SQLException {
    	
        super(xml, id_utente_sessione);
    }

	public void insertInsegnante(Insegnanti insegnante)
			throws SQLException, ClassNotFoundException, JDOMException, IOException {
		this.conn = this.getConnection();
		try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_INSERT,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, insegnante.getId_insegnante());
			preparedStatement.setInt(2, insegnante.getId_utente());
			preparedStatement.executeUpdate();
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				Integer id = generatedKeys.getInt(1);
			}
			this.salvaLogs(preparedStatement);
		} catch (SQLException e) {
			printException(e);
			throw new SQLException(e);
		}
	}

	public void deleteInsegnante(int idInsegnante)
			throws SQLException, ClassNotFoundException, JDOMException, IOException {
		this.conn = this.getConnection();
		try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_DELETE)) {
			preparedStatement.setInt(1, idInsegnante);
			preparedStatement.executeUpdate();
			this.salvaLogs(preparedStatement);
		} catch (SQLException e) {
			printException(e);
			throw new SQLException(e);
		}
	}

	public void updateInsegnante(Insegnanti insegnante)
			throws SQLException, ClassNotFoundException, JDOMException, IOException {
		this.conn = this.getConnection();
		try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_UPDATE)) {
			preparedStatement.setInt(1, insegnante.getId_utente());
			preparedStatement.setInt(2, insegnante.getId_insegnante());
			preparedStatement.executeUpdate();
			this.salvaLogs(preparedStatement);
		} catch (SQLException e) {
			printException(e);
			throw new SQLException(e);
		}
	}

	public List<Insegnanti> getAllInsegnanti() throws SQLException, ClassNotFoundException, JDOMException, IOException {
		List<Insegnanti> listInsegnanti = new ArrayList<>();
		this.conn = this.getConnection();
		try (Statement st = this.conn.createStatement(); ResultSet rs = st.executeQuery(SQL_GET_ALL)) {
			while (rs.next()) {
				Insegnanti insegnante = new Insegnanti();
				insegnante.setId_insegnante(rs.getInt("id_insegnante"));
				insegnante.setId_utente(rs.getInt("id_utente"));
				listInsegnanti.add(insegnante);
			}
		} catch (SQLException e) {
			printException(e);
			throw new SQLException(e);
		}
		return listInsegnanti;
	}

	public Insegnanti getInsegnanteById(int idInsegnante)
			throws SQLException, ClassNotFoundException, JDOMException, IOException {
		Insegnanti insegnante = new Insegnanti();
		this.conn = this.getConnection();
		try (PreparedStatement st = this.conn.prepareStatement(SQL_GET_BY_ID)) {
			st.setInt(1, idInsegnante);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					insegnante.setId_insegnante(rs.getInt("id_insegnante"));
					insegnante.setId_utente(rs.getInt("id_utente"));
				}
			}
		} catch (SQLException e) {
			printException(e);
			throw new SQLException(e);
		}
		return insegnante;
	}

	public int getIdLastInsegnante() throws SQLException, ClassNotFoundException, JDOMException, IOException {

		Statement st = this.conn.createStatement();
		this.conn = this.getConnection();

		try {
			int a = 0;
			ResultSet rs = st.executeQuery(GET_ID_LAST_INSEGNANTE);
			if (rs.next()) {
				a = rs.getInt("highest_id");
			}

			return a;

		} catch (SQLException e) {
			// processo la sql exception
			printException(e);

			throw new SQLException(e);
		}

	}
	public Insegnanti getInsegnanteByIdUtente(int idUtente)
			throws SQLException, ClassNotFoundException, JDOMException, IOException {
		Insegnanti insegnante = new Insegnanti();
		this.conn = this.getConnection();
		try (PreparedStatement st = this.conn.prepareStatement(SQL_GET_BY_ID_UTENTE)) {
			st.setInt(1, idUtente);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					insegnante.setId_insegnante(rs.getInt("id_insegnante"));
					insegnante.setId_utente(rs.getInt("id_utente"));
				}
			}
		} catch (SQLException e) {
			printException(e);
			throw new SQLException(e);
		}
		return insegnante;
	}
	public boolean insegnanteExistsByIdUtente(Integer idUtente) throws SQLException, ClassNotFoundException, JDOMException, IOException {
	    boolean exists = false;
	    if (idUtente != null) {

	    this.conn = this.getConnection();
	    try (PreparedStatement st = this.conn.prepareStatement(SQL_GET_BY_ID_UTENTE)) {
	        st.setInt(1, idUtente);
	        try (ResultSet rs = st.executeQuery()) {
	            if (rs.next()) {
	                exists = true;
	            }
	        }
	    } catch (SQLException e) {
	        printException(e);
	        throw new SQLException(e);
	    }
	    }
	    return exists;
	}

}
