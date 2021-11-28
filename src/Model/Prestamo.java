package Model;

public class Prestamo {
	
	private String idPrest;
	private String idLibro;
	private String dniUser;
	private String idBib;
	private String fechaPrest;
	private String fechaEntregaPrest;
	
	public Prestamo() {
		super();
	}


	public Prestamo(String idPrest, String idLibro, String dniUser, String idBib,
			String fechaEntregaPrest) {
		super();
		this.idPrest = idPrest;
		this.idLibro = idLibro;
		this.dniUser = dniUser;
		this.idBib = idBib;
		this.fechaEntregaPrest = fechaEntregaPrest;
	}


	public String getIdPrest() {
		return idPrest;
	}


	public void setIdPrest(String idPrest) {
		this.idPrest = idPrest;
	}


	public String getIdLibro() {
		return idLibro;
	}


	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}


	public String getDniUser() {
		return dniUser;
	}


	public void setDniUser(String dniUser) {
		this.dniUser = dniUser;
	}


	public String getIdBib() {
		return idBib;
	}


	public void setIdBib(String idBib) {
		this.idBib = idBib;
	}


	public String getFechaPrest() {
		return fechaPrest;
	}


	public void setFechaPrest(String fechaPrest) {
		this.fechaPrest = fechaPrest;
	}


	public String getFechaEntregaPrest() {
		return fechaEntregaPrest;
	}


	public void setFechaEntregaPrest(String fechaEntregaPrest) {
		this.fechaEntregaPrest = fechaEntregaPrest;
	}
	
}
