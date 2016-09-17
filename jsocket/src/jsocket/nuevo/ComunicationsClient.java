/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsocket.servidor;
import java.net.Socket;
/**
 *
 * @author limbe
 */
public class ComunicationsClient implements Runnable{

    private boolean LISTING = true;
    private Socket cliente;

    public ComunicationsClient(Socket client){
        this.cliente = client;
    }

    @Override
    public void run() {
        // escuchando los paquetes que el servidor enviará
        while(LISTING){
            
        }
    }
    
}
