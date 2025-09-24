package Backend;

public class Cama {
	private int idCama;
	private String tipoCama;
	private EstadoCama estado;
	private int idSala;
	
	

	public Cama(int idCama, String tipoCama, EstadoCama estado, int idSala) {
		super();
		this.idCama = idCama;
		this.tipoCama = tipoCama;
		this.estado = estado;
		this.idSala = idSala;
	}
	
	

	public Cama(String tipoCama, EstadoCama estado, int idSala) {
		super();
		this.tipoCama = tipoCama;
		this.estado = estado;
		this.idSala = idSala;
	}



	public int getIdCama() {
		return idCama;
	}

	public void setIdCama(int idCama) {
		this.idCama = idCama;
	}

	public String getTipoCama() {
		return tipoCama;
	}

	public void setTipoCama(String tipoCama) {
		this.tipoCama = tipoCama;
	}

	public EstadoCama getEstado() {
		return estado;
	}

	public void setEstado(EstadoCama estado) {
		this.estado = estado;
	}



	public int getIdSala() {
		return idSala;
	}



	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

}
