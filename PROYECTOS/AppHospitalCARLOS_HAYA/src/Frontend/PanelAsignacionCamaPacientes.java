package Frontend;

import javax.swing.*;

import Backend.ConectorBD;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanelAsignacionCamaPacientes {
private static ConectorBD conexion=new ConectorBD();
	

    public static JPanel crearPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 255, 250)); // Verde claro

        // Combos
        JComboBox<String> comboPacientes = new JComboBox<>();
        JComboBox<String> comboCamas = new JComboBox<>();

        // Llenar combos desde la BD
        List<String> pacientes = conexion.obtenerIdsPacientes();
        for (String p : pacientes) comboPacientes.addItem(p);

        List<String> camas = conexion.obtenerCamasDisponibles();
        for (String c : camas) comboCamas.addItem(c);

        JButton btnAsignar = new JButton("Asignar");
        btnAsignar.setBackground(new Color(173, 216, 230));

        // Área de historial
        JTextArea areaResumen = new JTextArea(10, 30);
        areaResumen.setEditable(false);
        areaResumen.setBorder(BorderFactory.createTitledBorder("Historial de Asignaciones"));
        JScrollPane scroll = new JScrollPane(areaResumen);

        // Panel formulario
        JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));
        formulario.setBorder(BorderFactory.createTitledBorder("Asignar cama a paciente"));
        formulario.setBackground(new Color(161, 223, 182));

        formulario.add(new JLabel("Paciente:"));
        formulario.add(comboPacientes);

        formulario.add(new JLabel("Cama disponible:"));
        formulario.add(comboCamas);

        formulario.add(new JLabel());
        formulario.add(btnAsignar);

        // Acción botón
        btnAsignar.addActionListener((ActionEvent e) -> {
            String pacienteItem = (String) comboPacientes.getSelectedItem();
            String camaItem = (String) comboCamas.getSelectedItem();

            if (pacienteItem == null || camaItem == null) {
                JOptionPane.showMessageDialog(panel, "Selecciona un paciente y una cama.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idPaciente = Integer.parseInt(pacienteItem.split(" - ")[0]);
            int idCama = Integer.parseInt(camaItem.split(" - ")[0]);

            boolean exito = conexion.asignarCamaAPaciente(idPaciente, idCama);
            if (exito) {
                JOptionPane.showMessageDialog(panel, "Cama asignada correctamente.");
                comboCamas.removeItem(camaItem); // Ya no está disponible
                mostrarHistorial(areaResumen);
            } else {
                JOptionPane.showMessageDialog(panel, "Error al asignar la cama.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Panel completo
        panel.add(formulario, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        // Mostrar historial inicial
        mostrarHistorial(areaResumen);

        return panel;
    }

    private static void mostrarHistorial(JTextArea areaResumen) {
        List<String> historial = conexion.obtenerHistorialAsignacionCama();
        StringBuilder texto = new StringBuilder();
        for (String linea : historial) {
            texto.append(linea).append("\n");
        }
        areaResumen.setText(texto.toString());
    }
}
