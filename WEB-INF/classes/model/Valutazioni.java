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

public class Valutazioni implements Serializable{

    public int getId_valutazione() {
		return id_valutazione;
	}

	public void setId_valutazione(int id_valutazione) {
		this.id_valutazione = id_valutazione;
	}

	public Integer getId_studente() {
		return id_studente;
	}

	public void setId_studente(Integer id_studente) {
		this.id_studente = id_studente;
	}

	public Integer getId_materia() {
		return id_materia;
	}

	public void setId_materia(Integer id_materia) {
		this.id_materia = id_materia;
	}

	public Integer getId_classe() {
		return id_classe;
	}

	public void setId_classe(Integer id_classe) {
		this.id_classe = id_classe;
	}

	public Date getDataValutazione() {
		return dataValutazione;
	}

	public void setDataValutazione(Date dataValutazione) {
		this.dataValutazione = dataValutazione;
	}

	public Double getVoto() {
		return voto;
	}

	public void setVoto(Double voto) {
		this.voto = voto;
	}

	private int id_valutazione;
    private Integer id_studente;
    private Integer id_materia;
    private Integer id_classe;
    private Date dataValutazione;
    private Double voto;
    private String tipoValutazione;

    // Costanti per i tipi di valutazione validi
    public static final String SCRITTA = "scritto";
    public static final String ORALE = "orale";

    // Getters and Setters

  

    public String getTipoValutazione() {
        return tipoValutazione;
    }

    public void setTipoValutazione(String tipoValutazione) {
        if (SCRITTA.equalsIgnoreCase(tipoValutazione) || ORALE.equalsIgnoreCase(tipoValutazione)) {
            this.tipoValutazione = tipoValutazione;
        } else {
            throw new IllegalArgumentException("Invalid value for TipoValutazione: " + tipoValutazione);
        }
    }
}
