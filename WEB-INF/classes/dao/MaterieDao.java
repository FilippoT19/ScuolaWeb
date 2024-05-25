package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import model.Materie;

public class MaterieDao extends DAO {

    private static final String SQL_INSERT = "INSERT INTO materie (id_materia, nome_materia) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE materie SET nome_materia=? WHERE id_materia=?";
    private static final String SQL_DELETE = "DELETE FROM materie WHERE id_materia=?";
    private static final String SQL_GET_ALL = "SELECT * FROM materie";
    private static final String SQL_GET_BY_ID = "SELECT * FROM materie WHERE id_materia=?";

    public MaterieDao(String xml,int id_utente_sessione) throws ClassNotFoundException, JDOMException, IOException, SQLException {
    	
        super(xml, id_utente_sessione);
    }
    public void insertMateria(Materie materia) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, materia.getId_materia());
            preparedStatement.setString(2, materia.getNome_materia());
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

    public void deleteMateria(int idMateria) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, idMateria);
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
    }

    public void updateMateria(Materie materia) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, materia.getNome_materia());
            preparedStatement.setInt(2, materia.getId_materia());
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
    }

    public List<Materie> getAllMaterie() throws SQLException, ClassNotFoundException, JDOMException, IOException {
        List<Materie> listMaterie = new ArrayList<>();
        this.conn = this.getConnection();
        try (Statement st = this.conn.createStatement(); ResultSet rs = st.executeQuery(SQL_GET_ALL)) {
            while (rs.next()) {
                Materie materia = new Materie();
                materia.setId_materia(rs.getInt("id_materia"));
                materia.setNome_materia(rs.getString("nome_materia"));
                listMaterie.add(materia);
            }
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
        return listMaterie;
    }

    public Materie getMateriaById(int idMateria) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        Materie materia = new Materie();
        this.conn = this.getConnection();
        try (PreparedStatement st = this.conn.prepareStatement(SQL_GET_BY_ID)) {
            st.setInt(1, idMateria);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    materia.setId_materia(rs.getInt("id_materia"));
                    materia.setNome_materia(rs.getString("nome_materia"));
                }
            }
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
        return materia;
    }
}
