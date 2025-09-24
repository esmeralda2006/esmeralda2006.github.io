package Backend;

public class Enfermero extends Empleado {

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

	public Enfermero(String nombre, String apellido, String dni, String correo, String contrasenia,
			TipoEmpleado tipoEmpleado, Turno turno) {
		super(nombre, apellido, dni, correo, contrasenia, tipoEmpleado, turno);
		// TODO Auto-generated constructor stub
	}

//completo
	public Enfermero(int idEmpleado, String nombre, String apellidos, String dni, String correo, String contraseña,
			TipoEmpleado tipoEmpleado, Turno turno) {
		super(idEmpleado, nombre, apellidos, dni, correo, contraseña, tipoEmpleado, turno);
	}

	public Enfermero(String dni, String contrasenia, TipoEmpleado tipoEmpleado) {
//constructor usado para el innicio de sesion
	}

	@Override
	public void iniciarSesion() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cerrarSesion() {
		// TODO Auto-generated method stub

	}

	public void asignarCama(Paciente paciente) {
	}

	public void marcarAlta(Paciente paciente) {
	}
}
