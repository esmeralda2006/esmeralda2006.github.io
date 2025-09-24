package Frontend;

import javax.swing.*;

import Backend.ConectorBD;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanelAsignarPacienteMedico {

	  private static ConectorBD conexion = new ConectorBD(); 

	    public static JPanel crearPanel() {
	        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

	        // Obtener datos reales desde la BD
	        List<String> pacientes = conexion.obtenerIdsPacientes(); // Ejemplo: "1 - Juan Perez"
	        List<String> medicos = conexion.obtenerIdsMedicos(); // Ejemplo: "10 - Dr. Gomez"

	        JComboBox<String> comboPacientes = new JComboBox<>(pacientes.toArray(new String[0]));
	        JComboBox<String> comboMedicos = new JComboBox<>(medicos.toArray(new String[0]));
	        JButton btnAsignar = new JButton("Asignar Paciente");

	        comboPacientes.setBackground(Color.WHITE);
	        comboMedicos.setBackground(Color.WHITE);
	        btnAsignar.setBackground(new Color(102, 205, 170));

	        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
	        panelFormulario.setBorder(BorderFactory.createTitledBorder("Asignación de Paciente a Médico"));
	        panelFormulario.setBackground(new Color(161, 223, 182));

	        panelFormulario.add(new JLabel("Seleccionar Paciente:"));
	        panelFormulario.add(comboPacientes);
	        panelFormulario.add(new JLabel("Seleccionar Médico:"));
	        panelFormulario.add(comboMedicos);
	        panelFormulario.add(new JLabel());
	        panelFormulario.add(btnAsignar);

	        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);

	        // Área de historial
	        JTextArea areaAsignaciones = new JTextArea();
	        areaAsignaciones.setEditable(false);
	        areaAsignaciones.setBorder(BorderFactory.createTitledBorder("Historial de Asignaciones"));
	        JScrollPane scroll = new JScrollPane(areaAsignaciones);
	        panelPrincipal.add(scroll, BorderLayout.CENTER);

	        // Mostrar historial al cargar el panel
	        cargarHistorialAsignaciones(areaAsignaciones);

	        // Acción del botón
	        btnAsignar.addActionListener(e -> {
	            String pacienteSeleccionado = (String) comboPacientes.getSelectedItem();
	            String medicoSeleccionado = (String) comboMedicos.getSelectedItem();

	            if (pacienteSeleccionado == null || medicoSeleccionado == null) {
	                JOptionPane.showMessageDialog(panelPrincipal, "Seleccione paciente y médico", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            int idPaciente = Integer.parseInt(pacienteSeleccionado.split(" - ")[0]);
	            int idMedico = Integer.parseInt(medicoSeleccionado.split(" - ")[0]);

	            boolean guardado = conexion.guardarAsignacionPacienteMedico(idPaciente, idMedico);

	            if (guardado) {
	                // Recargar historial desde la base de datos
	                cargarHistorialAsignaciones(areaAsignaciones);
	            } else {
	                JOptionPane.showMessageDialog(panelPrincipal, "Error al guardar la asignación", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        });

	        return panelPrincipal;
	    }

	    // Método auxiliar para cargar historial desde BD
	    private static void cargarHistorialAsignaciones(JTextArea area) {
	        List<String> historial = conexion.obtenerHistorialAsignaciones();
	        area.setText("");
	        for (String registro : historial) {
	            area.append(registro + "\n");
	        }
	    }
	}
	

