package jsocket.server;

import java.util.EventObject;
import jsocket.utils.Paquete;
import jsocket.utils.TipoMsg;

/**
 * Evento que se lanza dentro de los escuchadores del servidor
 * @author Alex Limbert Yalusqui
 */
public class OnConnectedEventServer extends EventObject{
    
    public OnConnectedEventServer(Object source) {
        super(source);
    }

    /**
     * Obtiene el identificador de cliente que envio el mensaje
     * @return Identificador del cliente que origino el mensaje
     */
    public int getOrigenClient(){
        if(this.getSource() instanceof Paquete){
            return ((Paquete) this.getSource()).getOrigen();
        }else{
            return 0;
        }
    }
    /**
     * Obtiene la key del cliente a donde va dirigido el mensaje
     * @return Identificador del cliente destinatario
     */
    public int getDestinoClient(){
        if(this.getSource() instanceof Paquete){
            return ((Paquete) this.getSource()).getDestino();
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
     * @return true si el cliente esta desconectado, en otro caso false
     */
    public boolean getClientDisconnect(){
        if(this.getSource() instanceof Paquete){
            return (((Paquete) this.getSource()).getTipoMsg() == TipoMsg.PQT_DESCONECTADO);
        }else{
            return false;
        }        
    }
    /*
    public String getUserName(){
        if(this.getSource() instanceof Paquete){
            Paquete p = (Paquete) this.getSource();
            if(p.getTipoMsg() == TipoMsg.PQT_CONFIGURATION){
                return p.getMsg();
            }else{
                return "";
            }
        }else{
            return "";
        }
        
    }
    */
}
