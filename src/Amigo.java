public class Amigo {
    private String nick;
    private int distancia;
    public Amigo(String nick, int distancia) {
        this.nick = nick;
        this.distancia = distancia;
    }

    public String getNick() {
        return nick;
    }

    public int getDistancia() {
        return distancia;
    }

    @Override
    public String toString() {
        return "Amigo{" +
                "nick='" + nick + '\'' +
                ", distancia=" + distancia +
                '}';
    }
}
