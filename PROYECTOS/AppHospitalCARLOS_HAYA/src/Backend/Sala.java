package Backend;

public class Sala {

	private int idSala;
	private TipoSala tipoSala;
	private Estado estado;
	private int capacidad;

	/**
	 * @param idSala
	 * @param tipoSala
	 * @param estado
	 * @param capacidad
	 */
	public Sala(int idSala, TipoSala tipoSala, Estado estado, int capacidad) {
		super();
		this.idSala = idSala;
		this.tipoSala = tipoSala;
		this.estado = estado;
		this.capacidad = capacidad;
	}

	public Sala(TipoSala tipoSala, Estado estado, int capacidad) {
		super();
		this.tipoSala = tipoSala;
		this.estado = estado;
		this.capacidad = capacidad;
	}

	public Sala(int idSala) {
		super();
		this.idSala = idSala;
	}

	public Sala(Sala otra) {
		this.idSala = otra.idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public TipoSala getTipoSala() {
		return tipoSala;
	}

	public void setTipoSala(TipoSala tipoSala) {
		this.tipoSala = tipoSala;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getIdSala() {
		return idSala;
	}
	
	@Override
	public String toString() {
		return "Sala :" + idSala + "-"+getTipoSala();
	}

}

