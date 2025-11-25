using MySql.Data.MySqlClient;
using System;

namespace HondaFactory
{
    public static class ConexionBD
    {
        private static  string connectionString =
            "Server=82.223.102.153;Port=3306;Database=FabricaHonda;User ID=fabrica_user;Password=Honda2025!;SslMode=Preferred;";

        public static MySqlConnection ObtenerConexion()
        {
            return new MySqlConnection(connectionString);
        }

        public static bool ProbarConexion()
        {
            try
            {
                using var conn = new MySqlConnection(connectionString);
                conn.Open();
                return true;
            }
            catch (Exception e)
            {
                Console.WriteLine($" Error al conectar: {e.Message}");
                return false;
            }
        }
    }
}
