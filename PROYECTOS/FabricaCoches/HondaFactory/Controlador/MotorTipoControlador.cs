using HondaFactory.Modelos;
using HondaFactory.Repositorio;
using HondaFactory.Vista;
using System;
using System.Collections.Generic;
using System.Linq;

namespace HondaFactory.Controlador
{
    public class MotorTipoControlador
    {
        private readonly MotorTipoRepositorio repo = new();

        // Vista REAL
        private readonly VistaBase vista = new();

        private static readonly Dictionary<int, int> mapa = new();

        // =====================================================
        // LISTAR
        // =====================================================
        public List<MotorTipo> Listar()
        {
            mapa.Clear();
            var listaBD = repo.ListarBD();
            var listaFinal = new List<MotorTipo>();

            Console.Clear();
            vista.TituloListaMotorTipo();

            if (listaBD.Count == 0)
            {
                vista.Error("No hay tipos de motor registrados.");
                return listaFinal;
            }

            vista.CabeceraTablaMotorTipo();

            int idLogico = 1;

            foreach (var t in listaBD)
            {
                mapa[idLogico] = t.Id;

                listaFinal.Add(new MotorTipo
                {
                    Id = t.Id,
                    IdLogico = idLogico,
                    Codigo = t.Codigo,
                    Descripcion = t.Descripcion,
                    CilindradaCc = t.CilindradaCc,
                    Alimentacion = t.Alimentacion
                });

                vista.FilaMotorTipo(
                    idLogico,
                    t.Codigo,
                    t.Descripcion,
                    t.CilindradaCc,
                    t.Alimentacion
                );

                idLogico++;
            }

            vista.FinTablaMotorTipo();
            return listaFinal;
        }

        // =====================================================
        // BUSCAR POR ID LÓGICO
        // =====================================================
        public MotorTipo? BuscarPorIdLogico(int idLogico)
        {
            if (!mapa.ContainsKey(idLogico))
                return null;

            int idReal = mapa[idLogico];
            return repo.ListarBD().FirstOrDefault(x => x.Id == idReal);
        }

        // =====================================================
        // AGREGAR
        // =====================================================
        public void Agregar()
        {
            Console.Clear();
            vista.TituloAgregarMotorTipo();

            var tipo = new MotorTipo();

            // Código
            vista.Input("Código: ");
            tipo.Codigo = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(tipo.Codigo))
            {
                vista.Error("El código no puede estar vacío.");
                return;
            }

            // Descripción
            vista.Input("Descripción: ");
            tipo.Descripcion = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(tipo.Descripcion))
            {
                vista.Error("La descripción no puede estar vacía.");
                return;
            }

            // Cilindrada
            vista.Input("Cilindrada (cc): ");
            tipo.CilindradaCc = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(tipo.CilindradaCc))
            {
                vista.Error("La cilindrada no puede estar vacía.");
                return;
            }

            // Alimentación
            vista.Input("Alimentación: ");
            tipo.Alimentacion = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(tipo.Alimentacion))
            {
                vista.Error("La alimentación no puede estar vacía.");
                return;
            }

            // Insertar
            repo.Insertar(tipo);

            int newLogico = mapa.Count == 0 ? 1 : mapa.Keys.Max() + 1;
            mapa[newLogico] = tipo.Id;
            tipo.IdLogico = newLogico;

            vista.Ok("Tipo de motor agregado correctamente.");
            vista.Pausa();
        }

        // =====================================================
        // EDITAR
        // =====================================================
        public void Editar()
        {
            Console.Clear();
            vista.TituloEditarMotorTipo();

            var lista = Listar();
            if (lista.Count == 0)
                return;

            vista.Input("\nSeleccione ID lógico: ");
            if (!int.TryParse(Console.ReadLine(), out int idLogico))
            {
                vista.Error("ID inválido.");
                return;
            }

            var existente = BuscarPorIdLogico(idLogico);
            if (existente == null)
            {
                vista.Error("No existe ese tipo de motor.");
                return;
            }

            // Campos editables
            vista.Input($"Nueva descripción ({existente.Descripcion}): ");
            string newDesc = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(newDesc))
                newDesc = existente.Descripcion;

            vista.Input($"Nueva cilindrada ({existente.CilindradaCc}): ");
            string newCil = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(newCil))
                newCil = existente.CilindradaCc;

            vista.Input($"Nueva alimentación ({existente.Alimentacion}): ");
            string newAli = (Console.ReadLine() ?? "").Trim();
            if (string.IsNullOrWhiteSpace(newAli))
                newAli = existente.Alimentacion;

            // Guardar cambios
            existente.Descripcion = newDesc;
            existente.CilindradaCc = newCil;
            existente.Alimentacion = newAli;

            repo.Actualizar(existente);

            vista.Ok("Tipo de motor actualizado correctamente.");
            vista.Pausa();
        }

        public MotorTipo? BuscarPorIdReal(int idReal)
{
    return repo.ListarBD().FirstOrDefault(x => x.Id == idReal);
}

    }
}
