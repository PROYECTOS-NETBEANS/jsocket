package jsocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.event.EventListenerList;
import jsocket.utils.OnReachableClientListener;

import jsocket.utils.Paquete;
import jsocket.utils.TipoMsg;

/**
 * Servidor socket para que los clientes puedan conectarse
 * @author Alex Limbert Yalusqui
*/
public class JSocketServer implements OnReachableClientListener{

    private ManagerConections manager = null;
    
    /**
     * Hilo para la verificar que las conexiones esten activas
     */
    //private CheckClient verificador = null;
    
    private static HashMap<Integer, ComunicationServer> clientHashMap = new HashMap<>();
    private static EventListenerList listenerList = new EventListenerList();

    private ServerSocket skServer;
    private int puerto = 5555;
    
    /**
     * Constructor del socket servidor
     * @param puerto Es el puerto donde se iniciara el socket servidor
     */
    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public JSocketServer(int puerto){
        try{
            this.puerto = puerto;
            listenerList = new EventListenerList();
            skServer = new ServerSocket(this.puerto);
            manager = new ManagerConections(skServer);
            
            //verificador = new CheckClient(this, 2000);
            //verificador.start();
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
     * @return Devuelve un map con los clientes
     */
     synchronized public static HashMap getClients(){
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
     * @param userName  Nombre de usuario unico
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
     * @param paquete Es un paquete donde viene encapsulado el mesaje
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
     * @param paquete Paquete donde viene encapsulado el mensaje
     * @param userName Nombre de usuario del cliente que se desconecta
     */
    public static void onDisconnect(Paquete paquete, String userName){
        Object[] listeners = JSocketServer.listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          if(listeners[i] instanceof OnConnectedListenerServer){
              OnConnectedEventServer sender = new OnConnectedEventServer(paquete);
              ((OnConnectedListenerServer)listeners[i]).onDisconnect("disconnect", sender, userName);
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
     * Metodo que envia un mensaje a todos los clientes
     * @param msg Mensaje que se enviara a todos los clientes
     * @param keyOrigen Identificador primario del origen
     */
    public void sendMessageAll(String msg, int keyOrigen){
        HashMap clientes = JSocketServer.getClients();
        Iterator<ComunicationServer> it = clientes.values().iterator();
        ComunicationServer cliente;
        while(it.hasNext()){
            cliente = it.next();
            cliente.sendMessage(msg, TipoMsg.MSG_PUBLICO, keyOrigen);
        }        
    }
    /**
     * Envia un mensaje a un cliente
     * @param keyClient Cliente al que se tiene que enviar el mensaje
     * @param msg Mensaje que se enviara al cliente
     * @param keyOrigen Cliente desde donde se origina el mensaje
     */
    public void sendMessage(int keyClient, String msg, int keyOrigen){
        ComunicationServer cliente = (ComunicationServer) JSocketServer.getClients().get(keyClient);
        cliente.sendMessage(msg, TipoMsg.MSG_PRIVADO, keyOrigen);
    }

    /**
     * Ocurre cuando se pierde la conexion con algun cliente
     */
    @Override
    public void onUnAvailable(int key) {
        //primero cierro las conexiones del cliente
        (JSocketServer.clientHashMap.get(key)).onDisconnect();
    }
}