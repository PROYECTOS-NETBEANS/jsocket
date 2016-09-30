package jsocket.server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsocket.utils.Paquete;
import jsocket.utils.TipoMsg;
/**
 * clase escuchador que envia y recibe los mensajes que llegan de los clientes
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class ComunicationServer extends Thread{
    private boolean LISTING = true;
    private Socket skConexion = null;
    private ObjectInputStream stRead = null;
    private ObjectOutputStream stWrite = null;
    private int key = 0;
    
    /**
     * Constructor del escuchador de mensajes del cliente
     * @param client canal de comunicacion con el cliente
     * @param key Identificador unico de cliente
     */
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
            try{
                this.leerDatos();
            }catch(Exception e){
                System.out.println("[ComunicationsServer.run] " + e.getMessage());
            }
            
        }
    }
    /**
     * Obtiene los flujos la primera vez que inicia y empieza a escuchar
     */
    private void getFlujo(){
        try{
            stRead = new ObjectInputStream(skConexion.getInputStream());
            stWrite = new ObjectOutputStream(skConexion.getOutputStream());
            stWrite.flush();
        }catch(IOException e){
            System.out.println("[ComunicationServer.getFlujo] " + e.getMessage());
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
            System.out.println("[ComunicationServer.escribirDatos] " + e.getMessage());
            this.cerrarConexion();
            this.desconectado();
        }
    }
    private void desconectado(){
        JSocketServer.onDisconnect(new Paquete("desconectado", this.key, this.key, TipoMsg.PQT_DESCONECTADO));
    }
    /**
     * Metodo que lee los datos que llegan del cliente
     */
    private void leerDatos(){
        try{
            Paquete paquete = (Paquete) stRead.readObject();
            System.out.println("antes del evento on read (leerDatos)");
            JSocketServer.onRead(paquete);
        }catch(IOException e){
            System.out.println("entre a desconectar [ComunicationServer.leerDatos] " + e.getMessage());
            this.desconectado();
        } catch (ClassNotFoundException ex) {
            System.out.println("[ComunicationServer.leerDatos] " + ex.getMessage());
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
            System.out.println("pase comunicationServer.cerrarConexion");
        } catch (IOException e) {
            System.out.println("[ComunicationServer.cerrarConexion] " + e.getMessage());
        }
    }
    /**
     * Detiene el escuchador
     */
    public void detenerEscuchador(){
        this.LISTING = false;
        this.cerrarConexion();
        System.out.println("pase comunicationServer.detenerEscuchador");
    }
}