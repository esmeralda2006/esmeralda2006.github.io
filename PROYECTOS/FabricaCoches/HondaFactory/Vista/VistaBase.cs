using System;

namespace HondaFactory.Vista
{
    public class VistaBase
    {
        // ================================================================
        //                  MÉTODOS REUTILIZABLES
        // ================================================================
        public void Input(string txt)
        {
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.Write(txt);
            Console.ResetColor();
        }

        public void Error(string msg)
        {
            Console.ForegroundColor = ConsoleColor.Red;
            Console.WriteLine("❌ " + msg);
            Console.ResetColor();
        }

        public void Ok(string msg)
        {
            Console.ForegroundColor = ConsoleColor.Green;
            Console.WriteLine("✔ " + msg);
            Console.ResetColor();
        }

        public void Mensaje(string msg)
        {
            Console.WriteLine(msg);
        }

        public void Pausa()
        {
            Console.WriteLine("\nPulse una tecla para continuar...");
            Console.ReadKey(true);
        }

        public void Limpiar()
{
    Console.Clear();
}

        public int LeerOpcion()
        {
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.Write("Seleccione una opción: ");
            Console.ResetColor();

            int opcion;
            int.TryParse(Console.ReadLine(), out opcion);
            return opcion;
        }
public int LeerEntero(string mensaje)
{
    Console.ForegroundColor = ConsoleColor.Cyan;
    Console.Write(mensaje);
    Console.ResetColor();

    int valor;
    int.TryParse(Console.ReadLine(), out valor);
    return valor;
}
public string LeerTexto(string mensaje)
{
    Console.ForegroundColor = ConsoleColor.Cyan;
    Console.Write(mensaje);
    Console.ResetColor();

    return Console.ReadLine() ?? "";
}

        // ====================================================================
        //                     SECCIÓN PAQUETES
        // ====================================================================

        public void TituloListaPaquete()
        {
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.WriteLine("╔══════════════════════════════════════════════════════╗");
            Console.WriteLine("║                LISTA DE PAQUETES REGISTRADOS         ║");
            Console.WriteLine("╚══════════════════════════════════════════════════════╝");
            Console.ResetColor();
        }

        public void TituloAgregarPaquete()
        {
            Console.ForegroundColor = ConsoleColor.Green;
            Console.WriteLine("╔════════════════════════════╗");
            Console.WriteLine("║      AGREGAR PAQUETE       ║");
            Console.WriteLine("╚════════════════════════════╝");
            Console.ResetColor();
        }

        public void TituloEditarPaquete()
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("╔════════════════════════════╗");
            Console.WriteLine("║       EDITAR PAQUETE       ║");
            Console.WriteLine("╚════════════════════════════╝");
            Console.ResetColor();
        }

        public void CabeceraTablaPaquete()
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("╔════╦════════════════════════╦════════════════════════════════════╗");
            Console.WriteLine("║ ID ║        NOMBRE          ║           DESCRIPCIÓN              ║");
            Console.WriteLine("╠════╬════════════════════════╬════════════════════════════════════╣");
            Console.ResetColor();
        }

        public void FinTablaPaquete()
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("╚════╩════════════════════════╩════════════════════════════════════╝");
            Console.ResetColor();
        }

        public void FilaPaquete(int idLogico, string nombre, string descripcion)
        {
            Console.ForegroundColor = ConsoleColor.Green;
            Console.Write($"║ {idLogico,2} ");
            Console.ResetColor();

            Console.Write($"║ {nombre,-22} ║ {descripcion,-30} ║\n");
        }


        // ====================================================================
        //                   SECCIÓN MOTOR TIPO
        // ====================================================================

        public void TituloListaMotorTipo()
        {
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.WriteLine("╔══════════════════════════════════════════════════════════════════════╗");
            Console.WriteLine("║                     LISTA DE TIPOS DE MOTOR REGISTRADOS              ║");
            Console.WriteLine("╚══════════════════════════════════════════════════════════════════════╝");
            Console.ResetColor();
        }

        public void TituloAgregarMotorTipo()
        {
            Console.ForegroundColor = ConsoleColor.Green;
            Console.WriteLine("╔══════════════════════════════════════╗");
            Console.WriteLine("║        AGREGAR TIPO DE MOTOR         ║");
            Console.WriteLine("╚══════════════════════════════════════╝");
            Console.ResetColor();
        }

        public void TituloEditarMotorTipo()
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("╔══════════════════════════════════════╗");
            Console.WriteLine("║        EDITAR TIPO DE MOTOR          ║");
            Console.WriteLine("╚══════════════════════════════════════╝");
            Console.ResetColor();
        }

        public void CabeceraTablaMotorTipo()
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("╔════╦════════════╦═════════════════════════════╦═══════════════╦══════════════╗");
            Console.WriteLine("║ ID ║   CÓDIGO   ║        DESCRIPCIÓN          ║ CILINDRADA    ║ ALIMENTACIÓN ║");
            Console.WriteLine("╠════╬════════════╬═════════════════════════════╬═══════════════╬══════════════╣");
            Console.ResetColor();
        }

        public void FinTablaMotorTipo()
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("╚════╩════════════╩═════════════════════════════╩═══════════════╩══════════════╝");
            Console.ResetColor();
        }

        public void FilaMotorTipo(int id, string codigo, string descripcion, string cilindrada, string alimentacion)
        {
            Console.ForegroundColor = ConsoleColor.Green;
            Console.Write($"║ {id,2} ");
            Console.ResetColor();

            Console.Write($"║ {codigo,-10} ║ {descripcion,-27} ║ {cilindrada,-13} ║ {alimentacion,-12} ║\n");
        }



        // ====================================================================
        //                     SECCIÓN MOTOR
        // ====================================================================

        public void TituloListaMotor()
        {
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.WriteLine("╔══════════════════════════════════════════════════════════════════════╗");
            Console.WriteLine("║                         LISTA DE MOTORES REGISTRADOS                ║");
            Console.WriteLine("╚══════════════════════════════════════════════════════════════════════╝");
            Console.ResetColor();
        }

        public void CabeceraTablaMotor()
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("╔════╦════════════════╦══════════════════════════════════╦═══════════════╦══════════════════╗");
            Console.WriteLine("║ ID ║ Nº SERIE       ║ TIPO MOTOR                       ║ POTENCIA (kW) ║ WLTP (g/km)      ║");
            Console.WriteLine("╠════╬════════════════╬══════════════════════════════════╬═══════════════╬══════════════════╣");
            Console.ResetColor();
        }

        public void FinTablaMotor()
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("╚════╩════════════════╩══════════════════════════════════╩═══════════════╩══════════════════╝");
            Console.ResetColor();
        }

        public void FilaMotor(int id, string serie, string tipo, double kw, double wltp)
        {
            Console.ForegroundColor = ConsoleColor.Green;
            Console.Write($"║ {id,2} ");
            Console.ResetColor();

            Console.Write($"║ {serie,-14} ║ {tipo,-30} ║ {kw,-13} ║ {wltp,-14} ║\n");
        }


        // ====================================================================
        //                     SECCIÓN MODELO
        // ====================================================================

        public void TituloListaModelo()
        {
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.WriteLine("╔════════════════════════════════════════════════════════════════════════════╗");
            Console.WriteLine("║                         LISTA DE MODELOS REGISTRADOS                       ║");
            Console.WriteLine("╚════════════════════════════════════════════════════════════════════════════╝");
            Console.ResetColor();
        }

        public void CabeceraTablaModelo()
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("╔════╦══════════════════╦══════════════════╦═══════════════════════════════╗");
            Console.WriteLine("║ ID ║     NOMBRE       ║ CÓDIGO MODELO    ║ SEGMENTO                      ║");
            Console.WriteLine("╠════╬══════════════════╬══════════════════╬═══════════════════════════════╣");
            Console.ResetColor();
        }

        public void FinTablaModelo()
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("╚════╩══════════════════╩══════════════════╩═══════════════════════════════╝");
            Console.ResetColor();
        }

        public void FilaModelo(int id, string nombre, string codigo, string segmento)
        {
            Console.ForegroundColor = ConsoleColor.Green;
            Console.Write($"║ {id,2} ");
            Console.ResetColor();

            Console.Write($"║ {nombre,-16} ║ {codigo,-16} ║ {segmento,-29} ║\n");
        }


        // ====================================================================
        //                     SECCIÓN COLOR
        // ====================================================================

        public void TituloListaColor()
        {
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.WriteLine("╔══════════════════════════════════════════════════════════════╗");
            Console.WriteLine("║                   LISTA DE COLORES REGISTRADOS               ║");
            Console.WriteLine("╚══════════════════════════════════════════════════════════════╝");
            Console.ResetColor();
        }

        public void CabeceraTablaColor()
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("╔════╦════════════════╦══════════════════════╦═════════════════╗");
            Console.WriteLine("║ ID ║     NOMBRE     ║   CÓDIGO PINTURA     ║     ACABADO     ║");
            Console.WriteLine("╠════╬════════════════╬══════════════════════╬═════════════════╣");
            Console.ResetColor();
        }

        public void FinTablaColor()
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("╚════╩════════════════╩══════════════════════╩═════════════════╝");
            Console.ResetColor();
        }

        public void FilaColor(int id, string nombre, string codigo, string acabado)
        {
            Console.ForegroundColor = ConsoleColor.Green;
            Console.Write($"║ {id,2} ");
            Console.ResetColor();

            Console.Write($"║ {nombre,-14} ║ {codigo,-20} ║ {acabado,-15} ║\n");
        }


        // ====================================================================
        //                     SECCIÓN COCHE
        // ====================================================================

        public void TituloListaCoche()
        {
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.WriteLine("╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
            Console.WriteLine("║                                  LISTA DE COCHES REGISTRADOS                                  ║");
            Console.WriteLine("╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
            Console.ResetColor();
        }

        public void CabeceraTablaCoche()
{
    Console.ForegroundColor = ConsoleColor.Yellow;
    Console.WriteLine("╔════╦═══════════════════╦════════════╦════════════╦════════════╦════════════╦══════════════════╦══════════════════════════════════╗");
    Console.WriteLine("║ ID ║       VIN         ║   MODELO   ║   COLOR    ║  PAQUETE   ║   MOTOR    ║      FECHA       ║          OBSERVACIONES           ║");
    Console.WriteLine("╠════╬═══════════════════╬════════════╬════════════╬════════════╬════════════╬══════════════════╬══════════════════════════════════╣");
    Console.ResetColor();
}

public void FilaCoche(int id, string vin, string modelo, string color, string paquete, string motor, string fecha, string obs)
{
    if (obs == null) obs = "";
    if (obs.Length > 30) obs = obs.Substring(0, 30);  // Limitar columnas

    Console.ForegroundColor = ConsoleColor.Green;
    Console.Write($"║ {id,2} ");
    Console.ResetColor();

    Console.Write(
        $"║ {vin,-17} ║ {modelo,-10} ║ {color,-10} ║ {paquete,-10} ║ {motor,-10} ║ {fecha,-14} ║ {obs,-30} ║\n"
    );
}

public void FinTablaCoche()
{
    Console.ForegroundColor = ConsoleColor.Yellow;
    Console.WriteLine("╚════╩═══════════════════╩════════════╩════════════╩════════════╩════════════╩══════════════════╩══════════════════════════════════╝");
    Console.ResetColor();
}
    }
}
