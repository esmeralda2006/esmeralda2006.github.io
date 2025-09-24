package Backend;

import java.sql.Date;

public class Receta {
	private int idReceta;
	private int idPaciente;
	private int idMedico;
	private String medicamento;
	private String dosis;
	private Date fecha;

	/**
	 * Constructor principal, usado por ConectorBD.registrarReceta()
	 */
	public Receta(int idPaciente, int idMedico, String medicamento, String dosis, Date fecha) {
		this.idPaciente = idPaciente;
		this.idMedico = idMedico;
		this.medicamento = medicamento;
		this.dosis = dosis;
		this.fecha = fecha;
	}

	public Receta(int idPaciente, int idMedico, String medicamento, Date fecha) {
		super();
		this.idPaciente = idPaciente;
		this.idMedico = idMedico;
		this.medicamento = medicamento;
		this.fecha = fecha;
	}

	public Receta(String medicamento, Date fecha) {
		super();
		this.medicamento = medicamento;
		this.fecha = fecha;
	}

	// Getters y setters exactos para ConectorBD:
	public int getIdReceta() {
		return idReceta;
	}

	public void setIdReceta(int idReceta) {
		this.idReceta = idReceta;
	}

	public int getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	public int getIdMedico() {
		return idMedico;
	}

	public void setIdMedico(int idMedico) {
		this.idMedico = idMedico;
	}

	public String getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}

	public String getDosis() {
		return dosis;
	}

	public void setDosis(String dosis) {
		this.dosis = dosis;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Receta [idReceta=" + idReceta + ", idPaciente=" + idPaciente + ", idMedico=" + idMedico
				+ ", medicamento=" + medicamento + ", dosis=" + dosis + ", fecha=" + fecha + "]";
	}

}
