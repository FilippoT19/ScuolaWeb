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
public class Materie implements Serializable {
	

	private int id_materia;
	public int getId_materia() {
		return id_materia;
	}
	public void setId_materia(int id_materia) {
		this.id_materia = id_materia;
	}
	public String getNome_materia() {
		return nome_materia;
	}
	public void setNome_materia(String nome_materia) {
		this.nome_materia = nome_materia;
	}
	private String nome_materia;
	

	
	
    
}
