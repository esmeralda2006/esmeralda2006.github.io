namespace HondaFactory.Modelos
{
    public class Coche
    {

         public int IdLogico { get; set; }
        // ATRIBUTOS DE LA BD
        public string Vin { get; set; } = "";  
        public int ModeloId { get; set; }
        public int? ColorId { get; set; }
        public int? PaqueteId { get; set; }
        public string? MotorSerie { get; set; }
        public string? Observaciones { get; set; }
        public DateTime? FechaFabricacion { get; set; }

       //ATRIBUTOS RECUPERAR NOMBRE DE LA FK DE MODELO,COLOR Y PAQUETE
        public string? ModeloNombre { get; set; }
        public string? ColorNombre { get; set; }
        public string? PaqueteNombre { get; set; }

      
       
    }
}
