using HondaFactory.Modelos;
using HondaFactory.Repositorio;
using HondaFactory.Vista;
using System;
using System.Collections.Generic;
using System.Linq;

namespace HondaFactory.Controlador
{
    public class MotorControlador
    {
        private readonly MotorRepositorio repo = new();
        private readonly MotorTipoControlador tipoCtrl = new();

        // Vista correcta
        private readonly VistaBase vista = new();

        private static Dictionary<int, string> mapa = new();

        // ============================================================
        // LISTAR
        // ============================================================
        public List<Motor> Listar()
        {
            mapa.Clear();
            var listaBD = repo.ListarBD();
            var listaFinal = new List<Motor>();

            Console.Clear();
            vista.TituloListaMotor();

            if (!listaBD.Any())
            {
                vista.Error("No hay motores registrados.");
                return listaFinal;
            }

            vista.CabeceraTablaMotor();

            int idLogico = 1;

            foreach (var m in listaBD)
            {
                // Buscamos tipo por ID REAL, no lógico
                var tipo = tipoCtrl.BuscarPorIdReal(m.MotorTipoId);

                mapa[idLogico] = m.Serie;

                listaFinal.Add(new Motor
                {
                    Serie = m.Serie,
                    IdLogico = idLogico,
                    MotorTipoId = m.MotorTipoId,
                    PotenciaKw = m.PotenciaKw,
                    EmisionesWltp = m.EmisionesWltp,
                    FechaFabricacion = m.FechaFabricacion
                });

                vista.FilaMotor(
                    idLogico,
                    m.Serie,
                    tipo?.Descripcion ?? "N/A",
                    m.PotenciaKw,
                    m.EmisionesWltp
                );

                idLogico++;
            }

            vista.FinTablaMotor();
            return listaFinal;
        }

        // ============================================================
        // BUSCAR POR ID LÓGICO
        // ============================================================
        public Motor? BuscarPorIdLogico(int id)
        {
            if (!mapa.ContainsKey(id)) return null;

            string serie = mapa[id];
            return repo.ListarBD().FirstOrDefault(x => x.Serie == serie);
        }

        // ============================================================
        // AGREGAR
        // ============================================================
        public void Agregar()
        {
            Console.Clear();
            vista.Mensaje("=== NUEVO MOTOR ===\n");

            Motor motor = new();

            // SERIE
            vista.Input("Número de serie: ");
            motor.Serie = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(motor.Serie))
            {
                vista.Error("El número de serie no puede estar vacío.");
                return;
            }

            // TIPOS
            var tipos = tipoCtrl.Listar();
            if (!tipos.Any())
            {
                vista.Error("Debe registrar primero tipos de motor.");
                return;
            }

            vista.Mensaje("\nTIPOS DISPONIBLES:");
            foreach (var t in tipos)
                vista.Mensaje($"{t.IdLogico}. {t.Descripcion} ({t.CilindradaCc} cc)");

            vista.Input("Seleccione tipo de motor: ");
            if (!int.TryParse(Console.ReadLine(), out int idLogicoTipo))
            {
                vista.Error("Tipo inválido.");
                return;
            }

            var tipoSel = tipoCtrl.BuscarPorIdLogico(idLogicoTipo);
            if (tipoSel == null)
            {
                vista.Error("Tipo de motor inexistente.");
                return;
            }

            motor.MotorTipoId = tipoSel.Id;

            // POTENCIA
            vista.Input("Potencia (kW): ");
            if (!double.TryParse(Console.ReadLine(), out double kw))
            {
                vista.Error("Potencia inválida.");
                return;
            }
            motor.PotenciaKw = kw;

            // WLTP
            vista.Input("Emisiones WLTP: ");
            if (!double.TryParse(Console.ReadLine(), out double wltp))
            {
                vista.Error("WLTP inválido.");
                return;
            }
            motor.EmisionesWltp = wltp;

            motor.FechaFabricacion = DateTime.Now;

            repo.Insertar(motor);

            int nuevoLogico = mapa.Count == 0 ? 1 : mapa.Keys.Max() + 1;
            mapa[nuevoLogico] = motor.Serie;

            vista.Ok("Motor agregado correctamente.");
            vista.Pausa();
        }

        // ============================================================
        // EDITAR
        // ============================================================
        public void Editar()
        {
            Console.Clear();
            var lista = Listar();

            if (!lista.Any())
            {
                vista.Error("No hay motores a editar.");
                return;
            }

            vista.Input("\nID lógico del motor a editar: ");
            if (!int.TryParse(Console.ReadLine(), out int id))
            {
                vista.Error("ID inválido.");
                return;
            }

            var motor = BuscarPorIdLogico(id);
            if (motor == null)
            {
                vista.Error("Ese motor no existe.");
                return;
            }

            // TIPOS
            var tipos = tipoCtrl.Listar();
            vista.Mensaje("\nTIPOS DISPONIBLES:");
            foreach (var t in tipos)
                vista.Mensaje($"{t.IdLogico}. {t.Descripcion}");

            vista.Input("Nuevo tipo: ");
            string tipoStr = Console.ReadLine() ?? "";
            if (int.TryParse(tipoStr, out int tipoLogicoNuevo))
            {
                var tipoSel = tipoCtrl.BuscarPorIdLogico(tipoLogicoNuevo);
                if (tipoSel != null)
                    motor.MotorTipoId = tipoSel.Id;
            }

            // POTENCIA
            vista.Input($"Nueva potencia ({motor.PotenciaKw}): ");
            string potStr = Console.ReadLine() ?? "";
            if (!string.IsNullOrWhiteSpace(potStr))
            {
                if (double.TryParse(potStr, out double nuevaPot))
                    motor.PotenciaKw = nuevaPot;
                else
                    vista.Error("Potencia inválida. Se mantiene el valor anterior.");
            }

            // WLTP
            vista.Input($"Nuevo WLTP ({motor.EmisionesWltp}): ");
            string wltpStr = Console.ReadLine() ?? "";
            if (!string.IsNullOrWhiteSpace(wltpStr))
            {
                if (double.TryParse(wltpStr, out double nuevoWltp))
                    motor.EmisionesWltp = nuevoWltp;
                else
                    vista.Error("WLTP inválido. Se mantiene el anterior.");
            }

            repo.Actualizar(motor);
            vista.Ok("Motor actualizado correctamente.");
            vista.Pausa();
        }

        // ============================================================
        // LISTAR DISPONIBLES
        // ============================================================
        public List<Motor> ListarDisponibles()
        {
            mapa.Clear();

            var disponibles = repo.ListarBD()
                                  .Where(m => !repo.EstaMontado(m.Serie))
                                  .ToList();

            var final = new List<Motor>();
            int id = 1;

            foreach (var m in disponibles)
            {
                mapa[id] = m.Serie;

                final.Add(new Motor
                {
                    Serie = m.Serie,
                    IdLogico = id,
                    MotorTipoId = m.MotorTipoId,
                    PotenciaKw = m.PotenciaKw,
                    EmisionesWltp = m.EmisionesWltp,
                    FechaFabricacion = m.FechaFabricacion
                });

                id++;
            }

            return final;
        }
    }
}
