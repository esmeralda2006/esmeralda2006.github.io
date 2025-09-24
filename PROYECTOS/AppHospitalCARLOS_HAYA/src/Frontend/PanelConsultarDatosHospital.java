package Frontend;

import javax.swing.*;

import Backend.ConectorBD;
import Backend.Empleado;
import Backend.Paciente;
import java.util.List;

import java.awt.*;


public class PanelConsultarDatosHospital {
   
	 static ConectorBD conector = new ConectorBD();

	    public static JPanel crearPanel() {
	        JPanel panelPrincipal = new JPanel(new BorderLayout());
	        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	        Color fondoVerde = new Color(161, 223, 182);
	        panelPrincipal.setBackground(fondoVerde);

	        JLabel titleLabel = new JLabel("Datos del Hospital", JLabel.CENTER);
	        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
	        panelPrincipal.add(titleLabel, BorderLayout.NORTH);

	        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 10, 10));
	        infoPanel.setBorder(BorderFactory.createTitledBorder("Información del Hospital"));
	        infoPanel.setBackground(fondoVerde);

	        // Datos estáticos
	        String hospitalName = "HOSPITAL REGIONAL UNIVERSITARIO DE MÁLAGA - CARLOS HAYA";
	        String hospitalAddress = "Avenida Carlos Haya, 84, Bailén Miraflores, 29010, Málaga";

	        // Datos dinámicos desde la base de datos
	        int numEmployees = conector.contarEmpleados();
	        int numRooms = conector.contarSalas();
	        int numPacientes = conector.contarPacientes();
	        int numInternados = conector.contarPacientesInternados();

	        Font fuenteDatos = new Font("Arial", Font.PLAIN, 16);
	        infoPanel.add(crearEtiqueta("Nombre del Hospital: " + hospitalName, fuenteDatos));
	        infoPanel.add(crearEtiqueta("Dirección: " + hospitalAddress, fuenteDatos));
	        infoPanel.add(crearEtiqueta("Número de Empleados: " + numEmployees, fuenteDatos));
	        infoPanel.add(crearEtiqueta("Número de Salas: " + numRooms, fuenteDatos));
	        infoPanel.add(crearEtiqueta("Total de Pacientes Registrados: " + numPacientes, fuenteDatos));
	        infoPanel.add(crearEtiqueta("Pacientes Actualmente Internados: " + numInternados, fuenteDatos));

	        panelPrincipal.add(infoPanel, BorderLayout.CENTER);

	        JPanel botonesPanel = new JPanel(new GridLayout(4, 1, 10, 10));
	        botonesPanel.setBackground(fondoVerde);

	        Dimension tamanoBoton = new Dimension(220, 35);
	        Font fuenteBoton = new Font("Arial", Font.PLAIN, 12);

	        JButton btnMostrarEmpleados = crearBoton("Mostrar Todos los Empleados", tamanoBoton, fuenteBoton);
	        btnMostrarEmpleados.addActionListener(e -> {
	            List<Empleado> lista = conector.obtenerTodosEmpleados();
	            StringBuilder sb = new StringBuilder();
	            for (Empleado emp : lista) {
	                sb.append(emp.toString()).append("\n");
	            }
	            mostrarResultado("Listado de Empleados", sb.toString(), fondoVerde);
	        });

	        JButton btnBuscarEmpleado = crearBoton("Buscar Empleado por DNI", tamanoBoton, fuenteBoton);
	        btnBuscarEmpleado.addActionListener(e -> {
	            String dni = JOptionPane.showInputDialog("Introduce el DNI del empleado:");
	            if (dni != null && !dni.trim().isEmpty()) {
	                Empleado emp = conector.buscarEmpleadoPorDNI(dni.trim());
	                if (emp != null) {
	                    mostrarResultado("Empleado encontrado", emp.toString(), fondoVerde);
	                } else {
	                    mostrarResultado("No encontrado", "No se encontró ningún empleado con ese DNI.", fondoVerde);
	                }
	            }
	        });

	        JButton btnMostrarPacientes = crearBoton("Mostrar Todos los Pacientes", tamanoBoton, fuenteBoton);
	        btnMostrarPacientes.addActionListener(e -> {
	            List<Paciente> lista = conector.obtenerTodosLosPacientes();
	            StringBuilder sb = new StringBuilder();
	            for (Paciente p : lista) {
	                sb.append(p.toString()).append("\n");
	            }
	            mostrarResultado("Listado de Pacientes", sb.toString(), fondoVerde);
	        });

	        JButton btnBuscarPaciente = crearBoton("Buscar Paciente por DNI", tamanoBoton, fuenteBoton);
	        btnBuscarPaciente.addActionListener(e -> {
	            String dni = JOptionPane.showInputDialog("Introduce el DNI del paciente:");
	            if (dni != null && !dni.trim().isEmpty()) {
	                Paciente p = conector.buscarPacientePorDNI(dni.trim());
	                if (p != null) {
	                    mostrarResultado("Paciente encontrado", p.toString(), fondoVerde);
	                } else {
	                    mostrarResultado("No encontrado", "No se encontró ningún paciente con ese DNI.", fondoVerde);
	                }
	            }
	        });

	        botonesPanel.add(btnMostrarEmpleados);
	        botonesPanel.add(btnBuscarEmpleado);
	        botonesPanel.add(btnMostrarPacientes);
	        botonesPanel.add(btnBuscarPaciente);
	        btnMostrarEmpleados.setBackground(new Color(173, 216, 230));
	        btnBuscarEmpleado.setBackground(new Color(253,253,150));
	        btnMostrarPacientes.setBackground(new Color(240,128,128));
	        btnBuscarPaciente.setBackground(new Color(200, 255, 200));

	        panelPrincipal.add(botonesPanel, BorderLayout.EAST);

	       /* JButton btnReturn = new JButton("Volver al Menú");
	        btnReturn.setBackground(new Color(253, 253, 150));
	        panelPrincipal.add(btnReturn, BorderLayout.SOUTH);*/

	        return panelPrincipal;
	    }

	    private static JLabel crearEtiqueta(String texto, Font fuente) {
	        JLabel label = new JLabel(texto);
	        label.setFont(fuente);
	        return label;
	    }

	    private static JButton crearBoton(String texto, Dimension dimension, Font fuente) {
	        JButton boton = new JButton(texto);
	        boton.setPreferredSize(dimension);
	        boton.setFont(fuente);
	        return boton;
	    }

	    private static void mostrarResultado(String titulo, String mensaje, Color fondo) {
	        JTextArea textArea = new JTextArea(mensaje);
	        textArea.setEditable(false);
	        textArea.setBackground(fondo);
	        JScrollPane scrollPane = new JScrollPane(textArea);
	        scrollPane.setPreferredSize(new Dimension(500, 400));
	        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
	    }
}