package Frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Backend.ConectorBD;
import Backend.Estado;
import Backend.Sala;
import Backend.TipoSala;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelSalas {

	private static ConectorBD conexion = new ConectorBD();

    public static JPanel crearPanel() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panelPrincipal.setBackground(new Color(161, 223, 182));

        JLabel titulo = new JLabel("Gestión de Salas", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // FORMULARIO
        JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));
        formulario.setBackground(new Color(161, 223, 182));

        JComboBox<TipoSala> comboTipoSala = new JComboBox<>(TipoSala.values());
        JComboBox<Estado> comboEstado = new JComboBox<>(Estado.values());
        JTextField campoCapacidad = new JTextField();

        formulario.add(new JLabel("Tipo de Sala:"));
        formulario.add(comboTipoSala);
        formulario.add(new JLabel("Estado:"));
        formulario.add(comboEstado);
        comboTipoSala.setBackground(Color.WHITE);
        comboEstado.setBackground(Color.WHITE);
        formulario.add(new JLabel("Capacidad:"));
        formulario.add(campoCapacidad);

        panelPrincipal.add(formulario, BorderLayout.WEST);

        // BOTONES
        JPanel botones = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnModificar = new JButton("Modificar");

        btnRegistrar.setBackground(new Color(102, 205, 170));
        btnModificar.setBackground(new Color(253, 253, 150));

        botones.add(btnRegistrar);
        botones.add(btnModificar);
        panelPrincipal.add(botones, BorderLayout.SOUTH);

        // TABLA
        DefaultTableModel modeloTabla = new DefaultTableModel(new String[]{"ID", "Tipo", "Estado", "Capacidad"}, 0);
        JTable tabla = new JTable(modeloTabla);
        tabla.getTableHeader().setBackground(new Color(100, 180, 100));
        tabla.setBackground(new Color(245, 255, 245));
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // MÉTODO PARA CARGAR LAS SALAS EN LA TABLA
        Runnable cargarSalas = () -> {
            modeloTabla.setRowCount(0); // Limpiar tabla
            List<Sala> salasExistentes = conexion.obtenerSalasDesdeBD();
            for (Sala sala : salasExistentes) {
                modeloTabla.addRow(new Object[]{
                        sala.getIdSala(),
                        sala.getTipoSala(),
                        sala.getEstado(),
                        sala.getCapacidad()
                });
            }
        };

        // CARGA INICIAL DE SALAS
        cargarSalas.run();

        // ACCIÓN: REGISTRAR SALA
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoSala tipo = (TipoSala) comboTipoSala.getSelectedItem();
                Estado estado = (Estado) comboEstado.getSelectedItem();
                int capacidad;

                try {
                    capacidad = Integer.parseInt(campoCapacidad.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Capacidad inválida. Debe ser un número entero.");
                    return;
                }

                Sala nuevaSala = new Sala(tipo, estado, capacidad);
                int resultado = conexion.registrarSalaBD(nuevaSala);
                if (resultado == 1) {
                    JOptionPane.showMessageDialog(null, "Sala registrada correctamente.");
                    campoCapacidad.setText("");
                    cargarSalas.run(); // recargar tabla
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la sala.");
                }
            }
        });

        // ACCIÓN: MODIFICAR SALA
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una sala de la tabla para modificar.");
                    return;
                }

                int idSala = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                TipoSala tipo = (TipoSala) comboTipoSala.getSelectedItem();
                Estado estado = (Estado) comboEstado.getSelectedItem();
                int capacidad;

                try {
                    capacidad = Integer.parseInt(campoCapacidad.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Capacidad inválida. Debe ser un número entero.");
                    return;
                }

                Sala salaModificada = new Sala(tipo, estado, capacidad);
                salaModificada.setIdSala(idSala);

                int resultado = conexion.modificarSalaBD(salaModificada);
                if (resultado == 1) {
                    JOptionPane.showMessageDialog(null, "Sala modificada correctamente.");
                    campoCapacidad.setText("");
                    cargarSalas.run(); // recargar tabla
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar la sala.");
                }
            }
        });

        return panelPrincipal;
    }
}
