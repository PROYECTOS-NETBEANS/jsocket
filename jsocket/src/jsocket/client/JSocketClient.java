package jsocket.client;
import java.io.IOException;
import java.net.Socket;
import java.util.EventListener;
import javax.swing.event.EventListenerList;
import jsocket.utils.Paquete;
import jsocket.utils.TipoMsg;
/**
 * Clase socket del cliente que envia y recibe mensajes del servidor
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class JSocketClient {
    
    private String IP_SERVER = "";
    private int PUERTO = 5555;
    private Socket skConexion = null;
    private String userName = "";
    
    private ComunicationClient comunicacion = null;
    private static EventListenerList listenerList = new EventListenerList();

    public JSocketClient(int puerto, String ip){
        this.PUERTO = puerto;
        this.IP_SERVER = ip;
        this.comunicacion = null;
        listenerList = new EventListenerList();
    }
    /**
     * adiciona un escuchador de eventos a la lista de escuhadores
     * @param listener Escuchador de eventos
     */
    public void addEventListener(EventListener listener){
        JSocketClient.listenerList.add(EventListener.class, listener);
    }
    /**
     * Elimina de la lista de escuchadores de eventos el escuhador de entrada
     * @param listener Escuhador a eliminar de la lista
     */
    public void removeEventListener(EventListener listener){
        JSocketClient.listenerList.remove(EventListener.class, listener);
      
    }
     /**
     * Metodo que lanza el evento cuando se conecta al servidor
     * @param paquete Es lo que se genera cuando el cliente se conecta
     */
    public static void onConnect(Paquete paquete){
        Object[] listeners = JSocketClient.listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){          
          if(listeners[i] instanceof OnConnectedListenerClient){
              System.out.println("jsocketclient.onConnect : key " + String.valueOf(paquete.getOrigen()));
              OnConnectedEventClient sender = new OnConnectedEventClient(paquete);
              ((OnConnectedListenerClient)listeners[i]).onConnect("onconnected", sender);
          }
        }
    }    
    /**
     * Metodo que lanza el evento de desconexion
     * @param paquete
     */
    public static void onDisconnect(Paquete paquete){
        Object[] listeners = JSocketClient.listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          if(listeners[i] instanceof OnConnectedListenerClient){
              OnConnectedEventClient sender = new OnConnectedEventClient(paquete);
              ((OnConnectedListenerClient)listeners[i]).onDisconnect("disconnect", sender);
              
          }
        }       
    }
    /**
     * Metodo que realiza la conexion al servidor
     * @param nick Nombre de usuario del cliente
     */
    public void conectarServidor(String nick){
        try {
            this.userName = nick;
            skConexion = new Socket(IP_SERVER, PUERTO);
            comunicacion = new ComunicationClient(skConexion);
            this.sendConfiguration();
            comunicacion.start();
            System.out.println("cliente conectado al servido");
            JSocketClient.onConnect(new Paquete("", -1, -1, TipoMsg.PQT_NONE));
        } catch (IOException e) {
            System.out.println("[JSocketClient.conectarServidor]: " + e.getMessage());
        }
    }
    /**
     * Metodo que realiza la desconexion con el servidor
     */
    public void desconectarServidor(){
        this.comunicacion.cerrarConexion();
    }
     /**
     * Metodo que lanza el evento de lectura del servidor
     * @param paquete Paquete con la informacion que llega desde el cliente
     */
    public static void onRead(Paquete paquete){
        Object[] listeners = JSocketClient.listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          if(listeners[i] instanceof OnConnectedListenerClient){
              OnConnectedEventClient sender = new OnConnectedEventClient(paquete);
              ((OnConnectedListenerClient)listeners[i]).onRead("read", sender);
          }
        }       
    }
    
    public void sendMessagePrivado(String msg, int keyDestino){
        comunicacion.sendMessage(msg, TipoMsg.MSG_PRIVADO, keyDestino);
    }
    public void sendMessageAll(String msg){
        System.out.println("send message 1");
        comunicacion.sendMessage(msg, TipoMsg.MSG_PUBLICO, -1);
    }
    /**
     * Este metodo envia las configuraciones necesarias al servidor
     * 
     */
    private void sendConfiguration(){
        System.out.println("entre a enviar nick");
        comunicacion.sendMessage(userName, TipoMsg.PQT_CONFIGURATION, -1);
    }
}