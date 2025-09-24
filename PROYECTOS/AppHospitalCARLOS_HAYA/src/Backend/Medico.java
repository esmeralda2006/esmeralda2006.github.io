package Backend;

import java.util.List;

public class Medico extends Empleado {

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

	public Medico(String nombre, String apellido, String dni, String correo, String contrasenia,
			TipoEmpleado tipoEmpleado, Turno turno) {
		super(nombre, apellido, dni, contrasenia, contrasenia, tipoEmpleado, turno);
		// TODO Auto-generated constructor stub
	}
//completo
	public Medico(int idEmpleado, String nombre, String apellidos, String dni, String correo, String contraseña,
			TipoEmpleado tipoEmpleado, Turno turno) {
		super(idEmpleado, nombre, apellidos, dni, correo, contraseña, tipoEmpleado, turno);
	}

	public Medico(String dni, String contrasenia, TipoEmpleado tipoEmpleado) {
		// constructor usado para el innicio de sesion
	}
	
	
	
	@Override
	public void iniciarSesion() {
		System.out.println("Sesión iniciada: Dr. " + nombre + " " + apellidos);
	}

	@Override
	public void cerrarSesion() {
		System.out.println("Sesión cerrada: Dr. " + nombre + " " + apellidos);
	}

	/**
	 * Añade al historial del paciente una nueva entrada con diagnóstico y
	 * tratamiento.
	 */
	public void registrarDiagnostico(Paciente paciente, String diagnostico, String tratamiento) {
		HistorialMedico entrada = new HistorialMedico();
		entrada.setDiagnostico(diagnostico);
		entrada.setTratamiento(tratamiento);
		entrada.setMedicoResponsable(this);
		paciente.getHistorial().add(entrada);
	}

	/**
	 * Añade al historial del paciente una nueva entrada con receta.
	 */
	public void registrarReceta(Paciente paciente, String receta) {
		HistorialMedico entrada = new HistorialMedico();
		entrada.setRecetas(receta); // usa el setter unificado setRecetas(...)
		entrada.setMedicoResponsable(this);
		paciente.getHistorial().add(entrada);
	}

	/**
	 * Devuelve la lista completa de entradas del historial médico.
	 */
	public List<HistorialMedico> verHistorial(Paciente paciente) {
		return paciente.getHistorial();
	}

	/**
	 * Devuelve la lista de pacientes que se le pasan, para mostrar en la GUI.
	 */
	public List<Paciente> verPaciente(List<Paciente> pacientesAsignados) {
		return pacientesAsignados;
	}

	/**
	 * Marca en el paciente que se ha solicitado el alta médica.
	 */
	public void solicitarAltaPaciente(Paciente paciente) {
		paciente.solicitarAlta();
	}
}
