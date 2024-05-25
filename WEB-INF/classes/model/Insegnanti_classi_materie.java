/**
 * 
 */
package model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import utils.*;

/**
 * @author Topinelli Filippo
 *
 */
public class Insegnanti_classi_materie implements Serializable {
	
	private int id_insegnante, id_materia, id_classe;

	public int getId_insegnante() {
		return id_insegnante;
	}

	public void setId_insegnante(int id_insegnante) {
		this.id_insegnante = id_insegnante;
	}

	public int getId_materia() {
		return id_materia;
	}

	public void setId_materia(int id_materia) {
		this.id_materia = id_materia;
	}

	public int getId_classe() {
		return id_classe;
	}

	public void setId_classe(int id_classe) {
		this.id_classe = id_classe;
	}
	
	
	
	
	
	
    
}
