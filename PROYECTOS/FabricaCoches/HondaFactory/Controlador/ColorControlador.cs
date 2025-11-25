using HondaFactory.Modelos;
using HondaFactory.Repositorio;
using HondaFactory.Vista;
using System;
using System.Collections.Generic;
using System.Linq;

namespace HondaFactory.Controlador
{
    public class ColorControlador
    {
        private readonly ColorRepositorio repo = new();

        // Usamos la clase Vista REAL
        private readonly VistaBase vista = new();

        private static readonly Dictionary<int, int> mapa = new();

        // =====================================================
        // LISTAR CON TABLA BONITA
        // =====================================================
        public List<Color> Listar()
        {
            mapa.Clear();
            var listaBD = repo.ListarBD();
            var listaFinal = new List<Color>();

            Console.Clear();
            vista.TituloListaColor();

            if (listaBD.Count == 0)
            {
                vista.Error("No hay colores registrados.");
                return listaFinal;
            }

            vista.CabeceraTablaColor();

            int idLogico = 1;
            foreach (var c in listaBD)
            {
                mapa[idLogico] = c.Id;

                listaFinal.Add(new Color
                {
                    Id = c.Id,
                    IdLogico = idLogico,
                    Nombre = c.Nombre,
                    Codigo = c.Codigo,
                    Acabado = c.Acabado
                });

                vista.FilaColor(idLogico, c.Nombre, c.Codigo, c.Acabado);

                idLogico++;
            }

            vista.FinTablaColor();
            return listaFinal;
        }

        // =====================================================
        // AGREGAR
        // =====================================================
        public void Agregar()
        {
            Console.Clear();
            vista.Ok("=== NUEVO COLOR ===");

            Color c = new();

            vista.Input("Nombre: ");
            c.Nombre = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(c.Nombre))
            {
                vista.Error("El nombre no puede estar vacío.");
                return;
            }

            vista.Input("Código de pintura: ");
            c.Codigo = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(c.Codigo))
            {
                vista.Error("El código no puede estar vacío.");
                return;
            }

            vista.Input("Acabado: ");
            c.Acabado = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(c.Acabado))
            {
                vista.Error("El acabado no puede estar vacío.");
                return;
            }

            try
            {
                int idReal = repo.Insertar(c);
                int nuevoLogico = mapa.Count == 0 ? 1 : mapa.Keys.Max() + 1;

                mapa[nuevoLogico] = idReal;
                c.IdLogico = nuevoLogico;

                vista.Ok("Color agregado correctamente.");
            }
            catch (Exception ex)
            {
                vista.Error("Error al agregar color: " + ex.Message);
            }

            vista.Pausa();
        }

        // =====================================================
        // BUSCAR POR ID LÓGICO
        // =====================================================
        public Color? BuscarPorIdLogico(int idLogico)
        {
            var lista = Listar();
            return lista.FirstOrDefault(c => c.IdLogico == idLogico);
        }

        // =====================================================
        // EDITAR
        // =====================================================
        public void Editar()
        {
            Console.Clear();
            vista.Ok("=== EDITAR COLOR ===");

            var colores = Listar();
            if (colores.Count == 0)
            {
                vista.Error("No hay colores registrados.");
                return;
            }

            vista.Input("\nSeleccione ID lógico: ");
            if (!int.TryParse(Console.ReadLine(), out int idLogico))
            {
                vista.Error("ID inválido.");
                return;
            }

            if (!mapa.ContainsKey(idLogico))
            {
                vista.Error("No existe ese ID lógico.");
                return;
            }

            var actual = BuscarPorIdLogico(idLogico);
            if (actual == null)
            {
                vista.Error("Color no encontrado.");
                return;
            }

            vista.Input($"Nuevo nombre ({actual.Nombre}): ");
            string nombre = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(nombre)) nombre = actual.Nombre;

            vista.Input($"Nuevo código ({actual.Codigo}): ");
            string codigo = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(codigo)) codigo = actual.Codigo;

            vista.Input($"Nuevo acabado ({actual.Acabado}): ");
            string acabado = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(acabado)) acabado = actual.Acabado;

            try
            {
                repo.Actualizar(new Color
                {
                    Id = actual.Id,
                    Nombre = nombre,
                    Codigo = codigo,
                    Acabado = acabado
                });

                vista.Ok("Color actualizado correctamente.");
            }
            catch (Exception ex)
            {
                vista.Error("Error al actualizar color: " + ex.Message);
            }

            vista.Pausa();
        }
    }
}
