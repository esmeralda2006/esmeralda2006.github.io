package Frontend;

import javax.swing.*;

import Backend.ConectorBD;
import Backend.Sala;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanelConsultarDisponibilidadSalas {
	
	private static ConectorBD conector = new ConectorBD();

    public static JPanel crearPanel() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(161, 223, 182));

        // Obtener la lista completa de salas
        List<Sala> listaSalas = conector.obtenerSalasDesdeBD();

        // Obtener solo los IDs de las salas (como Strings)
        List<String> listaIds = conector.obtenerSalasConId();

        // COMPONENTES
        JComboBox<String> comboSalas = new JComboBox<>(listaIds.toArray(new String[0]));
        JButton btnConsultar = new JButton("Consultar Disponibilidad");
        JTextArea areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setBorder(BorderFactory.createTitledBorder("Resultado de Disponibilidad"));
        areaResultado.setBackground(Color.WHITE);
        comboSalas.setBackground(Color.WHITE);
        btnConsultar.setBackground(new Color(253, 253, 150));

        // FORMULARIO
        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Consultar Disponibilidad de Salas"));
        panelFormulario.add(new JLabel("Seleccionar Sala (ID):"));
        panelFormulario.add(comboSalas);
        panelFormulario.add(new JLabel());
        panelFormulario.add(btnConsultar);
        panelFormulario.setBackground(new Color(161, 223, 182));

        // ACCIÓN DEL BOTÓN
        btnConsultar.addActionListener((ActionEvent e) -> {
            String idSeleccionadoStr = (String) comboSalas.getSelectedItem();
            if (idSeleccionadoStr != null) {
                int idSeleccionado = Integer.parseInt(idSeleccionadoStr);
                // Buscar la sala en la lista
                Sala salaSeleccionada = null;
                for (Sala s : listaSalas) {
                    if (s.getIdSala() == idSeleccionado) {
                        salaSeleccionada = s;
                        break;
                    }
                }

                if (salaSeleccionada != null) {
                    String mensaje = String.format(
                            "Sala %d (%s) está %s\nCapacidad: %d",
                            salaSeleccionada.getIdSala(),
                            salaSeleccionada.getTipoSala(),
                            salaSeleccionada.getEstado(),
                            salaSeleccionada.getCapacidad()
                    );
                    areaResultado.setText(mensaje);
                } else {
                    areaResultado.setText("No se encontró la sala con ID " + idSeleccionado);
                }
            } else {
                areaResultado.setText("Selecciona un ID válido.");
            }
        });

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(areaResultado);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        return panelPrincipal;
    }
}
