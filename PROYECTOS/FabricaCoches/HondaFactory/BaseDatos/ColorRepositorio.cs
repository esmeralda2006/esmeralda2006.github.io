using HondaFactory.Modelos;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;

namespace HondaFactory.Repositorio
{
    public class ColorRepositorio
    {
        public List<Color> ListarBD()
        {
            var lista = new List<Color>();

            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = "SELECT id, nombre, codigo_pintura, acabado FROM Color ORDER BY id";

                using var cmd = new MySqlCommand(sql, conn);
                using var reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    lista.Add(new Color
                    {
                        Id = reader.GetInt32("id"),
                        Nombre = reader["nombre"].ToString() ?? "",
                        Codigo = reader["codigo_pintura"].ToString() ?? "",
                        Acabado = reader["acabado"].ToString() ?? ""
                    });
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Error al listar colores: " + ex.Message);
            }

            return lista;
        }

        public int Insertar(Color c)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"INSERT INTO Color (nombre, codigo_pintura, acabado)
                               VALUES (@n, @codigo, @a);";

                using var cmd = new MySqlCommand(sql, conn);

                cmd.Parameters.AddWithValue("@n", c.Nombre);
                cmd.Parameters.AddWithValue("@codigo", c.Codigo);
                cmd.Parameters.AddWithValue("@a", c.Acabado);

                cmd.ExecuteNonQuery();
                return (int)cmd.LastInsertedId;
            }
            catch (Exception ex)
            {
                throw new Exception("Error al insertar color: " + ex.Message);
            }
        }

        public void Actualizar(Color c)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"UPDATE Color 
                               SET nombre=@n, codigo_pintura=@codigo, acabado=@a
                               WHERE id=@id";

                using var cmd = new MySqlCommand(sql, conn);

                cmd.Parameters.AddWithValue("@id", c.Id);
                cmd.Parameters.AddWithValue("@n", c.Nombre);
                cmd.Parameters.AddWithValue("@codigo", c.Codigo);
                cmd.Parameters.AddWithValue("@a", c.Acabado);

                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Error al actualizar color: " + ex.Message);
            }
        }
    }
}
