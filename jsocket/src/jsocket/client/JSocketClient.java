/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsocket.client;
import java.io.IOException;
import java.net.Socket;
/**
 * Clase socket del cliente
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class JSocketClient {
    private String IP_SERVER = "";
    private int PUERTO = 5555;
    private Socket skConexion;

    private ComunicationClient comunicacion = null;
    private OnConnectedListenerClient listener = null;

    public JSocketClient(int puerto, String ip){
        this.PUERTO = puerto;
        this.IP_SERVER = ip;
        this.comunicacion = null;
    }
    public void addEventListener(OnConnectedListenerClient listener){
        if(this.listener == null){
            this.listener = listener;
        }
    }
    public void removeEventListener(OnConnectedListenerClient listener){
        this.listener = null;
    }
    
    public void conectarServidor(){
        try {
            skConexion = new Socket(IP_SERVER, PUERTO);
            comunicacion = new ComunicationClient(skConexion, listener);
            comunicacion.start();       
        } catch (IOException e) {
            System.out.println("Error[jclientSocket.conectarServidor]: " + e.getMessage());
        }
    }
    public void desconectarServidor(){
        
    }
    public void onWrite(){
        try{
            OnConnectedEventClient sender = new OnConnectedEventClient(comunicacion);
            listener.onWrite(sender);
            System.out.println("pase el onWrite");
        }catch(Exception e){
            System.out.println("Error onWrite : " + e.getMessage());
        }

    }
}
