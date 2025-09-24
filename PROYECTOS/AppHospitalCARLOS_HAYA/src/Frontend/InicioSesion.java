package Frontend;

import javax.swing.*;

import Backend.Administrador;
import Backend.Administrativo;
import Backend.ConectorBD;
import Backend.Empleado;
import Backend.Enfermero;
import Backend.Mantenimiento;
import Backend.Medico;
import Backend.TipoEmpleado;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.time.LocalDate;

public class InicioSesion {
	static ConectorBD conn = new ConectorBD();

	public void mostrarVentana() {

		// Crear ventana principal
		JFrame ventana = new JFrame("Hospital Carlos Haya - Inicio Sesión");
		ventana.setSize(500, 450);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLayout(new BorderLayout());

		// ========== Panel de fondo con imagen ==========
		JLabel fondo = new JLabel(
				new ImageIcon("C:/Users/PC/Desktop/APP_HOSPITAL/AppHospitalModificada/hospital1.jpg"));
		fondo.setLayout(new BorderLayout()); // importante para poder colocar paneles encima
		ventana.setContentPane(fondo); // establecemos como fondo

		// Panel superior con logo y texto
		// ================================
		JPanel panelLogo = new JPanel();
		panelLogo.setOpaque(false);
		panelLogo.setBackground(new Color(255, 255, 255));

		// Intenta cargar el logo desde resources/img/logo.png
		ImageIcon icono = null;
		try {
			icono = new ImageIcon("C:/Users/PC/Desktop/APP_HOSPITAL/AppHospitalModificada/logo.png");
		} catch (Exception e) {
			System.out.println("No se pudo cargar el logo. Asegúrate que esté en /src/img/logo.png");
		}

		JLabel logoLabel;
		if (icono != null) {
			logoLabel = new JLabel("HOSPITAL CARLOS HAYA", icono, JLabel.CENTER);
			logoLabel.setHorizontalTextPosition(JLabel.CENTER);
			logoLabel.setVerticalTextPosition(JLabel.BOTTOM);
		} else {
			logoLabel = new JLabel("HOSPITAL CARLOS HAYA", JLabel.CENTER);
		}

		logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
		logoLabel.setForeground(new Color(0, 128, 0));
		panelLogo.add(logoLabel);
		ventana.add(panelLogo, BorderLayout.NORTH);

		// Crear panel con diseño
		JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.setBackground(new Color(144, 238, 144));
		// Componentes
		JTextField userDNI = new JTextField();
		JPasswordField password = new JPasswordField();
		JComboBox<String> tipoEmpleado = new JComboBox<>(
				new String[] { "ADMINISTRADOR", "ADMINISTRATIVO", "MEDICO", "ENFERMERO", "MANTENIMIENTO" });
		tipoEmpleado.setBackground(new Color(255, 255, 255));
		JButton botonInicioSesion = new JButton("Iniciar Sesión");
		botonInicioSesion.setBackground(new Color(255, 255, 255));

		// Agregar etiquetas y campos al panel
		panel.add(new JLabel("DNI:"));
		panel.add(userDNI);
		panel.add(new JLabel("CONTRASEÑA:"));
		panel.add(password);
		panel.add(new JLabel("TIPO DE EMPLEADO:"));
		panel.add(tipoEmpleado);
		panel.add(new JLabel());
		panel.add(botonInicioSesion);

		// Agregar panel a la ventana
		// Panel contenedor para centrar el panel de inicio de sesión
		JPanel panelContenedor = new JPanel(new GridBagLayout());
		panelContenedor.setOpaque(false); // dejar que se vea el fondo

		// Centrar el panel de login en el contenedor
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelContenedor.add(panel, gbc);

		// Agregar el panel contenedor al centro de la ventana
		ventana.add(panelContenedor, BorderLayout.CENTER);
		ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
		ventana.setVisible(true);

		// Acción del botón
		botonInicioSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String dni = userDNI.getText().trim();
				String pass = new String(password.getPassword()).trim();
				TipoEmpleado tipo = TipoEmpleado.valueOf((String) tipoEmpleado.getSelectedItem());

				boolean loginCorrecto = false;

				// AQUI PONEMOS METODO CONSULTA bdd AL QUE LE PASEMOS USUARIO, TIPO DE EMPLEADO
				// Y CONTRASEÑA Y LAS 3 TIENEN QUE TENER SI O SI UN RESULTADO EN LA TABLE, SI NO
				// LO TIENEN MENSAJE ERROR DEL FORMULARIO

				Empleado emp = conn.inicioSesion(dni, pass, tipo);
				// JOptionPane.showMessageDialog(ventana,emp.toString());

				if (emp != null) {

					switch (tipo) {
					case ADMINISTRADOR:

						Administrador adm = new Administrador(emp.getIdEmpleado(), emp.getNombre(), emp.getApellidos(),
								emp.getDni(), emp.getCorreo(), emp.getContraseña(), emp.getTipoEmpleado(),
								emp.getTurno());
						new AdministradorFrame().mostrarVentana(adm);
						loginCorrecto = true;

						break;
					case ADMINISTRATIVO:
						Administrativo admist = new Administrativo(emp.getIdEmpleado(), emp.getNombre(),
								emp.getApellidos(), emp.getDni(), emp.getCorreo(), emp.getContraseña(),
								emp.getTipoEmpleado(), emp.getTurno());

						new AdministrativoFrame().mostrarVentana(admist);
						loginCorrecto = true;

						break;
					case MEDICO:
						Medico med = new Medico(emp.getIdEmpleado(), emp.getNombre(), emp.getApellidos(), emp.getDni(),
								emp.getCorreo(), emp.getContraseña(), emp.getTipoEmpleado(), emp.getTurno());
						new MedicoFrame().mostrarVentana(med);
						loginCorrecto = true;

						break;
					case ENFERMERO:
						Enfermero enf = new Enfermero(emp.getIdEmpleado(), emp.getNombre(), emp.getApellidos(),
								emp.getDni(), emp.getCorreo(), emp.getContraseña(), emp.getTipoEmpleado(),
								emp.getTurno());
						new EnfermeroFrame().mostrarVentana(enf);
						loginCorrecto = true;

						break;
					case MANTENIMIENTO:
						Mantenimiento mant = new Mantenimiento(emp.getIdEmpleado(), emp.getNombre(), emp.getApellidos(),
								emp.getDni(), emp.getCorreo(), emp.getContraseña(), emp.getTipoEmpleado(),
								emp.getTurno());
						new MantenimientoFrame().mostrarVentana(mant);
						loginCorrecto = true;

						break;
					}

					ventana.dispose(); // cerrar login
				} else {
					JOptionPane.showMessageDialog(ventana, "Usuario, contraseña o tipo de empleado incorrecto.");

				}
			}
		});
	}

}
