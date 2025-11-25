using HondaFactory.Modelos;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;

namespace HondaFactory.Repositorio
{
    public class ModeloRepositorio
    {
        public List<Modelo> ListarBD()
        {
            var lista = new List<Modelo>();

            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = "SELECT id, nombre, codigo_modelo, segmento FROM ModeloHonda ORDER BY id";

                using var cmd = new MySqlCommand(sql, conn);
                using var reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    lista.Add(new Modelo
                    {
                        Id = reader.GetInt32("id"),
                        Nombre = reader["nombre"].ToString() ?? "",
                        CodigoModelo = reader["codigo_modelo"].ToString() ?? "",
                        Segmento = reader["segmento"].ToString() ?? ""
                    });
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Error al listar modelos: " + ex.Message);
            }

            return lista;
        }

        public int Insertar(Modelo m)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"INSERT INTO ModeloHonda (nombre, codigo_modelo, segmento)
                               VALUES (@n, @c, @s)";

                using var cmd = new MySqlCommand(sql, conn);

                cmd.Parameters.AddWithValue("@n", m.Nombre);
                cmd.Parameters.AddWithValue("@c", m.CodigoModelo);
                cmd.Parameters.AddWithValue("@s", m.Segmento);

                cmd.ExecuteNonQuery();
                return (int)cmd.LastInsertedId;
            }
            catch (Exception ex)
            {
                throw new Exception("Error al insertar modelo: " + ex.Message);
            }
        }

        public void Actualizar(Modelo m)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"UPDATE ModeloHonda SET 
                               nombre=@n, codigo_modelo=@c, segmento=@s
                               WHERE id=@id";

                using var cmd = new MySqlCommand(sql, conn);

                cmd.Parameters.AddWithValue("@id", m.Id);
                cmd.Parameters.AddWithValue("@n", m.Nombre);
                cmd.Parameters.AddWithValue("@c", m.CodigoModelo);
                cmd.Parameters.AddWithValue("@s", m.Segmento);

                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Error al actualizar modelo: " + ex.Message);
            }
        }
    }
}
