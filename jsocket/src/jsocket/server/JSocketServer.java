package jsocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.EventListener;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;
import jsocket.utils.Paquete;

/**
 * Servidor socket para que los clientes puedan conectarse
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com> */
public class JSocketServer {
    
    private ManagerConections manager = null;
    
    private static HashMap<Integer, ComunicationServer> clientHashMap = null;
    private static EventListenerList listenerList = null;

    private static ServerSocket skServer;
    private static int puerto = 5555;
    
    
    /**
     * Metodo estatico para obtener el socket server
     * @return ServerSocket
     */
    public static ServerSocket getSkServer(){
        if(skServer == null){
            try {
                skServer = new ServerSocket(puerto);
            } catch (IOException ex) {
                Logger.getLogger(JSocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return skServer;
    }
    
    public JSocketServer(int puerto){
        this.puerto = puerto;
        manager = new ManagerConections();
        listenerList = new EventListenerList();
    }
    public static ComunicationServer getConnectionsClient(int keyValue){
        if(clientHashMap == null){
            clientHashMap = new HashMap<>();
            return null;
        }else{
            return clientHashMap.get(keyValue);
        }
    }
    public static void setConnectionClient(ComunicationServer conexion){
        if(clientHashMap == null){
            clientHashMap = new HashMap<>();
        }
        clientHashMap.put(conexion.getKey(), conexion);
    }    
    /**
     * adiciona un escuchador de eventos a la lista de escuhadores
     * @param listener Escuchador de eventos
     */
    public void addEventListener(EventListener listener){
        this.listenerList.add(EventListener.class, listener);
    }
    /**
     * Elimina de la lista de escuchadores de eventos el escuhador de entrada
     * @param listener Escuhador a eliminar de la lista
     */
    public void removeEventListener(EventListener listener){
        this.listenerList.remove(EventListener.class, listener);
      
    }
    
    /**
     * Metodo que lanza el evento cuando se inicia el servicio del servidor
     */
    public static void onServerStar(){
        Object[] listeners = listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          
          if(listeners[i] instanceof OnConnectedListenerServer){
              OnConnectedEventServer sender = new OnConnectedEventServer(JSocketServer.getSkServer().getInetAddress().getHostAddress());
              ((OnConnectedListenerServer)listeners[i]).onServerStar(sender);
          }
        }
    }

     /**
     * Metodo que lanza el evento de lectura del servidor
     */
    public static void onRead(ComunicationServer senderObj){
        Object[] listeners = listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          
          if(listeners[i] instanceof OnConnectedListenerServer){
              OnConnectedEventServer sender = new OnConnectedEventServer(senderObj);
              ((OnConnectedListenerServer)listeners[i]).onRead("read", sender);
          }
        }       
    }

    /**
     * Metodo que lanza el evento cuando se conecta un cliente al servidor
     * @param key identificador del cliente
     * @param msg mensaje del que se enviara
     */
    public static void onConnect(int key, String msg){
        Object[] listeners = listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          
          if(listeners[i] instanceof OnConnectedListenerServer){
              OnConnectedEventServer sender = new OnConnectedEventServer(new Paquete(msg, key));
              ((OnConnectedListenerServer)listeners[i]).onConnect("onconnected", sender);
          }
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
     *
    public void onWrite(){
        manager.onWrite();
    }
    */
}