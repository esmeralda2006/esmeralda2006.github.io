namespace HondaFactory.Modelos
{
    public class Motor
    {
        public string Serie { get; set; } = ""; 
        public int MotorTipoId { get; set; }
        public DateTime FechaFabricacion { get; set; } = DateTime.Now;
        public double PotenciaKw { get; set; }
        public double EmisionesWltp { get; set; }
        public string Estado { get; set; } = "En almacén";
        public bool Montado { get; set; } = false;

        public string Observaciones { get; set; } = "";
        
         public int IdLogico { get; set; }
    }
}
