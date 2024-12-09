import java.util.Comparator;
public class NodoComparador implements Comparator<Nodo>{
    @Override
    public int compare(Nodo a , Nodo b){
        if(a.getCosto() < b.getCosto()){
            return -1;
        }else if(b.getCosto() < a.getCosto()){
            return 1;
        }else{
            return 0;
        }
    }
}

