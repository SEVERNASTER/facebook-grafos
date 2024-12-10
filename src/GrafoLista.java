import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Arrays;
public class GrafoLista{
    private ArrayList<Nodo>[] listaAdy;
    private String[] vertices;
    private boolean dirigido;
    private int cantVertices;
    public GrafoLista(int n, boolean dirigido){
        listaAdy = new ArrayList[n];
        vertices = new String[n];
        this.dirigido = dirigido;
        cantVertices = 0;
        inicializar();
    }
    private void inicializar(){
        for(int i = 0; i < listaAdy.length; i++){
            listaAdy[i] = new ArrayList();        
        }
    }
    public void agregarVertice(String v){
        if(cantVertices < listaAdy.length){
            if(getIndex(v) == -1){
                vertices[cantVertices] = v;
                cantVertices++;
            }
        }
    }
    private int getIndex(String v){
        int pos = -1;
        boolean bb = false;
        for(int i = 0; i < cantVertices &&  !bb; i++){
            if(vertices[i].equals(v)){
                pos = i;
                bb = true;
            }
        }
        return pos;
    }
    public void agregarArista(String o, String d, int costo){
        int posOri = getIndex(o);
        int posDes = getIndex(d);
        if((posOri != -1) && (posDes != -1)){
            listaAdy[posOri].add(new Nodo(costo, d));
            if(!dirigido){
                listaAdy[posDes].add(new Nodo(costo, o));
            }
        }
    }
    public int gradoVertice(String n){
        int grado = -1;
        int posNodo = getIndex(n);
        if(posNodo != -1){
            grado = 0;
            grado += listaAdy[posNodo].size();
            if(dirigido){
                for(int i = 0; i < cantVertices; i++){
                    if(i != posNodo){
                        grado += llegaAmi(listaAdy[i], n);
                    }
                }
            }
        } 
        return grado;
    }  
    private int llegaAmi(ArrayList<Nodo> lista, String n){
        int cont = 0;
        for(Nodo no : lista){
            if(no.getDestino().equals(n)){
                cont++;
            }
        }
        return cont;
    }
    public ArrayList<String> bfs(String vertice){
        ArrayList<String> resultado = new ArrayList();
        int posActual = getIndex(vertice);
        if(posActual != -1){
            boolean[] visitados = new boolean[cantVertices];
            Queue<String> cola = new LinkedList<String>();
            visitados[posActual] = true;
            cola.add(vertice);
            String verticeActual;
            while(!cola.isEmpty()){
                verticeActual = cola.poll();
                posActual = getIndex(verticeActual);
                resultado.add(verticeActual);
                ArrayList<Nodo> vecinos = listaAdy[posActual];
                for(Nodo n : vecinos){
                    int posVecino = getIndex(n.getDestino());
                    if(!visitados[posVecino]){
                        visitados[posVecino] = true;
                        cola.add(n.getDestino());
                    }
                }
            }
        } 
        return resultado;
    }
    public ArrayList<String> dfs(String vertice){
        ArrayList<String> resultado = new ArrayList();
        int posActual = getIndex(vertice);
        if(posActual != -1){
            boolean[] visitados = new boolean[cantVertices];
            Stack<String> pila = new Stack();
            visitados[posActual] = true;
            pila.add(vertice);
            String verticeActual;
            while(!pila.isEmpty()){
                verticeActual = pila.pop();
                posActual = getIndex(verticeActual);
                resultado.add(verticeActual);
                ArrayList<Nodo> vecinos = listaAdy[posActual];
                for(Nodo n : vecinos){
                    int posVecino = getIndex(n.getDestino());
                    if(!visitados[posVecino]){
                        visitados[posVecino] = true;
                        pila.add(n.getDestino());
                    }
                }
            }
        } 
        return resultado;
    }
    public ArrayList<String> dfsRecursivo(String vertice){
        ArrayList<String> resultado = new ArrayList();
        int posActual = getIndex(vertice);
        if(posActual != -1){
            boolean[] visitados = new boolean[cantVertices];
            visitados[posActual] = true;
            dfsRecursivo(vertice, visitados, resultado);
        }
        return resultado;
    }
    private void dfsRecursivo(String vertice, boolean[] visitados, ArrayList<String> resultado){
        resultado.add(vertice);
        ArrayList<Nodo> vecinos = listaAdy[getIndex(vertice)];
        for(Nodo no : vecinos){
            int posActual = getIndex(no.getDestino());
            if(!visitados[posActual]){
                visitados[posActual] = true;
                dfsRecursivo(no.getDestino(), visitados, resultado);
            }
        }        
    } 
    public ArrayList<ArrayList<String>> getCaminos(String vo, String vd){
        ArrayList<ArrayList<String>> resultado = new ArrayList();
        int posOri = getIndex(vo);
        int posDes = getIndex(vd);
        if((posOri != -1) && (posDes != -1)){
            ArrayList<String> camino = new ArrayList();
            boolean[] visitados = new boolean[cantVertices];
            camino.add(vo);
            visitados[posOri] = true;
            ArrayList<Nodo> vecinos = listaAdy[posOri];
            for(Nodo n : vecinos){
                ArrayList<String> caminoCopia = (ArrayList<String>)camino.clone();
                caminoCopia.add(n.getDestino());
                visitados[getIndex(n.getDestino())] = true;
                getCaminos(n.getDestino(), vd, caminoCopia, visitados, resultado);
                visitados[getIndex(n.getDestino())] = false;
            } 
        }
        return resultado;
    }
    private void getCaminos(String vo, String vd, ArrayList<String> ca, boolean[] vis, ArrayList<ArrayList<String>> res){
        if(vo.equals(vd)){
            res.add(ca);
        }else{
            ArrayList<Nodo> vecinos = listaAdy[getIndex(vo)];
            for(Nodo n : vecinos){
                int posVecino = getIndex(n.getDestino());
                if(!vis[posVecino]){
                    ArrayList<String> caminoCopia = (ArrayList<String >)ca.clone();
                    caminoCopia.add(n.getDestino());
                    vis[posVecino] = true;
                    getCaminos(n.getDestino(), vd, caminoCopia, vis, res); 
                    vis[posVecino] = false;
                }
            }  
        }
    }
    public void mostrar(){
        for(int i = 0; i < cantVertices; i++){
            System.out.println(vertices[i] + " -> " + listaAdy[i]);        
        }
    }
    
    public HashMap<String, Estadistica> getInformacion(){
        HashMap<String, Estadistica> resultado = new HashMap();
        
        for(String v : vertices){
            resultado.put(v, buscarInformacion(v));
        }
        
        return resultado;
    }
    private Estadistica buscarInformacion(String v){
        int posVertice = getIndex(v);
        int gradoSeguidores = 0;
        int totalReacciones = 0;
        for(int i = 0; i < cantVertices; i++){
            if(i != posVertice){
                ArrayList<Nodo> vecinos = listaAdy[i];
                for(Nodo n : vecinos){
                    if(n.getDestino().equals(v)){
                        gradoSeguidores++;
                        totalReacciones += n.getCosto();
                    }
                } 
            } 
        }
        return new Estadistica(gradoSeguidores, totalReacciones);
    }
    
    public ArrayList<Nodo> dijkstra(String origen){
        int[] distancias = new int[cantVertices];
        boolean[] visitados = new boolean[cantVertices];
        String[] anterior = new String[cantVertices];
        Arrays.fill(distancias, Integer.MAX_VALUE);
        distancias[getIndex(origen)] = 0;
        
        PriorityQueue<Nodo> cola = new PriorityQueue<Nodo>(new NodoComparador());
        cola.add(new Nodo(0, origen)); 
        
        Nodo actual;
        while(!cola.isEmpty()){
            actual = cola.poll();
            int posActual = getIndex(actual.getDestino());
            if(!visitados[posActual]){
                visitados[posActual] = true;
                ArrayList<Nodo> vecinos = listaAdy[posActual];
                for(Nodo n : vecinos){
                    int posVecino = getIndex(n.getDestino());
                    int costoVecino = n.getCosto();
                    if(!visitados[posVecino]){
                        if(distancias[posVecino] > distancias[posActual] + costoVecino){
                            distancias[posVecino] = distancias[posActual] + costoVecino;
                            anterior[posVecino] = actual.getDestino();
                            cola.add(new Nodo(distancias[posVecino], n.getDestino()));
                        }
                    }
                }                
            }
        }        
        
        cola.clear();
        int posOrigen = getIndex(origen);
        
        ArrayList<Nodo> vecinos = listaAdy[posOrigen];
        for(int i  = 0; i < cantVertices; i++){            
            if(i != posOrigen){
                boolean esta = false;
                for(Nodo n : vecinos){
                    if(n.getDestino().equals(vertices[i])){
                        esta = true;
                        break;
                    }
                }
                if(!esta){
                    cola.add(new Nodo(distancias[i], vertices[i]));
                }
            }
        }
        
        return new ArrayList<Nodo>(cola);
    }     
}
