using HondaFactory.Modelos;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;

namespace HondaFactory.Repositorio
{
    public class CocheRepositorio
    {
        // ======================================================
        // LISTAR
        // ======================================================
        public List<Coche> ListarBD()
        {
            var lista = new List<Coche>();

            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"
                    SELECT 
                        c.vin,
                        c.modelo_id,
                        m.nombre AS modelo_nombre,

                        c.color_id,
                        col.nombre AS color_nombre,

                        c.paquete_id,
                        p.nombre AS paquete_nombre,

                        c.motor_serie,
                        c.observaciones,
                        c.fecha_fabricacion
                    FROM Coche c
                    LEFT JOIN ModeloHonda m ON c.modelo_id = m.id
                    LEFT JOIN Color col ON c.color_id = col.id
                    LEFT JOIN PaqueteExtras p ON c.paquete_id = p.id
                    ORDER BY c.vin;
                ";

                using var cmd = new MySqlCommand(sql, conn);
                using var reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    lista.Add(new Coche
                    {
                        Vin = reader["vin"].ToString() ?? "",
                        ModeloId = reader.GetInt32("modelo_id"),

                        ModeloNombre = reader["modelo_nombre"].ToString(),

                        ColorId = reader["color_id"] == DBNull.Value ? null : (int?)reader.GetInt32("color_id"),
                        ColorNombre = reader["color_nombre"].ToString(),

                        PaqueteId = reader["paquete_id"] == DBNull.Value ? null : (int?)reader.GetInt32("paquete_id"),
                        PaqueteNombre = reader["paquete_nombre"].ToString(),

                        MotorSerie = reader["motor_serie"] == DBNull.Value ? null : reader["motor_serie"].ToString(),

                        Observaciones = reader["observaciones"] == DBNull.Value ? "" : reader["observaciones"].ToString(),

                        FechaFabricacion = reader["fecha_fabricacion"] == DBNull.Value
                            ? DateTime.Now
                            : Convert.ToDateTime(reader["fecha_fabricacion"])
                    });
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Error al listar coches: " + ex.Message);
            }

            return lista;
        }

        // ======================================================
        // INSERTAR
        // ======================================================
        public void Insertar(Coche c)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"
                    INSERT INTO Coche 
                    (vin, modelo_id, color_id, paquete_id, motor_serie, observaciones, fecha_fabricacion)
                    VALUES (@vin, @modelo, @color, @paquete, @motor, @obs, @fecha);
                ";

                using var cmd = new MySqlCommand(sql, conn);
                cmd.Parameters.AddWithValue("@vin", c.Vin);
                cmd.Parameters.AddWithValue("@modelo", c.ModeloId);
                cmd.Parameters.AddWithValue("@color", c.ColorId);
                cmd.Parameters.AddWithValue("@paquete", c.PaqueteId);
                cmd.Parameters.AddWithValue("@motor", string.IsNullOrWhiteSpace(c.MotorSerie) ? DBNull.Value : c.MotorSerie);
                cmd.Parameters.AddWithValue("@obs", c.Observaciones ?? (object)DBNull.Value);
                cmd.Parameters.AddWithValue("@fecha", c.FechaFabricacion);

                cmd.ExecuteNonQuery();
            }
            catch (MySqlException ex)
            {
                if (ex.Number == 1062)
                    throw new Exception("El VIN ya existe en la base de datos.");

                throw new Exception("Error de base de datos al insertar coche: " + ex.Message);
            }
            catch (Exception ex)
            {
                throw new Exception("Error inesperado al insertar coche: " + ex.Message);
            }
        }

        // ======================================================
        // ACTUALIZAR (CORREGIDO)
        // ======================================================
        public void Actualizar(Coche c)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"
                    UPDATE Coche SET
                        modelo_id = @modelo,
                        color_id = @color,
                        paquete_id = @paquete,
                        motor_serie = @motor,
                        observaciones = @obs,
                        fecha_fabricacion = @fecha         -- ← ← ←  CORREGIDO
                    WHERE vin = @vin;
                ";

                using var cmd = new MySqlCommand(sql, conn);

                cmd.Parameters.AddWithValue("@vin", c.Vin);
                cmd.Parameters.AddWithValue("@modelo", c.ModeloId);
                cmd.Parameters.AddWithValue("@color", c.ColorId);
                cmd.Parameters.AddWithValue("@paquete", c.PaqueteId);
                cmd.Parameters.AddWithValue("@motor", string.IsNullOrWhiteSpace(c.MotorSerie) ? DBNull.Value : c.MotorSerie);
                cmd.Parameters.AddWithValue("@obs", c.Observaciones ?? (object)DBNull.Value);
                cmd.Parameters.AddWithValue("@fecha", c.FechaFabricacion);   // ← ← ← NUEVA LINEA

                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Error al actualizar coche: " + ex.Message);
            }
        }

        // ======================================================
        // MOTOR YA MONTADO
        // ======================================================
        public bool MotorYaMontado(string serieMotor)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"SELECT COUNT(*) FROM Coche WHERE motor_serie = @motor";

                using var cmd = new MySqlCommand(sql, conn);
                cmd.Parameters.AddWithValue("@motor", serieMotor);

                long count = (long)cmd.ExecuteScalar();
                return count > 0;
            }
            catch (Exception ex)
            {
                throw new Exception("Error al comprobar si el motor está montado: " + ex.Message);
            }
        }

        // ======================================================
        // QUITAR MOTOR
        // ======================================================
        public void QuitarMotor(string vin)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"UPDATE Coche SET motor_serie = NULL WHERE vin = @vin";

                using var cmd = new MySqlCommand(sql, conn);
                cmd.Parameters.AddWithValue("@vin", vin);

                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Error al quitar motor del coche: " + ex.Message);
            }
        }
    }
}
