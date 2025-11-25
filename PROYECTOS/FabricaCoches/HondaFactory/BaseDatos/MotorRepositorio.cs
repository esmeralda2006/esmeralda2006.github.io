using HondaFactory.Modelos;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;

namespace HondaFactory.Repositorio
{
    public class MotorRepositorio
    {
      
        public List<Motor> ListarBD()
        {
            var lista = new List<Motor>();

            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"
                    SELECT 
                        num_serie,
                        motor_tipo_id,
                        fecha_fabricacion,
                        potencia_kw,
                        emisiones_wltp
                    FROM Motor
                    ORDER BY num_serie;
                ";

                using var cmd = new MySqlCommand(sql, conn);
                using var reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    lista.Add(new Motor
                    {
                        Serie = reader["num_serie"]?.ToString() ?? "",
                        MotorTipoId = Convert.ToInt32(reader["motor_tipo_id"]),

                        FechaFabricacion = reader["fecha_fabricacion"] == DBNull.Value
                            ? DateTime.Now
                            : Convert.ToDateTime(reader["fecha_fabricacion"]),

                        PotenciaKw = Convert.ToDouble(reader["potencia_kw"]),
                        EmisionesWltp = Convert.ToDouble(reader["emisiones_wltp"])
                    });
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Error al listar motores: " + ex.Message);
            }

            return lista;
        }


     
        public void Insertar(Motor m)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"
                    INSERT INTO Motor
                        (num_serie, motor_tipo_id, fecha_fabricacion,
                         potencia_kw, emisiones_wltp)
                    VALUES 
                        (@serie, @tipo, @fecha, @kw, @wltp);
                ";

                using var cmd = new MySqlCommand(sql, conn);

                cmd.Parameters.AddWithValue("@serie", m.Serie);
                cmd.Parameters.AddWithValue("@tipo", m.MotorTipoId);
                cmd.Parameters.AddWithValue("@fecha", m.FechaFabricacion);
                cmd.Parameters.AddWithValue("@kw", m.PotenciaKw);
                cmd.Parameters.AddWithValue("@wltp", m.EmisionesWltp);

                cmd.ExecuteNonQuery();
            }
            catch (MySqlException ex)
            {
                if (ex.Number == 1062)
                    throw new Exception("El número de serie ya existe en la base de datos.");

                if (ex.Number == 1452)
                    throw new Exception("El tipo de motor no existe en MotorTipo.");

                throw new Exception("Error de base de datos al insertar motor: " + ex.Message);
            }
            catch (Exception ex)
            {
                throw new Exception("Error inesperado al insertar motor: " + ex.Message);
            }
        }


     
        public bool EstaMontado(string serieMotor)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"SELECT COUNT(*) FROM Coche WHERE motor_serie = @serie";

                using var cmd = new MySqlCommand(sql, conn);
                cmd.Parameters.AddWithValue("@serie", serieMotor);

                long count = (long)cmd.ExecuteScalar();

                return count > 0; 
            }
            catch (Exception ex)
            {
                throw new Exception("Error al comprobar si el motor está montado: " + ex.Message);
            }
        }

        public void Actualizar(Motor m)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"
                    UPDATE Motor SET
                        motor_tipo_id = @tipo,
                        potencia_kw = @kw,
                        emisiones_wltp = @wltp
                    WHERE num_serie = @serie;
                ";

                using var cmd = new MySqlCommand(sql, conn);

                cmd.Parameters.AddWithValue("@serie", m.Serie);
                cmd.Parameters.AddWithValue("@tipo", m.MotorTipoId);
                cmd.Parameters.AddWithValue("@kw", m.PotenciaKw);
                cmd.Parameters.AddWithValue("@wltp", m.EmisionesWltp);

                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Error al actualizar motor: " + ex.Message);
            }
        }
    }
}
