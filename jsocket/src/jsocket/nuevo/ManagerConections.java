/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsocket.servidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
/**
 *
 * @author limbe
 */
public class ManagerConections implements Runnable{
    
    private ServerSocket skServer = null;
    private Socket skClient = null;
    
    private boolean LISTENING = true;
    private LinkedList<ComunicationsClient> clientes = null;
    
    
    
    public ManagerConections(ServerSocket server){
        this.skServer = server;
        clientes = new LinkedList<ComunicationsClient>();
    }

    @Override
    public void run() {
        
        while(LISTENING){
            this.onAccept();
        }
    }
    private void onAccept(){
        try{
            skClient = skServer.accept();
            //creamos el escuchador y lo ingresamos a la lista de clientes
            ComunicationsClient cliente = new ComunicationsClient(skClient);
            cliente.run();
            clientes.add(cliente);
            
        }catch(IOException ex){
            System.out.println("Error in jsocket.servidor.ManagerConections.onAccept()");
        }
        
        
    }
}
