package jsocket.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import jsocket.utils.OnReachableListener;

/**
 * Clase encargada de la reconexion con el servidor
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class ReconnectClient extends Thread{
    
    /**
     * Variable donde se configura el numero de reconexiones que hara el cliente
     * antes de darse por vencido.
     */
    private int nroIntentos = 3;
    /**
     * Varibla que lleva la cuenta del numero de intentos
     * de conexion que se llevaron a cabo.
     */
    private int count = 0;
    /**
     * Tiempo o intervalo que se tendra entre intentos de reconexiones.
     */
    private int timeInterval = 1000;
    /**
     * Variable que define si el hilo esta funcional.
     */
    private boolean LISTING = true;
    /**
     * Conexion al servidor.
     */
    private InetAddress conexion = null;
    
    private OnReachableListener listener = null;
    
    public ReconnectClient(OnReachableListener listener){
        this.listener = listener;
        this.LISTING = true;
        this.count = 0;
    }
    /**
     * Metodo que inicializa la configuracion del hilo
     * antes de ejecutar el hilo.
     * @param conexion Conexion al servidor
     */
    public void setConfiguracion(InetAddress conexion){
        this.conexion = conexion;
    }
    /**
     * Metodo que indica el tiempo que espera antes de intentar reconectar
     * [1000 = 1sg]
     * @param times tiempor medido en milisegundos
     */
    public void setTimeInterval(int times){
        this.timeInterval = times;
    }
    /**
     * Metodo que indica el nro de intentos a reconectar
     * @param nro intentos de reconexion
     */
    public void setNroIntento(int nro){
        this.nroIntentos = nro;
    }
    /**
     * Metodo que verifica si la conexion con el servidor esta disponible
     * @return true: si la conexion valida, caso contrario false
     */
    private boolean isAvalibleConnection(){
        try {
                try(Socket sk = new Socket()){
                    sk.connect(new InetSocketAddress(conexion, 5555), 500);
                    System.out.println("es reachable " + conexion.getHostAddress() + " :  true");
                }
                return true;             
        } catch (IOException ex) {
            System.out.println("[ReconnectClient.isAvalibleConnection] " + ex.getMessage());
            return false;
        }
    }
    /**
     * Metodo que cambia a false la variable LISTING del hilo principal
     */
    public void detener(){
        this.LISTING = false;
        this.count = 0;
    }
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run(){
        
        while(LISTING){
            try {
                if(this.conexion != null){
                    if(!this.isAvalibleConnection()){
                        if(count < nroIntentos){
                            //count = count + 1;
                            listener.onUnAvailable();
                        }else{
                            // cuando ya se agotaron los intentos de conexion                            
                            listener.OnLostConnection();
                        }                        
                    }              
                }
                Thread.sleep(timeInterval);
            } catch (InterruptedException ex) {
                System.out.println("[ReconnectClient.run] " + ex.getMessage());
            }
        }
    }
}