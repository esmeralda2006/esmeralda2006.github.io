package Frontend;

import javax.swing.*;

import Backend.CitaMedica;
import Backend.ConectorBD;
import Backend.Empleado;
import Backend.Paciente;
import Backend.Sala;
import Backend.SolicitudesAlta;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelSolicitarAlta {
	private static ConectorBD conector = new ConectorBD();

	public static JPanel crearPanel() {
		JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panelPrincipal.setBackground(new Color(161, 223, 182));

		// ComboBox y botón
		JPanel formulario = new JPanel(new GridLayout(2, 2, 10, 10));
		formulario.setBorder(BorderFactory.createTitledBorder("Solicitar Alta de Paciente"));
		formulario.setBackground(new Color(161, 223, 182));

		// Obtener datos desde BD
		List<Paciente> listaPacientes = conector.obtenerTodosLosPacientes();
		JComboBox<Paciente> comboPacientes = new JComboBox<>(listaPacientes.toArray(new Paciente[0]));
		comboPacientes.setBackground(Color.WHITE);

		JButton btnSolicitarAlta = new JButton("Solicitar Alta");
		btnSolicitarAlta.setBackground(new Color(102, 205, 170));

		formulario.add(new JLabel("Seleccionar Paciente:"));
		formulario.add(comboPacientes);
		formulario.add(new JLabel());
		formulario.add(btnSolicitarAlta);

		// Área de historial
		JTextArea areaSolicitudes = new JTextArea();
		areaSolicitudes.setEditable(false);
		areaSolicitudes.setBorder(BorderFactory.createTitledBorder("Solicitudes Realizadas"));
		JScrollPane scroll = new JScrollPane(areaSolicitudes);

		// Método para actualizar historial desde la BD
		Runnable actualizarHistorial = () -> {
			List<String> solicitudes = conector.obtenerSolicitudesAlta();
			areaSolicitudes.setText("");
			for (String p : solicitudes) {
				areaSolicitudes.append("- " + p + "\n");
			}
		};

		// Acción del botón
		btnSolicitarAlta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Paciente pacienteSeleccionado = (Paciente) comboPacientes.getSelectedItem();
				if (pacienteSeleccionado != null) {
					boolean exito = conector.solicitarAltaParaPaciente(pacienteSeleccionado.getIdPaciente());
					if (exito) {
						JOptionPane.showMessageDialog(panelPrincipal,
								"Alta solicitada para " + pacienteSeleccionado.getNombre() + " " + pacienteSeleccionado.getApellidos());
						actualizarHistorial.run();
					} else {
						JOptionPane.showMessageDialog(panelPrincipal,
								"Error al solicitar alta. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		// Inicializar historial al cargar
		actualizarHistorial.run();

		panelPrincipal.add(formulario, BorderLayout.NORTH);
		panelPrincipal.add(scroll, BorderLayout.CENTER);

		return panelPrincipal;
	}

}
