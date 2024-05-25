package dao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Valutazioni;
import org.jdom2.JDOMException;

public class ValutazioniDao extends DAO {

    private static final String SQL_INSERT = "INSERT INTO Valutazioni (id_studente, id_materia, id_classe, data_valutazione, voto, tipo_valutazione) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Valutazioni SET id_studente=?, id_materia=?, id_classe=?, data_valutazione=?, voto=?, tipo_valutazione=? WHERE id_valutazione=?";
    private static final String SQL_DELETE = "DELETE FROM Valutazioni WHERE id_valutazione=?";
    private static final String SQL_GET_ALL = "SELECT * FROM Valutazioni";
    private static final String SQL_GET_BY_ID = "SELECT * FROM Valutazioni WHERE id_valutazione=?";
    private static final String SQL_GET_BY_ID_STUDENTE = "SELECT * FROM Valutazioni WHERE id_studente=?";
    private static final String SQL_GET_BY_ID_MATERIA = "SELECT * FROM Valutazioni WHERE id_materia=?";
    private static final String SQL_GET_BY_ID_CLASSE = "SELECT * FROM Valutazioni WHERE id_classe=?";
    private static final String SQL_GET_BY_CLASSE_MATERIA = "SELECT * FROM Valutazioni WHERE id_classe=? AND id_materia=?";
    private static final String SQL_DELETE_FROM_STUDENTE = "DELETE FROM Valutazioni WHERE id_studente=?";
    private static final String SQL_GET_BY_STUDENTE_MATERIA = "SELECT * FROM Valutazioni WHERE id_studente=? AND id_materia=?";

    
    public ValutazioniDao(String xml,int id_utente_sessione) throws ClassNotFoundException, JDOMException, IOException, SQLException {
    	
        super(xml, id_utente_sessione);
    }

    public Valutazioni getById(int id) throws Exception {
        Valutazioni valutazione = new Valutazioni();

        try {
            this.conn = this.getConnection();
            PreparedStatement st = this.conn.prepareStatement(SQL_GET_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                valutazione.setId_valutazione(rs.getInt("id_valutazione"));
                valutazione.setId_studente(rs.getInt("id_studente"));
                valutazione.setId_materia(rs.getInt("id_materia"));
                valutazione.setId_classe(rs.getInt("id_classe"));
                valutazione.setDataValutazione(rs.getDate("data_valutazione"));
                valutazione.setVoto(rs.getDouble("voto"));
                valutazione.setTipoValutazione(rs.getString("tipo_valutazione"));
            }
            
        } catch (Exception e) {
            printException(e);
            throw new Exception(e);
        }

        return valutazione;
    }

    public List<Valutazioni> getAll() throws Exception {
        List<Valutazioni> listValutazioni = new ArrayList<>();

        this.conn = this.getConnection();

        try {
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery(SQL_GET_ALL);

            while (rs.next()) {
                Valutazioni temp = new Valutazioni();
                temp.setId_valutazione(rs.getInt("id_valutazione"));
                temp.setId_studente(rs.getInt("id_studente"));
                temp.setId_materia(rs.getInt("id_materia"));
                temp.setId_classe(rs.getInt("id_classe"));
                temp.setDataValutazione(rs.getDate("data_valutazione"));
                temp.setVoto(rs.getDouble("voto"));
                temp.setTipoValutazione(rs.getString("tipo_valutazione"));
                listValutazioni.add(temp);
            }
        } catch (Exception e) {
            printException(e);
            throw new Exception(e);
        }

        return listValutazioni;
    }

    public void insertValutazione(Valutazioni valutazione) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();

        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, valutazione.getId_studente());
            preparedStatement.setInt(2, valutazione.getId_materia());
            preparedStatement.setInt(3, valutazione.getId_classe());
            preparedStatement.setDate(4, new java.sql.Date(valutazione.getDataValutazione().getTime()));
            preparedStatement.setDouble(5, valutazione.getVoto());
            preparedStatement.setString(6, valutazione.getTipoValutazione());

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Integer id = generatedKeys.getInt(1);
                valutazione.setId_valutazione(id);
            }
            this.salvaLogs(preparedStatement);
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
    }

    public void deleteValutazione(int idValutazione) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();

        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, idValutazione);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
    }
    
    public void deleteValutazioneFromStudente(int idStudente) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();

        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_DELETE_FROM_STUDENTE)) {
            preparedStatement.setInt(1, idStudente);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
    }

    public void updateValutazione(Valutazioni valutazione) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        this.conn = this.getConnection();

        try (PreparedStatement preparedStatement = this.conn.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setInt(1, valutazione.getId_studente());
            preparedStatement.setInt(2, valutazione.getId_materia());
            preparedStatement.setInt(3, valutazione.getId_classe());
            preparedStatement.setDate(4, new java.sql.Date(valutazione.getDataValutazione().getTime()));
            preparedStatement.setDouble(5, valutazione.getVoto());
            preparedStatement.setString(6, valutazione.getTipoValutazione());
            preparedStatement.setInt(7, valutazione.getId_valutazione());

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        } catch (SQLException e) {
            printException(e);
            throw new SQLException(e);
        }
    }

    public List<Valutazioni> getValutazioniByIdStudente(int idStudente) throws Exception {
        return getValutazioniByField(SQL_GET_BY_ID_STUDENTE, idStudente);
    }

    public List<Valutazioni> getValutazioniByIdMateria(int idMateria) throws Exception {
        return getValutazioniByField(SQL_GET_BY_ID_MATERIA, idMateria);
    }

    public List<Valutazioni> getValutazioniByIdClasse(int idClasse) throws Exception {
        return getValutazioniByField(SQL_GET_BY_ID_CLASSE, idClasse);
    }

    private List<Valutazioni> getValutazioniByField(String query, int value) throws Exception {
        List<Valutazioni> listValutazioni = new ArrayList<>();

        this.conn = this.getConnection();

        try {
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setInt(1, value);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Valutazioni temp = new Valutazioni();
                temp.setId_valutazione(rs.getInt("id_valutazione"));
                temp.setId_studente(rs.getInt("id_studente"));
                temp.setId_materia(rs.getInt("id_materia"));
                temp.setId_classe(rs.getInt("id_classe"));
                temp.setDataValutazione(rs.getDate("data_valutazione"));
                temp.setVoto(rs.getDouble("voto"));
                temp.setTipoValutazione(rs.getString("tipo_valutazione"));
                listValutazioni.add(temp);
            }
        } catch (Exception e) {
            printException(e);
            throw new Exception(e);
        }

        return listValutazioni;
    }

    public List<Valutazioni> getValutazioniByClasseMateria(int idClasse, int idMateria) throws Exception {
        List<Valutazioni> listValutazioni = new ArrayList<>();

        this.conn = this.getConnection();

        try {
            String query = SQL_GET_BY_CLASSE_MATERIA;
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setInt(1, idClasse);
            st.setInt(2, idMateria);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Valutazioni temp = new Valutazioni();
                temp.setId_valutazione(rs.getInt("id_valutazione"));
                temp.setId_studente(rs.getInt("id_studente"));
                temp.setId_materia(rs.getInt("id_materia"));
                temp.setId_classe(rs.getInt("id_classe"));
                temp.setDataValutazione(rs.getDate("data_valutazione"));
                temp.setVoto(rs.getDouble("voto"));
                temp.setTipoValutazione(rs.getString("tipo_valutazione"));
                listValutazioni.add(temp);
            }
        } catch (Exception e) {
            printException(e);
            throw new Exception(e);
        }

        return listValutazioni;
    }
    
    public List<Valutazioni> getValutazioniByStudenteMateria(int idStudente, int idMateria) throws Exception {
        List<Valutazioni> listValutazioni = new ArrayList<>();

        this.conn = this.getConnection();

        try {
            PreparedStatement st = this.conn.prepareStatement(SQL_GET_BY_STUDENTE_MATERIA);
            st.setInt(1, idStudente);
            st.setInt(2, idMateria);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Valutazioni temp = new Valutazioni();
                temp.setId_valutazione(rs.getInt("id_valutazione"));
                temp.setId_studente(rs.getInt("id_studente"));
                temp.setId_materia(rs.getInt("id_materia"));
                temp.setId_classe(rs.getInt("id_classe"));
                temp.setDataValutazione(rs.getDate("data_valutazione"));
                temp.setVoto(rs.getDouble("voto"));
                temp.setTipoValutazione(rs.getString("tipo_valutazione"));
                listValutazioni.add(temp);
            }
        } catch (Exception e) {
            printException(e);
            throw new Exception(e);
        } finally {
            if (this.conn != null) {
                this.conn.close();
            }
        }

        return listValutazioni;
    }


    
}
