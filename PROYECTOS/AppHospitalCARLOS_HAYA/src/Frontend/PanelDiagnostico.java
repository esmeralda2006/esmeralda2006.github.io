package Frontend;

import javax.swing.*;

import Backend.ConectorBD;
import Backend.Empleado;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelDiagnostico {
	// Método que devuelve un panel
	public static JPanel crearPanel(Empleado med) {
		ConectorBD conn = new ConectorBD();
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		JLabel titulo = new JLabel("Registrar Diagnóstico", JLabel.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 22));
		panel.add(titulo, BorderLayout.NORTH);
		panel.setBackground(new Color(161, 223, 182));

		JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
		form.setBackground(new Color(161, 223, 182));

		JTextField campoDNI = new JTextField();
		JTextField campoPaciente = new JTextField();
		JTextArea campoDiagnostico = new JTextArea(5, 20);

		campoDiagnostico.setLineWrap(true);
		campoDiagnostico.setWrapStyleWord(true);
		campoPaciente.setBackground(new Color(255, 255, 255));
		campoDiagnostico.setBackground(new Color(255, 255, 255));

		form.add(new JLabel("DNI Paciente:"));
		form.add(campoDNI);
		form.add(new JLabel("Nombre del Paciente:"));
		form.add(campoPaciente);
		form.add(new JLabel("Diagnóstico:"));
		form.add(new JScrollPane(campoDiagnostico));

		JButton btnGuardar = new JButton("Guardar Diagnóstico");
		btnGuardar.setBackground(new Color(253, 253, 150));

		btnGuardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String dni = campoDNI.getText().trim().toUpperCase();
				String nombre = campoPaciente.getText().trim();
				String diagnostico = campoDiagnostico.getText().trim();

				// aqui poner el metodo de consulta para enviar datos a ese paciente

				if (nombre.isEmpty()) {
					JOptionPane.showMessageDialog(panel, "El campo 'Nombre' no puede estar vacío.", "Error de Entrada",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else if (diagnostico.isEmpty()) {
					JOptionPane.showMessageDialog(panel, "El campo 'Diagnóstico' no puede estar vacío.",
							"Error de Entrada", JOptionPane.WARNING_MESSAGE);
					return;
				} else if (dni.isBlank()) {
					JOptionPane.showMessageDialog(panel, "El campo 'DNI' no puede estar vacío.", "Error de Entrada",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else if (!dni.matches("^\\d{8}[A-za-z]$")) {
					// CARACTERES VALIDOS PARA DNI ESPAÑOL TRWAGMYFPDXBNJZSQVHLCKE

					JOptionPane.showMessageDialog(panel,
							"El formato del DNI no es válido. Debe tener 8 dígitos seguidos de una letra (Ej: 12345678A).",
							"Error de Formato DNI", JOptionPane.WARNING_MESSAGE);
					return;
				}

				conn.insertarDiagnosticoBD(dni, nombre, diagnostico, med.getIdEmpleado());
				JOptionPane.showMessageDialog(panel, "Diagnóstico guardado para: " + nombre);
				campoPaciente.setText("");
				campoDiagnostico.setText("");
			}
		});

		panel.add(form, BorderLayout.CENTER);
		panel.add(btnGuardar, BorderLayout.SOUTH);

		return panel;
	}
}
