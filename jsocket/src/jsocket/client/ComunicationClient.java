
package jsocket.client;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import jsocket.utils.Paquete;
import jsocket.utils.TipoMsg;
/**
 * Clase escuchador que envia y recibe mensajes del servidor
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class ComunicationClient extends Thread{
    
    private Socket skConexion = null;
    private DataInputStream stRead = null;
    private DataOutputStream stWrite = null;
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
        System.out.println("run pase get flujo");
        while(LISTING){
            System.out.println("entre al while");
            this.leerDatos();
            System.out.println("sali del leerDatos");
        }
    }
    /**
     * Metodo que lee los datos que llegan del servidor
    */
    private void leerDatos(){
        try{
            System.out.println("antes de esperar read");
            String data = stRead.readUTF();
            System.out.println("antes del evento on read (leerDatos)");            
            
            JSocketClient.onRead(this.toObject(data));
        }catch(IOException e){
            System.out.println("entre a desconectar [ComunicationServer.leerDatos] " + e.getMessage());
            this.desconectado();
        }        
    }
    private Paquete toObject(String data){
        Gson g = new Gson();
        Paquete paquete = g.fromJson(data, Paquete.class);
        
        return paquete;
    }
    private String toString(Paquete paquete){
        Gson g = new Gson();
        String data = g.toJson(paquete);
        
        return data;
    }
    private void desconectado(){
        JSocketClient.onDisconnect(new Paquete("desconectado", -1, -1, TipoMsg.PQT_DESCONECTADO));
    }
    
    /**
     * Metodo que envia un mensaje al servidor
     * @param msg Mensaje a enviarse al servidor
     * @param tipo Tipo de mensaje que se enviara
     * @param keyDestino El usuario a donde se enviara el mensaje
     */
    public void sendMessage(String msg, TipoMsg tipo, int keyDestino){
        try {
            System.out.println("pase jjjj");
            Paquete paquete = new Paquete(msg, -1, keyDestino, tipo);
            System.out.println("pase pque");
            stWrite.writeUTF(this.toString(paquete));
            stWrite.flush();
            System.out.println("Mensaje enviado al servidor");
        } catch (IOException e) {
            System.out.println("[ComunicationClient.sendMessage] " + e.getMessage());
        }
    }
    /**
     * Metodo que obtiene los flujos de datos del socket
     */
    private void getFlujo(){
        try{
            System.out.println("getflujo entre");
            stRead = new DataInputStream(skConexion.getInputStream());
            System.out.println("stRead pase");
            stWrite = new DataOutputStream(skConexion.getOutputStream());
            System.out.println("stWrite pase");
            stWrite.flush();
            System.out.println("pase el get-flujo client");
            if(stWrite== null)
                System.out.println("stwrite es nulo");
         }catch(IOException e){
            System.out.println("[ComunicationClient.getFlujo] " + e.getMessage());
        }
    }

}