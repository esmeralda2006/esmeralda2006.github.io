package Frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Backend.Empleado;
import Backend.Medico;

public class MedicoFrame {
//le paso emdico para luego sacar al responsable directamente de aqui
	public static void mostrarVentana(Empleado med) {
		// Crear la ventana
		JFrame ventana = new JFrame("Hospital Carlos Haya - Médico");
		// ventana.setSize(1000, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLayout(new BorderLayout());

		// Maximizar la ventana al inicio
		ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Evitar que el usuario pueda redimensionar la ventana
		ventana.setResizable(false);

		// Crear los paneles
		JPanel panelMenu = new JPanel(new GridLayout(0, 1, 10, 10));
		panelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelMenu.setBackground(new Color(161, 223, 182));

		JPanel panelContenido = new JPanel(new BorderLayout());
		panelContenido.setBackground(new Color(161, 223, 182));
		// Crear los botones del menú
		JButton btnDiagnostico = new JButton("Registrar Diagnóstico");
		JButton btnReceta = new JButton("Registrar Receta Médica");
		JButton btnHistorial = new JButton("Ver Historial Médico");
		JButton btnPacientes = new JButton("Mis Pacientes Asignados");
		JButton btnSolicitarAlta = new JButton("Solicitar Alta de Paciente");
		JButton btnSalir = new JButton("Cerrar Sesión");
		btnDiagnostico.setBackground(new Color(255, 255, 255));
		btnReceta.setBackground(new Color(255, 255, 255));
		btnHistorial.setBackground(new Color(255, 255, 255));
		btnPacientes.setBackground(new Color(255, 255, 255));
		btnSolicitarAlta.setBackground(new Color(255, 255, 255));
		btnSalir.setBackground(new Color(255, 255, 255));

		// Asignar acciones a los botones
		btnDiagnostico.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Limpiar contenido actual
				panelContenido.removeAll();
				panelContenido.add(PanelDiagnostico.crearPanel(med), BorderLayout.CENTER);
				// actualiza diseño,se reorganiza
				panelContenido.revalidate();
				// vuelve a dibujar el panel.para q los cambios se vean
				panelContenido.repaint();
			}
		});

		btnReceta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				panelContenido.removeAll();
				panelContenido.add(PanelRecetas.crearPanel(med), BorderLayout.CENTER);
				panelContenido.revalidate();
				panelContenido.repaint();
			}
		});

		btnHistorial.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				panelContenido.removeAll();
				panelContenido.add(PanelHistorial.crearPanel(med), BorderLayout.CENTER);
				panelContenido.revalidate();
				panelContenido.repaint();
			}
		});

		btnPacientes.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        panelContenido.removeAll();
		        panelContenido.add(PanelPacientesMedico.crearPanel(med.getIdEmpleado()), BorderLayout.CENTER);
		        panelContenido.revalidate();
		        panelContenido.repaint();
		    }
		});

		btnSolicitarAlta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				panelContenido.removeAll();
				panelContenido.add(PanelSolicitarAlta.crearPanel(), BorderLayout.CENTER);
				panelContenido.revalidate();
				panelContenido.repaint();
			}
		});

		btnSalir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ventana.dispose();
				new InicioSesion().mostrarVentana();
			}
		});

		// Agregar botones al panel menú
		panelMenu.add(btnDiagnostico);
		panelMenu.add(btnReceta);
		panelMenu.add(btnHistorial);
		panelMenu.add(btnPacientes);
		panelMenu.add(btnSolicitarAlta);
		panelMenu.add(btnSalir);

		// Agregar paneles a la ventana
		ventana.add(panelMenu, BorderLayout.WEST);
		ventana.add(panelContenido, BorderLayout.CENTER);

		// Mostrar la ventana
		ventana.setVisible(true);
	}

}
