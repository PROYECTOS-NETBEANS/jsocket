package jsocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.EventListener;
import java.util.HashMap;
import javax.swing.event.EventListenerList;
import jsocket.utils.Paquete;
import jsocket.utils.TipoMsg;

/**
 * Servidor socket para que los clientes puedan conectarse
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com> */
public class JSocketServer {
    
    private ManagerConections manager = null;
    
    private static HashMap<Integer, ComunicationServer> clientHashMap = null;
    private static EventListenerList listenerList = null;

    private ServerSocket skServer;
    private int puerto = 5555;
    
    public JSocketServer(int puerto){
        this.puerto = puerto;
        listenerList = new EventListenerList();
        try{
            skServer = new ServerSocket(this.puerto);
            manager = new ManagerConections(skServer);
        }catch(IOException e){
            System.out.println("error al iniciar el socket server " + e.getMessage());
        }
    }
    /**
     * Metodo que devuelve el hilo escuchador cliente, a partir de una key
     * @param keyValue Llave con la que se buscara
     * @return
     */
    public static ComunicationServer getConnectionsClient(int keyValue){
        if(clientHashMap == null){
            clientHashMap = new HashMap<>();
            return null;
        }else{
            return clientHashMap.get(keyValue);
        }
    }
    /**
     * Guarda el escuchador en la lista de escuchadores
     * @param conexion 
     */
    public static void setConnectionClient(ComunicationServer conexion){
        if(clientHashMap == null){
            clientHashMap = new HashMap<>();
        }
        clientHashMap.put(conexion.getKey(), conexion);
    }
    /**
     * Elimina un escuchador de la lista, a partir de una llave key
     * @param key Valor unico mediante el cual se buscara en la lista
     */
    public static void removeConnectionClient(int key){
        ComunicationServer conexion = clientHashMap.get(key);
        conexion.detenerEscuchador();
        clientHashMap.remove(key);
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
     * @param direccion Es la direccion ip del usuario que acaba de conectarse
     */
    public static void onServerStar(String direccion){
        Object[] listeners = listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          
          if(listeners[i] instanceof OnConnectedListenerServer){            
              OnConnectedEventServer sender = new OnConnectedEventServer(new Paquete(direccion,-1, TipoMsg.MSG_NORMAL));
              ((OnConnectedListenerServer)listeners[i]).onServerStar(sender);
          }
        }
    }

     /**
     * Metodo que lanza el evento de lectura del servidor
     * @param key Identificador unico del cliente que esta enviando el mensaje
     * @param msg Mensaje enviado por el cliente.
     */
    public static void onRead(int key, String msg){
        Object[] listeners = listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          
          if(listeners[i] instanceof OnConnectedListenerServer){
              OnConnectedEventServer sender = new OnConnectedEventServer(new Paquete(msg, key, TipoMsg.MSG_NORMAL));
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
              System.out.println("jsocketserver.onConnect : key " + String.valueOf(key));
              OnConnectedEventServer sender = new OnConnectedEventServer(new Paquete(msg, key, TipoMsg.MSG_NORMAL));
              ((OnConnectedListenerServer)listeners[i]).onConnect("onconnected", sender);
          }
        }
    }    
    
    /**
     * Metodo que lanza el evento de desconexion
     * @param key El identificador del cliente que se desconecto
     */
    public static void onDisconnect(int key){
        Object[] listeners = listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          if(listeners[i] instanceof OnConnectedListenerServer){
              OnConnectedEventServer sender = new OnConnectedEventServer(new Paquete("Desconectado", key, TipoMsg.MSG_DESCONECTADO));
              ((OnConnectedListenerServer)listeners[i]).onDisconnect("disconnect", sender);
          }
        }       
    }
    
    /**
     * Metodo que inicia el servidor
     */
    public void iniciarServicio(){
        manager.start();
        System.out.println("entre a iniciar servicio");
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