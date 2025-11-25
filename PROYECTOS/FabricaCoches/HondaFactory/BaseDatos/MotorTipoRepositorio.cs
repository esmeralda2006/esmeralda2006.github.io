using HondaFactory.Modelos;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;

namespace HondaFactory.Repositorio
{
    public class MotorTipoRepositorio
    {
        public List<MotorTipo> ListarBD()
        {
            var lista = new List<MotorTipo>();

            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = "SELECT id, codigo, descripcion, cilindrada_cc, alimentacion FROM MotorTipo";

                using var cmd = new MySqlCommand(sql, conn);
                using var reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    lista.Add(new MotorTipo
                    {
                        Id = Convert.ToInt32(reader["id"]),
                        Codigo = reader["codigo"].ToString() ?? "",
                        Descripcion = reader["descripcion"].ToString() ?? "",
                        CilindradaCc = reader["cilindrada_cc"].ToString() ?? "",
                        Alimentacion = reader["alimentacion"].ToString() ?? ""
                    });
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Error al listar tipos de motor: " + ex.Message);
            }

            return lista;
        }

        public void Insertar(MotorTipo t)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"INSERT INTO MotorTipo
                               (codigo, descripcion, cilindrada_cc, alimentacion)
                               VALUES (@codigo, @desc, @cc, @ali)";

                using var cmd = new MySqlCommand(sql, conn);

                cmd.Parameters.AddWithValue("@codigo", t.Codigo);
                cmd.Parameters.AddWithValue("@desc", t.Descripcion);
                cmd.Parameters.AddWithValue("@cc", t.CilindradaCc);
                cmd.Parameters.AddWithValue("@ali", t.Alimentacion);

                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Error al insertar tipo de motor: " + ex.Message);
            }
        }

        public void Actualizar(MotorTipo t)
        {
            try
            {
                using var conn = ConexionBD.ObtenerConexion();
                conn.Open();

                string sql = @"UPDATE MotorTipo SET
                                descripcion=@desc,
                                cilindrada_cc=@cc,
                                alimentacion=@ali
                                WHERE id=@id";

                using var cmd = new MySqlCommand(sql, conn);

                cmd.Parameters.AddWithValue("@id", t.Id);
                cmd.Parameters.AddWithValue("@desc", t.Descripcion);
                cmd.Parameters.AddWithValue("@cc", t.CilindradaCc);
                cmd.Parameters.AddWithValue("@ali", t.Alimentacion);

                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Error al actualizar tipo de motor: " + ex.Message);
            }
        }
    }
}
