package jsocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Administrador de conexiones de los clientes, donde estan todos los clientes conectados
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com> 
 */
public class ManagerConections extends Thread{
    
    private Socket skConexion = null;
    private int key = 1;
    private boolean LISTENING = true;
    private ServerSocket skServer = null;
    
    
    
    public ManagerConections(ServerSocket server){
        skServer = server;
    }
    @Override
    public void run() {
        
        while(LISTENING){
            JSocketServer.onServerStar(skServer.getInetAddress().getHostAddress());
            this.conectado();
        }
    }

    private void conectado(){
        try{
            skConexion = skServer.accept();
            
            ComunicationServer comunicacion = new ComunicationServer(skConexion, key);
            comunicacion.start();
            System.out.println("cliente conectado");
            System.out.println("cliente conectado key : " + String.valueOf(comunicacion.getKey()));
            JSocketServer.onConnect(comunicacion.getKey(), skConexion.getInetAddress().getHostAddress() + " : " + String.valueOf(skConexion.getPort()));
            JSocketServer.setConnectionClient(comunicacion);
            key = key + 1;
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
