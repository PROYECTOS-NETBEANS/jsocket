
package jsocket.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import jsocket.utils.Paquete;
import jsocket.utils.TipoMsg;
/**
 * Clase escuchador que envia y recibe mensajes del servidor
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class ComunicationClient extends Thread{
    
    private Socket skConexion = null;
    private ObjectInputStream stRead = null;
    private ObjectOutputStream stWrite = null;
    private boolean LISTING = true;
    
    /**
     * Constructor del escuchador que se comunica con el cliente
     * @param conexion Socket de comunicacion con el servidor
     * @param listener Escuchador de eventos del cliente
     */
    public ComunicationClient(Socket conexion){
        this.LISTING = true;
        this.skConexion = conexion;
    }
    
    @Override
    public void run(){
        
        this.getFlujo();
        
        while(LISTING){
            if(!skConexion.isClosed()){
                // aqui tengo que lanzar una excepcion
            }
            if(!skConexion.isConnected()){
                // aqui tengo que lanzar una excepcion
            }
            this.leerDatos();
        }
    }
    /**
     * Metodo que lee los datos que llegan del servidor
    */
    private void leerDatos(){
        try{
            Paquete paquete = (Paquete) stRead.readObject();
            System.out.println("antes del evento on read (leerDatos)");
            JSocketClient.onRead(paquete);
        }catch(IOException e){
            System.out.println("entre a desconectar [ComunicationServer.leerDatos] " + e.getMessage());
            this.desconectado();
        } catch (ClassNotFoundException ex) {
            System.out.println("[ComunicationServer.leerDatos] " + ex.getMessage());
        }        
    }
    private void desconectado(){
        JSocketClient.onDisconnect(new Paquete("desconectado", -1, -1, TipoMsg.PQT_DESCONECTADO));
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
            stRead = new ObjectInputStream(skConexion.getInputStream());
            stWrite = new ObjectOutputStream(skConexion.getOutputStream());
            stWrite.flush();
         }catch(IOException e){
            System.out.println("[ComunicationClient.getFlujo] " + e.getMessage());
        }
    }

}