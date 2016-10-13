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
    private boolean RUN = true;
    private Socket skConexion = null;
    private DataInputStream stRead = null;
    private DataOutputStream stWrite = null;
    private int key = 0;
    private String userName = "";
    /**
     * Constructor del escuchador de mensajes del cliente
     * @param client canal de comunicacion con el cliente
     * @param key Identificador unico de cliente
     */
    public ComunicationServer(Socket client, int key){
        this.skConexion = client;
        this.RUN = true;
        this.key = key;
        this.obtenerFlujos();
    }
    public int getKey(){
        return this.key;
    }
    /**
     * Proceso que se ejecutara cuando se desencadene una lectura
     */
    @Override
    public void run() {
        
        while(RUN){
            //try{
                this.leerDatos();
            //}catch(Exception e){
                //System.out.println("RUN. listening : " + String.valueOf(RUN));
                //RUN = false;
                //System.out.println("[ComunicationsServer.run.] " + e.getMessage());
            //}
        }
    }
    /**
     * Obtiene los flujos la primera vez que inicia y empieza a escuchar
     */
    private void obtenerFlujos(){
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
     * Metodo que envia un mesaje al cliente de esta conexion
     * @param msg Mensaje que se envia a los clientes
     * @param tipo Tipo de mensaje
     * @param keyOrigen El cliente o servidor que envia el mensaje
     */
    public void sendMessage(String msg, TipoMsg tipo, int keyOrigen){

        Paquete paquete = new Paquete(msg, keyOrigen, this.key, tipo);
        if(!this.sendMessage(this.toString(paquete))){
            System.out.println("[ComunicationClient.sendMessage] no se pudo enviar msg al cliente");
            System.out.println("aqui nesecito saltar un evento !!!");
        }
    }
    
    /**
     * Envia un mensaje de eco al cliente.
     * @return true si se envio el mensaje correctamente, falso en otro caso.
     */
    public boolean sendMessageEco(){

        Paquete paquete = new Paquete("", -1, -1, TipoMsg.PQT_ICMP);
        if(!this.sendMessage(this.toString(paquete))){
            System.out.println("Eco no envio!");
            return false;
        }else{
            return true;
        }
    }

    synchronized private boolean sendMessage(String paquete){
        try {         
            stWrite.writeUTF(paquete);
            stWrite.flush();
            System.out.println("Mensaje enviado al cliente");
            return true;
        } catch (IOException e) {
            System.out.println("[ComunicationServer.sendMessage] " + e.getMessage());
            return false;
        }
    }
        
    public void onDisconnect(){
        if(!skConexion.isClosed()){
            System.out.println("onDisconnect");
            this.cerrarConexion();
            JSocketServer.onDisconnect(new Paquete("desconectado", this.key, this.key, TipoMsg.PQT_DESCONECTADO));            
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
    
    /**
     * Metodo que lee los datos que llegan del cliente
     */
    private void leerDatos(){
        try{
            System.out.println("esperando paquetes del cliente");
            String data = stRead.readUTF();
            System.out.println("paquete llegado");
            Paquete paquete = this.toObject(data);
            this.onRead(paquete);
        }catch(IOException e){
            System.out.println("cliente acaba de desconectarse [ComunicationServer.leerDatos]");
            this.onDisconnect();
        }catch(Exception ex){
            System.out.println("[comunicationServer.leerDatos] " + ex.getMessage());
        }
    }
    private void onRead(Paquete paquete){
        paquete.setOrigen(this.key);
        
        switch(paquete.getTipoMsg()){
            case PQT_CONFIGURATION:
                //es paquete que llega con el nick
                this.userName = paquete.getMsg();
                JSocketServer.onConnect(paquete);                
                break;
            case PQT_ICMP:
                System.out.println("msg: mensaje eco");
                break;
            default:
                // si llega un paquete desde el cliente
                JSocketServer.onRead(paquete, this.userName);
        }
    }
    /**
     * Cerramos el stream el socket e hilo
     */
    private void cerrarConexion(){
        try {
            if(!skConexion.isClosed()){
                RUN = false;                
                stRead.close();
                stWrite.close();
                skConexion.close();
                System.out.println("pase comunicationServer.cerrarConexion");                
            }
        } catch (IOException e) {
            System.out.println("[ComunicationServer.cerrarConexion] " + e.getMessage());
        }
    }
}