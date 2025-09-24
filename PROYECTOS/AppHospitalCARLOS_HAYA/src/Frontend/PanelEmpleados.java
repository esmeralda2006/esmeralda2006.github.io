package Frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Backend.ConectorBD;
import Backend.Empleado;
import Backend.TipoEmpleado;
import Backend.Turno;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelEmpleados {
	static ConectorBD conexion = new ConectorBD();

    public static JPanel crearPanel() {

        ArrayList<Empleado> listaEmpleados = new ArrayList<>();

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(161, 223, 182));

        // Panel superior con título y buscador
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBackground(new Color(161, 223, 182));

        JLabel titulo = new JLabel("Gestión de Empleados", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelSuperior.add(titulo, BorderLayout.NORTH);

        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBackground(new Color(161, 223, 182));
        JTextField campoBuscarDNI = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar por DNI");
        JButton btnMostrarTodos = new JButton("Mostrar Todos");

        btnBuscar.setBackground(new Color(173, 216, 230));
        btnMostrarTodos.setBackground(new Color(200, 255, 200));

        panelBusqueda.add(new JLabel("Buscar DNI:"));
        panelBusqueda.add(campoBuscarDNI);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnMostrarTodos);

        panelSuperior.add(panelBusqueda, BorderLayout.SOUTH);
        panel.add(panelSuperior, BorderLayout.NORTH);

        // FORMULARIO
        JPanel formulario = new JPanel(new GridLayout(0, 2, 10, 10));
        formulario.setBackground(new Color(161, 223, 182));

        JTextField campoNombre = new JTextField();
        JTextField campoApellidos = new JTextField();
        JTextField campoDNI = new JTextField();
        JTextField campoCorreo = new JTextField();
        JPasswordField campoContraseña = new JPasswordField();

        JComboBox<String> comboTipoEmpleado = new JComboBox<>(new String[] {
            "Seleccione tipo", "ADMINISTRADOR", "ADMINISTRATIVO", "MEDICO", "ENFERMERO", "MANTENIMIENTO"
        });
        JComboBox<String> comboTurno = new JComboBox<>(new String[] {
            "Seleccione turno", "MAÑANA", "TARDE", "NOCHE"
        });

        comboTipoEmpleado.setBackground(Color.WHITE);
        comboTurno.setBackground(Color.WHITE);

        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Apellidos:"));
        formulario.add(campoApellidos);
        formulario.add(new JLabel("DNI:"));
        formulario.add(campoDNI);
        formulario.add(new JLabel("Correo:"));
        formulario.add(campoCorreo);
        formulario.add(new JLabel("Contraseña:"));
        formulario.add(campoContraseña);
        formulario.add(new JLabel("Tipo de Empleado:"));
        formulario.add(comboTipoEmpleado);
        formulario.add(new JLabel("Turno:"));
        formulario.add(comboTurno);

        panel.add(formulario, BorderLayout.WEST);

        // TABLA
        DefaultTableModel modeloTabla = new DefaultTableModel(
            new String[] { "ID", "Nombre", "Apellidos", "DNI", "Correo", "Contraseña", "Tipo", "Turno" }, 0);
        JTable tabla = new JTable(modeloTabla);
        tabla.getTableHeader().setBackground(new Color(100, 180, 100));
        tabla.setBackground(new Color(245, 255, 245));
        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);

        // BOTONES
        JPanel botones = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");

        btnAgregar.setBackground(new Color(102, 205, 170));
        btnModificar.setBackground(new Color(253, 253, 150));
        btnEliminar.setBackground(new Color(240, 128, 128));

        // --- Eventos ---

        btnBuscar.addActionListener(e -> {
            String dni = campoBuscarDNI.getText().trim();
            if (dni.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Ingrese un DNI para buscar.");
                return;
            }

            Empleado emp = conexion.buscarEmpleadoPorDNI(dni);
            if (emp != null) {
                modeloTabla.setRowCount(0);
                listaEmpleados.clear();
                listaEmpleados.add(emp);
                modeloTabla.addRow(new Object[] {
                    emp.getIdEmpleado(), emp.getNombre(), emp.getApellidos(), emp.getDni(), emp.getCorreo(),
                    emp.getContraseña(), emp.getTipoEmpleado(), emp.getTurno()
                });
            } else {
                JOptionPane.showMessageDialog(panel, "Empleado no encontrado.");
            }
        });

        btnMostrarTodos.addActionListener(e -> {
            modeloTabla.setRowCount(0);
            listaEmpleados.clear();
            listaEmpleados.addAll(conexion.obtenerTodosEmpleados());
            for (Empleado emp : listaEmpleados) {
                System.out.println(emp); // <- Para depurar en consola
                modeloTabla.addRow(new Object[] {
                    emp.getIdEmpleado(), emp.getNombre(), emp.getApellidos(), emp.getDni(),
                    emp.getCorreo(), emp.getContraseña(), emp.getTipoEmpleado(), emp.getTurno()
                });
            }
        });

        btnAgregar.addActionListener(e -> {
            if (validarCampos(campoNombre, campoApellidos, campoDNI, campoCorreo, campoContraseña, comboTipoEmpleado, comboTurno)) {
                TipoEmpleado tipo = TipoEmpleado.valueOf(comboTipoEmpleado.getSelectedItem().toString());
                Turno turno = Turno.valueOf(comboTurno.getSelectedItem().toString());

                Empleado emp = new Empleado(
                    campoNombre.getText(),
                    campoApellidos.getText(),
                    campoDNI.getText(),
                    campoCorreo.getText(),
                    new String(campoContraseña.getPassword()),
                    tipo,
                    turno
                );

                if (conexion.insertarEmpleadoBD(emp.getNombre(), emp.getApellidos(), emp.getDni(), emp.getCorreo(), emp.getContraseña(), emp.getTipoEmpleado(), emp.getTurno())) {
                    listaEmpleados.add(emp);
                    emp.setIdEmpleado(conexion.obtenerUltimoIdEmpleado());
                    modeloTabla.addRow(new Object[] {
                        emp.getIdEmpleado(), emp.getNombre(), emp.getApellidos(), emp.getDni(), emp.getCorreo(),
                        emp.getContraseña(), emp.getTipoEmpleado(), emp.getTurno()
                    });
                    limpiarCampos(campoNombre, campoApellidos, campoDNI, campoCorreo, campoContraseña, comboTipoEmpleado, comboTurno, tabla);
                    JOptionPane.showMessageDialog(panel, "Empleado insertado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(panel, "Error al insertar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(panel, "Seleccione un empleado para modificar.");
                return;
            }

            if (validarCampos(campoNombre, campoApellidos, campoDNI, campoCorreo, campoContraseña, comboTipoEmpleado, comboTurno)) {
                Empleado emp = listaEmpleados.get(fila);
                String dniOriginal = emp.getDni(); // <- Guarda el DNI original

                // Actualiza objeto con nuevos datos
                emp.setNombre(campoNombre.getText());
                emp.setApellidos(campoApellidos.getText());
                emp.setDni(campoDNI.getText());
                emp.setCorreo(campoCorreo.getText());
                emp.setContraseña(new String(campoContraseña.getPassword()));
                emp.setTipoEmpleado(TipoEmpleado.valueOf(comboTipoEmpleado.getSelectedItem().toString()));
                emp.setTurno(Turno.valueOf(comboTurno.getSelectedItem().toString()));

                // Actualiza tabla visual
                modeloTabla.setValueAt(emp.getNombre(), fila, 1);
                modeloTabla.setValueAt(emp.getApellidos(), fila, 2);
                modeloTabla.setValueAt(emp.getDni(), fila, 3);
                modeloTabla.setValueAt(emp.getCorreo(), fila, 4);
                modeloTabla.setValueAt(emp.getContraseña(), fila, 5);
                modeloTabla.setValueAt(emp.getTipoEmpleado(), fila, 6);
                modeloTabla.setValueAt(emp.getTurno(), fila, 7);

                // Llama al método pasando el DNI original
                if (conexion.modificarEmpleadoBD(
                        emp.getIdEmpleado(),
                        emp.getNombre(),
                        emp.getApellidos(),
                        emp.getDni(),
                        emp.getCorreo(),
                        emp.getContraseña(),
                        emp.getTipoEmpleado(),
                        emp.getTurno())) {
                    JOptionPane.showMessageDialog(panel, "Empleado actualizado en la base de datos.");
                } else {
                    JOptionPane.showMessageDialog(panel, "Error al actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                limpiarCampos(campoNombre, campoApellidos, campoDNI, campoCorreo, campoContraseña, comboTipoEmpleado, comboTurno, tabla);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(panel, "Seleccione un empleado para eliminar.");
                return;
            }
            Empleado emp = listaEmpleados.get(fila);
            if (conexion.eliminarEmpleadoBD(emp.getDni())) {
                listaEmpleados.remove(fila);
                modeloTabla.removeRow(fila);
                limpiarCampos(campoNombre, campoApellidos, campoDNI, campoCorreo, campoContraseña, comboTipoEmpleado, comboTurno, tabla);
                JOptionPane.showMessageDialog(panel, "Empleado eliminado.");
            } else {
                JOptionPane.showMessageDialog(panel, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    Empleado emp = listaEmpleados.get(fila);
                    campoNombre.setText(emp.getNombre());
                    campoApellidos.setText(emp.getApellidos());
                    campoDNI.setText(emp.getDni());
                    campoCorreo.setText(emp.getCorreo());
                    campoContraseña.setText(emp.getContraseña());
                    comboTipoEmpleado.setSelectedItem(emp.getTipoEmpleado().toString());
                    comboTurno.setSelectedItem(emp.getTurno().toString());
                }
            }
        });

        botones.add(btnAgregar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }
	private static boolean validarCampos(JTextField nombre, JTextField apellidos, JTextField dni, JTextField correo,
			JPasswordField contraseña, JComboBox<String> tipo, JComboBox<String> turno) {

		if (nombre.getText().trim().isEmpty() || apellidos.getText().trim().isEmpty() ||
	            dni.getText().trim().isEmpty() || correo.getText().trim().isEmpty() ||
	            new String(contraseña.getPassword()).trim().isEmpty() ||
	            tipo.getSelectedIndex() == 0 || turno.getSelectedIndex() == 0) {
	            JOptionPane.showMessageDialog(null, "Complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
	            return false;
	        }
		if (!correo.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			JOptionPane.showMessageDialog(null, "Correo electrónico no válido.");
			return false;
		}
		String dniTexto = dni.getText().trim();
		if (!dniTexto.matches("\\d{8}[A-Za-z]")) {
			JOptionPane.showMessageDialog(null, "El DNI debe tener 8 dígitos seguidos de una letra.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

// Validación real del DNI
		String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		int numero = Integer.parseInt(dniTexto.substring(0, 8));
		char letraCorrecta = letras.charAt(numero % 23);
		char letraIntroducida = Character.toUpperCase(dniTexto.charAt(8));

		if (letraCorrecta != letraIntroducida) {
			JOptionPane.showMessageDialog(null, "La letra del DNI no es válida.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	 private static void limpiarCampos(JTextField nombre, JTextField apellidos, JTextField dni,
             JTextField correo, JPasswordField contraseña,
             JComboBox<String> tipo, JComboBox<String> turno,
             JTable tabla) {
nombre.setText("");
apellidos.setText("");
dni.setText("");
correo.setText("");
contraseña.setText("");
tipo.setSelectedIndex(0);
turno.setSelectedIndex(0);
tabla.clearSelection();
}
}
