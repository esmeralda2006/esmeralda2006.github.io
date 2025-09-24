package Backend;

import java.util.Date;

public class HistorialMedico {

	private Date fecha;
	private int idHistorial;
	private String diagnostico;
	private String tratamiento;// lista?
	private String recetas;// lista?
	private Medico medicoResponsable;

	/**
	 * @param fecha
	 * @param idHistorial
	 * @param diagnostico
	 * @param tratamiento
	 * @param recetas
	 * @param medicoResponsable
	 */
	public HistorialMedico(Date fecha, int idHistorial, String diagnostico, String tratamiento, String recetas,
			Medico medicoResponsable) {
		super();
		this.fecha = fecha;
		this.idHistorial = idHistorial;
		this.diagnostico = diagnostico;
		this.tratamiento = tratamiento;
		this.recetas = recetas;
		this.medicoResponsable = medicoResponsable;
	}
	/**
	 * @param idUsuario
	 * @param empleado
	 * @param nombre
	 * @param apellido
	 * @param correo
	 * @param dni
	 * @param contrasenia
	 * @param empleado
	 * @param tipoEmpleado
	 * @param turno
	 */

	public HistorialMedico() {
		// Constructor por defecto
	}

	// Getters y setters
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getIdHistorial() {
		return idHistorial;
	}

	public void setIdHistorial(int id) {
		this.idHistorial = id;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String d) {
		this.diagnostico = d;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String t) {
		this.tratamiento = t;
	}

	public String getRecetas() {
		return recetas;
	}

	public void setRecetas(String recetas) {
		this.recetas = recetas;
	}

	public Medico getMedicoResponsable() {
		return medicoResponsable;
	}

	public void setMedicoResponsable(Medico medicoResponsable) {
		this.medicoResponsable = medicoResponsable;
	}
}
