package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanelAsignacionPacientes {
	// Método que devuelve un panel
	public static JPanel crearPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));

		List<String> asignaciones = new ArrayList<>();

		// Crear los elementos del formulario
		JComboBox<String> comboPacientes = new JComboBox<>(new String[] { "Paciente 1", "Paciente 2", "Paciente 3" });
		JComboBox<String> comboCamas = new JComboBox<>(new String[] { "Cama 101", "Cama 102", "Cama 103" });
		JButton btnAsignar = new JButton("Asignar");
		
		comboPacientes.setBackground(new Color(255,255,255));
		comboCamas.setBackground(new Color(255,255,255));
		
		btnAsignar.setBackground(new Color(173,216,230));

		// Panel del formulario
		JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));
		formulario.setBorder(BorderFactory.createTitledBorder("Asignación de Cama a Paciente"));
        formulario.setBackground(new Color(161,223,182));
		
		formulario.add(new JLabel("Paciente:"));
		formulario.add(comboPacientes);

		formulario.add(new JLabel("Cama:"));
		formulario.add(comboCamas);

		formulario.add(new JLabel());
		formulario.add(btnAsignar);

		// Crear área de resumen
		JTextArea areaResumen = new JTextArea();
		areaResumen.setEditable(false);
		areaResumen.setBorder(BorderFactory.createTitledBorder("Resumen de Asignaciones"));
        areaResumen.setBackground(new Color(255,255,255));
		
		
		JScrollPane scroll = new JScrollPane(areaResumen);

		// Acción del botón asignar
		btnAsignar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String paciente = (String) comboPacientes.getSelectedItem();
				String cama = (String) comboCamas.getSelectedItem();

				String registro = String.format("Paciente: %s asignado a %s", paciente, cama);
				asignaciones.add(registro);
				// Actualizar el área de resumen
				StringBuilder resumen = new StringBuilder();
				for (String a : asignaciones) {
					resumen.append(a).append("\n");
				}
				areaResumen.setText(resumen.toString());
			}
		});

		// Agregar los paneles a la ventana
		panel.add(formulario, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);

		return panel;
	}
}
