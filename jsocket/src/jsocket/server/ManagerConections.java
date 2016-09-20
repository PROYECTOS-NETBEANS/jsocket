package jsocket.server;

import jsocket.client.OnConnectedEventClient;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ManagerConections extends Thread{
    
    private ServerSocket skServer = null;
    private Socket skConexion = null;
    
    private boolean LISTENING = true;

    private HashMap<String, ComunicationServer> clientHashMap = null;

    //private EventListenerList listenerList = null;
    private OnConnectedListenerServer listener = null;
    
    public ManagerConections(ServerSocket server){
        this.skServer = server;
        this.clientHashMap = new HashMap<>();
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
    
    private void onServerStar(){
        OnConnectedEventServer sender = new OnConnectedEventServer(this, "Servidor iniciado");
        listener.onServerStar(sender);
    }

    private void conectado(){
        try{
            skConexion = skServer.accept();
            
            String key = skConexion.getInetAddress().getHostAddress()+ ":" + String.valueOf(skConexion.getPort());
            ComunicationServer comunicacion = new ComunicationServer(skConexion, listener);
            comunicacion.start();
            this.onConnect(key);
            clientHashMap.put(key, comunicacion);
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
        
        clientHashMap.entrySet().stream().forEach((entry) -> {
            String key = entry.getKey();
            ComunicationServer value = entry.getValue();
            value.escribirDatos(msg);
        });
    }
    /**
     * Metodo que lanza el evento de escritura
     */
    public void onWrite(){
        OnConnectedEventServer sender = new OnConnectedEventServer(this, "Escribir");
        listener.onWrite(sender);
    }
    
    private void onConnect(String key){
        OnConnectedEventServer sender = new OnConnectedEventServer(key, "usuario conectado");
        listener.onConnect(sender);
    }
}
