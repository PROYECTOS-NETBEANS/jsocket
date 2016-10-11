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
     * Estado que representa cuando se tiene una 
     * conexion establecida al servidor [true : conectado al servidor]
     */
    private boolean estadoConexion = false;
    
    private int puerto = 5555;
    
    private String direccionIp = "localhost";
    
    private OnReachableListener listener = null;
    
    private InetAddress conexion = null;
    
    public ReconnectClient(OnReachableListener listener, String ip, int port){
        this.listener = listener;
        this.LISTING = true;
        this.count = 0;
        this.direccionIp = ip;
        this.puerto = port;
        this.estadoConexion = false;
    }
    /**
     * Cambia el estado de conexion del socket al servidor
     * @param stado True si la socket esta conectado al servidor, falso en otro caso.
     */
    public void setEstadoConexion(boolean stado){
        this.estadoConexion = stado;
    }
    public void setConexion(InetAddress conexion){
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
     * Metodo que verifica si existe una conexion disponible al servidor.
     * @return true: si la conexion valida, caso contrario false
     */
    private boolean isAvalibleConnection(){
        try (Socket sk = new Socket()) {                
                sk.connect(new InetSocketAddress(direccionIp, puerto), 500);
                //sk.connect(new InetSocketAddress( , puerto), 500);
                sk.close();
                System.out.println("isAvalibleConnection : true");
                return true;
        } catch (IOException ex) {
            System.out.println("[ReconnectClient.isAvalibleConnection] No se encuentra conexion");
            return false;
        }        
    }
    /**
     * verifica la conexion con el inetAddress
     * @return 
     */
    private boolean isAvalibleConnectionInetAddress(){
        try {
                if(conexion != null){
                    if(conexion.isReachable(500)){
                        System.out.println("isAvalibleConnection : true");
                        return true;
                    }else{
                        System.out.println("isAvalibleConnection : false");
                        return false;
                    }
                }else{
                    return false;
                }
        } catch (IOException ex) {
            System.out.println("[ReconnectClient.isAvalibleConnection] No se encuentra conexion");
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
                listener.onMessageEco();
                Thread.sleep(timeInterval);
            } catch (InterruptedException ex) {
                System.out.println("[ReconnectClient.run] " + ex.getMessage());
            }
        }
    }
    /*
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run(){
        
        while(LISTING){
            try {
                if(!this.isAvalibleConnectionInetAddress()){
                //if(!this.isAvalibleConnection()){
                    if(count < nroIntentos){
                        count = count + 1;
                        estadoConexion = false;
                        conexion = null;
                        listener.onUnAvailable();
                        
                    }else{
                        // cuando ya se agotaron los intentos de conexion
                        listener.onLostConnection();
                    }                        
                }else{
                    if(!estadoConexion){// no esta conectado 
                        System.out.println("primera conexion");
                        // conectar al servidor
                        listener.onUnAvailable();                        
                    }
                }
                Thread.sleep(timeInterval);
            } catch (InterruptedException ex) {
                System.out.println("[ReconnectClient.run] " + ex.getMessage());
            }
        }
    }    
    
    
    */
}