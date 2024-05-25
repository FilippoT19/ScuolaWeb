package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import model.Studenti;

public class StudentiDao extends DAO {

    private static final String SQL_INSERT = "INSERT INTO studenti (id_studente, id_classe, id_utente) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE studenti SET id_classe=? WHERE id_utente=?";
    private static final String SQL_DELETE = "DELETE FROM studenti WHERE id_studente=?";
    private static final String SQL_GET_ALL = "SELECT * FROM studenti";
    private static final String SQL_GET_BY_ID = "SELECT * FROM studenti WHERE id_studente=?";
    private static final String SQL_GET_BY_ID_UTENTE = "SELECT * FROM studenti WHERE id_utente=?";
    private static final String SQL_GET_COUNT_BY_ID_UTENTE =  "SELECT COUNT(*) FROM studenti WHERE id_utente=?";
    private static final String SQL_COUNT_STUDENTI_BY_CLASSE_ID = "SELECT COUNT(*) FROM studenti WHERE id_classe=?";
    

    public StudentiDao(String xml,int id_utente_sessione) throws ClassNotFoundException, JDOMException, IOException, SQLException {
    	
        super(xml, id_utente_sessione);
    }

    public void insertStudente(Studenti studente) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, studente.getId_studente());
            preparedStatement.setInt(2, studente.getId_classe());
            preparedStatement.setInt(3, studente.getId_utente());
            
            System.out.println(preparedStatement);
            
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

    public void deleteStudente(int idStudente) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, idStudente);
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
    }

    public void updateStudente(Studenti studente) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_UPDATE)) {
        	
            preparedStatement.setInt(1, studente.getId_classe());
            preparedStatement.setInt(2, studente.getId_utente());
            
            System.out.println(preparedStatement);
            
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
    }

    public List<Studenti> getAllStudenti() throws SQLException, ClassNotFoundException, JDOMException, IOException {
        List<Studenti> listStudenti = new ArrayList<>();
        this.conn = this.getConnection();
        try (Statement st = this.conn.createStatement(); ResultSet rs = st.executeQuery(SQL_GET_ALL)) {
            while (rs.next()) {
                Studenti studente = new Studenti();
                studente.setId_studente(rs.getInt("id_studente"));
                studente.setId_classe(rs.getInt("id_classe"));
                studente.setId_utente(rs.getInt("id_utente"));
                listStudenti.add(studente);
            }
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
        return listStudenti;
    }

    public Studenti getStudenteById(int idStudente) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        Studenti studente = new Studenti();
        this.conn = this.getConnection();
        try (PreparedStatement st = this.conn.prepareStatement(SQL_GET_BY_ID)) {
            st.setInt(1, idStudente);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    studente.setId_studente(rs.getInt("id_studente"));
                    studente.setId_classe(rs.getInt("id_classe"));
                    studente.setId_utente(rs.getInt("id_utente"));
                }
            }
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
        return studente;
    }
    public Studenti getStudenteByIdUtente(int idUtente) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        Studenti studente = new Studenti();
        this.conn = this.getConnection();
        try (PreparedStatement st = this.conn.prepareStatement(SQL_GET_BY_ID_UTENTE)) {
            st.setInt(1, idUtente);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    studente.setId_studente(rs.getInt("id_studente"));
                    studente.setId_classe(rs.getInt("id_classe"));
                    studente.setId_utente(rs.getInt("id_utente"));
                }
            }
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
        return studente;
    }
    
    public List<Studenti> getStudentiByIdClasse(int idClasse) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        List<Studenti> listStudenti = new ArrayList<>();
        this.conn = this.getConnection();
        try (PreparedStatement st = this.conn.prepareStatement("SELECT * FROM studenti WHERE id_classe=?")) {
            st.setInt(1, idClasse);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Studenti studente = new Studenti();
                    studente.setId_studente(rs.getInt("id_studente"));
                    studente.setId_classe(rs.getInt("id_classe"));
                    studente.setId_utente(rs.getInt("id_utente"));
                    listStudenti.add(studente);
                }
            }
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
        return listStudenti;
    }
    
    public boolean studenteExistsByIdUtente(int idUtente) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        boolean exists = false;
        this.conn = this.getConnection();
        try (PreparedStatement st = this.conn.prepareStatement(SQL_GET_COUNT_BY_ID_UTENTE)) {
            st.setInt(1, idUtente);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    exists = count > 0;
                }
            }
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
        return exists;
    }
    
    public int getNumeroStudentiByClasseId(int idClasse) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();
        int numeroStudenti = 0;
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_COUNT_STUDENTI_BY_CLASSE_ID)) {
            preparedStatement.setInt(1, idClasse);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    numeroStudenti = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
        return numeroStudenti;
    }


}
