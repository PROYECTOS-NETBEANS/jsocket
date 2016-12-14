package jsocket.server;

import java.util.HashMap;
import java.util.Iterator;
import jsocket.utils.OnReachableClientListener;

/**
 * Clase encargada de verificar si la conexion del cliente esta activa
 * @author Alex Limbert Yalusqui Godoy
 */
public class CheckClient extends Thread{
    
    private boolean LISTING = true;
    
    private int timeInterval = 1000;
    
    private OnReachableClientListener listener = null;
    
    public CheckClient(OnReachableClientListener listener, int timeInterval){
        this.LISTING = true;
        this.timeInterval = timeInterval;
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
                boolean sw = cliente.sendMessageEco();
                if(!sw){
                    System.out.println("cliente desconectado : " + String.valueOf(cliente.getKey()));
                    return cliente.getKey();
                }
        }
        return -1;
    }
}

