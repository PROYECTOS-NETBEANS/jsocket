package jsocket.utils;

import java.util.EventObject;

public class OnConnectedEvent  extends EventObject{
    private String idObject = "";
    
    public OnConnectedEvent(Object source, String id) {
        super(source);
        this.idObject = id;
    }
    public String getIdObject(){
        return this.idObject;
    }
    
    
}
