package jsocket.server;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import jsocket.utils.Paquete;
import jsocket.utils.TipoMsg;
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
    private String username = "";
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
        System.out.println("entre al hilo");
        this.getFlujo();
        // escuchando los paquetes que el servidor enviará        
        System.out.println("entrando al while");
        while(LISTING){
            try{
                System.out.println("antes de entrar al metodo");
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
            stRead = new DataInputStream(skConexion.getInputStream());
            System.out.println("pase st read server");
            stWrite = new DataOutputStream(skConexion.getOutputStream());
            System.out.println("pase st read cliente");
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
    
    /**
     * Metodo que lee los datos que llegan del cliente
     */
    private void leerDatos(){
        try{
            String data = stRead.readUTF();

            Paquete paquete = this.toObject(data);
            paquete.setOrigen(this.key);
            
            this.onRead(paquete);
        }catch(IOException e){
            System.out.println("cliente desconectado [ComunicationServer.leerDatos] " + e.getMessage());
            this.desconectado();
        }
    }
    private void onRead(Paquete paquete){
        if(paquete.getTipoMsg() == TipoMsg.PQT_CONFIGURATION){
            JSocketServer.onConnect(paquete);
        }else{
            JSocketServer.onRead(paquete);
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