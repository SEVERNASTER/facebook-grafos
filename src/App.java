import java.util.ArrayList;
import java.util.HashMap;
public class App{
    private ArrayList<Usuario> usuarios;
    private ArrayList<String> nicks;
    private GrafoLista amigos, seguidores, reacciones;
    public App(ArrayList<Usuario> usuarios, ArrayList<String> nicks){
        this.usuarios = usuarios;
        this.nicks = nicks;
        amigos = new GrafoLista(nicks.size(), false);
        seguidores = new GrafoLista(nicks.size(), true);
        reacciones = new GrafoLista(nicks.size(), true);
        agregarUsuarios();
    }
    private void agregarUsuarios(){
        for(String n : nicks){
            amigos.agregarVertice(n);
            seguidores.agregarVertice(n);
            reacciones.agregarVertice(n);
        }
        
        for(Usuario u : usuarios){
            String nickActual = u.getNick();
            // Agregar al Grafo de amigos
            for(Amigo amigo : u.getAmigos()){
                amigos.agregarArista(nickActual, amigo.getNick(), amigo.getDistancia());
            } 
            // Agregar al Grafo de seguidores
            for(String sigue : u.getSigue()){
                seguidores.agregarArista(nickActual, sigue, 1);
            }            
            // Agregar al Grafo de reacciones
            for(Reaccion reaccion : u.getRedireccionamientos()){
                reacciones.agregarArista(nickActual, reaccion.getNick(), reaccion.getCantidad());
            }            
        }
    }
    public Usuario getUsuario(String nick){
        for(Usuario u : usuarios){
            if(u.getNick().equals(nick)){
                return u;
            }
        }
        return null;
    }
    public ArrayList<Usuario> buscarInfluencers(){
        ArrayList<Usuario> influencers = new ArrayList();
        //seguidores.mostrar();
        //reacciones.mostrar();
        HashMap<String, Estadistica> res1 = seguidores.getInformacion(); // Usamos el Grafo de seguidores
        HashMap<String, Estadistica> res2 = reacciones.getInformacion(); // Usamos el Grafo de reacciones
        
        for(String nick : res1.keySet()){
            Estadistica e1 = res1.get(nick);
            Estadistica e2 = res2.get(nick);
            
            if(e1.getTotalSeguidores() > 2 && e2.getTotalReacciones() > 20){
                influencers.add(getUsuario(nick));
            }
        }
        return influencers;
    }
    public HashMap<String, ArrayList<String>> getSugerencias(String nick){
        HashMap<String, ArrayList<String>> sugerencias = new HashMap<String, ArrayList<String>>();
        ArrayList<String> amistades = new ArrayList<String>();
        
        ArrayList<Nodo> res = amigos.dijkstra(nick); // Usamos el Grafo de amigos
        
        for(int i = 0 ; i < res.size() / 2; i++){
            amistades.add(res.get(i).getDestino());
        }
        sugerencias.put("Amistades", amistades);
        sugerencias.put("Grupos", obtenerGruposDeInteres(getUsuario(nick), usuarios));
        
        return sugerencias;
    } 

    
    private ArrayList<String> obtenerGruposDeInteres(Usuario usuario, ArrayList<Usuario> listaUsuarios) {
        HashMap<String, Integer> contadorGrupos = new HashMap<>();
        ArrayList<String> gruposUsuario = usuario.getGrupos();

        // Contar la frecuencia de cada grupo en la lista de usuarios
        for (Usuario u : listaUsuarios) {
            for (String grupo : u.getGrupos()) {
                if (!gruposUsuario.contains(grupo)) { // Solo contar grupos en los que el usuario no está
                    contadorGrupos.put(grupo, contadorGrupos.getOrDefault(grupo, 0) + 1);
                }
            }
        }

        // Identificar los grupos que son populares (la mayoría)
        int limite = 3; // Limitar la frecuencia
        ArrayList<String> gruposSugeridos = new ArrayList<>();
        for (String grupo : contadorGrupos.keySet()) {
            if (contadorGrupos.get(grupo) > limite) {
                gruposSugeridos.add(grupo);
            }
        }

        return gruposSugeridos;
    } 
    
    public ArrayList<ArrayList<Usuario>> encontrarGruposInteresesComunes(int porcentaje){
        ArrayList<ArrayList<Usuario>> grupos = new ArrayList<>();
        ArrayList<Usuario> visitados = new ArrayList<>();
    
        for(Usuario usuario : usuarios) {
            if (visitados.contains(usuario)) {
                continue; // Si el usuario ya está en un clúster, saltarlo
            }
    
            ArrayList<Usuario> grupo = new ArrayList<>();
            grupo.add(usuario);
            visitados.add(usuario);
    
            for(Usuario otroUsuario : usuarios) {
                if (usuario.equals(otroUsuario) || visitados.contains(otroUsuario)) {
                    continue; // No comparar consigo mismo ni con usuarios ya agrupados
                }
    
                // Calcular porcentaje de intereses compartidos
                int interesesCompartidos = calcularInteresesCompartidos(usuario, otroUsuario);
                int totalIntereses = Math.min(usuario.getIntereses().size(), otroUsuario.getIntereses().size());
    
                if(totalIntereses > 0 && (interesesCompartidos * 100 / totalIntereses) >= porcentaje) {
                    grupo.add(otroUsuario);
                    visitados.add(otroUsuario);
                }
            }
    
            grupos.add(grupo);
        }
        return grupos;
    }


    private int calcularInteresesCompartidos(Usuario u1, Usuario u2) {
        int contador = 0;
        for (String interes : u1.getIntereses()) {
            if (u2.getIntereses().contains(interes)) {
                contador++;
            }
        }
        return contador;
    }
}
