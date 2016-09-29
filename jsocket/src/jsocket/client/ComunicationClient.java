
package jsocket.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * Clase escuchador que envia y recibe mensajes del servidor
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class ComunicationClient extends Thread{
    
    private Socket skConexion = null;
    private DataInputStream stRead = null;
    private DataOutputStream stWrite = null;
    private boolean LISTING = true;
    private OnConnectedListenerClient listener;
    
    /**
     * Constructor del escuchador que se comunica con el cliente
     * @param conexion Socket de comunicacion con el servidor
     * @param listener Escuchador de eventos del cliente
     */
    public ComunicationClient(Socket conexion, OnConnectedListenerClient listener){
        this.LISTING = true;
        this.skConexion = conexion;
        this.listener = listener;
    }
    
    @Override
    public void run(){
        
        this.getFlujo();
        
        while(LISTING){
            try {
                if(!skConexion.isClosed()){
                    // aqui tengo que lanzar una excepcion
                }
                if(!skConexion.isConnected()){
                    // aqui tengo que lanzar una excepcion
                }
                sleep(1000);
                this.onRead();
            } catch (InterruptedException ex) {
                System.out.println("error : cliente run" + ex.getMessage());
            }
        }
    }
    /**
     * Devuelve los datos que llegan del servidor
     * @return String
     */   
    public String getDatos(){
        try {
            return stRead.readUTF();
        } catch (IOException e) {
            System.out.println("Error en ComunicationClient.getDatos : " + e.getMessage());
            return "";
        }
    }
    /**
     * Metodo que lanza el evento de lectura cuando llega un mensaje
     */
    private void onRead(){
        listener.OnRead(new OnConnectedEventClient(this));
    }
    /**
     * Metodo que envia un mensaje al servidor
     * @param msg Mensaje a enviarse al servidor
     */
    public void sendMessage(String msg){
        try {
            stWrite.writeUTF(msg);
            stWrite.flush();
            System.out.println("Mensaje enviado al server");
            
        } catch (IOException e) {
            System.out.println("Error : writeData : " + e.getMessage());
        }
    }
    /**
     * Metodo que obtiene los flujos de datos del socket
     */
    private void getFlujo(){
        try{
            stRead = new DataInputStream(skConexion.getInputStream());
            stWrite = new DataOutputStream(skConexion.getOutputStream());
            stWrite.flush();
         }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

}