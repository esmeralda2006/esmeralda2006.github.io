using HondaFactory.Modelos;
using HondaFactory.Repositorio;
using HondaFactory.Vista;
using System;
using System.Collections.Generic;
using System.Linq;

namespace HondaFactory.Controlador
{
    public class ModeloControlador
    {
        private readonly ModeloRepositorio repo = new();

        // USAR LA VISTA REAL
        private readonly VistaBase vista = new();

        private static readonly Dictionary<int, int> mapa = new();

        // ============================================================
        // LISTAR
        // ============================================================
        public List<Modelo> Listar()
        {
            mapa.Clear();
            var listaBD = repo.ListarBD();
            var listaFinal = new List<Modelo>();

            Console.Clear();
            vista.TituloListaModelo();

            if (listaBD.Count == 0)
            {
                vista.Error("No hay modelos registrados.");
                return listaFinal;
            }

            vista.CabeceraTablaModelo();

            int idLogico = 1;

            foreach (var m in listaBD)
            {
                mapa[idLogico] = m.Id;

                listaFinal.Add(new Modelo
                {
                    Id = m.Id,
                    IdLogico = idLogico,
                    Nombre = m.Nombre,
                    CodigoModelo = m.CodigoModelo,
                    Segmento = m.Segmento
                });

                vista.FilaModelo(
                    idLogico,
                    m.Nombre,
                    m.CodigoModelo,
                    m.Segmento
                );

                idLogico++;
            }

            vista.FinTablaModelo();
            return listaFinal;
        }

        // ============================================================
        // AGREGAR
        // ============================================================
        public void Agregar()
        {
            Console.Clear();
            vista.Ok("=== NUEVO MODELO ===\n");

            Modelo m = new();

            // NOMBRE
            vista.Input("Nombre: ");
            m.Nombre = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(m.Nombre))
            {
                vista.Error("El nombre no puede estar vacío.");
                return;
            }

            // CÓDIGO
            vista.Input("Código del Modelo: ");
            m.CodigoModelo = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(m.CodigoModelo))
            {
                vista.Error("El código no puede estar vacío.");
                return;
            }

            // SEGMENTO
            vista.Input("Segmento: ");
            m.Segmento = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(m.Segmento))
            {
                vista.Error("El segmento no puede estar vacío.");
                return;
            }

            // BD
            int idReal = repo.Insertar(m);

            int nuevoLogico = mapa.Count == 0 ? 1 : mapa.Keys.Max() + 1;
            mapa[nuevoLogico] = idReal;
            m.IdLogico = nuevoLogico;

            vista.Ok("Modelo agregado correctamente.");
        }

        // ============================================================
        // BUSCAR
        // ============================================================
        public Modelo? BuscarPorIdLogico(int idLogico)
        {
            var lista = Listar();
            return lista.FirstOrDefault(m => m.IdLogico == idLogico);
        }

        // ============================================================
        // EDITAR
        // ============================================================
        public void Editar()
        {
            Console.Clear();
            var lista = Listar();

            if (lista.Count == 0)
            {
                vista.Error("No hay modelos para editar.");
                return;
            }

            vista.Input("\nID lógico a editar: ");
            if (!int.TryParse(Console.ReadLine(), out int id))
            {
                vista.Error("ID inválido.");
                return;
            }

            Modelo? actual = BuscarPorIdLogico(id);
            if (actual == null)
            {
                vista.Error("Modelo no encontrado.");
                return;
            }

            // NOMBRE
            vista.Input($"Nombre ({actual.Nombre}): ");
            string nombre = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(nombre)) nombre = actual.Nombre;

            // CÓDIGO
            vista.Input($"Código Modelo ({actual.CodigoModelo}): ");
            string codigo = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(codigo)) codigo = actual.CodigoModelo;

            // SEGMENTO
            vista.Input($"Segmento ({actual.Segmento}): ");
            string segmento = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(segmento)) segmento = actual.Segmento;

            repo.Actualizar(new Modelo
            {
                Id = actual.Id,
                Nombre = nombre,
                CodigoModelo = codigo,
                Segmento = segmento
            });

            vista.Ok("Modelo actualizado correctamente.");
        }
    }
}
