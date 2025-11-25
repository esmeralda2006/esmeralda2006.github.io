using HondaFactory.Modelos;
using HondaFactory.Repositorio;
using HondaFactory.Vista;
using System;
using System.Collections.Generic;
using System.Linq;

namespace HondaFactory.Controlador
{
    public class CocheControlador
    {
        private readonly CocheRepositorio repo = new();
        private readonly ModeloControlador modeloCtrl = new();
        private readonly ColorControlador colorCtrl = new();
        private readonly PaqueteControlador paqueteCtrl = new();
        private readonly MotorControlador motorCtrl = new();

        private readonly VistaBase vista = new();
        private static Dictionary<int, string> mapa = new();

        private const int COLOR_POR_DEFECTO = 46;
        private const int PAQUETE_POR_DEFECTO = 31;

        // ===========================================================
        // LISTAR
        // ===========================================================
        public void Listar()
        {
            vista.Limpiar();
            var lista = repo.ListarBD();
            mapa.Clear();

            vista.TituloListaCoche();

            if (!lista.Any())
            {
                vista.Error("No hay coches registrados.");
                return;
            }

            vista.CabeceraTablaCoche();

            int i = 1;
            foreach (var c in lista)
            {
                mapa[i] = c.Vin;

                string fecha = c.FechaFabricacion?.ToString("dd/MM/yyyy") ?? "Sin fecha";

                vista.FilaCoche(
                    i,
                    c.Vin,
                    c.ModeloNombre ?? "N/A",
                    c.ColorNombre ?? "N/A",
                    c.PaqueteNombre ?? "N/A",
                    c.MotorSerie ?? "Sin motor",
                    fecha,
                    c.Observaciones ?? ""
                );

                i++;
            }

            vista.FinTablaCoche();
        }

        // ===========================================================
        // AGREGAR
        // ===========================================================
        public void Agregar()
        {
            vista.Limpiar();
            vista.Ok("=== CREAR COCHE ===");

            string vin;
            while (true)
            {
                vin = vista.LeerTexto("VIN (17 caracteres): ").Trim();
                if (vin.Length == 17) break;

                vista.Error("El VIN debe tener exactamente 17 caracteres.");
            }

            // MODELO
            var modelos = modeloCtrl.Listar();
            if (!modelos.Any())
            {
                vista.Error("No hay modelos registrados.");
                return;
            }

            foreach (var m in modelos)
                vista.Mensaje($"{m.IdLogico}. {m.Nombre}");

            int idModelo = vista.LeerEntero("Modelo: ");
            var modelo = modeloCtrl.BuscarPorIdLogico(idModelo);

            if (modelo == null)
            {
                vista.Error("Modelo inválido.");
                return;
            }

            var coche = new Coche
            {
                Vin = vin,
                ModeloId = modelo.Id,
                ColorId = COLOR_POR_DEFECTO,
                PaqueteId = PAQUETE_POR_DEFECTO,
                MotorSerie = null,
                Observaciones = "",
                FechaFabricacion = DateTime.Now
            };

            repo.Insertar(coche);
            vista.Ok("Coche registrado correctamente.");
        }

        // ===========================================================
        // EDITAR POR FASES
        // ===========================================================
        public void EditarCampoMenu()
        {
            Listar();

            int id = vista.LeerEntero("\nID coche: ");
            var coche = BuscarPorId(id);

            if (coche == null)
            {
                vista.Error("Ese coche no existe.");
                return;
            }

            int opcion;
            var menu = new MenuVista();

            do
            {
                vista.Limpiar();
                menu.MenuEditarCoche();
                opcion = vista.LeerOpcion();

                switch (opcion)
                {
                    case 1:
                        CambiarColor(id);
                        break;

                    case 2:
                        CambiarPaquete(id);
                        break;

                    case 3:
                        string entradaFecha = vista.LeerTexto("Nueva fecha (dd/mm/yyyy): ");
                        if (DateTime.TryParse(entradaFecha, out var nueva))
                        {
                            coche.FechaFabricacion = nueva;
                            repo.Actualizar(coche);
                            vista.Ok("Fecha actualizada.");
                        }
                        else vista.Error("Formato incorrecto.");
                        break;

                    case 4:
                        coche.Observaciones = vista.LeerTexto("Observaciones: ");
                        repo.Actualizar(coche);
                        vista.Ok("Observaciones actualizadas.");
                        break;

                    case 5:
                        return;

                    default:
                        vista.Error("Opción inválida.");
                        break;
                }

                vista.Pausa();

            } while (true);
        }

        // ===========================================================
        // CAMBIAR COLOR
        // ===========================================================
        public void CambiarColor(int idLogico)
        {
            var colores = colorCtrl.Listar();
            if (!colores.Any())
            {
                vista.Error("No hay colores disponibles.");
                return;
            }

            foreach (var c in colores)
                vista.Mensaje($"{c.IdLogico}. {c.Nombre}");

            int idColor = vista.LeerEntero("Color: ");
            var col = colorCtrl.BuscarPorIdLogico(idColor);

            if (col == null)
            {
                vista.Error("Color inválido.");
                return;
            }

            EditarCampo(idLogico, color: col.Id);
            vista.Ok("Color actualizado.");
        }

        // ===========================================================
        // CAMBIAR PAQUETE
        // ===========================================================
        public void CambiarPaquete(int idLogico)
        {
            var paquetes = paqueteCtrl.Listar();
            if (!paquetes.Any())
            {
                vista.Error("No hay paquetes disponibles.");
                return;
            }

            foreach (var p in paquetes)
                vista.Mensaje($"{p.IdLogico}. {p.Nombre}");

            int idPaq = vista.LeerEntero("Paquete: ");
            var paq = paqueteCtrl.BuscarPorIdLogico(idPaq);

            if (paq == null)
            {
                vista.Error("Paquete inválido.");
                return;
            }

            EditarCampo(idLogico, paquete: paq.Id);
            vista.Ok("Paquete actualizado.");
        }

        // ===========================================================
        // MOTOR
        // ===========================================================
        public void AsignarOModificarMotor()
        {
            Listar();

            int id = vista.LeerEntero("\nID coche: ");
            var coche = BuscarPorId(id);

            if (coche == null)
            {
                vista.Error("No existe ese coche.");
                return;
            }

            vista.Mensaje("1. Asignar motor");
            vista.Mensaje("2. Quitar motor");

            int op = vista.LeerEntero("Opción: ");

            if (op == 1) AsignarMotor(id);
            else if (op == 2) QuitarMotor(id);
            else vista.Error("Opción inválida.");
        }

        public void AsignarMotor(int idLogico)
        {
            var disp = motorCtrl.ListarDisponibles();
            if (!disp.Any())
            {
                vista.Error("No hay motores disponibles.");
                return;
            }

            foreach (var m in disp)
                vista.Mensaje($"{m.IdLogico}. {m.Serie}");

            int id = vista.LeerEntero("Motor: ");
            var motor = disp.FirstOrDefault(x => x.IdLogico == id);

            if (motor == null)
            {
                vista.Error("Motor inválido.");
                return;
            }

            EditarCampo(idLogico, motor: motor.Serie);
            vista.Ok("Motor asignado.");
        }

        public void QuitarMotor(int idLogico)
        {
            var coche = BuscarPorId(idLogico);

            if (coche == null)
            {
                vista.Error("Coche no encontrado.");
                return;
            }

            if (string.IsNullOrWhiteSpace(coche.MotorSerie))
            {
                vista.Error("El coche ya no tiene motor.");
                return;
            }

            coche.MotorSerie = null;
            repo.Actualizar(coche);

            vista.Ok("Motor quitado.");
        }

        // ===========================================================
        // UTILIDADES
        // ===========================================================
        private void EditarCampo(int idLogico, int? color = null, int? paquete = null, string? motor = null, string? obs = null)
        {
            var coche = BuscarPorId(idLogico);
            if (coche == null) return;

            if (color != null) coche.ColorId = color;
            if (paquete != null) coche.PaqueteId = paquete;
            if (motor != null) coche.MotorSerie = motor;
            if (obs != null) coche.Observaciones = obs;

            repo.Actualizar(coche);
        }

        private Coche? BuscarPorId(int idLogico)
        {
            if (!mapa.ContainsKey(idLogico)) return null;

            string vin = mapa[idLogico];
            return repo.ListarBD().FirstOrDefault(c => c.Vin == vin);
        }
    }
}
