package jsocket.client;

import java.util.EventObject;
import jsocket.utils.Paquete;
/**
 * Evento que se lanza dentro de los escuchadores del cliente
 * @author Alex Limbert Yalusqui
 */
public class OnConnectedEventClient  extends EventObject{
    
    public OnConnectedEventClient(Object source) {
        super(source);
    }
    
    /**
    * Metodo devuelve el mensaje que llega al cliente
    * @return String mensaje
    */
    public String getMessage(){
        if(this.getSource() instanceof Paquete){
            return ((Paquete) this.getSource()).getMsg();
        }else{
            return "";
        }
    }
}
