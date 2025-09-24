package Backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Persona {

	private int idPaciente;
	private String contacto;
	private String obraSocial;
	private Sala habitacionAsignada;
	private boolean altaSolicitada = false;
	private LocalDate fechaAlta;
	
	private List<HistorialMedico> historial;
	private List<Turno> citas = new ArrayList<>();
	
	public Paciente(String nombre, String apellidos, String dni, String contacto,
            String obraSocial, Sala salaSeleccionada, LocalDate fechaAlta, boolean altaSolicitada) {
this.nombre = nombre;
this.apellidos = apellidos;
this.dni = dni;
this.contacto = contacto;
this.obraSocial = obraSocial;
this.habitacionAsignada = salaSeleccionada != null ? new Sala(salaSeleccionada) : null;
this.fechaAlta = fechaAlta;
this.altaSolicitada = altaSolicitada;
}
	
	
	
	

	public Paciente() {
		super();
	}





	public Paciente(int id, String nombre, String apellidos, String dni, String contacto,
            String obraSocial, Sala salaSeleccionada, LocalDate fechaAlta, boolean altaSolicitada) {
super(nombre, apellidos, dni);
this.idPaciente = id;
this.contacto = contacto;
this.obraSocial = obraSocial;
this.habitacionAsignada = salaSeleccionada;
this.fechaAlta = fechaAlta;
this.altaSolicitada = altaSolicitada;
}

	

	public Paciente( int idPaciente,String nombre, String apellidos, String dni) {
		super(nombre, apellidos, dni);
		this.idPaciente = idPaciente;
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
	
	

	public List<HistorialMedico> getHistorial() {
		return historial;
	}

	public int getIdPaciente() {
		return idPaciente;
	}




	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}




	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}	

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getObraSocial() {
		return obraSocial;
	}

	public void setObraSocial(String obraSocial) {
		this.obraSocial = obraSocial;
	}

	public Sala getHabitacionAsignada() {
		return habitacionAsignada;
	}

	public void setHabitacionAsignada(Sala habitacionAsignada) {
		this.habitacionAsignada = habitacionAsignada;
	}
	

	public boolean isAltaSolicitada() {
		return altaSolicitada;
	}

	public void setAltaSolicitada(boolean a) {
		this.altaSolicitada = a;
	}

	public List<Turno> getCitas() {
		return citas;
	}

	public void agregarCita(Turno turno) {
		this.citas.add(turno);
	}

	public void solicitarAlta() {
		altaSolicitada = true;
		System.out.println("Alta interna solicitada para " + nombre + " " + apellidos);
	}

	public void consultarCitas() {
		if (citas.isEmpty()) {
			System.out.println("No tienes citas programadas.");
		} else {
			System.out.println("Citas de " + nombre + " " + apellidos + ":");
			for (Turno turno : citas) {
				System.out.println(" - " + turno);
			}
		}
	}

	public void setHabitacionAsignada(String string) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String toString() {
		return idPaciente +"-"+getNombre()+" "+getApellidos()+",";
	}
	

}
