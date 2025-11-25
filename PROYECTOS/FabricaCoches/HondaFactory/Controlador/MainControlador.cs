using HondaFactory.Controlador;
using HondaFactory.Vista;

namespace HondaFactory
{
    public class MainControlador
    {
        private readonly MenuVista menú;
        private readonly VistaBase vista;

        private readonly CocheControlador cocheCtrl;
        private readonly ModeloControlador modeloCtrl;
        private readonly ColorControlador colorCtrl;
        private readonly PaqueteControlador paqueteCtrl;
        private readonly MotorControlador motorCtrl;
        private readonly MotorTipoControlador motorTipoCtrl;

        public MainControlador()
        {
            menú = new MenuVista();
            vista = new VistaBase();

            cocheCtrl = new CocheControlador();
            modeloCtrl = new ModeloControlador();
            colorCtrl = new ColorControlador();
            paqueteCtrl = new PaqueteControlador();
            motorCtrl = new MotorControlador();
            motorTipoCtrl = new MotorTipoControlador();
        }

        // ======================================================
        // INICIO DEL MENÚ PRINCIPAL
        // ======================================================
        public void Iniciar()
        {
            int opcion;

            do
            {
                vista.Limpiar();
                menú.MenuPrincipal();
                opcion = vista.LeerOpcion();

                switch (opcion)
                {
                    case 1: MenuCoches(); break;
                    case 2: MenuModelos(); break;
                    case 3: MenuPaquetes(); break;
                    case 4: MenuColores(); break;
                    case 5: MenuMotores(); break;
                    case 6: MenuMotorTipos(); break;
                    case 7:
                        vista.Ok("Saliendo...");
                        break;

                    default:
                        vista.Error("Opción inválida.");
                        break;
                }

                if (opcion != 7)
                    vista.Pausa();

            } while (opcion != 7);
        }

        // ======================================================
        // SUBMENÚ COCHES
        // ======================================================
        private void MenuCoches()
        {
            int opcion;

            do
            {
                vista.Limpiar();
                menú.MenuCoches();
                opcion = vista.LeerOpcion();

                switch (opcion)
                {
                    case 1: cocheCtrl.Listar(); break;
                    case 2: cocheCtrl.Agregar(); break;
                    case 3: cocheCtrl.EditarCampoMenu(); break;
                    case 4: cocheCtrl.AsignarOModificarMotor(); break;
                    case 5: return;

                    default:
                        vista.Error("Opción inválida.");
                        break;
                }

                vista.Pausa();

            } while (true);
        }

        // ======================================================
        // SUBMENÚS GENÉRICOS
        // ======================================================
        private void MenuModelos() => EjecutarGenerico(modeloCtrl);
        private void MenuPaquetes() => EjecutarGenerico(paqueteCtrl);
        private void MenuColores() => EjecutarGenerico(colorCtrl);
        private void MenuMotores() => EjecutarGenerico(motorCtrl);
        private void MenuMotorTipos() => EjecutarGenerico(motorTipoCtrl);

        private void EjecutarGenerico(dynamic controlador)
        {
            int opcion;

            do
            {
                vista.Limpiar();
                menú.MenuGeneral();
                opcion = vista.LeerOpcion();

                switch (opcion)
                {
                    case 1: controlador.Listar(); break;
                    case 2: controlador.Agregar(); break;
                    case 3: controlador.Editar(); break;
                    case 4: return;

                    default:
                        vista.Error("Opción inválida.");
                        break;
                }

                vista.Pausa();

            } while (true);
        }
    }
}
