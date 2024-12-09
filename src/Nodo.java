public class Nodo{
    private int costo;
    private String destino;
    public Nodo(int costo, String destino){
        this.costo = costo;
        this.destino = destino;
    } 
    public int getCosto(){
        return costo;
    }
    public String getDestino(){
        return destino;
    }
    @Override
    public String toString(){
        return destino;
    }
}


