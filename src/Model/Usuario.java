package Model;

public class Usuario {
	
	private String dniUser;
	private String nomUser;
	private String apellUser;
	private String telfUser;
	private int edadUser;
	
	
	public Usuario() {
		super();
	}

	public Usuario(String dniUser, String nomUser, String apellUser, String telfUser, int edadUser) {
		super();
		this.dniUser = dniUser;
		this.nomUser = nomUser;
		this.apellUser = apellUser;
		this.telfUser = telfUser;
		this.edadUser = edadUser;
	}

	public String getDniUser() {
		return dniUser;
	}

	public void setDniUser(String dniUser) {
		this.dniUser = dniUser;
	}

	public String getNomUser() {
		return nomUser;
	}

	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}

	public String getApellUser() {
		return apellUser;
	}

	public void setApellUser(String apellUser) {
		this.apellUser = apellUser;
	}

	public String getTelfUser() {
		return telfUser;
	}

	public void setTelfUser(String telfUser) {
		this.telfUser = telfUser;
	}

	public int getEdadUser() {
		return edadUser;
	}

	public void setEdadUser(int edadUser) {
		this.edadUser = edadUser;
	}

	
}
