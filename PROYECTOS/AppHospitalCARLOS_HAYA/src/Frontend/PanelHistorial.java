package Frontend;

import javax.swing.*;

import Backend.ConectorBD;
import Backend.Diagnostico;
import Backend.Empleado;
import Backend.Receta;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelHistorial {
	// Método que devuelve un panel
	public static JPanel crearPanel(Empleado med) {
		ConectorBD conn = new ConectorBD();
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		panel.setBackground(new Color(161, 223, 182));

		JLabel titulo = new JLabel("Historial Médico", JLabel.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 22));
		panel.add(titulo, BorderLayout.NORTH);

		JPanel top = new JPanel(new BorderLayout(10, 10));
		JTextField campoPaciente = new JTextField();
		JButton btnBuscar = new JButton("Buscar Historial");
		top.add(new JLabel("Paciente DNI:"), BorderLayout.WEST);
		top.add(campoPaciente, BorderLayout.CENTER);
		top.add(btnBuscar, BorderLayout.EAST);
		campoPaciente.setBackground(new Color(255, 255, 255));
		btnBuscar.setBackground(new Color(173, 216, 230));

		panel.add(top, BorderLayout.NORTH);

		JTextArea areaHistorial = new JTextArea();
		areaHistorial.setEditable(false);
		areaHistorial.setBorder(BorderFactory.createTitledBorder("Historial"));
		panel.add(new JScrollPane(areaHistorial), BorderLayout.CENTER);

		btnBuscar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Simulación de búsqueda
				String paciente = campoPaciente.getText().trim();

				if (paciente.isEmpty()) {
					JOptionPane.showMessageDialog(panel, "Ingrese el DNI del paciente.");
					return;
				}
				if (conn.comprobarPacienteMedico(med.getIdEmpleado(), paciente) != -1) {

					int idPaciente = conn.comprobarPacienteMedico(med.getIdEmpleado(), paciente);
					String diagnostico = "";
					String receta = "";

					for (Diagnostico i : conn.verHistorialDiagnosticos(idPaciente)) {
						diagnostico = diagnostico + i.toString() + "\n";
					}

					for (Receta i : conn.verHistorialRecetas(idPaciente)) {
						receta = receta + i.toString() + "\n";

					}

					areaHistorial.setText("Historial de " + idPaciente + ":\n- Diagnóstico: " + diagnostico
							+ "\n- Receta: " + receta + "\n...");

				} else {
					JOptionPane.showMessageDialog(panel, "El paciente no esta en tu lsita de asignados.");
					return;
				}

			}

		});

		return panel;
	}

}
