/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsocket.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import jsocket.utils.OnReachableClientListener;


/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class CheckClient extends Thread{
    
    private boolean LISTING = true;
    
    private int timeInterval = 1000;
    
    private OnReachableClientListener listener = null;
    
    public CheckClient(OnReachableClientListener listener){
        this.LISTING = true;
        this.timeInterval = 1000;
        this.listener = listener;
    }
    
    @Override
    public void run(){
        int key;
        while(LISTING){
            try {
                key = this.estaConectado();
                if(key > 0){
                    listener.onUnAvailable(key);
                }
                Thread.sleep(timeInterval);
            } catch (InterruptedException ex) {
                System.out.println("[CheckClient.run] " + ex.getMessage());
            }
        }
    }
    /**
     * Metodo que verifica si hay conexion con el cliente.
     * @return un numero negativo si todos los clientes estan conectados.
     * caso contrario una key del socket que esta desconectado.
     */
    public int estaConectado(){
        HashMap clientes = JSocketServer.getClients();
        ComunicationServer cliente;
        Iterator<ComunicationServer> it = clientes.values().iterator();
        while(it.hasNext()){
            cliente = it.next();
            try {
                boolean sw = cliente.getConexion().isReachable(500);
                System.out.println( "< " + cliente.getConexion().getHostAddress() + " >" + String.valueOf(cliente.getKey()) + ": " + String.valueOf(sw));
            } catch (IOException ex) {
                System.out.println("[CheckClient.estaConectado] no se encontro conexion");
                return cliente.getKey();
            }
        }
        return -1;
    }
}

