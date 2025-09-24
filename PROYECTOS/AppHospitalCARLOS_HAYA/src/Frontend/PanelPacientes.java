package Frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Backend.ConectorBD;
import Backend.Paciente;
import Backend.Sala;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelPacientes {

	  public static JPanel crearPanel() {
	        ConectorBD conector = new ConectorBD();
	        JPanel panel = new JPanel(new BorderLayout(10, 10));
	        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
	        panel.setBackground(new Color(161, 223, 182));

	     // Panel superior que contiene el título y el panel de búsqueda
	        JPanel panelSuperior = new JPanel(new BorderLayout());
	        panelSuperior.setBackground(new Color(161, 223, 182));

	        // Título
	        JLabel titulo = new JLabel("Gestión de Pacientes", JLabel.CENTER);
	        titulo.setFont(new Font("Arial", Font.BOLD, 22));
	        panelSuperior.add(titulo, BorderLayout.NORTH);

	        // Panel de búsqueda (ya existente)
	        JPanel panelBusqueda = new JPanel(new FlowLayout());
	        panelBusqueda.setBackground(new Color(161, 223, 182));
	        JTextField campoBusqueda = new JTextField(10);
	        JButton btnBuscar = new JButton("Buscar");
	        btnBuscar.setBackground(new Color(173,216,230));
	        JButton btnMostrar = new JButton("Mostrar Todos");
	        btnMostrar.setBackground(new Color(200, 255, 200));
	        panelBusqueda.add(new JLabel("ID:"));
	        panelBusqueda.add(campoBusqueda);
	        panelBusqueda.add(btnBuscar);
	        panelBusqueda.add(btnMostrar);

	        // Añade búsqueda debajo del título
	        panelSuperior.add(panelBusqueda, BorderLayout.SOUTH);

	        // Añade todo el panel superior al principal
	        panel.add(panelSuperior, BorderLayout.NORTH);

	        JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
	        form.setBackground(new Color(161, 223, 182));

	        JTextField campoID = new JTextField();
	        campoID.setEditable(false);
	        JTextField nombre = new JTextField(), apellidos = new JTextField(), dni = new JTextField();
	        JTextField contacto = new JTextField(), obra = new JTextField();
	        JComboBox<Sala> comboHab = new JComboBox<>();
	        JTextField fechaAlta = new JTextField();
	        fechaAlta.setEditable(false);
	        campoID.setBackground(Color.WHITE); // color blanco para campoID
	        comboHab.setBackground(Color.WHITE);
	        fechaAlta.setBackground(Color.WHITE);

	        comboHab.addItem(null);
	        List<Sala> habitacionesDisponibles = conector.obtenerHabitacionesDisponibles();
	        for (Sala sala : habitacionesDisponibles) {
	            comboHab.addItem(sala);
	        }

	        comboHab.setRenderer(new DefaultListCellRenderer() {
	            @Override
	            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	                if (value == null) {
	                    setText("No asignada");
	                } else if (value instanceof Sala) {
	                    Sala sala = (Sala) value;
	                    setText("Sala #" + sala.getIdSala());
	                }
	                return this;
	            }
	        });

	        form.add(new JLabel("ID:")); form.add(campoID);
	        form.add(new JLabel("Nombre:")); form.add(nombre);
	        form.add(new JLabel("Apellidos:")); form.add(apellidos);
	        form.add(new JLabel("DNI:")); form.add(dni);
	        form.add(new JLabel("Contacto:")); form.add(contacto);
	        form.add(new JLabel("Obra Social:")); form.add(obra);
	        form.add(new JLabel("Habitación:")); form.add(comboHab);
	        form.add(new JLabel("Fecha Alta:")); form.add(fechaAlta);
	        panel.add(form, BorderLayout.WEST);

	        DefaultTableModel model = new DefaultTableModel(
	                new String[]{"ID", "Nombre", "Apellidos", "DNI", "Contacto", "Obra", "Habitación", "Fecha Alta"}, 0
	        );
	        JTable tabla = new JTable(model);
	        tabla.getTableHeader().setBackground(new Color(100,180,100));
	        tabla.setBackground(new Color(245,255,245));
	        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

	        JPanel bot = new JPanel(new GridLayout(1, 3, 10, 10));
	        JButton btnReg = new JButton("Registrar"), btnMod = new JButton("Modificar"), btnElim = new JButton("Eliminar");
	        btnReg.setBackground(new Color(102,205,170));
	        btnMod.setBackground(new Color(253,253,150));
	        btnElim.setBackground(new Color(240,128,128));
	        
	        bot.add(btnReg); bot.add(btnMod); bot.add(btnElim);
	        panel.add(bot, BorderLayout.SOUTH);

	        btnReg.addActionListener(e -> {
	            if (validar(nombre, apellidos, dni, contacto, obra)) {
	                Sala salaSeleccionada = (Sala) comboHab.getSelectedItem();
	                LocalDate hoy = LocalDate.now();
	                Paciente p = new Paciente(
	                        nombre.getText(), apellidos.getText(), dni.getText(),
	                        contacto.getText(), obra.getText(),
	                        salaSeleccionada, hoy, false
	                );
	                if (conector.insertarPacienteBD(p)) {
	                    model.addRow(new Object[]{
	                            p.getIdPaciente(), p.getNombre(), p.getApellidos(), p.getDni(),
	                            p.getContacto(), p.getObraSocial(),
	                            salaSeleccionada != null ? "Sala #" + salaSeleccionada.getIdSala() : "No asignada",
	                            hoy.toString()
	                    });
	                    JOptionPane.showMessageDialog(panel, "Paciente registrado exitosamente.");
	                    limpiar(campoID, nombre, apellidos, dni, contacto, obra, fechaAlta);
	                    comboHab.setSelectedIndex(0);
	                } else {
	                    JOptionPane.showMessageDialog(panel, "Error al registrar el paciente.");
	                }
	            }
	        });

	        btnMod.addActionListener(e -> {
	            int f = tabla.getSelectedRow();
	            if (f < 0) {
	                JOptionPane.showMessageDialog(panel, "Seleccione una fila.");
	                return;
	            }
	            if (validar(nombre, apellidos, dni, contacto, obra)) {
	                int id = Integer.parseInt(campoID.getText());
	                Sala salaSeleccionada = (Sala) comboHab.getSelectedItem();
	                LocalDate fecha = LocalDate.parse(fechaAlta.getText());
	                Paciente p = new Paciente(id, nombre.getText(), apellidos.getText(), dni.getText(),
	                        contacto.getText(), obra.getText(), salaSeleccionada, fecha, false);
	                if (conector.modificarPacienteBD(p)) {
	                    model.setValueAt(p.getNombre(), f, 1);
	                    model.setValueAt(p.getApellidos(), f, 2);
	                    model.setValueAt(p.getDni(), f, 3);
	                    model.setValueAt(p.getContacto(), f, 4);
	                    model.setValueAt(p.getObraSocial(), f, 5);
	                    model.setValueAt(salaSeleccionada != null ? "Sala #" + salaSeleccionada.getIdSala() : "No asignada", f, 6);
	                    JOptionPane.showMessageDialog(panel, "Paciente modificado.");
	                    limpiar(campoID, nombre, apellidos, dni, contacto, obra, fechaAlta);
	                    comboHab.setSelectedIndex(0);
	                }
	            }
	        });

	        btnElim.addActionListener(e -> {
	            int f = tabla.getSelectedRow();
	            if (f < 0) {
	                JOptionPane.showMessageDialog(panel, "Seleccione una fila.");
	                return;
	            }
	            int id = Integer.parseInt(model.getValueAt(f, 0).toString());
	            if (conector.eliminarPacientePorID(id)) {
	                model.removeRow(f);
	                JOptionPane.showMessageDialog(panel, "Paciente eliminado.");
	                limpiar(campoID, nombre, apellidos, dni, contacto, obra, fechaAlta);
	                comboHab.setSelectedIndex(0);
	            }
	        });

	        tabla.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	                int f = tabla.getSelectedRow();
	                if (f >= 0) {
	                    campoID.setText(model.getValueAt(f, 0).toString());
	                    nombre.setText(model.getValueAt(f, 1).toString());
	                    apellidos.setText(model.getValueAt(f, 2).toString());
	                    dni.setText(model.getValueAt(f, 3).toString());
	                    contacto.setText(model.getValueAt(f, 4).toString());
	                    obra.setText(model.getValueAt(f, 5).toString());
	                    String habTexto = model.getValueAt(f, 6).toString();
	                    if (habTexto.equals("No asignada")) {
	                        comboHab.setSelectedIndex(0);
	                    } else {
	                        int idSala = Integer.parseInt(habTexto.replace("Sala #", ""));
	                        for (int i = 0; i < comboHab.getItemCount(); i++) {
	                            Sala s = comboHab.getItemAt(i);
	                            if (s != null && s.getIdSala() == idSala) {
	                                comboHab.setSelectedIndex(i);
	                                break;
	                            }
	                        }
	                    }
	                    fechaAlta.setText(model.getValueAt(f, 7).toString());
	                }
	            }
	        });

	        btnMostrar.addActionListener(e -> {
	            model.setRowCount(0); // Limpia la tabla
	            for (Paciente p : conector.obtenerTodosLosPacientes()) {
	                model.addRow(new Object[]{
	                        p.getIdPaciente(),
	                        p.getNombre(),
	                        p.getApellidos(),
	                        p.getDni(),
	                        p.getContacto(),
	                        p.getObraSocial(),
	                        p.getHabitacionAsignada() != null ? "Sala #" + p.getHabitacionAsignada().getIdSala() : "No asignada",
	                        p.getFechaAlta() != null ? p.getFechaAlta().toString() : "Nulo"
	                });
	            }
	        });

	        btnBuscar.addActionListener(e -> {
	            String texto = campoBusqueda.getText().trim();
	            if (!texto.matches("\\d+")) {
	                JOptionPane.showMessageDialog(panel, "Ingrese ID numérico.");
	                return;
	            }
	            Paciente p = conector.buscarPacientePorId(Integer.parseInt(texto));
	            model.setRowCount(0);
	            if (p != null) {
	                model.addRow(new Object[]{
	                        p.getIdPaciente(), p.getNombre(), p.getApellidos(), p.getDni(),
	                        p.getContacto(), p.getObraSocial(),
	                        p.getHabitacionAsignada() != null ? "Sala #" + p.getHabitacionAsignada().getIdSala() : "No asignada",
	                        p.getFechaAlta() != null ? p.getFechaAlta().toString() : "Nulo"
	                });
	            } else {
	                JOptionPane.showMessageDialog(panel, "Paciente no encontrado.");
	            }
	        });

	        btnMostrar.doClick();
	        return panel;
	    }
	  private static boolean esDNIValido(String dni) {
		    if (!dni.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
		        return false; // Debe tener 8 dígitos y una letra válida (sin I, Ñ, O, U)
		    }

		    // Validar letra del DNI
		    String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		    int numero = Integer.parseInt(dni.substring(0, 8));
		    char letraEsperada = letras.charAt(numero % 23);
		    char letraIngresada = dni.charAt(8);

		    return letraEsperada == letraIngresada;
		}

	  private static boolean validar(JTextField... campos) {
		    for (JTextField c : campos) {
		        if (c.getText().trim().isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Complete todos los campos.");
		            return false;
		        }
		    }

		    // Validación de DNI
		    String dniTexto = campos[2].getText().trim(); // campos[2] es DNI
		    if (!esDNIValido(dniTexto)) {
		        JOptionPane.showMessageDialog(null, "DNI inválido. Debe tener 8 dígitos seguidos de una letra correcta (ej: 12345678Z).");
		        return false;
		    }

		    return true;
		}

	    private static void limpiar(JTextField... campos) {
	        for (JTextField c : campos) c.setText("");
	    }}
	

