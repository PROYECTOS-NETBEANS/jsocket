package jsocket.client;

import java.util.EventObject;
/**
 * Evento que se lanza dentro de los escuchadores del cliente
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com> */
public class OnConnectedEventClient  extends EventObject{
    
    public OnConnectedEventClient(Object source) {
        super(source);
    }
}
