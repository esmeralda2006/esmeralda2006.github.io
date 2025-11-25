using HondaFactory.Modelos;
using HondaFactory.Repositorio;
using HondaFactory.Vista;
using System;
using System.Collections.Generic;
using System.Linq;

namespace HondaFactory.Controlador
{
    public class PaqueteControlador
    {
        private readonly PaqueteRepositorio repo = new();

        // USAMOS LA CLASE VISTA REAL
        private readonly VistaBase vista = new();

        private static readonly Dictionary<int, int> mapa = new();

        // ======================================================
        // LISTAR
        // ======================================================
        public List<Paquete> Listar()
        {
            mapa.Clear();
            var listaBD = repo.ListarBD();
            var listaFinal = new List<Paquete>();

            Console.Clear();
            vista.TituloListaPaquete();

            if (listaBD.Count == 0)
            {
                vista.Error("No hay paquetes registrados.");
                return listaFinal;
            }

            vista.CabeceraTablaPaquete();

            int idLogico = 1;

            foreach (var p in listaBD)
            {
                mapa[idLogico] = p.Id;

                var fila = new Paquete
                {
                    Id = p.Id,
                    Nombre = p.Nombre,
                    Descripcion = p.Descripcion,
                    IdLogico = idLogico
                };

                listaFinal.Add(fila);

                vista.FilaPaquete(
                    idLogico,
                    p.Nombre,
                    p.Descripcion
                );

                idLogico++;
            }

            vista.FinTablaPaquete();
            return listaFinal;
        }

        // ======================================================
        // AGREGAR
        // ======================================================
        public void Agregar()
        {
            Console.Clear();
            vista.TituloAgregarPaquete();

            Paquete p = new();

            // Nombre
            vista.Input("Nombre del paquete: ");
            p.Nombre = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(p.Nombre))
            {
                vista.Error("El nombre no puede estar vacío.");
                return;
            }

            // Descripción
            vista.Input("Descripción: ");
            p.Descripcion = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(p.Descripcion))
            {
                vista.Error("La descripción no puede estar vacía.");
                return;
            }

            try
            {
                int idReal = repo.Insertar(p);

                int nuevoLogico = mapa.Count == 0 ? 1 : mapa.Keys.Max() + 1;
                mapa[nuevoLogico] = idReal;
                p.IdLogico = nuevoLogico;

                vista.Ok("Paquete agregado correctamente.");
            }
            catch (Exception ex)
            {
                vista.Error("Error: " + ex.Message);
            }

            vista.Pausa();
        }

        // ======================================================
        // BUSCAR POR ID LÓGICO
        // ======================================================
        public Paquete? BuscarPorIdLogico(int idLogico)
        {
            if (!mapa.ContainsKey(idLogico))
                return null;

            int idReal = mapa[idLogico];

            return repo.ListarBD().FirstOrDefault(p => p.Id == idReal);
        }

        // ======================================================
        // EDITAR
        // ======================================================
        public void Editar()
        {
            Console.Clear();
            vista.TituloEditarPaquete();

            var lista = Listar();
            if (lista.Count == 0) return;

            vista.Input("ID lógico a editar: ");
            if (!int.TryParse(Console.ReadLine(), out int idLogico) || !mapa.ContainsKey(idLogico))
            {
                vista.Error("ID inválido.");
                return;
            }

            var existente = BuscarPorIdLogico(idLogico);
            if (existente == null)
            {
                vista.Error("Ese paquete no existe.");
                return;
            }

            // Nombre
            vista.Input($"Nuevo nombre ({existente.Nombre}): ");
            string nuevoNombre = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(nuevoNombre))
                nuevoNombre = existente.Nombre;

            // Descripción
            vista.Input($"Nueva descripción ({existente.Descripcion}): ");
            string nuevaDesc = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(nuevaDesc))
                nuevaDesc = existente.Descripcion;

            try
            {
                repo.Actualizar(new Paquete
                {
                    Id = existente.Id,
                    Nombre = nuevoNombre,
                    Descripcion = nuevaDesc
                });

                vista.Ok("Paquete actualizado correctamente.");
            }
            catch (Exception ex)
            {
                vista.Error("Error: " + ex.Message);
            }

            vista.Pausa();
        }
    }
}
