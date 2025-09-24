package Frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Backend.ConectorBD;
import Backend.Estado;
import Backend.Sala;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanelDisponibilidad {
	 private static ConectorBD conexion = new ConectorBD();
		// Variable para almacenar salas cargadas desde BD
	    private static List<Sala> salas;

	    public static JPanel crearPanel() {
	        JPanel panel = new JPanel(new BorderLayout());

	        // Cargar salas desde base de datos
	        salas = conexion.obtenerSalasDesdeBD();

	        DefaultTableModel modeloTabla = new DefaultTableModel(
	            new Object[] { "ID Sala", "Tipo Sala", "Estado", "Capacidad" }, 0);

	        JTable tablaSalas = new JTable(modeloTabla);
	        tablaSalas.getTableHeader().setBackground(new Color(100, 180, 100));
	        tablaSalas.setBackground(new Color(245, 255, 245));

	        JScrollPane scrollPane = new JScrollPane(tablaSalas);

	        JButton cambiarEstado = new JButton("Cambiar Estado");
	        cambiarEstado.setBackground(new Color(253, 253, 150));

	        // Cargar datos iniciales en la tabla
	        cargarTabla(modeloTabla, salas);

	        // Acción para cambiar estado y actualizar BD
	        cambiarEstado.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                int fila = tablaSalas.getSelectedRow();
	                if (fila != -1) {
	                    String[] estadosDisponibles = { "DISPONIBLE", "OCUPADA", "MANTENIMIENTO" };
	                    String estadoActual = modeloTabla.getValueAt(fila, 2).toString().toUpperCase();

	                    String nuevoEstadoStr = (String) JOptionPane.showInputDialog(
	                        panel,
	                        "Seleccione el nuevo estado:",
	                        "Cambiar Estado de Sala",
	                        JOptionPane.PLAIN_MESSAGE,
	                        null,
	                        estadosDisponibles,
	                        estadoActual
	                    );

	                    if (nuevoEstadoStr != null && !nuevoEstadoStr.equals(estadoActual)) {
	                        int idSala = (int) modeloTabla.getValueAt(fila, 0);

	                        try {
	                            Estado nuevoEstado = Estado.valueOf(nuevoEstadoStr);
	                            int filasActualizadas = conexion.actualizarEstadoSalaBD(idSala, nuevoEstado);

	                            if (filasActualizadas == 1) {
	                                // Actualizar en la lista local para que refleje en la tabla
	                                for (Sala sala : salas) {
	                                    if (sala.getIdSala() == idSala) {
	                                        sala.setEstado(nuevoEstado);
	                                        break;
	                                    }
	                                }
	                                cargarTabla(modeloTabla, salas);
	                                JOptionPane.showMessageDialog(panel, "Estado actualizado correctamente.");
	                            } else {
	                                JOptionPane.showMessageDialog(panel, "No se encontró la sala con ID: " + idSala);
	                            }
	                        } catch (IllegalArgumentException ex) {
	                            JOptionPane.showMessageDialog(panel, "Estado inválido.");
	                        }
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(panel, "Selecciona una sala primero.");
	                }
	            }
	        });

	        panel.add(scrollPane, BorderLayout.CENTER);
	        panel.add(cambiarEstado, BorderLayout.SOUTH);

	        return panel;
	    }

	    private static void cargarTabla(DefaultTableModel modelo, List<Sala> salas) {
	        modelo.setRowCount(0); // limpiar tabla
	        for (Sala sala : salas) {
	            modelo.addRow(new Object[] {
	                sala.getIdSala(),
	                sala.getTipoSala().name(),
	                sala.getEstado().name(),
	                sala.getCapacidad()
	            });
	        }
	    }

}
