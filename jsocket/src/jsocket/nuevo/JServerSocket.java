/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsocket.servidor;

import java.io.IOException;
import jsocket.servidor.ManagerConections;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author limbe
 */
public class JServerSocket {
    private int puerto = 5555;
    private ServerSocket skServer;
    private ManagerConections manager = null;
    
    
    public JServerSocket(){
        inicializar();
    }
    
    private void inicializar(){
        try{
            skServer = new ServerSocket(puerto);
            manager = new ManagerConections(skServer);
        }catch(IOException ex){
            
        }
    }

}
