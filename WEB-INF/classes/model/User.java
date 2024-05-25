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
public class User implements Serializable {
	
	private String first_name, last_name, username, email, cellphone, password;
	private Integer id;
	private Date birth_date, date_access, date_insert;
	private String[] roles;
	
    DateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
    private String birth_date_string;
    private String date_access_string;
    private String date_insert_String;
    private int id_role;
    
	
	public int getIdRole() {
		return id_role;
	}

	public void setIdRole(int idRole) {
		this.id_role = idRole;
	}
	public String getRoleString(){
		switch (id_role) {
		case 1:
			return "Dirigenza";
		case 2:
			return "Insegnante";
		case 3:
			return "Segreteria";
		case 4:
			return "Studente";
		default:
			return "Studente";
		}
	}


	public User() {

	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}

	public Date getDate_access() {
		return date_access;
	}

	public void setDate_access(Date date_access) {
		this.date_access = date_access;
	}

	public Date getDate_insert() {
		return date_insert;
	}

	public void setDate_insert(Date date_insert) {
		this.date_insert = date_insert;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	
	public String getBirth_dateString() {
		if(this.getBirth_date() != null) {
			birth_date_string = sdf.format(birth_date);
		}
		
		return birth_date_string;
	}
	
	public void setBrith_dateString(String birth_date_string) {
		this.birth_date_string = birth_date_string;
	}
	
	public String getDate_accessString() {
		if(this.getDate_access() != null) {
			date_access_string = sdf.format(date_access);
		}
		
		return date_access_string;
	}
	
	public void setDate_accesseString(String date_access_string) {
		this.date_access_string = date_access_string;
	}
	public String getDate_insertString() {
		if(this.getDate_insert() != null) {
			date_insert_String = sdf.format(birth_date);
		}
		
		return date_insert_String;
	}
	
	public void setDate_insertString(String date_insert_String) {
		
		this.date_insert_String = date_insert_String;
	}
}
