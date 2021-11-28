package Model;

public class Bibliotecario {

	private String idBib;
	private String nomBib;
	private String apellBib;
	private int estadoBib;
	private String password;
	
	
	public Bibliotecario() {
		super();
	}


	public Bibliotecario(String idBib, String nomBib, String apellBib, String password) {
		super();
		this.idBib = idBib;
		this.nomBib = nomBib;
		this.apellBib = apellBib;
		this.password = password;
	}


	public String getIdBib() {
		return idBib;
	}


	public void setIdBib(String idBib) {
		this.idBib = idBib;
	}


	public String getNomBib() {
		return nomBib;
	}


	public void setNomBib(String nomBib) {
		this.nomBib = nomBib;
	}


	public String getApellBib() {
		return apellBib;
	}


	public void setApellBib(String apellBib) {
		this.apellBib = apellBib;
	}


	public int getEstadoBib() {
		return estadoBib;
	}


	public void setEstadoBib(int estadoBib) {
		this.estadoBib = estadoBib;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
}
