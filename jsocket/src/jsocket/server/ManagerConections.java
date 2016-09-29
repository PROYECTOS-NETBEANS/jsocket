package jsocket.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
/**
 * Administrador de conexiones de los clientes, donde estan todos los clientes conectados
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com> 
 */
public class ManagerConections extends Thread{
    
    private Socket skConexion = null;
    private int key = 1;
    private boolean LISTENING = true;
        
    public ManagerConections(){
        
    }
    @Override
    public void run() {
        
        while(LISTENING){
            JSocketServer.onServerStar();
            this.conectado();
        }
    }

    private void conectado(){
        try{
            skConexion = JSocketServer.getSkServer().accept();
            
            ComunicationServer comunicacion = new ComunicationServer(skConexion, key);
            key = key + 1;
            comunicacion.start();
            JSocketServer.onConnect(key, skConexion.getInetAddress().getHostAddress() + " : " + String.valueOf(skConexion.getPort()));
            JSocketServer.setConnectionClient(comunicacion);
        }catch(IOException ex){
            System.out.println("Err : ManagerConections.onAccept() " + ex.getMessage());
        }
    }
    public void detenerServicio(){
      // aqui tendremos todas las opciones para la detencion   
    }
    /**
     * Envia un mensaje a todos los clientes conectados
     * @param msg Mensaje a enviar al cliente
     *
    public void sendMessageAll(String msg){
        //modificar esto por que se cambio el hashMap
        for (Map.Entry<String, ComunicationServer> entry : clientHashMap.entrySet()){
            String key = entry.getKey();
            ComunicationServer value = entry.getValue();
            value.escribirDatos(msg);
        }
    }
    */
    
    /**
     * Metodo que lanza el evento de escritura
     
    public void onWrite(){
        listener.onWrite(new OnConnectedEventServer(this));
    }
    */

}
