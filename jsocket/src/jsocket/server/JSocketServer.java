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
     * Metodo que devuelve la lista de clientes conectados
     * @return  
     */
    public static HashMap getClients(){
        if(clientHashMap == null){
            return null;
        }else{
            return JSocketServer.clientHashMap;
        }
    }
    /**
     * Guarda el escuchador en la lista de escuchadores
     * @param conexion Escuchador que esta pendiente de los mensajes que llegan del cliente
     */
    public static void setConnectionClient(ComunicationServer conexion){
        if(clientHashMap == null){
            JSocketServer.clientHashMap = new HashMap<>();
        }
        JSocketServer.clientHashMap.put(conexion.getKey(), conexion);
    }
    /**
     * Elimina un escuchador de la lista, a partir de una llave key
     * @param key Valor unico mediante el cual se buscara en la lista
     */
    public static void removeConnectionClients(int key){
        System.out.println("entrando a removeClient");
        ComunicationServer conexion = JSocketServer.clientHashMap.get(key);
        conexion.detenerEscuchador();
        JSocketServer.clientHashMap.remove(key);
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
        Object[] listeners = JSocketServer.listenerList.getListenerList();
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
        Object[] listeners = JSocketServer.listenerList.getListenerList();
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
        Object[] listeners = JSocketServer.listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          if(listeners[i] instanceof OnConnectedListenerServer){
              System.out.println("pase onDisconect 1");
              OnConnectedEventServer sender = new OnConnectedEventServer(new Paquete("Desconectado", key, TipoMsg.MSG_DESCONECTADO));
              System.out.println("pase onDisconect 2");
              ((OnConnectedListenerServer)listeners[i]).onDisconnect("disconnect", sender);
              System.out.println("pase onDisconect 3");
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
        try{
            System.out.println("deten 1 ");
            aqui cambiar por otra recorrido
            for(Integer key : JSocketServer.clientHashMap.keySet()){
                System.out.println("antes detener 1");
                JSocketServer.onDisconnect(key);
                System.out.println("antes detener 2");
            }
            System.out.println("finalizacion de detenerServicio");
            JSocketServer.clientHashMap = null;
        }catch(Exception e){
            System.out.println("[JSocketServer.detenerServicio] " + e.getMessage());
        }

    }
    /**
     * Metodo que lanza el evento de envio de mensaje
     *
    public void onWrite(){
        manager.onWrite();
    }
    */
}