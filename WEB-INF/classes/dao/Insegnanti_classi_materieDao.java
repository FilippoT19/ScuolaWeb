package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import model.Insegnanti_classi_materie;

public class Insegnanti_classi_materieDao extends DAO {

    private static final String SQL_INSERT = "INSERT INTO insegnanti_classi_materie (id_insegnante, id_materia, id_classe) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE insegnanti_classi_materie SET id_materia=?, id_classe=? WHERE id_insegnante=?";
    private static final String SQL_DELETE = "DELETE FROM insegnanti_classi_materie WHERE id_insegnante=?";
    private static final String SQL_GET_ALL = "SELECT * FROM insegnanti_classi_materie";
    private static final String SQL_GET_BY_ID = "SELECT * FROM insegnanti_classi_materie WHERE id_insegnante=?";
    private static final String SQL_EXIST = "SELECT COUNT(*) AS count FROM insegnanti_classi_materie WHERE id_insegnante=? AND id_materia=? AND id_classe=?";
    private static final String SQL_DELETE_BY_INSEGNANTE_MATERIA_CLASSE = "DELETE FROM insegnanti_classi_materie WHERE id_insegnante=? AND id_materia=? AND id_classe=?";
    private static final String SQL_GET_MATERIE_BY_CLASSE = "SELECT id_materia FROM insegnanti_classi_materie WHERE id_classe=?";
    private static final String SQL_COUNT_CLASSI_BY_MATERIA = "SELECT COUNT(DISTINCT id_classe) AS count FROM insegnanti_classi_materie WHERE id_materia = ?";

    
    
    public Insegnanti_classi_materieDao(String xml,int id_utente_sessione) throws ClassNotFoundException, JDOMException, IOException, SQLException {
    	
        super(xml, id_utente_sessione);
    }

    public void insertInsegnantiClassiMaterie(Insegnanti_classi_materie insegnanteClasseMateria) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        try (PreparedStatement preparedStatement = this.getConnection().prepareStatement(SQL_INSERT)) {
            preparedStatement.setInt(1, insegnanteClasseMateria.getId_insegnante());
            preparedStatement.setInt(2, insegnanteClasseMateria.getId_materia());
            preparedStatement.setInt(3, insegnanteClasseMateria.getId_classe());
           
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
            
        }
    }
    
    public void updateInsegnantiClassiMaterie(Insegnanti_classi_materie insegnanteClasseMateria) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        try (PreparedStatement preparedStatement = this.getConnection().prepareStatement(SQL_UPDATE)) {
            preparedStatement.setInt(1, insegnanteClasseMateria.getId_materia());
            preparedStatement.setInt(2, insegnanteClasseMateria.getId_classe());
            preparedStatement.setInt(3, insegnanteClasseMateria.getId_insegnante());
            
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        }
    }

    public void deleteInsegnantiClassiMaterie(int idInsegnante) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        try (PreparedStatement preparedStatement = this.getConnection().prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, idInsegnante);
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        }
    }

    public void deleteClasseMateriaFromInsegnante(int idInsegnante, int idMateria, int idClasse) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        try (PreparedStatement preparedStatement = this.getConnection().prepareStatement(SQL_DELETE_BY_INSEGNANTE_MATERIA_CLASSE)) {
            preparedStatement.setInt(1, idInsegnante);
            preparedStatement.setInt(2, idMateria);
            preparedStatement.setInt(3, idClasse);
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        }
    }
    public void deleteClasseMateriaFromInsegnante(Insegnanti_classi_materie icm) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        try (PreparedStatement preparedStatement = this.getConnection().prepareStatement(SQL_DELETE_BY_INSEGNANTE_MATERIA_CLASSE)) {
            preparedStatement.setInt(1, icm.getId_insegnante());
            preparedStatement.setInt(2, icm.getId_materia());
            preparedStatement.setInt(3, icm.getId_classe());
            preparedStatement.executeUpdate();
            this.salvaLogs(preparedStatement);
        }
    }

    public List<Insegnanti_classi_materie> getAllInsegnantiClassiMaterie() throws SQLException, ClassNotFoundException, JDOMException, IOException {
        List<Insegnanti_classi_materie> listInsegnantiClasseMateria = new ArrayList<>();
        try (Statement st = this.getConnection().createStatement(); ResultSet rs = st.executeQuery(SQL_GET_ALL)) {
            while (rs.next()) {
                Insegnanti_classi_materie insegnanteClasseMateria = new Insegnanti_classi_materie();
                insegnanteClasseMateria.setId_insegnante(rs.getInt("id_insegnante"));
                insegnanteClasseMateria.setId_materia(rs.getInt("id_materia"));
                insegnanteClasseMateria.setId_classe(rs.getInt("id_classe"));
                listInsegnantiClasseMateria.add(insegnanteClasseMateria);
            }
        }
        return listInsegnantiClasseMateria;
    }

    public List<Insegnanti_classi_materie> getInsegnantiClassiMaterieById(int idInsegnante) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        List<Insegnanti_classi_materie> listInsegnantiClasseMateria = new ArrayList<>();
        try (PreparedStatement st = this.getConnection().prepareStatement(SQL_GET_BY_ID)) {
            st.setInt(1, idInsegnante);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Insegnanti_classi_materie insegnanteClasseMateria = new Insegnanti_classi_materie();
                    insegnanteClasseMateria.setId_insegnante(rs.getInt("id_insegnante"));
                    insegnanteClasseMateria.setId_materia(rs.getInt("id_materia"));
                    insegnanteClasseMateria.setId_classe(rs.getInt("id_classe"));
                    listInsegnantiClasseMateria.add(insegnanteClasseMateria);
                }
            }
        }
        return listInsegnantiClasseMateria;
    }
    
    public boolean isInsegnanteClasseMateriaExist(Insegnanti_classi_materie insegnanteClasseMateria) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        boolean isInsegnanteClasseMateriaExist = false;
        try (PreparedStatement preparedStatement = this.getConnection().prepareStatement(SQL_EXIST)) {
            preparedStatement.setInt(1, insegnanteClasseMateria.getId_insegnante());
            preparedStatement.setInt(2, insegnanteClasseMateria.getId_materia());
            preparedStatement.setInt(3, insegnanteClasseMateria.getId_classe());
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    isInsegnanteClasseMateriaExist = count > 0;
                }
            }
        }
        return isInsegnanteClasseMateriaExist;
    }
    public List<Integer> getMaterieByClasseId(int idClasse) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        List<Integer> materie = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.getConnection().prepareStatement(SQL_GET_MATERIE_BY_CLASSE)) {
            preparedStatement.setInt(1, idClasse);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    materie.add(rs.getInt("id_materia"));
                }
            }
        }
        return materie;
    }
    public int getNumeroDiClassiPerMateria(int idMateria) throws SQLException, ClassNotFoundException, JDOMException, IOException {
        int numeroClassi = 0;
        try (PreparedStatement preparedStatement = this.getConnection().prepareStatement(SQL_COUNT_CLASSI_BY_MATERIA)) {
            preparedStatement.setInt(1, idMateria);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    numeroClassi = rs.getInt("count");
                }
            }
        }
        return numeroClassi;
    }
    
}
