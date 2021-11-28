package Model;

public class Libro {
	private String idLibro;
	private String titLibro;
	private String catLibro;
	private String a�oLibro;
	private String paisLibro;
	private String autorLibro;
	
	
	public Libro() {
		super();
	}


	public Libro(String idLibro, String titLibro, String autorLibro, String catLibro, String a�oLibro, String paisLibro) {
		super();
		this.idLibro = idLibro;
		this.titLibro = titLibro;
		this.catLibro = catLibro;
		this.autorLibro = autorLibro;
		this.a�oLibro = a�oLibro;
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


	public String getA�oLibro() {
		return a�oLibro;
	}


	public void setA�oLibro(String a�oLibro) {
		this.a�oLibro = a�oLibro;
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
