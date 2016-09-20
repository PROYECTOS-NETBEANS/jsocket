package jsocket.server;

import java.util.EventObject;

/**
 * 
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class OnConnectedEventServer extends EventObject{
    private String idObject = "";
    
    public OnConnectedEventServer(Object source, String id) {
        super(source);
        this.idObject = id;
    }
    public String getIdObject(){
        return this.idObject;
    }
}
