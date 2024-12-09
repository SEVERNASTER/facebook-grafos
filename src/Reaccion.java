public class Reaccion {
    private String nick;
    private int cantidad;
    public Reaccion(String nick, int cantidad) {
        this.nick = nick;
        this.cantidad = cantidad;
    }

    public String getNick() {
        return nick;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return "Reaccion{" +
                "nick='" + nick + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
