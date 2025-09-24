package Backend;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ConectorBD {

	private static Connection conn = null;

	public ConectorBD() {
		try {
			// Cargar el driver JDBC para MySQL desde JDBC 6 no es necesario escribir este
			// bloque de código
			// pero al hacerlo podemos incluir información en el caso de fallo.
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver no instalado correctamente");
		}

		// Datos de conexión a la base de datos,
		// El puerto, el nombre de la base de datos "tema9", el login y el password
		// pueden cambiar
		// dependiendo de tu configuración
		String url = "jdbc:mysql://localhost:3306/hospital";
		String login = "root";
		String password = "";

		try {
			// Establecer la conexión
			conn = DriverManager.getConnection(url, login, password);
			if (conn != null) {
				System.out.println("Conexión a base de datos Hospital lista.");
			}
		} catch (SQLException e) {
			System.out.println("Error al conectar o ejecutar la consulta: " + e.getMessage());
		}
	}

	public void cerrarConexion() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				System.out.println("Conexión cerrada.");
			}
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexión: " + e.getMessage());
		}
	}

	public static Connection getConn() {
		return conn;
	}

	public Empleado inicioSesion(String dni, String pass, TipoEmpleado tipo) {
		Empleado emp = null;

		if (conn != null) {
			String sql = "SELECT * FROM empleado WHERE dni='" + dni + "' and contraseña='" + pass
					+ "' and tipoEmpleado='" + tipo.toString() + "' ";
			try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
				if (rs.next()) {

					int id = rs.getInt("idEmpleado");
					String nombre = rs.getString("nombre");
					String apellidos = rs.getString("apellidos");
					String correo = rs.getString("correo");
					Turno turno = Turno.valueOf(rs.getString("turno"));

					emp = new Empleado(id, nombre, apellidos, dni, correo, pass, tipo, turno);
					return emp;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	// REGISTRAR SALA -EMP ADMINISTRADOR PANEL SALAS
	public int registrarSalaBD(Sala sala) {
		int resultado = 0;
		if (conn != null) {
			String sql = "INSERT INTO sala (tipoSala, estado, capacidad) VALUES (?, ?, ?)";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, sala.getTipoSala().name());
				ps.setString(2, sala.getEstado().name());
				ps.setInt(3, sala.getCapacidad());
				resultado = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	// MODIFICAR SALA -EMP ADMINISTRADOR PANEL SALAS
	public int modificarSalaBD(Sala sala) {
		int resultado = 0;
		if (conn != null) {
			String sql = "UPDATE sala SET tipoSala = ?, estado = ?, capacidad = ? WHERE idSala = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, sala.getTipoSala().name());
				ps.setString(2, sala.getEstado().name());
				ps.setInt(3, sala.getCapacidad());
				ps.setInt(4, sala.getIdSala());
				resultado = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	// EMPLEADO DE MANTENIMIENTO REGISTRA TAREA DE MANTENIMIENTO PANEL REPARACIONES
	public boolean insertarTarea(TareaMantenimiento tarea) {
		String sql = "INSERT INTO tareamantenimiento (descripcion, fecha, responsableDni, salaAfectada) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, tarea.getDescripcion());
			stmt.setString(2, tarea.getFecha());
			stmt.setString(3, tarea.getResponsableDni());
			stmt.setString(4, tarea.getSalaAfectada());

			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Error al insertar la tarea: " + e.getMessage());
			return false;
		}
	}

	// OBTIENE ID DE LA SALAS REGISTRADAS EN LA BD PARA SELECCIONAR UNA SALA AL
	// REGISTRAR TAREA DE MANTENIMEINTO
	public List<String> obtenerSalasConId() {
		List<String> salas = new ArrayList<>();
		if (conn != null) {
			String sql = "SELECT idSala FROM sala";
			try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					int id = rs.getInt("idSala");
					salas.add(String.valueOf(id));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return salas;
	}

	// MOSTRAR HISTORIAL TAREAS EN PANEL REPARACIONES DE EMP MANTENIMIENTO
	public List<TareaMantenimiento> obtenerHistorialTareas() {
		List<TareaMantenimiento> tareas = new ArrayList<>();
		String sql = "SELECT * FROM tareamantenimiento ORDER BY fecha DESC";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				int id = rs.getInt("idTarea");
				String descripcion = rs.getString("descripcion");
				String fecha = rs.getString("fecha");
				String responsableDni = rs.getString("responsableDni");
				String sala = rs.getString("salaAfectada");

				TareaMantenimiento tarea = new TareaMantenimiento(id, descripcion, fecha, responsableDni, sala);
				tareas.add(tarea);
			}
		} catch (SQLException e) {
			System.err.println("Error al obtener historial de tareas: " + e.getMessage());
		}

		return tareas;
	}

	// MOSTRAR SALAS EN PANEL DISPONIBILIDAD PARA ACTUALIZAR ESTADO---EMP
	// MANTENIMIENTO
	public List<Sala> obtenerSalasDesdeBD() {
		List<Sala> listaSalas = new ArrayList<>();
		if (conn != null) {
			String sql = "SELECT idSala, tipoSala, estado, capacidad FROM sala";
			try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int idSala = rs.getInt("idSala");
					String tipoSalaStr = rs.getString("tipoSala");
					String estadoStr = rs.getString("estado");
					int capacidad = rs.getInt("capacidad");

					// Convertir String a Enum
					TipoSala tipoSala = TipoSala.valueOf(tipoSalaStr);
					Estado estado = Estado.valueOf(estadoStr);

					Sala sala = new Sala(idSala, tipoSala, estado, capacidad);
					listaSalas.add(sala);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaSalas;
	}

	// ACTUALIZAR ESTADO SALA DE PANEL DISPONIBILIDAD ---EMPLEADO MANTENIMIENTO
	public int actualizarEstadoSalaBD(int idSala, Estado nuevoEstado) {
		int filasActualizadas = 0;

		if (conn != null) {
			try {
				String sql = "UPDATE sala SET estado = ? WHERE idSala = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, nuevoEstado.name());
				ps.setInt(2, idSala);
				filasActualizadas = ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return filasActualizadas;
	}

	// METODO INSERTAR PACIENTE EN BD --POR ADMINISTRADOR PANEL PACIENTES
	public boolean insertarPacienteBD(Paciente p) {
		String sql = "INSERT INTO paciente (nombre, apellidos, dni, contacto, obraSocial,habitacionAsignada,fechaAlta,altaSolicitada) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, p.getNombre());
			ps.setString(2, p.getApellidos());
			ps.setString(3, p.getDni());
			ps.setString(4, p.getContacto());
			ps.setString(5, p.getObraSocial());
			ps.setObject(6, p.getHabitacionAsignada() != null ? p.getHabitacionAsignada().getIdSala() : null);
			ps.setDate(7, java.sql.Date.valueOf(p.getFechaAlta()));
			ps.setInt(8, 1);

			int filasAfectadas = ps.executeUpdate();

			// Obtener ID generado
			if (filasAfectadas > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						p.setIdPaciente(rs.getInt(1));
					}
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// METODO MODIFICAR PACIENTE EN BD --POR ADMINISTRADOR PANEL PACIENTES
	public boolean modificarPacienteBD(Paciente paciente) {
		String sql = "UPDATE paciente SET nombre = ?, apellidos = ?, dni = ?, contacto = ?, obraSocial = ?, habitacionAsignada = ?, fechaAlta = ?, altaSolicitada = ? WHERE idPaciente = ?";
		try (PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setString(1, paciente.getNombre());
			pst.setString(2, paciente.getApellidos());
			pst.setString(3, paciente.getDni());
			pst.setString(4, paciente.getContacto());
			pst.setString(5, paciente.getObraSocial());

			if (paciente.getHabitacionAsignada() != null)
				pst.setInt(6, paciente.getHabitacionAsignada().getIdSala());
			else
				pst.setNull(6, Types.INTEGER);

			if (paciente.getFechaAlta() != null)
				pst.setDate(7, java.sql.Date.valueOf(paciente.getFechaAlta()));
			else
				pst.setNull(7, Types.DATE);

			pst.setBoolean(8, paciente.isAltaSolicitada());

			pst.setInt(9, paciente.getIdPaciente());

			return pst.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// METODO ELIMINAR PACIENTE EN BD --POR ADMINISTRADOR PANEL PACIENTES
	public boolean eliminarPacientePorID(int idPaciente) {
		try (PreparedStatement pst = conn.prepareStatement("DELETE FROM paciente WHERE idPaciente = ?")) {
			pst.setInt(1, idPaciente);
			return pst.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// METODO BUSCAR PACIENTE POR ID EN BD --POR ADMINISTRADOR PANEL PACIENTES
	public Paciente buscarPacientePorId(int idPaciente) {
		String sql = "SELECT * FROM paciente WHERE idPaciente = ?";
		try (PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, idPaciente);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				Integer habitacionId = rs.getObject("habitacionAsignada") != null ? rs.getInt("habitacionAsignada")
						: null;
				Sala sala = habitacionId != null ? new Sala(habitacionId) : null;

				return new Paciente(rs.getInt("idPaciente"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("dni"), rs.getString("contacto"), rs.getString("obraSocial"), sala,
						rs.getDate("fechaAlta") != null ? rs.getDate("fechaAlta").toLocalDate() : null,
						rs.getBoolean("altaSolicitada"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Paciente buscarPacientePorDNI(String dni) {
		String sql = "SELECT * FROM paciente WHERE dni = ?";
		try (PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setString(1, dni);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				Integer habitacionId = rs.getObject("habitacionAsignada") != null ? rs.getInt("habitacionAsignada")
						: null;
				Sala sala = habitacionId != null ? new Sala(habitacionId) : null;

				return new Paciente(rs.getInt("idPaciente"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("dni"), rs.getString("contacto"), rs.getString("obraSocial"), sala,
						rs.getDate("fechaAlta") != null ? rs.getDate("fechaAlta").toLocalDate() : null,
						rs.getBoolean("altaSolicitada"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// METODO MOSTRAR PACIENTES EN TABLA --POR ADMINISTRADOR PANEL PACIENTES
	public List<Paciente> obtenerTodosLosPacientes() {
		List<Paciente> lista = new ArrayList<>();
		String sql = "SELECT * FROM paciente";

		try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				// Obtener ID de la habitación
				Integer habitacionId = rs.getObject("habitacionAsignada") != null ? rs.getInt("habitacionAsignada")
						: null;
				Sala sala = habitacionId != null ? new Sala(habitacionId) : null;

				Date sqlFechaAlta = rs.getDate("fechaAlta");
				LocalDate fechaAlta = null;
				if (sqlFechaAlta != null) {
					try {
						fechaAlta = sqlFechaAlta.toLocalDate();
					} catch (Exception ex) {

						fechaAlta = null;
					}
				}
				Paciente p = new Paciente(rs.getInt("idPaciente"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("dni"), rs.getString("contacto"), rs.getString("obraSocial"), sala, fechaAlta,
						rs.getBoolean("altaSolicitada"));

				lista.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	// METODO POR SI SE QUIERE ASIGNAR UN SALA AL REGISTRAR UN PACIENTE PERO SOLO EN
	// SALA DISPONIBLE --POR ADMINISTRADOR PANEL PACIENTES
	public List<Sala> obtenerHabitacionesDisponibles() {
		List<Sala> hab = new ArrayList<>();
		String sql = "SELECT idSala FROM sala WHERE estado = 'Disponible'";

		try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				int idSala = rs.getInt("idSala");
				Sala sala = new Sala(idSala); // Usa el constructor que toma solo el ID
				hab.add(sala);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return hab;
	}

	// INSERTAR EMPLEADO --ADMINISTRADOR PANEL EMPLEADOS
	public boolean insertarEmpleadoBD(String nombre, String apellidos, String dni, String correo, String contraseña,
			TipoEmpleado tipoEmpleado, Turno turno) {
		String sql = "INSERT INTO empleado (nombre, apellidos, dni, correo, contraseña, tipoEmpleado, turno) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, nombre);
			stmt.setString(2, apellidos);
			stmt.setString(3, dni);
			stmt.setString(4, correo);
			stmt.setString(5, contraseña);
			stmt.setString(6, tipoEmpleado.name());
			stmt.setString(7, turno.name());
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Error al insertar empleado: " + e.getMessage());
			return false;
		}
	}

	// ELIMINAR EMPLEADO ADMINISTRADOR PANEL EMPLEADOS
	public boolean eliminarEmpleadoBD(String dni) {
		String sql = "DELETE FROM empleado WHERE dni = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, dni);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println("Error al eliminar empleado: " + e.getMessage());
			return false;
		}
	}

	// MODIFICAR ADMINISTRADOR PANEL EMPLEADOS
	public boolean modificarEmpleadoBD(int idEmpleado, String nombre, String apellidos, String dni, String correo,
			String contraseña, TipoEmpleado tipoEmpleado, Turno turno) {
		String sql = "UPDATE empleado SET nombre = ?, apellidos = ?, dni = ?, correo = ?, contraseña = ?, tipoEmpleado = ?, turno = ? WHERE idEmpleado = ?";
		try (Connection conn = getConn(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, nombre);
			stmt.setString(2, apellidos);
			stmt.setString(3, dni);
			stmt.setString(4, correo);
			stmt.setString(5, contraseña);
			stmt.setString(6, tipoEmpleado.name());
			stmt.setString(7, turno.name());
			stmt.setInt(8, idEmpleado);

			int rows = stmt.executeUpdate();
			System.out.println("Filas modificadas: " + rows);
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// BUSCAR EMPLEADO POR DNI --ADMINISTRADOR PANEL EMPLEADOS
	public Empleado buscarEmpleadoPorDNI(String dni) {
		String sql = "SELECT * FROM empleado WHERE dni = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, dni);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Empleado(rs.getInt("idEmpleado"), // <- ¡Importante!
						rs.getString("nombre"), rs.getString("apellidos"), rs.getString("dni"), rs.getString("correo"),
						rs.getString("contraseña"), TipoEmpleado.valueOf(rs.getString("tipoEmpleado")), // Asegúrate que
																										// coincida el
																										// nombre exacto
																										// del campo
						Turno.valueOf(rs.getString("turno")));
			}
		} catch (SQLException e) {
			System.out.println("Error al buscar empleado: " + e.getMessage());
		}
		return null;
	}

	// MOSTRAR TODOS EMPLEADOS--ADMINISTRADOR PANEL EMPLEADOS
	public List<Empleado> obtenerTodosEmpleados() {
		List<Empleado> empleados = new ArrayList<>();
		String sql = "SELECT * FROM empleado";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Empleado emp = new Empleado(rs.getInt("idEmpleado"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("dni"), rs.getString("correo"), rs.getString("contraseña"),
						TipoEmpleado.valueOf(rs.getString("tipoEmpleado")), Turno.valueOf(rs.getString("turno")));
				empleados.add(emp);
			}
		} catch (SQLException e) {
			System.err.println("Error al obtener empleados: " + e.getMessage());
		}

		return empleados;
	}

	// OBTENER ULTIMA ID REGISTRADA--ADMINISTRADOR PANEL EMPLEADOS
	public int obtenerUltimoIdEmpleado() {
		int ultimoId = -1;
		String sql = "SELECT MAX(idEmpleado) FROM Empleado";

		try (Connection conn = getConn();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				ultimoId = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al obtener el último ID de empleado.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return ultimoId;
	}

	// ADMINISTRATIVO ---PANEL CONSULTAR DATOS HOSPITAL

	// NUMERO TOTAL DE EMPLEADOS
		public int contarEmpleados() {
			String sql = "SELECT COUNT(*) FROM empleado";
			try {
				Connection conn = getConn();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				if (rs.next())
					return rs.getInt(1);
			} catch (SQLException e) {
				System.out.println("Error al contar empleados: " + e.getMessage());
			}
			return 0;
		}

		// NUMERO TOAL DE SALAS
		public int contarSalas() {
			String sql = "SELECT COUNT(*) FROM sala";
			try  {Connection conn = getConn();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
				if (rs.next())
					return rs.getInt(1);
			} catch (SQLException e) {
				System.out.println("Error al contar salas: " + e.getMessage());
			}
			return 0;
		}

		// NUMERO DE PACIENTES
		public int contarPacientes() {
			String sql = "SELECT COUNT(*) FROM paciente";
			try  {Connection conn = getConn();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
				if (rs.next())
					return rs.getInt(1);
			} catch (SQLException e) {
				System.out.println("Error al contar pacientes: " + e.getMessage());
			}
			return 0;
		}

		// NUMERO DE PACIENTES INTERNADOS (ALTASOLCITADA = 0)
		public int contarPacientesInternados() {
			String sql = "SELECT COUNT(*) FROM paciente WHERE altaSolicitada = 0"; // 0 = internado
			try {
				Connection conn = getConn();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				if (rs.next())
					return rs.getInt(1);
			} catch (SQLException e) {
				System.out.println("Error al contar pacientes internados: " + e.getMessage());
			}
			return 0;
		}

	// ADMINISTRATIVO --CONSULTAR DISPONIBILIDAD DE SALAS
	// esta ya creadp obtenerSalasDesdeBD();
	// ya esta creado conector.obtenerSalasConId();

	// ADMINISTRATIVO ---PANEL DAR CITA MEDICA
	// OBTENER LOS EMPLEADO TIPO MEDICOS
	public List<Empleado> obtenerTodosMedicos() {
		List<Empleado> medicos = new ArrayList<>();
		String sql = "SELECT idEmpleado, nombre, apellidos, dni, tipoEmpleado, turno FROM empleado WHERE tipoEmpleado = 'MEDICO'";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Empleado emp = new Empleado(rs.getInt("idEmpleado"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("dni"), TipoEmpleado.valueOf(rs.getString("tipoEmpleado")),
						Turno.valueOf(rs.getString("turno")));
				medicos.add(emp);
			}
		} catch (SQLException e) {
			System.err.println("Error al obtener médicos: " + e.getMessage());
		}
		return medicos;
	}

	// GUARDAR CITA MEDICA
	public boolean guardarCitaMedica(CitaMedica cita) {
		String sql = "INSERT INTO citaMedica(fecha, hora, idPaciente, idEmpleado, sala) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setDate(1, java.sql.Date.valueOf(cita.getFecha()));
			pst.setTime(2, java.sql.Time.valueOf(cita.getHora()));
			pst.setInt(3, cita.getPaciente().getIdPaciente());
			pst.setInt(4, cita.getMedico().getIdEmpleado());
			pst.setInt(5, cita.getSala() != null ? cita.getSala().getIdSala() : java.sql.Types.NULL);
			int filas = pst.executeUpdate();
			return filas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// PARA MOSTRAR LAS CITA MEDICAS
	public List<CitaMedica> obtenerTodasLasCitas() {
		List<CitaMedica> citas = new ArrayList<>();
		String sql = "SELECT * FROM citaMedica";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int idCita = rs.getInt("idCita");
				LocalDate fecha = rs.getDate("fecha").toLocalDate();
				LocalTime hora = rs.getTime("hora").toLocalTime();

				int idPaciente = rs.getInt("idPaciente");
				Paciente paciente = buscarPacientePorId(idPaciente);

				int idEmpleado = rs.getInt("idEmpleado");
				Empleado medico = obtenerEmpleadoPorId(idEmpleado);

				int idSala = rs.getInt("sala");
				Sala sala = obtenerSalaPorId(idSala);

				CitaMedica cita = new CitaMedica(fecha, hora, paciente, medico, sala);
				cita.setIdCita(idCita);

				citas.add(cita);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return citas;
	}

	// BUSCAR PACIENTE POR ID LO HE REUTILIZADO DE UNO ANTERIOR
	// OBTENER ID DE SALA PAR MOSTRAR CITAS
	public Sala obtenerSalaPorId(int idSala) {
		String sql = "SELECT * FROM sala WHERE idSala = ?";
		try (PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, idSala);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				TipoSala tipoSala = TipoSala.valueOf(rs.getString("tipoSala").toUpperCase());
				Estado estado = Estado.valueOf(rs.getString("estado").toUpperCase());

				return new Sala(rs.getInt("idSala"), tipoSala, estado, rs.getInt("capacidad"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// OBTNER ID DE EMPLEADO PARA CITA MEDICA
	public Empleado obtenerEmpleadoPorId(int idEmpleado) {
		String sql = "SELECT idEmpleado, nombre, apellidos, dni, tipoEmpleado, turno FROM empleado WHERE idEmpleado = ?";
		try (PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, idEmpleado);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return new Empleado(rs.getInt("idEmpleado"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("dni"), TipoEmpleado.valueOf(rs.getString("tipoEmpleado")),
						Turno.valueOf(rs.getString("turno")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> obtenerIdsPacientes() {
		List<String> ids = new ArrayList<>();
		String sql = "SELECT idPaciente, nombre FROM paciente"; // Puedes agregar nombre para mostrar algo legible

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				int id = rs.getInt("idPaciente");
				String nombre = rs.getString("nombre");
				ids.add(id + " - " + nombre); // Puedes devolver solo String con id o id + nombre para combo
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

	public List<String> obtenerIdsMedicos() {
		List<String> ids = new ArrayList<>();
		String sql = "SELECT idEmpleado, nombre, apellidos FROM empleado WHERE tipoEmpleado = 'MEDICO'";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				int id = rs.getInt("idEmpleado");
				String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellidos");
				ids.add(id + " - " + nombreCompleto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

	public List<String> obtenerIdsSalas() {
		List<String> ids = new ArrayList<>();
		String sql = "SELECT idSala, tipoSala FROM sala";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				int id = rs.getInt("idSala");
				String tipoSala = rs.getString("tipoSala");
				ids.add(id + " - " + tipoSala);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

	// PANEL ASIGNACION PACIENTE A MEDICO
	public boolean guardarAsignacionPacienteMedico(int idPaciente, int idMedico) {
		String sql = "INSERT INTO AsignacionPacienteAMedico (idPaciente, idMedico, fechaAsignacion) VALUES (?, ?, NOW())";
		try (PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, idPaciente);
			pst.setInt(2, idMedico);
			int rows = pst.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// OBTENER HISTORIAL DE ASIGNACIONES
	public List<String> obtenerHistorialAsignaciones() {
		List<String> historial = new ArrayList<>();
		String sql = "SELECT a.idPaciente, p.nombre AS nombrePaciente, "
				+ "a.idMedico, e.nombre AS nombreMedico, e.apellidos, a.fechaAsignacion "
				+ "FROM AsignacionPacienteAMedico a " + "JOIN paciente p ON a.idPaciente = p.idPaciente "
				+ "JOIN empleado e ON a.idMedico = e.idEmpleado";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int idPaciente = rs.getInt("idPaciente");
				String nombrePaciente = rs.getString("nombrePaciente");

				int idMedico = rs.getInt("idMedico");
				String nombreMedico = rs.getString("nombreMedico") + " " + rs.getString("apellidos");

				Timestamp fecha = rs.getTimestamp("fechaAsignacion");

				String registro = String.format("Paciente %d - %s asignado a Médico %d - %s en %s", idPaciente,
						nombrePaciente, idMedico, nombreMedico, fecha.toString());
				historial.add(registro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return historial;
	}

	// ENFERMERO
	// OBTENER CAMAS DISPONIBLES
	// OBTENER CAMAS DISPONIBLES
	public List<String> obtenerCamasDisponibles() {
		List<String> camas = new ArrayList<>();
		String sql = "SELECT idCama, tipoCama FROM Cama WHERE estado = 'DISPONIBLE'";

		try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				camas.add(rs.getInt("idCama") + " - " + rs.getString("tipoCama"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return camas;
	}

	// ASIGNAR CAMA
	public boolean asignarCamaAPaciente(int idPaciente, int idCama) {
		String sql = "INSERT INTO AsignacionCamaPaciente (idPaciente, idCama) VALUES (?, ?)";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, idPaciente);
			stmt.setInt(2, idCama);
			stmt.executeUpdate();

			// Marcar la cama como OCUPADA
			String update = "UPDATE Cama SET estado = 'OCUPADA' WHERE idCama = ?";
			try (PreparedStatement updateStmt = conn.prepareStatement(update)) {
				updateStmt.setInt(1, idCama);
				updateStmt.executeUpdate();
			}

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<String> obtenerHistorialAsignacionCama() {
		List<String> historial = new ArrayList<>();
		String sql = """
				    SELECT p.nombre AS paciente, c.idCama, c.tipoCama
				    FROM AsignacionCamaPaciente a
				    JOIN Paciente p ON a.idPaciente = p.idPaciente
				    JOIN Cama c ON a.idCama = c.idCama
				    ORDER BY a.idAsignacion DESC
				""";

		try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				String paciente = rs.getString("paciente");
				int idCama = rs.getInt("idCama");
				String tipoCama = rs.getString("tipoCama");

				historial.add("Paciente: " + paciente + " asignado a Cama " + idCama + " - " + tipoCama);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return historial;
	}

	public List<Paciente> getPacientesConAltaSolicitada1() {
	    List<Paciente> lista = new ArrayList<>();
	    String sql = "SELECT * FROM Paciente WHERE altaSolicitada = 1";

	    try (Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	            // Si tienes un constructor que recibe los campos:
	            Paciente p = new Paciente(
	                rs.getInt("idPaciente"),
	                rs.getString("nombre"),
	                rs.getString("apellidos"),
	                rs.getString("dni")
	                // Añade otros campos si el constructor los tiene
	            );
	            lista.add(p);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return lista;
	}

	// 2. Marcar el alta médica (fechaAlta = hoy, altaSolicitada = 0)
	public boolean marcarAltaMedica(Paciente paciente) {
		String sql = "UPDATE Paciente SET fechaAlta = CURDATE(), altaSolicitada = 0 WHERE idPaciente = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, paciente.getIdPaciente());
			int filas = stmt.executeUpdate();
			return filas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 3. Obtener historial de altas médicas (pacientes con fechaAlta no nula)
	public List<Paciente> getHistorialAltasMedicas() {
	    List<Paciente> lista = new ArrayList<>();
	    String sql = "SELECT * FROM Paciente WHERE fechaAlta IS NOT NULL ORDER BY fechaAlta DESC";

	    try (PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Paciente p = new Paciente();
	            p.setIdPaciente(rs.getInt("idPaciente"));
	            p.setNombre(rs.getString("nombre"));
	            p.setApellidos(rs.getString("apellidos"));

	            java.sql.Date sqlDate = rs.getDate("fechaAlta");
	            p.setFechaAlta(sqlDate != null ? sqlDate.toLocalDate() : null);

	            lista.add(p);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return lista;
	}
	// DIAGNOSTICO MEDICO

	public void insertarDiagnosticoBD(String dni, String nombre, String diagnostico, int idMedico) {
		conn = getConn();
		int idPaciente = 0;
		Date fecha = Date.valueOf(LocalDate.now());
		String sql1 = "Select idPaciente FROM paciente where DNI='" + dni + "' and nombre='" + nombre + "';";
		if (conn != null) {

			try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql1)) {

				if (rs.next()) {
					idPaciente = rs.getInt(1);

				}

			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al obtener la ID del paciente.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

			String sql2 = "INSERT INTO `historialmedico`(`fecha`, `diagnostico`, `idMedicoResponsable`, `idPaciente`) VALUES ('"
					+ fecha + "','" + diagnostico + "'," + idMedico + "," + idPaciente + ")";

			try (Statement order = conn.createStatement()) {
				order.executeUpdate(sql2);
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}

		} else {
			System.err.println("No fue posible conectar con la base de datos");
		}
		conn = null;
	}

	// RECETA MEDICO
	public void insertarRecetaBD(String dni, String nombre, String receta, int idMedico) {
		conn = getConn();
		int idPaciente = 0;
		Date fecha = Date.valueOf(LocalDate.now());
		String sql1 = "Select idPaciente FROM paciente where DNI='" + dni + "' and nombre='" + nombre + "';";
		if (conn != null) {

			try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql1)) {

				if (rs.next()) {
					idPaciente = rs.getInt(1);

				}

			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al obtener la ID del paciente.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

			String sql2 = "INSERT INTO `historialmedico`(`fecha`, `receta`, `idMedicoResponsable`, `idPaciente`) VALUES ('"
					+ fecha + "','" + receta + "'," + idMedico + "," + idPaciente + ")";

			try (Statement order = conn.createStatement()) {
				order.executeUpdate(sql2);
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}

		} else {
			System.err.println("No fue posible conectar con la base de datos");
		}
		conn = null;
	}

	// TENGO Q TENER ANTES MEDICO PARA GESTIONAR ESTO
	// ME FALTA UNIRLO A LOS PANELES
	// ENFERMERO MARCAR ALTA DE PACIENTE SOLICITADO POR UN MEDICO
	public boolean marcarAltaMedica(Paciente paciente, Medico medicoSolicitante) {
		String sql = "UPDATE paciente SET habitacionAsignada = NULL, fechaAlta = CURRENT_DATE, altaSolicitada = false WHERE dni = '"
				+ paciente.getDni() + "' AND altaSolicitada = true";

		try (Statement st = conn.createStatement()) {
			int filas = st.executeUpdate(sql);

			if (filas > 0) {
				// paciente.setHabitacionAsignada(null);
				paciente.setFechaAlta(LocalDate.now());
				paciente.setAltaSolicitada(false);
				return true;
			} else {
				return false; // No se actualizó (quizá no había alta solicitada)
			}

		} catch (SQLException e) {
			System.out.println("Error al marcar el alta médica del paciente: " + e.getMessage());
			return false;
		}
	}

	public static List<Paciente> getPacientesConAltaSolicitada() {
		List<Paciente> lista = new ArrayList<>();
		String sql = "SELECT * FROM paciente WHERE altaSolicitada = true";

		try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

			/*
			 * while (rs.next()) { Paciente p = new Paciente( rs.getString("nombre"),
			 * rs.getString("apellidos"), rs.getString("dni"), rs.getString("contacto"),
			 * rs.getString("obraSocial") ); p.setAltaSolicitada(true);
			 * p.setHabitacionAsignada(rs.getString("habitacionAsignada")); lista.add(p); }
			 */

		} catch (SQLException e) {
			System.out.println("Error al obtener pacientes con alta solicitada: " + e.getMessage());
		}

		return lista;
	}
	/* ------------ Adrián: Inicio sección CRUD Medico ------------ */

	// Crear Medico
	public void crearMedico(Medico m) {
		String sql = "INSERT INTO Medico (nombre, apellidos, dni, correo, contrasenia, tipoEmpleado, turno) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, m.getNombre());
			ps.setString(2, m.getApellidos());
			ps.setString(3, m.getDni());
			ps.setString(4, m.getCorreo());
			ps.setString(5, m.getContraseña());
			ps.setString(6, m.getTipoEmpleado().name());
			ps.setString(7, m.getTurno().name());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al crear Medico:");
			e.printStackTrace();
		}
	}

	// Leer Medico
	public Medico leerMedico(int idEmpleado) {
		String sql = "SELECT idUsuario, idEmpleado, nombre, apellidos, dni, correo, contrasenia, tipoEmpleado, turno FROM Medico WHERE idEmpleado = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idEmpleado);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Medico m = new Medico(rs.getString("nombre"), rs.getString("apellidos"), rs.getString("dni"),
							rs.getString("correo"), rs.getString("contrasenia"),
							TipoEmpleado.valueOf(rs.getString("tipoEmpleado")), Turno.valueOf(rs.getString("turno")));
					m.setIdUsuario(rs.getInt("idUsuario"));
					m.setIdEmpleado(rs.getInt("idEmpleado"));
					return m;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al leer Medico:");
			e.printStackTrace();
		}
		return null;
	}

	// Listar Medicos
	public List<Medico> listarMedicos() {
		List<Medico> lista = new ArrayList<>();
		String sql = "SELECT idUsuario, idEmpleado, nombre, apellidos, dni, correo, contrasenia, tipoEmpleado, turno FROM Medico";
		try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				Medico m = new Medico(rs.getString("nombre"), rs.getString("apellidos"), rs.getString("dni"),
						rs.getString("correo"), rs.getString("contrasenia"),
						TipoEmpleado.valueOf(rs.getString("tipoEmpleado")), Turno.valueOf(rs.getString("turno")));
				m.setIdUsuario(rs.getInt("idUsuario"));
				m.setIdEmpleado(rs.getInt("idEmpleado"));
				lista.add(m);
			}
		} catch (SQLException e) {
			System.err.println("Error al listar Medicos:");
			e.printStackTrace();
		}
		return lista;
	}

	// Actualizar Medico
	public void actualizarMedico(Medico m) {
		String sql = "UPDATE Medico SET nombre = ?, apellidos = ?, dni = ?, correo = ?, contrasenia = ?, tipoEmpleado = ?, turno = ? WHERE idEmpleado = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, m.getNombre());
			ps.setString(2, m.getApellidos());
			ps.setString(3, m.getDni());
			ps.setString(4, m.getCorreo());
			ps.setString(5, m.getContraseña());
			ps.setString(6, m.getTipoEmpleado().name());
			ps.setString(7, m.getTurno().name());
			ps.setInt(8, m.getIdEmpleado());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al actualizar Medico:");
			e.printStackTrace();
		}
	}

	// Eliminar Medico
	public void eliminarMedico(int idEmpleado) {
		String sql = "DELETE FROM Medico WHERE idEmpleado = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idEmpleado);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al eliminar Medico:");
			e.printStackTrace();
		}
	}

	/* ------------ Fin CRUD Medico ------------ */

	/* ------------ Adrián: Inicio sección CRUD Sala ------------ */

	// Crear Sala
	public void crearSala(Sala s) {
		String sql = "INSERT INTO Sala (idSala, tipoSala, estado, capacidad) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, s.getIdSala());
			ps.setString(2, s.getTipoSala().name());
			ps.setString(3, s.getEstado().name());
			ps.setInt(4, s.getCapacidad());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al crear Sala:");
			e.printStackTrace();
		}
	}

	// Leer Sala
	public Sala leerSala(int idSala) {
		String sql = "SELECT idSala, tipoSala, estado, capacidad FROM Sala WHERE idSala = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idSala);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Sala(rs.getInt("idSala"), TipoSala.valueOf(rs.getString("tipoSala")),
							Estado.valueOf(rs.getString("estado")), rs.getInt("capacidad"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al leer Sala:");
			e.printStackTrace();
		}
		return null;
	}

	// Listar Salas
	public List<Sala> listarSalas() {
		List<Sala> lista = new ArrayList<>();
		String sql = "SELECT idSala, tipoSala, estado, capacidad FROM Sala";
		try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				lista.add(new Sala(rs.getInt("idSala"), TipoSala.valueOf(rs.getString("tipoSala")),
						Estado.valueOf(rs.getString("estado")), rs.getInt("capacidad")));
			}
		} catch (SQLException e) {
			System.err.println("Error al listar Salas:");
			e.printStackTrace();
		}
		return lista;
	}

	// Actualizar Sala
	public void actualizarSala(Sala s) {
		String sql = "UPDATE Sala SET tipoSala = ?, estado = ?, capacidad = ? WHERE idSala = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, s.getTipoSala().name());
			ps.setString(2, s.getEstado().name());
			ps.setInt(3, s.getCapacidad());
			ps.setInt(4, s.getIdSala());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al actualizar Sala:");
			e.printStackTrace();
		}
	}

	// Eliminar Sala
	public void eliminarSala(int idSala) {
		String sql = "DELETE FROM Sala WHERE idSala = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idSala);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al eliminar Sala:");
			e.printStackTrace();
		}
	}

	/* ------------ Fin CRUD Sala ------------ */

	/* ------------ Fin sección Adrián ------------ */

	// Registrar Diagnóstico
	public void registrarDiagnostico(Diagnostico d) {
		String sql = "INSERT INTO Diagnostico (idPaciente, idMedico, descripcion, fecha) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, d.getIdPaciente());
			ps.setInt(2, d.getIdMedico());
			ps.setString(3, d.getDescripcion());
			ps.setTimestamp(4, new Timestamp(d.getFecha().getTime()));
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al registrar diagnóstico:");
			e.printStackTrace();
		}
	}

	// Registrar Receta
	public void registrarReceta(Receta r) {
		String sql = "INSERT INTO Receta (idPaciente, idMedico, medicamento, dosis, fecha) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, r.getIdPaciente());
			ps.setInt(2, r.getIdMedico());
			ps.setString(3, r.getMedicamento());
			ps.setString(4, r.getDosis());
			ps.setTimestamp(5, new Timestamp(r.getFecha().getTime()));
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al registrar receta:");
			e.printStackTrace();
		}
	}

	// Ver Historial Médico (Diagnósticos)
	public List<Diagnostico> verHistorialDiagnosticos(int idPaciente) {
		List<Diagnostico> lista = new ArrayList<>();
		String sql = "SELECT * FROM  historialmedico WHERE idPaciente = ? ORDER BY fecha DESC";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idPaciente);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {

					Diagnostico d = new Diagnostico(rs.getInt("idPaciente"), rs.getInt("idMedicoResponsable"),
							rs.getString("diagnostico"), rs.getTimestamp("fecha"));
					d.setIdDiagnostico(rs.getInt("idHistorial"));
					lista.add(d);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al ver historial de Diagnosticos:");
			e.printStackTrace();
		}
		return lista;
	}

	public List<Receta> verHistorialRecetas(int idPaciente) {
		List<Receta> lista = new ArrayList<>();
		String sql = "SELECT * FROM historialmedico WHERE idPaciente = ? ORDER BY fecha DESC";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idPaciente);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Receta d = new Receta(rs.getInt("idPaciente"), rs.getInt("idMedicoResponsable"),
							rs.getString("recetas"), rs.getDate("fecha"));
					lista.add(d);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al ver historial de Recetas:");
			e.printStackTrace();
		}
		return lista;
	}

	// Ver Pacientes asignados a Médico
	public List<Paciente> verPacientesAsignadosAMedico(int idMedico) {
	    List<Paciente> lista = new ArrayList<>();
	    String sql = "SELECT p.idPaciente, p.nombre, p.apellidos, p.dni " +
	                 "FROM Paciente p " +
	                 "JOIN asignacionpacienteamedico a ON p.idPaciente = a.idPaciente " +
	                 "WHERE a.idMedico = ?";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, idMedico);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Paciente p = new Paciente(
	                    rs.getInt("idPaciente"),
	                    rs.getString("nombre"),
	                    rs.getString("apellidos"),
	                    rs.getString("dni")
	                );
	                lista.add(p);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al ver pacientes asignados:");
	        e.printStackTrace();
	    }
	    return lista;
	}

	// Ver si un paciente esta asignado al Médico
	public int comprobarPacienteMedico(int idMedico, String dniPaciente) {

		String sql = "SELECT * FROM Paciente p JOIN asignacionpacienteamedico a ON p.idPaciente = a.idPaciente WHERE a.idMedico ="
				+ idMedico + " and dni='" + dniPaciente + "';";
		try (Statement ps = conn.createStatement()) {

			try (ResultSet rs = ps.executeQuery(sql)) {
				if (rs.next()) {

					return rs.getInt("idPaciente");
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al ver pacientes asignados:");
			e.printStackTrace();
		}
		return -1;
	}

	// Solicitar Alta Paciente
	public void solicitarAltaPaciente(int idPaciente) {
		String sql = "UPDATE Paciente SET estado = 'DADO_ALTA' WHERE idPaciente = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idPaciente);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al solicitar alta de paciente:");
			e.printStackTrace();
		}
	}

	// ADMINISTRATIVO

	public List<String> obtenerPacientesInternados() {
		List<String> pacientes = new ArrayList<>();
		String sql = "SELECT nombre, apellidos FROM paciente WHERE habitacionAsignada IS NOT NULL AND altaSolicitada = 0";

		try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellidos");
				pacientes.add(nombreCompleto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pacientes;
	}

	public List<String> obtenerSalasDisponibles() {
		List<String> salas = new ArrayList<>();
		String sql = "SELECT idSala, tipoSala, capacidad FROM Sala WHERE estado = 'DISPONIBLE'";

		try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("idSala");
				String tipo = rs.getString("tipoSala");
				int capacidad = rs.getInt("capacidad");
				salas.add("Sala " + id + " - Tipo: " + tipo + " - Capacidad: " + capacidad);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return salas;
	}

	// OBTENER CAMAS DISPONIBLE SE RUTILIZA

	public List<String> obtenerActividadMedicos() {
		List<String> actividad = new ArrayList<>();

		String sql = """
				    SELECT e.nombre, e.apellidos, COUNT(a.idPaciente) AS totalPacientes
				    FROM asignacionpacienteamedico a
				    JOIN empleado e ON a.idMedico = e.idEmpleado
				    GROUP BY a.idMedico
				""";

		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				String nombre = rs.getString("nombre") + " " + rs.getString("apellidos");
				int totalPacientes = rs.getInt("totalPacientes");
				actividad.add("Médico: " + nombre + " - Pacientes asignados: " + totalPacientes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actividad;
	}

	// ADMINISTRATIVO PAEL ASIGANR ROLES TURNOS
	public List<Empleado> obtenerEmpleados() {
		List<Empleado> empleados = new ArrayList<>();
		String sql = "SELECT idEmpleado, nombre, apellidos, tipoEmpleado FROM empleado";
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Empleado emp = new Empleado();
				emp.setIdEmpleado(rs.getInt("idEmpleado"));
				emp.setNombre(rs.getString("nombre"));
				emp.setApellidos(rs.getString("apellidos"));

				// Convertir String a enum
				String tipoStr = rs.getString("tipoEmpleado");
				if (tipoStr != null) {
					emp.setTipoEmpleado(TipoEmpleado.valueOf(tipoStr.toUpperCase()));
					// Esto asume que el texto en la BD coincide con el enum (mayúsculas)
				}

				empleados.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empleados;
	}

	public boolean asignarRolTurnoSala(int idEmpleado, String tipoEmpleado, String turno, Integer idSala) {
		String sql = "UPDATE empleado SET tipoEmpleado = ?, turno = ?, idSala = ? WHERE idEmpleado = ?";
		try (PreparedStatement pst = getConn().prepareStatement(sql)) {
			pst.setString(1, tipoEmpleado.toUpperCase());
			pst.setString(2, turno.toUpperCase());
			if (idSala != null) {
				pst.setInt(3, idSala);
			} else {
				pst.setNull(3, java.sql.Types.INTEGER);
			}
			pst.setInt(4, idEmpleado);
			return pst.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Método para obtener todas las asignaciones de empleados en forma de lista de
	// objetos
	public List<String[]> obtenerAsignaciones() {
		List<String[]> lista = new ArrayList<>();
		String sql = "SELECT nombre, apellidos, tipoEmpleado, turno, idSala FROM empleado";

		try (PreparedStatement pst = getConn().prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellidos");
				String rol = rs.getString("tipoEmpleado");
				String turno = rs.getString("turno");
				Integer idSala = rs.getObject("idSala") != null ? rs.getInt("idSala") : null;

				String salaStr = (idSala != null) ? String.valueOf(idSala) : "";

				lista.add(new String[] { nombreCompleto, rol, turno, salaStr });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	  public List<Paciente> obtenerPacientesSinSolicitudAlta() {
	        List<Paciente> pacientes = new ArrayList<>();
	        String sql = "SELECT idPaciente, nombre, apellidos FROM paciente WHERE altaSolicitada = 0";

	        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
	            while (rs.next()) {
	                Paciente p = new Paciente();
	                p.setIdPaciente(rs.getInt("idPaciente"));
	                p.setNombre(rs.getString("nombre"));
	                p.setApellidos(rs.getString("apellidos"));
	                System.out.println("Paciente obtenido: " + p);
	                pacientes.add(p);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return pacientes;
	    }
	public boolean solicitarAltaParaPaciente(int idPaciente) {
	    String sql = "UPDATE paciente SET altaSolicitada = 1 WHERE idPaciente = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idPaciente);
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public List<String> obtenerSolicitudesAlta() {
	    List<String> solicitudes = new ArrayList<>();
	    String sql = "SELECT nombre, apellidos FROM paciente WHERE altaSolicitada = 1";

	    try (
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery()
	    ) {
	        while (rs.next()) {
	            String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellidos");
	            solicitudes.add(nombreCompleto);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return solicitudes;
	}
	
	
		public List<HistorialMedico> verHistorialClinico(int idPaciente) {
		    List<HistorialMedico> historial = new ArrayList<>();
		    String sql = "SELECT fecha, diagnostico, tratamiento, recetas, idMedicoResponsable FROM historial WHERE idPaciente = ?";

		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setInt(1, idPaciente);
		        ResultSet rs = stmt.executeQuery();

		        while (rs.next()) {
		            HistorialMedico h = new HistorialMedico();
		            h.setFecha(rs.getDate("fecha"));
		            h.setDiagnostico(rs.getString("diagnostico"));
		            h.setTratamiento(rs.getString("tratamiento"));
		            h.setRecetas(rs.getString("recetas"));

		            int idMedico = rs.getInt("idMedicoResponsable");

		            if (idMedico > 0) {
		                Empleado empleado = this.obtenerMedicoPorId(idMedico);

		                // Convertir Empleado a Medico
		                Empleado medico = new 	Empleado();
		                medico.setIdEmpleado(empleado.getIdEmpleado());
		                medico.setNombre(empleado.getNombre());
		                medico.setApellidos(empleado.getApellidos());
		                medico.setDni(empleado.getDni());
		                medico.setCorreo(empleado.getCorreo());
		                medico.setTipoEmpleado(empleado.getTipoEmpleado());
		                medico.setTurno(empleado.getTurno());
		                medico.setIdSala(empleado.getIdSala());

		                h.setMedicoResponsable((Medico) medico);
		            }

		            historial.add(h);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return historial;
		}
	public Empleado obtenerMedicoPorId(int idMedico) {
	    String sql = "SELECT * FROM empleado WHERE idEmpleado = ? AND tipoEmpleado = 'MEDICO'";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idMedico);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Empleado medico = new Empleado();
	            medico.setIdEmpleado(rs.getInt("idEmpleado"));
	            medico.setNombre(rs.getString("nombre"));
	            medico.setApellidos(rs.getString("apellidos"));
	            medico.setDni(rs.getString("dni"));
	            medico.setCorreo(rs.getString("correo"));
	            // Convertir de String a Enum
	            String tipoStr = rs.getString("tipoEmpleado");
	            String turnoStr = rs.getString("turno");

	            if (tipoStr != null) {
	                medico.setTipoEmpleado(TipoEmpleado.valueOf(tipoStr));
	            }

	            if (turnoStr != null) {
	                medico.setTurno(Turno.valueOf(turnoStr));
	            }

	            medico.setIdSala(rs.getInt("idSala"));
	            return medico;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
}
