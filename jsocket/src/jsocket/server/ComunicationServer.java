package jsocket.server;
import jsocket.client.OnConnectedEventClient;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * clase escuchador que envia y recibe los mensajes que llegan de los clientes
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class ComunicationServer extends Thread{

    private boolean LISTING = true;
    private Socket skConexion = null;
    private DataInputStream stRead = null;
    private DataOutputStream stWrite = null;

    private OnConnectedListenerServer listener = null;
    
    public ComunicationServer(Socket client, OnConnectedListenerServer listener){
        this.skConexion = client;
        this.LISTING = true;
        this.listener = listener;
    }
    /**
     * Proceso que se ejecutara cuando se desencadene una lectura
     */
    @Override
    public void run() {
        // Obtenemos los flujos de datos
        this.getFlujo();
        // escuchando los paquetes que el servidor enviará        
        while(LISTING){
            if(skConexion.isClosed()){
                System.out.println("Socket cerrado");
            }
            if(!skConexion.isConnected()){
                System.out.println("Socket Desconectado");
            }
            this.leerDatos();
        }
    }
    private void getFlujo(){
        try{
            stRead = new DataInputStream(skConexion.getInputStream());
            stWrite = new DataOutputStream(skConexion.getOutputStream());
            stWrite.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
   /**
    * Envia un mensaje al cliente
    * @param msg mensaje para enviar a los cliente
    */
    public void escribirDatos(String msg){
        try {
            stWrite.writeUTF(msg);
            stWrite.flush();
        } catch (IOException e) {
            System.out.println("Error al scribir  datos" + e.getMessage());
        }
    }    
    private void leerDatos(){
        try {
            String msg = stRead.readUTF();
            listener.onRead(new OnConnectedEventServer(msg, "lectura"));
        } catch (IOException e) {
            System.out.println("Error a leer Datos " + e.getMessage());
        }
        
    }
}