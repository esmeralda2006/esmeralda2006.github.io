package Frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Backend.ConectorBD;
import Backend.Empleado;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PanelTurnos_Roles_Salas {
	
	 private static ConectorBD conexion = new ConectorBD();

	    public static JPanel crearPanel() {
	        JPanel panelPrincipal = new JPanel(new BorderLayout());
	        panelPrincipal.setBackground(new Color(161, 223, 182));

	        // Panel formulario
	        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
	        panelFormulario.setBorder(BorderFactory.createTitledBorder("Asignar Turno, Rol y Sala"));
	        panelFormulario.setBackground(new Color(161, 223, 182));

	        // ComboBox empleados
	        JComboBox<String> comboEmpleado = new JComboBox<>();
	        List<Empleado> empleados = conexion.obtenerEmpleados();
	        for (Empleado emp : empleados) {
	            comboEmpleado.addItem(emp.getIdEmpleado() + " - " + emp.getNombre() + " " + emp.getApellidos());
	        }

	        // ComboBox roles
	        JComboBox<String> comboRol = new JComboBox<>(new String[] {
	            "ADMINISTRADOR", "ADMINISTRATIVO", "MEDICO", "ENFERMERO"
	        });

	        // ComboBox turnos
	        JComboBox<String> comboTurno = new JComboBox<>(new String[] {
	            "MAÑANA", "TARDE", "NOCHE"
	        });

	        // ComboBox salas
	        JComboBox<String> comboSala = new JComboBox<>();
	        List<String> salas = conexion.obtenerIdsSalas();
	        for (String sala : salas) {
	            comboSala.addItem(sala); // Ej: "5 - QUIRÓFANO"
	        }

	        // Estilo visual combos
	        comboEmpleado.setBackground(Color.WHITE);
	        comboRol.setBackground(Color.WHITE);
	        comboTurno.setBackground(Color.WHITE);
	        comboSala.setBackground(Color.WHITE);

	        JButton btnAsignar = new JButton("Asignar");
	        btnAsignar.setBackground(new Color(173, 216, 230));

	        // Agregar componentes a formulario
	        panelFormulario.add(new JLabel("Empleado:"));
	        panelFormulario.add(comboEmpleado);
	        panelFormulario.add(new JLabel("Rol:"));
	        panelFormulario.add(comboRol);
	        panelFormulario.add(new JLabel("Turno:"));
	        panelFormulario.add(comboTurno);
	        panelFormulario.add(new JLabel("Sala:"));
	        panelFormulario.add(comboSala);
	        panelFormulario.add(new JLabel());
	        panelFormulario.add(btnAsignar);

	        // Tabla para asignaciones
	        DefaultTableModel modeloTabla = new DefaultTableModel(
	            new Object[] { "Empleado", "Rol", "Turno", "Sala" }, 0);
	        JTable tablaAsignaciones = new JTable(modeloTabla);
	        tablaAsignaciones.getTableHeader().setBackground(new Color(100, 180, 100));
	        tablaAsignaciones.setBackground(new Color(245, 255, 245));

	        JScrollPane scrollPane = new JScrollPane(tablaAsignaciones);

	        // Agregar panel formulario y tabla al panel principal
	        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
	        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

	        // Cargar datos iniciales en tabla usando método de ConectorBD
	        cargarAsignacionesEnTabla(modeloTabla);

	        // Acción botón asignar
	        btnAsignar.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                asignarTurno(comboEmpleado, comboRol, comboTurno, comboSala, modeloTabla);
	            }
	        });

	        return panelPrincipal;
	    }

	    private static void asignarTurno(JComboBox<String> comboEmpleado, JComboBox<String> comboRol,
	                                     JComboBox<String> comboTurno, JComboBox<String> comboSala,
	                                     DefaultTableModel modeloTabla) {

	        String empleadoStr = (String) comboEmpleado.getSelectedItem();
	        if (empleadoStr == null || empleadoStr.trim().isEmpty()) return;

	        int idEmpleado = Integer.parseInt(empleadoStr.split(" - ")[0]);

	        String rol = (String) comboRol.getSelectedItem();
	        String turno = (String) comboTurno.getSelectedItem();
	        String sala = (String) comboSala.getSelectedItem();

	        Integer idSala = null;
	        if (sala != null && sala.contains(" - ")) {
	            try {
	                idSala = Integer.parseInt(sala.split(" - ")[0]);
	            } catch (NumberFormatException e) {
	                idSala = null;
	            }
	        }

	        try {
	            boolean ok = conexion.asignarRolTurnoSala(idEmpleado, rol.toUpperCase(), turno.toUpperCase(), idSala);
	            if (ok) {
	                cargarAsignacionesEnTabla(modeloTabla);
	                JOptionPane.showMessageDialog(null, "Asignación exitosa.");
	            } else {
	                JOptionPane.showMessageDialog(null, "No se pudo actualizar el rol, turno y sala.");
	            }
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(null, "Error al asignar: " + ex.getMessage());
	            ex.printStackTrace();
	        }
	    }

	    private static void cargarAsignacionesEnTabla(DefaultTableModel modeloTabla) {
	        modeloTabla.setRowCount(0);

	        List<String[]> asignaciones = conexion.obtenerAsignaciones();
	        for (String[] fila : asignaciones) {
	            modeloTabla.addRow(fila);
	        }
	    }
   
}
