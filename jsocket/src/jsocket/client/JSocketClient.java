/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsocket.client;
import java.io.IOException;
import java.net.Socket;
import java.util.EventListener;
import javax.swing.event.EventListenerList;
import jsocket.utils.Paquete;
import jsocket.utils.TipoMsg;
/**
 * Clase socket del cliente que envia y recibe mensajes del servidor
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class JSocketClient {
    
    private String IP_SERVER = "";
    private int PUERTO = 5555;
    private Socket skConexion;

    private ComunicationClient comunicacion = null;
    private static EventListenerList listenerList = new EventListenerList();

    public JSocketClient(int puerto, String ip){
        this.PUERTO = puerto;
        this.IP_SERVER = ip;
        this.comunicacion = null;
        listenerList = new EventListenerList();
    }
    /**
     * adiciona un escuchador de eventos a la lista de escuhadores
     * @param listener Escuchador de eventos
     */
    public void addEventListener(EventListener listener){
        JSocketClient.listenerList.add(EventListener.class, listener);
    }
    /**
     * Elimina de la lista de escuchadores de eventos el escuhador de entrada
     * @param listener Escuhador a eliminar de la lista
     */
    public void removeEventListener(EventListener listener){
        JSocketClient.listenerList.remove(EventListener.class, listener);
      
    }
     /**
     * Metodo que lanza el evento cuando se conecta al servidor
     * @param paquete Es lo que se genera cuando el cliente se conecta
     */
    public static void onConnect(Paquete paquete){
        Object[] listeners = JSocketClient.listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          
          if(listeners[i] instanceof OnConnectedListenerClient){
              System.out.println("jsocketclient.onConnect : key " + String.valueOf(paquete.getOrigen()));
              OnConnectedEventClient sender = new OnConnectedEventClient(paquete);
              ((OnConnectedListenerClient)listeners[i]).onConnect("onconnected", sender);
          }
        }
    }    
    /**
     * Metodo que lanza el evento de desconexion
     */
    public static void onDisconnect(Paquete paquete){
        Object[] listeners = JSocketClient.listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          if(listeners[i] instanceof OnConnectedListenerClient){
              System.out.println("pase onDisconect 1");
              OnConnectedEventClient sender = new OnConnectedEventClient(paquete);
              System.out.println("pase onDisconect 2");
              ((OnConnectedListenerClient)listeners[i]).onDisconnect("disconnect", sender);
              System.out.println("pase onDisconect 3");
          }
        }       
    }

    public void conectarServidor(String nick){
        try {
            skConexion = new Socket(IP_SERVER, PUERTO);
            comunicacion = new ComunicationClient(skConexion);
            comunicacion.start();
            
            System.out.println("dicen que esta conectado");
            this.sendConfiguration(nick);
        } catch (IOException e) {
            System.out.println("[JSocketClient.conectarServidor]: " + e.getMessage());
        }
    }
     /**
     * Metodo que lanza el evento de lectura del servidor
     * @param paquete Paquete con la informacion que llega desde el cliente
     */
    public static void onRead(Paquete paquete){
        Object[] listeners = JSocketClient.listenerList.getListenerList();
        for(int i = 0; i<listeners.length; i++){
          if(listeners[i] instanceof OnConnectedListenerClient){
              OnConnectedEventClient sender = new OnConnectedEventClient(paquete);
              ((OnConnectedListenerClient)listeners[i]).onRead("read", sender);
          }
        }       
    }
    
    public void sendMessagePrivado(String msg, int keyDestino){
        comunicacion.sendMessage(msg, TipoMsg.MSG_PRIVADO, keyDestino);
    }
    public void sendMessageAll(String msg){
        System.out.println("send message 1");
        comunicacion.sendMessage(msg, TipoMsg.MSG_PUBLICO, -1);
    }
    /**
     * Este metodo envia las configuraciones necesarias al servidor
     * 
     */
    private void sendConfiguration(String nick){   
        System.out.println("entre a enviar nick");
        comunicacion.sendMessage(nick, TipoMsg.PQT_CONFIGURATION, -1);
    }
}