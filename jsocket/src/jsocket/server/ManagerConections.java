package jsocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
/**
 * Administrador de conexiones de los clientes, donde estan todos los clientes conectados
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com> */
public class ManagerConections extends Thread{
    
    private ServerSocket skServer = null;
    private Socket skConexion = null;
    
    private boolean LISTENING = true;
    //private EventListenerList listenerList = null;
    private OnConnectedListenerServer listener = null;
    
    public ManagerConections(ServerSocket server){
        this.skServer = server;        
        //this.listenerList = new EventListenerList();
    }
    //public void addEventListener(EventListener listener){
    public void addEventListener(OnConnectedListenerServer listener){
        //this.listenerList.add(EventListener.class, listener);
        if(this.listener == null){
            this.listener = listener;
        }
    }
    public void removeEventListener(OnConnectedListenerServer listener){
        //this.listenerList.remove(EventListener.class, listener);
        this.listener = null;
    }
    @Override
    public void run() {
        
        while(LISTENING){
            this.onServerStar();
            this.conectado();
        }
    }
    /**
     * Metodo que lanza el evento cuando se inicia el servicio del servidor
     */
    private void onServerStar(){
        listener.onServerStar(new OnConnectedEventServer(this));
    }

    private void conectado(){
        try{
            skConexion = skServer.accept();
            
            ComunicationServer comunicacion = new ComunicationServer(skConexion, listener);
            comunicacion.start();
            this.onConnect("key de ip");
            JSocketServer.setConnectionClient(comunicacion);
        }catch(IOException ex){
            System.out.println("Error in jsocket.servidor.ManagerConections.onAccept()");
        }
    }
    public void detenerServicio(){
      // aqui tendremos todas las opciones para la detencion   
    }
    /**
     * Envia un mensaje a todos los clientes conectados
     * @param msg Mensaje a enviar al cliente
     */
    public void sendMessageAll(String msg){
        //modificar esto por que se cambio el hashMap
        for (Map.Entry<String, ComunicationServer> entry : clientHashMap.entrySet()){
            String key = entry.getKey();
            ComunicationServer value = entry.getValue();
            value.escribirDatos(msg);
        }
    }
    /**
     * Metodo que lanza el evento de escritura
     */
    public void onWrite(){
        listener.onWrite(new OnConnectedEventServer(this));
    }
    /**
     * Metodo que lanza el evento cuando se conecta un cliente al servidor
     * @param ip String que contiene la ip del servidor
     */
    private void onConnect(String ip){
        listener.onConnect(new OnConnectedEventServer(ip));
    }
}
