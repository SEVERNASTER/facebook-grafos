import java.util.ArrayList;

public class Usuario {
    private String nick;
    private ArrayList<String> intereses = new ArrayList();
    private ArrayList<String> sigue = new ArrayList();
    private ArrayList<String> seguidores = new ArrayList();
    private ArrayList<String> grupos = new ArrayList();
    private ArrayList<Amigo> amigos = new ArrayList();
    private ArrayList<Reaccion> redireccionamientos = new ArrayList(); 
    public Usuario(String nick) {
        this.nick = nick;
    }
    public String getNick() {
        return nick;
    }
    public ArrayList<String> getIntereses() {
        return intereses;
    }
    public ArrayList<String> getSigue() {
        return sigue;
    }
    public ArrayList<String> getSeguidores() {
        return seguidores;
    }
    public ArrayList<Amigo> getAmigos() {
        return amigos;
    }
    public ArrayList<String> getGrupos() {
        return grupos;
    }
    public ArrayList<Reaccion> getRedireccionamientos() {
        return redireccionamientos;
    }
    @Override
    public String toString() {
        return "Usuario{" +
                "nick='" + nick + '\'' +
                ", intereses=" + intereses +
                ", sigue=" + sigue +
                ", seguidores=" + seguidores +
                ", amigos=" + amigos +
                ", grupos=" + grupos +
                ", redireccionamientos=" + redireccionamientos +
                '}';
    } 
}

