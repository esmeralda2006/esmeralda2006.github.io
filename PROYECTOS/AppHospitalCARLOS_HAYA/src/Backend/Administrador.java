package Backend;

public class Administrador extends Empleado {

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

	public Administrador(String nombre, String apellido, String dni, String correo, String contrasenia,
			TipoEmpleado tipoEmpleado, Turno turno) {
		super(nombre, apellido, dni, contrasenia, contrasenia, tipoEmpleado, turno);
		// TODO Auto-generated constructor stub
	}

//completo
	public Administrador(int idEmpleado, String nombre, String apellidos, String dni, String correo, String contraseña,
			TipoEmpleado tipoEmpleado, Turno turno) {
		super(idEmpleado, nombre, apellidos, dni, correo, contraseña, tipoEmpleado, turno);
	}

	public Administrador(String dni, String contrasenia, TipoEmpleado tipoEmpleado) {

	}

	public Administrador() {
		super();
	}

	@Override
	public void iniciarSesion() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cerrarSesion() {
		// TODO Auto-generated method stub

	}

	public void registrarEmpleado() {
	}

	public void eliminarEmpleado() {
	}

	public void modificarEmpleado() {
	}

	public void registrarPaciente() {
	}

	public void eliminarPaciente() {
	}

	public void modificarPaciente() {
	}

	public void registrarSala() {
	}

	public void eliminarSala() {
	}

	public void modificarSala() {
	}

	public void generarReportes() {
	}
}
