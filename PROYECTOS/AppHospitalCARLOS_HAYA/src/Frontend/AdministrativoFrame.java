package Frontend;

import javax.swing.*;

import Backend.Administrativo;
import Backend.Empleado;
import Backend.Medico;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministrativoFrame {

	public static void mostrarVentana(Empleado Admist) {
		// Crear ventana principal
				JFrame ventana = new JFrame("Hospital Carlos Haya --Administrativo");
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

				// Botones
				JButton consultarDatosHospital = new JButton("Consultar Datos del Hospital");
				JButton asignarPacienteMedico = new JButton("Asignar Paciente a Médico");
				JButton darCitaPaciente = new JButton("Dar Cita Médica");
				JButton consultarDisponibilidadSalas = new JButton("Consultar Disponibilidad de Salas");
				JButton salir = new JButton("Cerrar Sesión");
				consultarDatosHospital.setBackground(new Color(255, 255, 255));
				asignarPacienteMedico.setBackground(new Color(255, 255, 255));
				darCitaPaciente.setBackground(new Color(255, 255, 255));
				consultarDisponibilidadSalas.setBackground(new Color(255, 255, 255));
				salir.setBackground(new Color(255, 255, 255));

				// Acciones
				consultarDatosHospital.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						panelContenido.removeAll();
						panelContenido.add(PanelConsultarDatosHospital.crearPanel(), BorderLayout.CENTER);
						panelContenido.revalidate();
						panelContenido.repaint();
					}
				});

				asignarPacienteMedico.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						panelContenido.removeAll();
						panelContenido.add(PanelAsignarPacienteMedico.crearPanel(), BorderLayout.CENTER);
						panelContenido.revalidate();
						panelContenido.repaint();
					}
				});

				darCitaPaciente.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						panelContenido.removeAll();
						panelContenido.add(PanelDarCitaPaciente.crearPanel(), BorderLayout.CENTER);
						panelContenido.revalidate();
						panelContenido.repaint();
					}
				});

				consultarDisponibilidadSalas.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						panelContenido.removeAll();
						panelContenido.add(PanelConsultarDisponibilidadSalas.crearPanel(), BorderLayout.CENTER);
						panelContenido.revalidate();
						panelContenido.repaint();
					}
				});

				salir.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						ventana.dispose();
						new InicioSesion().mostrarVentana();
						;
					}
				});

				// Agregar botones al panel menú
				panelMenu.add(consultarDatosHospital);
				panelMenu.add(asignarPacienteMedico);
				panelMenu.add(darCitaPaciente);
				panelMenu.add(consultarDisponibilidadSalas);
				panelMenu.add(salir);

				// Agregar paneles a la ventana
				ventana.add(panelMenu, BorderLayout.WEST);
				ventana.add(panelContenido, BorderLayout.CENTER);

				// Mostrar ventana
				ventana.setVisible(true);
			}
}
