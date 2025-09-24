package Frontend;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Backend.Administrador;
import Backend.Empleado;

public class AdministradorFrame {
	// Método público para mostrar la ventana (invocado desde InicioSesion)
	public static void mostrarVentana(Empleado admin) {
		// Crear ventana principal
		JFrame ventana = new JFrame("Hospital Carlos Haya -- Administrador");
		// ventana.setSize(1000, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLayout(new BorderLayout());

		// Maximizar la ventana al inicio
		ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Evitar que el usuario pueda redimensionar la ventana
		ventana.setResizable(false);

		// Paneles principales
		JPanel panelMenu = new JPanel(new GridLayout(0,1, 10, 10));
		panelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelMenu.setBackground(new Color(161, 223, 182));

		// 180, 240,180
		JPanel panelContenido = new JPanel(new BorderLayout());
		panelContenido.setBackground(new Color(161, 223, 182));

		// Crear botones del menú
		JButton empleados = new JButton("GESTIÓN DE EMPLEADOS");
		JButton pacientes = new JButton("GESTIÓN DE PACIENTES");
		JButton salas = new JButton("GESTION DE SALAS");
		JButton reportes = new JButton("REPORTES Y ESTADÍSTICAS");
		JButton turnos = new JButton("ASIGNACIÓN DE ROLES,TURNOS Y SALAS DE TRABAJO");
		JButton disponibilidad = new JButton("CONTROL DE DISPONIBILIDAD DE SALAS");
		JButton salir = new JButton("CERRAR SESIÓN");
		empleados.setBackground(new Color(255, 255, 255));
		pacientes.setBackground(new Color(255, 255, 255));
		salas.setBackground(new Color(255, 255, 255));
		reportes.setBackground(new Color(255, 255, 255));
		turnos.setBackground(new Color(255, 255, 255));
		disponibilidad.setBackground(new Color(255, 255, 255));
		salir.setBackground(new Color(255, 255, 255));
		
		// verde intenso (0, 122, 51)
		// verde palido(60, 160, 100)
		// verde my claro (100, 190, 140)

		// Acción para cambiar el contenido del panel central
		ActionListener cambiarContenido = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton fuente = (JButton) e.getSource();
				JPanel nuevoPanel;

				switch (fuente.getText()) {
				case "GESTIÓN DE EMPLEADOS":
					nuevoPanel = PanelEmpleados.crearPanel();
					break;
				case "GESTIÓN DE PACIENTES":
					nuevoPanel = PanelPacientes.crearPanel();
					break;
				case "GESTION DE SALAS":
					nuevoPanel = PanelSalas.crearPanel();
					break;
				case "REPORTES Y ESTADÍSTICAS":
					nuevoPanel = PanelReportes_Estadisticas.crearPanel();
					break;
				case "ASIGNACIÓN DE ROLES,TURNOS Y SALAS DE TRABAJO":
					nuevoPanel = PanelTurnos_Roles_Salas.crearPanel();
					break;
				case "CONTROL DE DISPONIBILIDAD DE SALAS":
					nuevoPanel = PanelDisponibilidad.crearPanel();
					break;
				default:
					nuevoPanel = new JPanel();
				}

				// limpia panel
				panelContenido.removeAll();
				// agrega
				panelContenido.add(nuevoPanel, BorderLayout.CENTER);
				// reorganiza
				panelContenido.revalidate();
				// vuelve a dibujar los cambios
				panelContenido.repaint();

			}
		};

		// Asignar acciones a los botones del menú
		empleados.addActionListener(cambiarContenido);
		pacientes.addActionListener(cambiarContenido);
		salas.addActionListener(cambiarContenido);
		reportes.addActionListener(cambiarContenido);
		turnos.addActionListener(cambiarContenido);
		disponibilidad.addActionListener(cambiarContenido);

		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// set visible solo oculta sin cerrarla
				// cerrar ventana y libera los recursos
				ventana.dispose();
				new InicioSesion().mostrarVentana();
			}
		});

		// Agregar botones al panel de menú
		panelMenu.add(empleados);
		panelMenu.add(pacientes);
		panelMenu.add(salas);
		panelMenu.add(reportes);
		panelMenu.add(turnos);
		panelMenu.add(disponibilidad);
		panelMenu.add(salir);

		// Agregar paneles a la ventana
		ventana.add(panelMenu, BorderLayout.WEST);
		ventana.add(panelContenido, BorderLayout.CENTER);

		// Mostrar ventana
		ventana.setVisible(true);
	}
}
