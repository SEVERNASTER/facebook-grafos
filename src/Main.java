import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Leer el archivo JSON 
            BufferedReader br = new BufferedReader(new FileReader("src/datos.json"));
            StringBuilder jsonBuilder = new StringBuilder();
            String linea;

            while ((linea = br.readLine()) != null) {
                jsonBuilder.append(linea);
            }  
            br.close();

            // Convertir el texto a un objeto JSON
            JSONObject root = new JSONObject(jsonBuilder.toString());
            JSONArray usuariosArray = root.getJSONArray("Usuarios");

            // Lista para almacenar los usuarios mapeados
            ArrayList<Usuario> usuarios = new ArrayList<>();

            // Mapear cada usuario en la lista de JSON
            for (int i = 0; i < usuariosArray.length(); i++) {
                JSONObject usuarioJSON = usuariosArray.getJSONObject(i);
                Usuario usuario = mapearUsuario(usuarioJSON);
                usuarios.add(usuario);
            }

            // Lista de nicks
            ArrayList<String> nicks = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                nicks.add(usuario.getNick());
            } 

            // Crear la instancia de App
            App app = new App(usuarios, nicks);
            
            // Imprimir la cantidad total de usuarios
            System.out.println("\nCantidad total de usuarios: " + usuarios.size());
            
            Scanner scanner = new Scanner(System.in);
            int opcion = -1;

            do {
                System.out.println("\nMenú:");
                System.out.println("1) Ver todos los usuarios");
                System.out.println("2) Ver información de un usuario");
                System.out.println("3) Lista influencers");
                System.out.println("4) Buscar sugerencias");
                System.out.println("5) Buscar clústeres");
                System.out.println("6) Salir");
                System.out.print("Elige una opción: ");
    
                // Validar entrada
                if (scanner.hasNextInt()) {  // Verifica si hay un entero disponible
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea
                } else {
                    System.out.println("Por favor, introduce un número válido.");
                    scanner.nextLine(); // Consumir entrada inválida
                    continue; // Reintentar
                }
    
                switch (opcion) {
                    case 1:
                        System.out.println("Lista de usuarios:");
                        for (String nick : nicks) {
                            System.out.println(" - " + nick);
                        } 
                        break;
    
                    case 2:
                        System.out.print("Introduce el nick del usuario: ");
                        String nickUsuario = scanner.nextLine();
                        Usuario usuario = app.getUsuario(nickUsuario);
                        if (usuario != null) {
                            System.out.println("Información del usuario:");
                            System.out.println(usuario);
                        } else {
                            System.out.println("No existe un usuario con el nick: " + nickUsuario);
                        }
                        break;
    
                    case 3:
                        System.out.println("Lista de influencers:");
                        ArrayList<Usuario> influencers = app.buscarInfluencers();
                        if (influencers.isEmpty()) {
                            System.out.println("No se encontraron influencers.");
                        } else {
                            System.out.println("Lista de influencers:");
                            for (Usuario influencer : influencers) {
                                System.out.println(" - " + influencer.getNick());
                            }
                        }   
                        break;
    
                    case 4:
                        System.out.print("Introduce el nick del usuario: ");
                        String nickParaSugerencias = scanner.nextLine();
                        Usuario usuarioParaSugerencias = app.getUsuario(nickParaSugerencias);
                        if (usuarioParaSugerencias != null) {
                            System.out.println("Sugerencias para " + nickParaSugerencias + ":");
                            System.out.println(app.getSugerencias(nickParaSugerencias));
                        } else {
                            System.out.println("No existe un usuario con el nick: " + nickParaSugerencias);
                        } 
                        break; 
    
                    case 5:
                        System.out.print("Introduce un porcentaje entre 1 y 100: ");
                        if (scanner.hasNextInt()) {
                            int porcentaje = scanner.nextInt();
                            scanner.nextLine();
                            if (porcentaje >= 1 && porcentaje <= 100) {
                                ArrayList<ArrayList<Usuario>> clusters = app.getClusters(porcentaje);
                                System.out.println("Clústeres encontrados:");
                                for (int i = 0; i < clusters.size(); i++) {
                                    System.out.println("Clúster " + (i + 1) + ":");
                                    for (Usuario u : clusters.get(i)) {
                                        System.out.println(" - " + u.getNick());
                                    }
                                } 
                            } else {
                                System.out.println("Por favor, introduce un porcentaje válido entre 1 y 100.");
                            }
                        } else {
                            System.out.println("Por favor, introduce un número válido.");
                            scanner.nextLine();
                        }
                        break;
    
                    case 6:
                        System.out.println("Saliendo del programa...");
                        break;
    
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }while(opcion != 6);
    
            scanner.close(); 
            
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    // Método para mapear el JSON a una instancia de Usuario
    public static Usuario mapearUsuario(JSONObject jsonObject) {
        Usuario usuario = new Usuario(jsonObject.getString("nick"));
        
        
        // Mapear los intereses
        JSONArray interesesArray = jsonObject.getJSONArray("intereses");
        for (int i = 0; i < interesesArray.length(); i++) {
            usuario.getIntereses().add(interesesArray.getString(i));
        }

        // Mapear sigue
        JSONArray sigueArray = jsonObject.getJSONArray("sigue");
        for (int i = 0; i < sigueArray.length(); i++) {
            usuario.getSigue().add(sigueArray.getString(i));
        }

        // Mapear seguidores
        JSONArray seguidoresArray = jsonObject.getJSONArray("seguidores");
        for (int i = 0; i < seguidoresArray.length(); i++) {
            usuario.getSeguidores().add(seguidoresArray.getString(i));
        }

        // Mapear grupos
        JSONArray gruposArray = jsonObject.getJSONArray("grupos");
        for (int i = 0; i < gruposArray.length(); i++) {
            usuario.getGrupos().add(gruposArray.getString(i));
        }

        // Mapear amigos
        JSONArray amigosArray = jsonObject.getJSONArray("amigos");
        for (int i = 0; i < amigosArray.length(); i++) {
            JSONObject amigoJSON = amigosArray.getJSONObject(i);
            Amigo amigo = new Amigo(amigoJSON.getString("nick"), amigoJSON.getInt("distancia"));
            usuario.getAmigos().add(amigo);
        }

        // Mapear redireccionamientos
        JSONArray redireccionamientosArray = jsonObject.getJSONArray("redireccionamientos");
        for (int i = 0; i < redireccionamientosArray.length(); i++) {
            JSONObject redirJSON = redireccionamientosArray.getJSONObject(i);
            Reaccion reaccion = new Reaccion(redirJSON.getString("nick"), redirJSON.getInt("cantidad"));
            usuario.getRedireccionamientos().add(reaccion);
        }

        return usuario;
    }
}
