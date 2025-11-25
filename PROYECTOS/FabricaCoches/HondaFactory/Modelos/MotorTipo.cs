namespace HondaFactory.Modelos
{
    public class MotorTipo
    {
        public int Id { get; set; }
         public int IdLogico { get; set; }
        public string Codigo { get; set; } = "";
        public string Descripcion { get; set; } = "";
        public string CilindradaCc { get; set; } = "";
        public string Alimentacion { get; set; } = "";
    }
}
