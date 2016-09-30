package jsocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import jsocket.utils.Paquete;
import jsocket.utils.TipoMsg;
/**
 * Administrador de conexiones de los clientes, donde estan todos los clientes conectados
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com> 
 */
public class ManagerConections extends Thread{
    
    private Socket skConexion = null;
    private int key = 1;
    private boolean LISTENING = true;
    private ServerSocket skServer = null;
    
    
    
    public ManagerConections(ServerSocket server){
        skServer = server;
    }
    @Override
    public void run() {
        
        JSocketServer.onServerStar(skServer.getInetAddress().getHostAddress());
        
        while(LISTENING){            
            this.esperandoConexiones();
        }
        
    }
    /**
     * Metodo qu espera las conexiones de clientes
     */
    private void esperandoConexiones(){
        try{
            skConexion = skServer.accept();
            
            ComunicationServer comunicacion = new ComunicationServer(skConexion, key);
            this.usuarioConectado(comunicacion);
            comunicacion.start();
            
            key = key + 1;
        }catch(IOException ex){
            System.out.println("[ManagerConections.esperandoConexiones] " + ex.getMessage());
        }
    }
    /**
     * Metodo que llama al evento del escuchador
     * @param comunicacion 
     */
    private void usuarioConectado(ComunicationServer comunicacion){
        String ip = skConexion.getInetAddress().getHostAddress() + " : " + String.valueOf(skConexion.getPort());
        JSocketServer.onConnect(new Paquete(ip, comunicacion.getKey(), comunicacion.getKey(), TipoMsg.PQT_SALUDO));
        JSocketServer.setConnectionClient(comunicacion);
    }
    /**
     * Detiene el escuchador de cliente
     */
    public void detenerServicio(){
        LISTENING = false;
        this.cerrarConexion();
        System.out.println("pase managerConections.detenerServicio");
    }
    /**
     * Metodo que cierra la conexion del servidor
     */
    private void cerrarConexion(){
        try {
            this.skServer.close();
            System.out.println("pase ManagerConection.cerrarConexion");
        } catch (IOException ex) {
            System.out.println("[ManagerConection.cerrarConexion] " + ex.getMessage());
        }
    }
    /**
     * Envia un mensaje a todos los clientes conectados
     * @param msg Mensaje a enviar al cliente
     *
    public void sendMessageAll(String msg){
        //modificar esto por que se cambio el hashMap
        for (Map.Entry<String, ComunicationServer> entry : clientHashMap.entrySet()){
            String key = entry.getKey();
            ComunicationServer value = entry.getValue();
            value.escribirDatos(msg);
        }
    }
    */
    
    /**
     * Metodo que lanza el evento de escritura
     
    public void onWrite(){
        listener.onWrite(new OnConnectedEventServer(this));
    }
    */

}
