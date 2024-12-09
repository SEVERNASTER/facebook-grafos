public class Estadistica{
    private int totalSeguidores;
    private int totaReacciones;
    public Estadistica(int totalSeguidores, int totaReacciones){
        this.totalSeguidores = totalSeguidores;
        this.totaReacciones = totaReacciones;
    }
    
    public int getTotalSeguidores(){
        return totalSeguidores;
    }
    
    public int getTotalReacciones(){
        return totaReacciones;
    }
}
