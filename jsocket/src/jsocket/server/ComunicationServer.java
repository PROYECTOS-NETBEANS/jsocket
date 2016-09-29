package jsocket.server;
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
    private int key = 0;
    
    public ComunicationServer(Socket client, int key){
        this.skConexion = client;
        this.LISTING = true;
        this.key = key;
    }
    public int getKey(){
        return this.key;
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
    /**
     * Metodo que lee los datos que llegan del servidor
     */
    private void leerDatos(){
        try{
            String msg = stRead.readUTF();
            System.out.println("antes del evento on read (leerDatos)");
            JSocketServer.onRead(this.key, msg);
        }catch(IOException e){
            System.out.println("Error : ComunicationServer.leerDatos() key : " + String.valueOf(this.key));
            this.cerrarConexion();
            JSocketServer.onDisconnect(this.key);
        }
    }
    /**
     * Cerramos el stream el socket e hilo
     */
    private void cerrarConexion(){
        try {
            stRead.close();
            stWrite.close();
            skConexion.close();
            this.detenerEscuchador();
        } catch (IOException e) {
            System.out.println("ComunicationServer.cerrarConexion() : " + e.getMessage());
        }
    }
    /**
     * Detiene el escuchador
     */
    public void detenerEscuchador(){
        this.LISTING = false;
    }
}