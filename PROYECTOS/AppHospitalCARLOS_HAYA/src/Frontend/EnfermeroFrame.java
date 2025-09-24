package Frontend;

import javax.swing.*;

import Backend.Empleado;
import Backend.Enfermero;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnfermeroFrame {

	public static void mostrarVentana(Enfermero enf) {
		// Crear ventana principal
		JFrame ventana = new JFrame("Hospital Carlos Haya - Enfermero");
		// ventana.setSize(1000, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLayout(new BorderLayout());

		// Maximizar la ventana al inicio
		ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Evitar que el usuario pueda redimensionar la ventana
		ventana.setResizable(false);

		// Paneles
		JPanel panelMenu = new JPanel(new GridLayout(0, 1, 10, 10));
		panelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelMenu.setBackground(new Color(161, 223, 182));

		JPanel panelContenido = new JPanel(new BorderLayout());
		panelContenido.setBackground(new Color(161, 223, 182));
		// Botones del menú
		JButton btnPacientes = new JButton("Asignación de Cama a Paciente");
		JButton btnSalas = new JButton("Marcar Alta Medica");
		JButton btnSalir = new JButton("Cerrar Sesión");
		btnPacientes.setBackground(new Color(255, 255, 255));

		btnSalas.setBackground(new Color(255, 255, 255));
		btnSalir.setBackground(new Color(255, 255, 255));

		// Acciones de los botones
		btnPacientes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Verificar que panelContenido esté correctamente instanciado
				panelContenido.removeAll(); // Limpiar contenido actual
				panelContenido.add(PanelAsignacionCamaPacientes.crearPanel(), BorderLayout.CENTER); // Agregar el panel de
																								// asignación
				panelContenido.revalidate(); // Revalidar el contenedor para que se actualice
				panelContenido.repaint(); // Redibujar el contenedor para reflejar el cambio
			}
		});

		btnSalas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				panelContenido.removeAll();
				panelContenido.add(PanelMarcarAltaMedica.crearPanel(), BorderLayout.CENTER);
				panelContenido.revalidate();
				panelContenido.repaint();
			}
		});

		btnSalir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.dispose();
				new InicioSesion().mostrarVentana(); // Volver al login
			}
		});

		// Agregar botones al panel del menú
		panelMenu.add(btnPacientes);
		panelMenu.add(btnSalas);
		panelMenu.add(btnSalir);

		// Agregar paneles a la ventana
		ventana.add(panelMenu, BorderLayout.WEST);
		ventana.add(panelContenido, BorderLayout.CENTER);

		// Mostrar ventana
		ventana.setVisible(true);
	}

}
