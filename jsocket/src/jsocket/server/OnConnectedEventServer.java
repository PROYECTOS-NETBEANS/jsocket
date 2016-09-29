package jsocket.server;

import java.util.EventObject;
import jsocket.utils.Paquete;
import jsocket.utils.TipoMsg;

/**
 * Evento que se lanza dentro de los escuchadores del servidor
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class OnConnectedEventServer extends EventObject{
    
    public OnConnectedEventServer(Object source) {
        super(source);
    }
    /**
     * Metodo que envia mensajes a todos los clientes que estan conectados
     * @param msg Mensaje que se enviara a los clientes conectados
     *
    public void sendMessageAll(String msg){
        if(this.getSource() instanceof ManagerConections)
            ((ManagerConections) this.getSource()).sendMessageAll(msg);
    }
    */
    
    /**
     * Devuelve la ip del servidor despues de iniciar el servidor
     * @return String
     */
    public String getIpServer(){
        
        if(this.getSource() instanceof Paquete){
            return ((Paquete) this.getSource()).getMsg();
        }else{
            return "Ip no encontrada !!";
        }
    }
    public int getKeyClient(){
        if(this.getSource() instanceof Paquete){
            return ((Paquete) this.getSource()).getKey();
        }else{
            return 0;
        }
    }
    /**
     * Metodo devuelve el mensaje que llega de los clientes
     * @return String mensaje
     */
    public String getMessageClient(){
        if(this.getSource() instanceof Paquete){
            return ((Paquete) this.getSource()).getMsg();
        }else{
            return "";
        }
    }
    /**
     * Devuelve true si el cliente esta desconectado, falso caso contrario
     * @return 
     */
    public boolean getClientDisconnect(){
        if(this.getSource() instanceof Paquete){
            return (((Paquete) this.getSource()).getTipoMsg() == TipoMsg.MSG_DESCONECTADO);
        }else{
            return false;
        }        
    }
}
