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
public class Insegnanti implements Serializable {
	

	private int id_insegnante, id_utente;

	public int getId_insegnante() {
		return id_insegnante;
	}

	public void setId_insegnante(int id_insegnante) {
		this.id_insegnante = id_insegnante;
	}

	public int getId_utente() {
		return id_utente;
	}

	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}
	
    
}
