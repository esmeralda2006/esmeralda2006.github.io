package Frontend;

import javax.swing.*;

import Backend.ConectorBD;
import Backend.Medico;
import Backend.Paciente;
import Backend.SolicitudesAlta;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanelMarcarAltaMedica {
	private static ConectorBD conexion=new ConectorBD();

	
	public static JPanel crearPanel() {
	    JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
	    panelPrincipal.setBackground(new Color(200, 230, 201));

	    JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 10));
	    panelFormulario.setBackground(new Color(200, 230, 201));
	    panelFormulario.setBorder(BorderFactory.createTitledBorder("Solicitar Alta para Pacientes"));

	    JComboBox<String> comboPacientes = new JComboBox<>();
	    cargarPacientesSinSolicitud(comboPacientes);  // Aquí carga una vez

	    JButton btnSolicitarAlta = new JButton("Solicitar Alta");
	    comboPacientes.setBackground(Color.WHITE);
	    btnSolicitarAlta.setBackground(new Color(255, 255, 153));

	    panelFormulario.add(new JLabel("Paciente sin solicitud:"));
	    panelFormulario.add(comboPacientes);
	    panelFormulario.add(new JLabel());
	    panelFormulario.add(btnSolicitarAlta);

	    btnSolicitarAlta.addActionListener(e -> {
	        String seleccionado = (String) comboPacientes.getSelectedItem();
	        if (seleccionado == null) {
	            JOptionPane.showMessageDialog(panelPrincipal, "Seleccione un paciente.");
	            return;
	        }

	        int idPaciente = Integer.parseInt(seleccionado.split(" - ")[0].trim());
	        boolean exito = conexion.solicitarAltaParaPaciente(idPaciente);
	        if (exito) {
	            JOptionPane.showMessageDialog(panelPrincipal, "Solicitud de alta registrada correctamente.");
	        } else {
	            JOptionPane.showMessageDialog(panelPrincipal, "No se pudo solicitar el alta.");
	        }

	        cargarPacientesSinSolicitud(comboPacientes);  // Recarga para actualizar combo
	    });

	    panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
	    return panelPrincipal;
	}

	private static void cargarPacientesSinSolicitud(JComboBox<String> combo) {
	    combo.removeAllItems();
	    List<Paciente> lista = conexion.obtenerPacientesSinSolicitudAlta();
	    for (Paciente p : lista) {
	        combo.addItem(p.getIdPaciente() + " - " + p.getNombre() + " " + p.getApellidos());
	        System.out.println("Cargando paciente en combo: " + p);
	    }
	
	}
}

	

