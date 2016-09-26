package jsocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

/**
 * Servidor socket para que los clientes puedan conectarse
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com> */
public class JSocketServer {
    
    private int puerto = 5555;
    private ServerSocket skServer;
    private ManagerConections manager = null;
    private static HashMap<String, ComunicationServer> clientHashMap = null;

    public JSocketServer(int puerto){
        this.puerto = puerto;        
        init();
    }
    public static ComunicationServer getConnectionsClient(String key){
        if(clientHashMap == null){
            clientHashMap = new HashMap<>();
            return null;
        }else{
            return clientHashMap.get(key);
        }
    }
    public static void setConnectionClient(ComunicationServer conexion){
        if(clientHashMap == null){
            clientHashMap = new HashMap<>();
            clientHashMap.put(key, conexion)
        }
    }    
    public void addEventListener(OnConnectedListenerServer listener){
        manager.addEventListener(listener);
    }
    public void removeEventListener(OnConnectedListenerServer listener){
        manager.removeEventListener(listener);
    }
    private void init(){
        try{
            skServer = new ServerSocket(puerto);
            manager = new ManagerConections(skServer);
        }catch(IOException ex){
            System.out.println("jsocket.server.JServerSocket.inicializar()");        
        }
    }
    /**
     * Metodo que inicia el servidor
     */
    public void iniciarServicio(){
        manager.start();
    }
    // Detiene el servicio y deja de escuchar a los clientes
    public void detenerServicio(){
        manager.interrupt();
    }
    /**
     * Metodo que lanza el evento de envio de mensaje
     */
    public void onWrite(){
        manager.onWrite();
    }
}