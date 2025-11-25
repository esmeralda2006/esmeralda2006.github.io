using HondaFactory.Vista;
using System;

namespace HondaFactory
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Title = "Honda Factory - Gestión de Fábrica de Coches";
            Console.Clear();

            // ==========================
            // MENSAJE DE BIENVENIDA
            // ==========================
            Console.ForegroundColor = ConsoleColor.DarkRed;
            Console.WriteLine("========================================");
            Console.WriteLine("        BIENVENIDO A HONDA FACTORY      ");
            Console.WriteLine("========================================\n");
            Console.ResetColor();

            Console.ForegroundColor = ConsoleColor.DarkBlue;
            Console.WriteLine("Iniciando aplicación...");
            Console.WriteLine("Probando conexión con la base de datos...");
            Console.ResetColor();
            Console.WriteLine();

            // ==========================
            // PROBAR CONEXIÓN
            // ==========================

            if (!ConexionBD.ProbarConexion())
            {
                Console.ForegroundColor = ConsoleColor.DarkRed;
                Console.WriteLine(" No se pudo conectar con la base de datos.");
                Console.ResetColor();
                Console.WriteLine("\nPresione una tecla para salir...");
                Console.ReadKey();
                return;
            }

            // ==========================
            // CONEXIÓN EXITOSA
            // ==========================
            Console.ForegroundColor = ConsoleColor.DarkGreen;
            Console.WriteLine(" Conexión establecida correctamente.");
            Console.ResetColor();
            Console.WriteLine("\nPresione una tecla para continuar...");
            Console.ReadKey();

            // ==========================
            // CARGAR MENÚ
            // ==========================
            new MainControlador().Iniciar();

            // ==========================
            // MENSAJE DE SALIDA
            // ==========================
            Console.Clear();
            Console.ForegroundColor = ConsoleColor.DarkRed;
            Console.WriteLine("========================================");
            Console.WriteLine("      GRACIAS POR USAR HONDA FACTORY    ");
            Console.WriteLine("========================================\n");
            Console.ResetColor();

            Console.WriteLine("Hasta la próxima. ¡Que tenga un excelente día!");
            Console.ReadKey();
        }
    }
}
