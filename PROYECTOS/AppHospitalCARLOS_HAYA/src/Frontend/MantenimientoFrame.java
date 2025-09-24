package Frontend;

import javax.swing.*;

import Backend.Empleado;
import Backend.Mantenimiento;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MantenimientoFrame {
	public static void mostrarVentana(Empleado mant) {
		// Crear ventana
		JFrame ventana = new JFrame("Hospital Carlos Haya - Mantenimiento");
		// ventana.setSize(1000, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLayout(new BorderLayout());

		// Maximizar la ventana al inicio
		ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Evitar que el usuario pueda redimensionar la ventana
		ventana.setResizable(false);

		// Crear paneles
		JPanel panelMenu = new JPanel(new GridLayout(0, 1, 10, 10));
		panelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelMenu.setBackground(new Color(161, 223, 182));

		JPanel panelContenido = new JPanel(new BorderLayout());
		panelContenido.setBackground(new Color(161, 223, 182));
		// Crear botones
		JButton btnReparaciones = new JButton("Registrar tarea de mantenimiento en salas");
		JButton btnDisponibilidad = new JButton("Actualizar estado de las Salas");
		JButton btnSalir = new JButton("Cerrar Sesión");
		btnReparaciones.setBackground(new Color(255, 255, 255));
		btnDisponibilidad.setBackground(new Color(255, 255, 255));
		btnSalir.setBackground(new Color(255, 255, 255));
		// Acciones de los botones
		btnReparaciones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				panelContenido.removeAll();
				panelContenido.add(PanelReparaciones.crearPanel(), BorderLayout.CENTER);
				panelContenido.revalidate();
				panelContenido.repaint();
			}
		});

		btnDisponibilidad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				panelContenido.removeAll();
				panelContenido.add(PanelDisponibilidad.crearPanel(), BorderLayout.CENTER);
				panelContenido.revalidate();
				panelContenido.repaint();
			}
		});

		btnSalir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ventana.dispose();
			}
		});

		// Agregar botones al menú
		panelMenu.add(btnReparaciones);
		panelMenu.add(btnDisponibilidad);
		panelMenu.add(btnSalir);

		// Agregar paneles a la ventana
		ventana.add(panelMenu, BorderLayout.WEST);
		ventana.add(panelContenido, BorderLayout.CENTER);

		// Mostrar ventana
		ventana.setVisible(true);
	}

}
