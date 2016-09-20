package jsocket.client;

import java.util.EventObject;

public class OnConnectedEventClient  extends EventObject{
    
    public OnConnectedEventClient(Object source) {
        super(source);
    }
    /**
     * Metodo que envia un mensaje al servidor
     * @param msg String que se envia al servidor
     */
    public void sendMessage(String msg){
        if(this.getSource() instanceof ComunicationClient)
            ((ComunicationClient) this.getSource()).sendMessage(msg);
            
    }
    /**
     * Funcion que devuelve los datos llegados del servidor
     * @return String
     */
    public String getDatos(){
        if(this.getSource() instanceof ComunicationClient)
            return ((ComunicationClient) this.getSource()).getDatos();
        else
            return "";
    }
}
