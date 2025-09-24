package Backend;

public abstract class Persona {

	 protected int idUsuario;
	    protected String nombre;
	    protected String apellidos;
	    protected String dni;

	/**
	 * @param idUsiario
	 * @param nombre
	 * @param apellido	 
	 * @param dni
	 * @param empleado
	 */
	    public Persona(String nombre, String apellidos, String dni) {
	        this.idUsuario=idUsuario;
	        this.nombre = nombre;
	        this.apellidos = apellidos;
	        this.dni = dni;

	    }

	
	    
	public Persona() {
		super();
	}



	public int getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}
}
