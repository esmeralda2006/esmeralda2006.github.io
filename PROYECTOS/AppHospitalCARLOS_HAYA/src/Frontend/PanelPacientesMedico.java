package Frontend;

import javax.swing.*;

import Backend.ConectorBD;
import Backend.Paciente;
import java.util.List;

import java.awt.*;

public class PanelPacientesMedico {
	private static ConectorBD conector=new ConectorBD();

	 // Método que devuelve un panel con la lista real de pacientes asignados
    public static JPanel crearPanel(int idMedico) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(161,223,182));

        JLabel titulo = new JLabel("Mis Pacientes Asignados", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(titulo, BorderLayout.NORTH);

        // Obtener la lista real de pacientes desde la base de datos
      
        List<Paciente> listaPacientes = conector.verPacientesAsignadosAMedico(idMedico);

        // Crear array de Strings para mostrar en el JList
        String[] nombresPacientes = new String[listaPacientes.size()];
        for (int i = 0; i < listaPacientes.size(); i++) {
            Paciente p = listaPacientes.get(i);
            nombresPacientes[i] = p.getNombre() + " " + p.getApellidos();
        }

        JList<String> listaPacientesUI = new JList<>(nombresPacientes);
        listaPacientesUI.setBorder(BorderFactory.createTitledBorder("Pacientes"));

        panel.add(new JScrollPane(listaPacientesUI), BorderLayout.CENTER);

        return panel;
    }
}

