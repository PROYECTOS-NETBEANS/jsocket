/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import jsocket.utils.OnConnectedEvent;

/**
 *
 * @author limbe
 */
public class JSocketServer {
    
    private int puerto = 5555;
    private ServerSocket skServer;
    private ManagerConections manager = null;
    
    
    public JSocketServer(int puerto){
        this.puerto = puerto;
        init();
    }
 
    public void addEventListener(OnConnectedListenerServer listener){
        manager.addEventListener(listener);
    }
    public void removeEventListener(OnConnectedListenerServer listener){
        manager.removeEventListener(listener);
    }
    private void init(){
        try{
            skServer = new ServerSocket(puerto);
            manager = new ManagerConections(skServer);
        }catch(IOException ex){
            System.out.println("jsocket.server.JServerSocket.inicializar()");        
        }
    }
    /**
     * Metodo que inicia el servidor
     */
    public void iniciarServicio(){
        manager.start();
    }
    // Detiene el servicio y deja de escuchar a los clientes
    public void detenerServicio(){
        manager.interrupt();
    }
    public void onWrite(){
        manager.onWrite();
    }
}