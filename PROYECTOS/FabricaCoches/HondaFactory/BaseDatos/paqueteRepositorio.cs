using HondaFactory.Modelos;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;

namespace HondaFactory.Repositorio
{
    public class PaqueteRepositorio
    {
        public List<Paquete> ListarBD()
        {
            var lista = new List<Paquete>();

            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = "SELECT id, nombre, descripcion FROM PaqueteExtras ORDER BY id";

                using var cmd = new MySqlCommand(sql, conn);
                using var reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    lista.Add(new Paquete
                    {
                        Id = reader.GetInt32("id"),
                        Nombre = reader["nombre"].ToString() ?? "",
                        Descripcion = reader["descripcion"].ToString() ?? ""
                    });
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Error al listar paquetes: " + ex.Message);
            }

            return lista;
        }

        public int Insertar(Paquete p)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"INSERT INTO PaqueteExtras (nombre, descripcion)
                               VALUES (@n, @d)";

                using var cmd = new MySqlCommand(sql, conn);

                cmd.Parameters.AddWithValue("@n", p.Nombre);
                cmd.Parameters.AddWithValue("@d", p.Descripcion);

                cmd.ExecuteNonQuery();
                return (int)cmd.LastInsertedId;
            }
            catch (Exception ex)
            {
                throw new Exception("Error al insertar paquete: " + ex.Message);
            }
        }

        public void Actualizar(Paquete p)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"UPDATE PaqueteExtras 
                               SET nombre=@n, descripcion=@d 
                               WHERE id=@id";

                using var cmd = new MySqlCommand(sql, conn);

                cmd.Parameters.AddWithValue("@id", p.Id);
                cmd.Parameters.AddWithValue("@n", p.Nombre);
                cmd.Parameters.AddWithValue("@d", p.Descripcion);

                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Error al actualizar paquete: " + ex.Message);
            }
        }
    }
}
