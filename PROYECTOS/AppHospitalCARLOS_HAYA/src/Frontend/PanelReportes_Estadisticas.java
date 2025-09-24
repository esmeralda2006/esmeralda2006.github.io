package Frontend;

import javax.swing.*;

import Backend.ConectorBD;
import Backend.Diagnostico;
import Backend.HistorialMedico;
import Backend.Medico;
import Backend.Receta;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class PanelReportes_Estadisticas {
	
	private static ConectorBD conexion=new ConectorBD();
	// Método que devuelve un panel
	public static JPanel crearPanel() {
		JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		panelPrincipal.setBackground(new Color(161,223,182));
		

		JLabel titulo = new JLabel("Reportes y Estadísticas", JLabel.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 22));
		panelPrincipal.add(titulo, BorderLayout.NORTH);

		// Panel de botones
		JPanel botones = new JPanel(new GridLayout(1, 4, 10, 10));
		JButton btnReportePacientesInternados = new JButton("Pacientes Internados");
		JButton btnDisponibilidadHabitaciones = new JButton("Disponibilidad Habitaciones");
		JButton btnActividadMedicoEnfermero = new JButton("Actividad Médico/Enfermero");
		JButton btnHistorialClinico = new JButton("Historial Clínico");
		btnReportePacientesInternados.setBackground(new Color(102,205,170));
		btnDisponibilidadHabitaciones.setBackground(new Color(253,253,150));
		btnActividadMedicoEnfermero.setBackground(new Color(173,216,230));
		btnHistorialClinico.setBackground(new Color(255, 182, 193));

		// Agregar botones al panel de botones
		botones.add(btnReportePacientesInternados);
		botones.add(btnDisponibilidadHabitaciones);
		botones.add(btnActividadMedicoEnfermero);
		botones.add(btnHistorialClinico);
		panelPrincipal.add(botones, BorderLayout.NORTH);

		// Área para mostrar los reportes
		JTextArea areaReporte = new JTextArea(20, 50);
		areaReporte.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(areaReporte);
		panelPrincipal.add(scrollPane, BorderLayout.CENTER);

		// Acciones para los botones
		btnReportePacientesInternados.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generarReportePacientesInternados(areaReporte);
			}
		});

		btnDisponibilidadHabitaciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generarReporteDisponibilidadHabitaciones(areaReporte);
			}
		});

		btnActividadMedicoEnfermero.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generarReporteActividadMedicoEnfermero(areaReporte);
			}
		});

		btnHistorialClinico.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generarReporteHistorialClinico(areaReporte);
			}
		});

		return panelPrincipal;
	}

	private static void generarReportePacientesInternados(JTextArea areaReporte) {
	    StringBuilder reporte = new StringBuilder();
	    reporte.append("Reporte de Pacientes Internados\n");
	    reporte.append("--------------------------------\n");

	    List<String> pacientes = conexion.obtenerPacientesInternados();
	    if (pacientes.isEmpty()) {
	        reporte.append("No hay pacientes internados actualmente.\n");
	    } else {
	        for (int i = 0; i < pacientes.size(); i++) {
	            reporte.append("Paciente " + (i + 1) + ": " + pacientes.get(i) + "\n");
	        }
	    }

	    areaReporte.setText(reporte.toString());
	}

	private static void generarReporteDisponibilidadHabitaciones(JTextArea areaReporte) {
	    StringBuilder reporte = new StringBuilder();
	    reporte.append("Reporte de Disponibilidad de Habitaciones y Salas\n");
	    reporte.append("--------------------------------------------------\n");

	    List<String> camas = conexion.obtenerCamasDisponibles();
	    reporte.append("Camas Disponibles:\n");
	    if (camas.isEmpty()) {
	        reporte.append("- No hay camas disponibles.\n");
	    } else {
	        for (String cama : camas) {
	            reporte.append("- " + cama + "\n");
	        }
	    }

	    reporte.append("\nSalas Disponibles:\n");
	    List<String> salas = conexion.obtenerSalasDisponibles();
	    if (salas.isEmpty()) {
	        reporte.append("- No hay salas disponibles.\n");
	    } else {
	        for (String sala : salas) {
	            reporte.append("- " + sala + "\n");
	        }
	    }

	    areaReporte.setText(reporte.toString());
	}

	private static void generarReporteActividadMedicoEnfermero(JTextArea areaReporte) {
	    StringBuilder reporte = new StringBuilder();
	    reporte.append("Reporte de Actividad de Médicos (Asignación de Pacientes)\n");
	    reporte.append("----------------------------------------------------------\n");

	    List<String> actividad = conexion.obtenerActividadMedicos();

	    if (actividad.isEmpty()) {
	        reporte.append("No se encontró actividad registrada para médicos.\n");
	    } else {
	        for (String linea : actividad) {
	            reporte.append(linea).append("\n");
	        }
	    }

	    areaReporte.setText(reporte.toString());
	}

	private static void generarReporteHistorialClinico(JTextArea areaReporte) {
	    String input = JOptionPane.showInputDialog(null, "Ingrese el ID del paciente:", "Historial Clínico", JOptionPane.QUESTION_MESSAGE);
	    
	    if (input == null || input.trim().isEmpty()) {
	        areaReporte.setText("Operación cancelada o sin ID ingresado.");
	        return;
	    }

	    int idPaciente;
	    try {
	        idPaciente = Integer.parseInt(input);
	    } catch (NumberFormatException e) {
	        areaReporte.setText("ID inválido. Debe ser un número entero.");
	        return;
	    }

	    StringBuilder reporte = new StringBuilder();
	    reporte.append("Reporte de Historial Clínico - Paciente ID: ").append(idPaciente).append("\n");
	    reporte.append("--------------------------------------------------\n\n");

	    // Historial clínico (de la tabla 'historial')
	    List<HistorialMedico> historial = conexion.verHistorialClinico(idPaciente);
	    if (historial.isEmpty()) {
	        reporte.append("No se encontraron entradas en el historial clínico.\n\n");
	    } else {
	        reporte.append("Historial Clínico:\n");
	        for (HistorialMedico h : historial) {
	            reporte.append("- Fecha: ").append(h.getFecha()).append("\n");
	            reporte.append("  Diagnóstico: ").append(h.getDiagnostico()).append("\n");
	            reporte.append("  Tratamiento: ").append(h.getTratamiento()).append("\n");
	            reporte.append("  Recetas: ").append(h.getRecetas()).append("\n");

	            Medico medico = h.getMedicoResponsable();
	            if (medico != null) {
	                reporte.append("  Médico Responsable: ").append(medico.getNombre()).append(" ").append(medico.getApellidos())
	                       .append(" (ID: ").append(medico.getIdEmpleado()).append(")\n");
	            } else {
	                reporte.append("  Médico Responsable: No registrado\n");
	            }
	            reporte.append("\n");
	        }
	    }

	    // Diagnósticos (opcional si están separados)
	    List<Diagnostico> diagnosticos = conexion.verHistorialDiagnosticos(idPaciente);
	    if (!diagnosticos.isEmpty()) {
	        reporte.append("Diagnósticos separados:\n");
	        for (Diagnostico d : diagnosticos) {
	            reporte.append("- Fecha: ").append(d.getFecha()).append("\n");
	            reporte.append("  Médico ID: ").append(d.getIdMedico()).append("\n");
	            reporte.append("  Descripción: ").append(d.getDescripcion()).append("\n\n");
	        }
	    }

	    // Recetas (opcional si están separadas)
	    List<Receta> recetas = conexion.verHistorialRecetas(idPaciente);
	    if (!recetas.isEmpty()) {
	        reporte.append("Recetas separadas:\n");
	        for (Receta r : recetas) {
	            reporte.append("- Fecha: ").append(r.getFecha()).append("\n");
	            reporte.append("  Médico ID: ").append(r.getIdMedico()).append("\n");
	            reporte.append("  Medicamento: ").append(r.getMedicamento()).append("\n");
	            reporte.append("  Dosis: ").append(r.getDosis()).append("\n\n");
	        }
	    }

	    areaReporte.setText(reporte.toString());
	}

}
