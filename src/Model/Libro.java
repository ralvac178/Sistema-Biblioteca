package Model;

public class Libro {
	private String idLibro;
	private String titLibro;
	private String catLibro;
	private String añoLibro;
	private String paisLibro;
	private String autorLibro;
	
	
	public Libro() {
		super();
	}


	public Libro(String idLibro, String titLibro, String autorLibro, String catLibro, String añoLibro, String paisLibro) {
		super();
		this.idLibro = idLibro;
		this.titLibro = titLibro;
		this.catLibro = catLibro;
		this.autorLibro = autorLibro;
		this.añoLibro = añoLibro;
		this.paisLibro = paisLibro;
	}


	public String getIdLibro() {
		return idLibro;
	}


	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}


	public String getTitLibro() {
		return titLibro;
	}


	public void setTitLibro(String titLibro) {
		this.titLibro = titLibro;
	}


	public String getCatLibro() {
		return catLibro;
	}


	public void setCatLibro(String carLibro) {
		this.catLibro = carLibro;
	}


	public String getAñoLibro() {
		return añoLibro;
	}


	public void setAñoLibro(String añoLibro) {
		this.añoLibro = añoLibro;
	}


	public String getPaisLibro() {
		return paisLibro;
	}


	public void setPaisLibro(String paisLibro) {
		this.paisLibro = paisLibro;
	}


	public String getAutorLibro() {
		return autorLibro;
	}


	public void setAutorLibro(String autorLibro) {
		this.autorLibro = autorLibro;
	}
	
	
}
