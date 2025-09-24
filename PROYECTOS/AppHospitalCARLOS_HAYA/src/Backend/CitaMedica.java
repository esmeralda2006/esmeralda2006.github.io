package Backend;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaMedica {
	 private int idCita;
	    private LocalDate fecha;
	    private LocalTime hora;
	    private Paciente paciente;
	    private Empleado medico;
	    private Sala sala;
	    
		public CitaMedica(LocalDate fecha, LocalTime hora, Paciente paciente, Empleado medico, Sala sala) {
			super();
			this.fecha = fecha;
			this.hora = hora;
			this.paciente = paciente;
			this.medico = medico;
			this.sala = sala;
		}

		public int getIdCita() {
			return idCita;
		}

		public void setIdCita(int idCita) {
			this.idCita = idCita;
		}

		public LocalDate getFecha() {
			return fecha;
		}

		public void setFecha(LocalDate fecha) {
			this.fecha = fecha;
		}

		public LocalTime getHora() {
			return hora;
		}

		public void setHora(LocalTime hora) {
			this.hora = hora;
		}

		public Paciente getPaciente() {
			return paciente;
		}

		public void setPaciente(Paciente paciente) {
			this.paciente = paciente;
		}

		public Empleado getMedico() {
			return medico;
		}

		public void setMedico(Empleado medico) {
			this.medico = medico;
		}

		public Sala getSala() {
			return sala;
		}

		public void setSala(Sala sala) {
			this.sala = sala;
		}

	    
	    
}
