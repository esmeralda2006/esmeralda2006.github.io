namespace HondaFactory.Vista
{
    public class MenuVista
    {
        public void MenuPrincipal()
        {
            Console.WriteLine("===== FÁBRICA HONDA =====");
            Console.WriteLine("1. Administrar Coches");
            Console.WriteLine("2. Administrar Modelos");
            Console.WriteLine("3. Administrar Paquetes");
            Console.WriteLine("4. Administrar Colores");
            Console.WriteLine("5. Administrar Motores");
            Console.WriteLine("6. Administrar Tipos de Motor");
            Console.WriteLine("7. Salir");
           
        }

        public void MenuCoches()
        {
            Console.WriteLine("----- COCHES -----");
            Console.WriteLine("1. Listar coches");
            Console.WriteLine("2. Crear coche nuevo");
            Console.WriteLine("3. Editar coche por fases");
            Console.WriteLine("4. Asignar o Cambiar Motor");
            Console.WriteLine("5. Volver");
            
        }

        public void MenuGeneral()
        {
            Console.WriteLine("----- OPCIONES -----");
            Console.WriteLine("1. Listar");
            Console.WriteLine("2. Añadir");
            Console.WriteLine("3. Editar");
            Console.WriteLine("4. Volver");
          
        }

        public void MenuEditarCoche()
        {
            Console.WriteLine("----- EDITAR COCHE -----");
            Console.WriteLine("1. Cambiar color");
            Console.WriteLine("2. Cambiar paquete");
            Console.WriteLine("3. Cambiar fecha fabricación");
            Console.WriteLine("4. Añadir / Cambiar observaciones");
            Console.WriteLine("5. Volver");
            
        }
    }
}

