package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import model.Classi;

public class ClassiDao extends DAO {

    private static final String SQL_INSERT = "INSERT INTO classi (id_classe, nome_classe) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE classi SET nome_classe=? WHERE id_classe=?";
    private static final String SQL_DELETE = "DELETE FROM classi WHERE id_classe=?";
    private static final String SQL_GET_ALL = "SELECT * FROM classi";
    private static final String SQL_GET_BY_ID = "SELECT * FROM classi WHERE id_classe=?";
    

    public ClassiDao(String xml,int id_utente_sessione) throws ClassNotFoundException, JDOMException, IOException, SQLException {
    	
        super(xml, id_utente_sessione);
    }

    public void insertClasse(Classi classe) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, classe.getId_classe());
            preparedStatement.setString(2, classe.getNome_classe());
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

    public void deleteClasse(int idClasse) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, idClasse);
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
    }

    public void updateClasse(Classi classe) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, classe.getNome_classe());
            preparedStatement.setInt(2, classe.getId_classe());
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
    }

    public List<Classi> getAllClassi() throws SQLException, ClassNotFoundException, JDOMException, IOException {
        List<Classi> listClassi = new ArrayList<>();
        this.conn = this.getConnection();
        try (Statement st = this.conn.createStatement(); ResultSet rs = st.executeQuery(SQL_GET_ALL)) {
            while (rs.next()) {
                Classi classe = new Classi();
                classe.setId_classe(rs.getInt("id_classe"));
                classe.setNome_classe(rs.getString("nome_classe"));
                listClassi.add(classe);
            }
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
        return listClassi;
    }

    public Classi getClassiById(int idClasse) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        Classi classe = new Classi();
        this.conn = this.getConnection();
        try (PreparedStatement st = this.conn.prepareStatement(SQL_GET_BY_ID)) {
            st.setInt(1, idClasse);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    classe.setId_classe(rs.getInt("id_classe"));
                    classe.setNome_classe(rs.getString("nome_classe"));
                }
            }
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
        return classe;
    }
    
}
