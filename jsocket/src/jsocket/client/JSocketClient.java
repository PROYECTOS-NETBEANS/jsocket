package jsocket.client;
import java.io.IOException;
import java.net.Socket;
import java.util.EventListener;
import javax.swing.event.EventListenerList;
import jsocket.utils.OnReachableListener;
import jsocket.utils.Paquete;
import jsocket.utils.TipoMsg;
/**
 * Clase socket del cliente que envia y recibe mensajes del servidor
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class JSocketClient implements OnReachableListener{
    
    private String IP_SERVER = "";
    private int PUERTO = 5555;
    private Socket skConexion = null;
    private String userName = "";
    
    /**
     * Hilo de comunicacion con el servidor
     */
    private ComunicationClient comunicacion = null;
    
    /**
     * Hilo que va verificando la conexion cada cierto tiempo
     */
    private ReconnectClient reconnect = null;
    
    /**
     * Lista de escuchadores
     */
    private static EventListenerList listenerList = new EventListenerList();

    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public JSocketClient(int puerto, String ip){
        this.PUERTO = puerto;
        this.IP_SERVER = ip;
        this.comunicacion = null;
        listenerList = new EventListenerList();
        // esto es al inicio , ya que no tenemos ninguna conexion
        this.inicializarReconexion();
    }
    /**
     * Metodo donde se inicializa el hilo que intentara realizar la conexion con el servidor
     */
    private void inicializarReconexion(){
        reconnect = new ReconnectClient(this, IP_SERVER, PUERTO);
        reconnect.setTimeInterval(2000);
        reconnect.setNroIntento(4);
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
     * Metodo que se lanza cuando no se pudo conectar al servidor
     */
    public static void onConnectRefused(){
        Object[] listeners = JSocketClient.listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if(listeners[i] instanceof OnConnectedListenerClient){
               ((OnConnectedListenerClient) listeners[i]).onConnectRefused();
            }
        }
    }
    /**
     * Metodo que se genera cuando se termino los intentos de conexion al servidor
     */
    public static void onConnectFinally(){
        Object[] listeners = JSocketClient.listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if(listeners[i] instanceof OnConnectedListenerClient){
               ((OnConnectedListenerClient) listeners[i]).onConnectFinally();
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
        this.userName = nick;        
        this.reconnect.start();
    }
    private void reconectarServidor(){
        try {
            comunicacion = null;
            skConexion = new Socket(IP_SERVER, PUERTO);
            System.out.println("despues de conextar al server");
            comunicacion = new ComunicationClient(skConexion);
            this.sendConfiguration();
            comunicacion.start();
            reconnect.setEstadoConexion(true);
            JSocketClient.onConnect(new Paquete("", -1, -1, TipoMsg.PQT_NONE));
        } catch (IOException e) {
            // No puede conectarse al servidor
            System.out.println("[JSocketClient.reconectarServidor]: " + e.getMessage());
            JSocketClient.onConnectRefused();         
        }
    }
    /**
     * Metodo que realiza la desconexion con el servidor
     */
    public void desconectarServidor(){
        if(this.comunicacion != null){
            this.comunicacion.cerrarConexion();
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
        comunicacion.sendMessage(userName, TipoMsg.PQT_CONFIGURATION, -1);
    }

    /**
     * Metodo que se implementara cuando no haya conexion con el servidor
     */
    @Override
    public void onUnAvailable() {
        System.out.println("intentando Reconectar");
        //primero detengo todos los servicios para luego intentar conectar nuevamente
        this.desconectarServidor();
        
        this.reconectarServidor();
    }

    @Override
    public void onLostConnection() {
        System.out.println("finalizando intentos de conexion");
        this.reconnect.detener();
        JSocketClient.onConnectFinally();
    }
}