/**
 * 
 */
package model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import utils.*;

/**
 * @author Topinelli Filippo
 *
 */
public class LogsUsers implements Serializable {
	

	private int id;
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	public Timestamp getCreationLog() {
		return creationLog;
	}
	public void setCreationLog(Timestamp creationLog) {
		this.creationLog = creationLog;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	private int idUtente;
    private Timestamp creationLog;
    private String action;
	

	
	
    
}
