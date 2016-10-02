package jsocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
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
            System.out.println("usuario conectado : (esperandoConexiones)");
            ComunicationServer comunicacion = new ComunicationServer(skConexion, key);
            comunicacion.start();
            JSocketServer.addClient(comunicacion);
            key = key + 1;
        }catch(IOException ex){
            // servidor cerrado
            this.LISTENING = false;
            System.out.println("finalizando la espera de conexion");
        }
    }
    
    /**
     * Detiene el escuchador de cliente
     */
    public void detenerServicio(){
        LISTENING = false;        
        this.detenerConexionesClientes();
        this.cerrarConexion();
        System.out.println("pase managerConections.detenerServicio");
    }
    private void detenerConexionesClientes(){
        try{
            System.out.println("deten 1 ");
            
            Iterator it = JSocketServer.getClients().entrySet().iterator();
            while(it.hasNext()){
                Map.Entry entry = (Map.Entry) it.next();
                int keyValue = (int) entry.getKey();
                ComunicationServer cliente = (ComunicationServer) entry.getValue();
                cliente.onDisconnect();
                System.out.println("key > " + String.valueOf(cliente.getKey()) + " : " + String.valueOf(keyValue));
                it.remove();
            }
            System.out.println("fin sercives : " + String.valueOf(JSocketServer.getClients().size()));
        }catch(Exception e){
            System.out.println("[JSocketServer.detenerServicio] " + e.getMessage());
        }        
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
    
}
