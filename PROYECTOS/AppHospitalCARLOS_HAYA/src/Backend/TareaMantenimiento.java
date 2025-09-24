package Backend;

public class TareaMantenimiento {

	 private int idTarea; // autoincremental en la BD
	    private String descripcion;
	    private String fecha;
	    private String responsableDni;
	    private String salaAfectada;
	    
	    // Constructor sin ID (para nuevas tareas que aún no están en la BD)
	    public TareaMantenimiento(String descripcion, String fecha, String responsableDni, String salaAfectada) {
	        this.descripcion = descripcion;
	        this.fecha = fecha;
	        this.responsableDni = responsableDni;
	        this.salaAfectada = salaAfectada;
	    }

	    // Constructor con ID (para leer desde la BD)
	    public TareaMantenimiento(int idTarea, String descripcion, String fecha, String responsableDni, String salaAfectada) {
	        this.idTarea = idTarea;
	        this.descripcion = descripcion;
	        this.fecha = fecha;
	        this.responsableDni = responsableDni;
	        this.salaAfectada = salaAfectada;
	    }

	    // Getters y Setters
	    public int getIdTarea() {
	        return idTarea;
	    }

	    public void setIdTarea(int idTarea) {
	        this.idTarea = idTarea;
	    }

	    public String getDescripcion() {
	        return descripcion;
	    }

	    public void setDescripcion(String descripcion) {
	        this.descripcion = descripcion;
	    }

	    public String getFecha() {
	        return fecha;
	    }

	    public void setFecha(String fecha) {
	        this.fecha = fecha;
	    }

	    public String getResponsableDni() {
	        return responsableDni;
	    }

	    public void setResponsableDni(String responsableDni) {
	        this.responsableDni = responsableDni;
	    }

	    public String getSalaAfectada() {
	        return salaAfectada;
	    }

	    public void setSalaAfectada(String salaAfectada) {
	        this.salaAfectada = salaAfectada;
	    }

	    @Override
	    public String toString() {
	        return String.format("ID: %d | Sala: %s | Fecha: %s | Responsable: %s | Tarea: %s",
	                idTarea, salaAfectada, fecha, responsableDni, descripcion);
	    }
}
