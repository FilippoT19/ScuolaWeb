package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/**
 * Classe di configurazione per l�applicazione
 * 
 * @author ste
 *
 */
public class Config {
	
	private  String driver;
    private String url;
    private String user;
    private String psw;
    private String dbType;
    private final String xmlurl;

	/**
	 * Costruttore della classe
	 * @param inputStream path del file di configurazione
	 * @throws MalformedURLException 
	 */
	public Config(String xmlurl) throws MalformedURLException {
		super();
		this.xmlurl = xmlurl;
	}


	/**
	 * Leggo il file XML di configurazione contenuto nella cartella Config
	 * 
	 * @return l�elemento di root del file XML di configurazione
	 * @throws JDOMException
	 * @throws IOException
	 */
	private  Element readConfig() throws JDOMException, IOException {
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = null;

		document = saxBuilder.build(new File(this.xmlurl));

		Element root = document.getRootElement();



		return root;
	}
	


	/**
	 * Carica il file di configurazione per recupoerare la connection string
	 * 
	 * @throws IOException
	 * @throws JDOMException
	 */
	public  void loadConfig() throws JDOMException, IOException {
		
		Element root = readConfig();
		dbType = root.getChildText("dbType");
		Element mysql = root.getChild("connection").getChild(dbType);
		driver = mysql.getChildText("driver").trim();
		url = mysql.getChildText("url").trim();
		user = mysql.getChildText("user").trim();
		psw = mysql.getChildText("password").trim();


		System.out.println("dbType: " + dbType);
		System.out.println("driver: " + driver);
		System.out.println("url: " + url);
		System.out.println("user: " + user);
		System.out.println("psw: " + psw);
	}

	/**
	 * Restituisce le query da eseguire
	 * 
	 * @param query nome del tag contenente la query nel file XNL di configurazione
	 * @return la query da eseguire
	 * @throws JDOMException
	 * @throws IOException
	 */
	 public String getQuery(String query) throws JDOMException, IOException {
		Element root = readConfig();
		Element mysql = root.getChild("query").getChild(dbType);
		return mysql.getChildText(query).trim();
	}

	/**
	 * Restituisce il driver per il database
	 * 
	 * @return il driver appropriato x la connessione al db
	 */
	public  String getDriver() {
		return driver;
	}

	/**
	 * Restituisce il pattern URL per il database
	 * 
	 * @return url per il database
	 */
	public  String getDbUrl() {
		return url;
	}

	/**
	 * Restituisce il nome utente per il Database
	 * 
	 * @return the user
	 */
	public  String getUser() {
		return user;
	}

	/**
	 * Restituisce la password per il Database
	 * 
	 * @return the password
	 */
	public  String getPassword() {
		return psw;
	}

}
