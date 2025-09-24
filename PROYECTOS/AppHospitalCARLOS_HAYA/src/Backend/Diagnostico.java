
package Backend;

import java.util.Date;

public class Diagnostico {
	private int idDiagnostico;
	private int idPaciente;
	private int idMedico;
	private String descripcion;
	private Date fecha;

	public Diagnostico(int idPaciente, int idMedico, String descripcion, Date fecha) {
		this.idPaciente = idPaciente;
		this.idMedico = idMedico;
		this.descripcion = descripcion;
		this.fecha = fecha;
	}

	public Diagnostico(String descripcion, Date fecha) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
	}

	// Getters y setters exactos para ConectorBD:
	public int getIdDiagnostico() {
		return idDiagnostico;
	}

	public void setIdDiagnostico(int idDiagnostico) {
		this.idDiagnostico = idDiagnostico;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Diagnostico [idDiagnostico=" + idDiagnostico + ", idPaciente=" + idPaciente + ", idMedico=" + idMedico
				+ ", descripcion=" + descripcion + ", fecha=" + fecha + "]";
	}
}
