package Frontend;

import javax.swing.*;

import Backend.ConectorBD;
import Backend.Empleado;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelRecetas {
	// Método que devuelve un panel
	public static JPanel crearPanel(Empleado med) {
		ConectorBD conn = new ConectorBD();
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		panel.setBackground(new Color(161, 223, 182));

		JLabel titulo = new JLabel("Registrar Receta", JLabel.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 22));
		panel.add(titulo, BorderLayout.NORTH);

		JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
		form.setBackground(new Color(161, 223, 182));

		JTextField campoDNI = new JTextField();
		JTextField campoPaciente = new JTextField();
		JTextArea campoReceta = new JTextArea(5, 20);
		campoReceta.setLineWrap(true);
		campoReceta.setWrapStyleWord(true);

		form.add(new JLabel("DNI Paciente:"));
		form.add(campoDNI);
		form.add(new JLabel("Nombre del Paciente:"));
		form.add(campoPaciente);
		form.add(new JLabel("Receta Médica:"));
		form.add(new JScrollPane(campoReceta));

		JButton btnGuardar = new JButton("Guardar Receta");

		btnGuardar.setBackground(new Color(255, 182, 193));

		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String dni = campoDNI.getText().trim().toUpperCase();
				String nombre = campoPaciente.getText().trim();
				String receta = campoReceta.getText().trim();

				// aqui poner el metodo de consulta para enviar datos a ese paciente

				if (nombre.isEmpty()) {
					JOptionPane.showMessageDialog(panel, "El campo 'Nombre' no puede estar vacío.", "Error de Entrada",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else if (receta.isEmpty()) {
					JOptionPane.showMessageDialog(panel, "El campo 'Receta' no puede estar vacío.",
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

				conn.insertarDiagnosticoBD(dni, nombre, receta, med.getIdEmpleado());
				JOptionPane.showMessageDialog(panel, "Receta guardada para: " + nombre);
				campoPaciente.setText("");
				campoReceta.setText("");
			}
		});

		panel.add(form, BorderLayout.CENTER);
		panel.add(btnGuardar, BorderLayout.SOUTH);

		return panel;
	}

}
