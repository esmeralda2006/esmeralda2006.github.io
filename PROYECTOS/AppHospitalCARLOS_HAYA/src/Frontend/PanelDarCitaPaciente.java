package Frontend;

import javax.swing.*;

import Backend.CitaMedica;
import Backend.ConectorBD;
import Backend.Empleado;
import Backend.Paciente;
import Backend.Sala;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PanelDarCitaPaciente {
	private static ConectorBD conector = new ConectorBD();

    public static JPanel crearPanel() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(161, 223, 182));

        // Obtener datos desde BD
        List<Paciente> listaPacientes = conector.obtenerTodosLosPacientes();
        List<Empleado> listaMedicos = conector.obtenerTodosMedicos();
        List<Sala> listaSalas = conector.obtenerSalasDesdeBD();
        List<CitaMedica> listaCitas = conector.obtenerTodasLasCitas();

        // FORMULARIO
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Dar Cita Médica"));
        panelFormulario.setBackground(new Color(161, 223, 182));

        JComboBox<Paciente> comboPacientes = new JComboBox<>(listaPacientes.toArray(new Paciente[0]));
        JComboBox<Empleado> comboMedicos = new JComboBox<>(listaMedicos.toArray(new Empleado[0]));
        JComboBox<Sala> comboSalas = new JComboBox<>(listaSalas.toArray(new Sala[0]));

        JTextField campoFecha = new JTextField();
        JTextField campoHora = new JTextField();
        JButton btnDarCita = new JButton("Dar Cita");
        btnDarCita.setBackground(new Color(255, 182, 193));

        comboPacientes.setBackground(Color.WHITE);
        comboMedicos.setBackground(Color.WHITE);
        comboSalas.setBackground(Color.WHITE);
        campoFecha.setBackground(Color.WHITE);
        campoHora.setBackground(Color.WHITE);

        panelFormulario.add(new JLabel("Seleccionar Paciente:"));
        panelFormulario.add(comboPacientes);
        panelFormulario.add(new JLabel("Seleccionar Médico:"));
        panelFormulario.add(comboMedicos);
        panelFormulario.add(new JLabel("Seleccionar Sala:"));
        panelFormulario.add(comboSalas);
        panelFormulario.add(new JLabel("Fecha (yyyy-mm-dd):"));
        panelFormulario.add(campoFecha);
        panelFormulario.add(new JLabel("Hora (HH:mm):"));
        panelFormulario.add(campoHora);

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);

        // HISTORIAL
        JTextArea areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        areaHistorial.setBorder(BorderFactory.createTitledBorder("Historial de Citas"));
        JScrollPane scroll = new JScrollPane(areaHistorial);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        actualizarHistorialCompleta(areaHistorial, listaCitas);

        // ACCIÓN BOTÓN
        btnDarCita.addActionListener(e -> {
            Paciente paciente = (Paciente) comboPacientes.getSelectedItem();
            Empleado medico = (Empleado) comboMedicos.getSelectedItem();
            Sala sala = (Sala) comboSalas.getSelectedItem();
            String fechaStr = campoFecha.getText().trim();
            String horaStr = campoHora.getText().trim();

            if (paciente == null || medico == null || sala == null || fechaStr.isEmpty() || horaStr.isEmpty()) {
                JOptionPane.showMessageDialog(panelPrincipal, "Complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate fecha = LocalDate.parse(fechaStr);
                LocalTime hora = LocalTime.parse(horaStr);

                CitaMedica nuevaCita = new CitaMedica(fecha, hora, paciente, medico, sala);

                boolean exito = conector.guardarCitaMedica(nuevaCita);
                if (exito) {
                    listaCitas.add(nuevaCita);
                    actualizarHistorialCompleta(areaHistorial, listaCitas);
                    limpiarCampos(campoFecha, campoHora);
                    JOptionPane.showMessageDialog(panelPrincipal, "Cita guardada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, "Error al guardar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelPrincipal, "Formato de fecha o hora incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelPrincipal.add(btnDarCita, BorderLayout.SOUTH);

        return panelPrincipal;
    }

    private static void actualizarHistorialCompleta(JTextArea areaHistorial, List<CitaMedica> citas) {
        areaHistorial.setText("");
        for (CitaMedica cita : citas) {
            String texto = String.format("Paciente: %s | Médico: %s | Sala: %s | Fecha: %s | Hora: %s",
                cita.getPaciente().getNombre(),
                cita.getMedico().getNombre(),
                cita.getSala() != null ? cita.getSala().getTipoSala() : "N/A",
                cita.getFecha(),
                cita.getHora());
            areaHistorial.append(texto + "\n");
        }
    }

    private static void limpiarCampos(JTextField campoFecha, JTextField campoHora) {
        campoFecha.setText("");
        campoHora.setText("");
    }
}
