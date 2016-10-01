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
    
    private static HashMap<Integer, ComunicationServer> clientHashMap = new HashMap<>();
    private static EventListenerList listenerList = new EventListenerList();

    private ServerSocket skServer;
    private int puerto = 5555;
    
    /**
     * Constructor del socket servidor
     * @param puerto Es el puerto donde se iniciara el socket servidor
     */
    public JSocketServer(int puerto){
        try{
            this.puerto = puerto;
            listenerList = new EventListenerList();
            skServer = new ServerSocket(this.puerto);
            manager = new ManagerConections(skServer);            
        }catch(IOException e){
            System.out.println("[JSocketServer.JSocketServer] " + e.getMessage());
        }
    }
    /**
     * Guarda el escuchador en la lista de escuchadores
     * @param conexion Escuchador que esta pendiente de los mensajes que llegan del cliente
     */
    public static void addClient(ComunicationServer conexion){
        if(JSocketServer.clientHashMap == null){
            JSocketServer.clientHashMap = new HashMap<>();
        }
        JSocketServer.clientHashMap.put(conexion.getKey(), conexion);
    }
    /**
     * Metodo que devuelve la lista de todos los clientes
     * @return 
     */
    public static HashMap getClients(){
        return JSocketServer.clientHashMap;
    }
    /**
     * Elimina un escuchador de la lista, a partir de una llave key
     * @param key Valor unico mediante el cual se buscara en la lista
     */
    public static void removeClient(int key){
        System.out.println("count1 : " + String.valueOf(JSocketServer.clientHashMap.size()));
        JSocketServer.clientHashMap.remove(key);
        System.out.println("count2 : " + String.valueOf(JSocketServer.clientHashMap.size()));        
    }
    
    /**
     * adiciona un escuchador de eventos a la lista de escuhadores
     * @param listener Escuchador de eventos
     */
    public void addEventListener(EventListener listener){
        JSocketServer.listenerList.add(EventListener.class, listener);
    }
    /**
     * Elimina de la lista de escuchadores de eventos el escuhador de entrada
     * @param listener Escuhador a eliminar de la lista
     */
    public void removeEventListener(EventListener listener){
        JSocketServer.listenerList.remove(EventListener.class, listener);
      
    }
    
    /**
     * Metodo que lanza el evento cuando se inicia el servicio del servidor
     * @param direccion Es la direccion ip del usuario que acaba de conectarse
     */
    public static void onServerStar(String direccion){
        Object[] listeners = JSocketServer.listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          if(listeners[i] instanceof OnConnectedListenerServer){
              OnConnectedEventServer sender = new OnConnectedEventServer(new Paquete(direccion, -1, -1, TipoMsg.PQT_NONE));
              ((OnConnectedListenerServer)listeners[i]).onServerStar(sender);
          }
        }
    }

     /**
     * Metodo que lanza el evento de lectura del servidor
     * @param paquete Paquete con la informacion que llega desde el cliente
     */
    public static void onRead(Paquete paquete, String userName){
        Object[] listeners = JSocketServer.listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          if(listeners[i] instanceof OnConnectedListenerServer){
              OnConnectedEventServer sender = new OnConnectedEventServer(paquete);
              ((OnConnectedListenerServer)listeners[i]).onRead("read", sender, userName);
          }
        }       
    }

    /**
     * Metodo que lanza el evento cuando se conecta un cliente al servidor
     * @param paquete
     */
    public static void onConnect(Paquete paquete){
        Object[] listeners = JSocketServer.listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          
          if(listeners[i] instanceof OnConnectedListenerServer){
              System.out.println("jsocketserver.onConnect : key " + String.valueOf(paquete.getOrigen()));
              OnConnectedEventServer sender = new OnConnectedEventServer(paquete);
              ((OnConnectedListenerServer)listeners[i]).onConnect("onconnected", sender, paquete.getMsg());
          }
        }
    }    
    
    /**
     * Metodo que lanza el evento de desconexion
     * @param key El identificador del cliente que se desconecto
     */
    public static void onDisconnect(Paquete paquete){
        Object[] listeners = JSocketServer.listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          if(listeners[i] instanceof OnConnectedListenerServer){
              OnConnectedEventServer sender = new OnConnectedEventServer(paquete);
              ((OnConnectedListenerServer)listeners[i]).onDisconnect("disconnect", sender);
          }
        }       
    }
    
    /**
     * Metodo que inicia el servidor
     */
    public void iniciarServicio(){
        manager.start();
    }
    /** 
     * Detiene el servicio y deja de escuchar a los clientes, 
     * desconectando todos los clientes
     */
    public void detenerServicio(){
        manager.detenerServicio();
    }
    /**
     * Metodo que lanza el evento de envio de mensaje
     *
    public void onWrite(){
        manager.onWrite();
    }
    */
}