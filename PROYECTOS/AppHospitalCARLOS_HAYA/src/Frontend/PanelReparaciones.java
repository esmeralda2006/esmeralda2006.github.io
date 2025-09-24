package Frontend;

import javax.swing.*;

import Backend.ConectorBD;
import Backend.TareaMantenimiento;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanelReparaciones {
	
	// Suponiendo que tienes esta conexión estática
    private static ConectorBD conexion = new ConectorBD();

    // Método que devuelve un panel
    public static JPanel crearPanel() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(161, 223, 182));

        // Obtener salas desde BD para llenar el combo
        List<String> salasRegistradas = conexion.obtenerSalasConId();
        JComboBox<String> comboSalas = new JComboBox<>(salasRegistradas.toArray(new String[0]));

        JTextField txtFecha = new JTextField();
        JTextArea txtDescripcion = new JTextArea(3, 20);
        JButton btnRegistrar = new JButton("Registrar");

        comboSalas.setBackground(Color.WHITE);
        txtFecha.setBackground(Color.WHITE);
        txtDescripcion.setBackground(Color.WHITE);
        btnRegistrar.setBackground(new Color(253, 253, 150));

        // Formulario
        JPanel formulario = new JPanel(new GridLayout(5, 2, 10, 10));
        formulario.setBorder(BorderFactory.createTitledBorder("Registrar Tarea de Mantenimiento"));
        formulario.setBackground(new Color(161, 223, 182));

        formulario.add(new JLabel("Sala:"));
        formulario.add(comboSalas);

        formulario.add(new JLabel("Fecha (YYYY-MM-DD):"));
        formulario.add(txtFecha);

        formulario.add(new JLabel("Descripción:"));
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        formulario.add(scrollDescripcion);

        // Campo para DNI del responsable (añadido en el formulario)
        JTextField txtDniResponsable = new JTextField();
        txtDniResponsable.setBackground(Color.WHITE);
        formulario.add(new JLabel("DNI Responsable:"));
        formulario.add(txtDniResponsable);

        formulario.add(new JLabel()); // espacio vacío
        formulario.add(btnRegistrar);

        panelPrincipal.add(formulario, BorderLayout.NORTH);

        // Historial
        JTextArea areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        areaHistorial.setBorder(BorderFactory.createTitledBorder("Historial de Tareas"));
        JScrollPane scrollHistorial = new JScrollPane(areaHistorial);
        panelPrincipal.add(scrollHistorial, BorderLayout.CENTER);

        // Carga inicial del historial al abrir el panel
        actualizarHistorial(areaHistorial);

        // Acción del botón Registrar
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sala = (String) comboSalas.getSelectedItem();
                String fecha = txtFecha.getText().trim();
                String descripcion = txtDescripcion.getText().trim();
                String responsableDni = txtDniResponsable.getText().trim();

                if (sala == null || sala.isEmpty() || fecha.isEmpty() || descripcion.isEmpty() || responsableDni.isEmpty()) {
                    JOptionPane.showMessageDialog(panelPrincipal, "Todos los campos deben estar completos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear objeto tarea
                TareaMantenimiento tarea = new TareaMantenimiento(descripcion, fecha, responsableDni, sala);

                // Insertar en base de datos
                boolean insertada = conexion.insertarTarea(tarea);

                if (insertada) {
                    JOptionPane.showMessageDialog(panelPrincipal, "Tarea registrada correctamente.");
                    // Limpiar campos
                    txtFecha.setText("");
                    txtDescripcion.setText("");
                    txtDniResponsable.setText("");
                    // Actualizar historial
                    actualizarHistorial(areaHistorial);
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, "Error al registrar la tarea.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panelPrincipal;
    }

    // Método para actualizar el historial de tareas en el JTextArea
    private static void actualizarHistorial(JTextArea areaHistorial) {
        List<TareaMantenimiento> historial = conexion.obtenerHistorialTareas();
        areaHistorial.setText("");
        if (historial == null || historial.isEmpty()) {
            areaHistorial.append("No hay tareas registradas.\n");
        } else {
            for (TareaMantenimiento tarea : historial) {
                areaHistorial.append(tarea.toString() + "\n");
            }
        }
    }
}
