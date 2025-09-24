package Backend;

public class Empleado extends Persona {

	protected int idEmpleado;
	protected String correo;
	protected String contraseña;
	protected TipoEmpleado tipoEmpleado;
	protected Turno turno;
	protected int idSala;

	/**
	 * @param idUsuario
	 * @param empleado
	 * @param nombre
	 * @param apellido
	 * @param dni
	 * @param correo
	 * @param contrasenia
	 */

	public String getCorreo() {
		return correo;
	}

	public Empleado(String nombre, String apellidos, String dni, String correo, String contraseña,
            TipoEmpleado tipoEmpleado, Turno turno) {
super(nombre, apellidos, dni);
this.correo = correo;
this.contraseña = contraseña;
this.tipoEmpleado = tipoEmpleado;
this.turno = turno;
}
	
	 public Empleado(int idEmpleado, String nombre, String apellidos, String dni, String correo,
             String contraseña, TipoEmpleado tipoEmpleado, Turno turno) {
 this.idEmpleado = idEmpleado;
 this.nombre = nombre;
 this.apellidos = apellidos;
 this.dni = dni;
 this.correo = correo;
 this.contraseña = contraseña;
 this.tipoEmpleado = tipoEmpleado;
 this.turno = turno;
}

	public Empleado(int idEmpleado,String nombre, String apellidos, String dni, TipoEmpleado tipoEmpleado,
			Turno turno) {
		super(nombre, apellidos, dni);
		this.idEmpleado = idEmpleado;
		this.idEmpleado = idEmpleado;
		this.tipoEmpleado = tipoEmpleado;
		this.turno = turno;
	}
	
	

	public Empleado(int idEmpleado,String nombre, String apellidos, String dni) {
		super(nombre, apellidos, dni);
		this.idEmpleado = idEmpleado;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}



	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public Empleado() {
		super();
	}


	// Métodos comunes a todos los empleados (si los hubiera)

	public TipoEmpleado getTipoEmpleado() {
		return tipoEmpleado;
	}

	public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
		this.tipoEmpleado = tipoEmpleado;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	
	
	/*public Empleado iniciarSesion(String dni, String contraseña, TipoEmpleado tipo) {
		ConectorBD conn = new ConectorBD();
		Empleado usuario = null;
		boolean flag = true;
		for (Empleado i : conn.obtenerEmpleadosLogin()) {
			if (flag) {
				if (usuario == null && i.getDni().equals(dni) && i.getContraseña().equals(contraseña)
						&& i.getTipoEmpleado().equals(tipo)) {
					usuario = i;
					return usuario;
				}
			}
		}

		return usuario;// si devuelve usuario==null marcar que el usuario no fue encontrado
	}*/

	public void cerrarSesion() {
		System.out.println("Empleado " + nombre + " cerró sesión.");
	}

	public void iniciarSesion() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String toString() {
		return  idEmpleado + "."+getNombre()+" "+getApellidos()+","+getTipoEmpleado();
	}

}
