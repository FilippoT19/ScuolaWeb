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
public class Classi implements Serializable {
	

	private int id_classe;
	public int getId_classe() {
		return id_classe;
	}
	public void setId_classe(int id_classe) {
		this.id_classe = id_classe;
	}
	public String getNome_classe() {
		return nome_classe;
	}
	public void setNome_classe(String nome_classe) {
		this.nome_classe = nome_classe;
	}
	private String nome_classe;
	
}
