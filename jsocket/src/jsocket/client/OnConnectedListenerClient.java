package jsocket.client;

import java.util.EventListener;
/**
 * Intefaza que escucha los eventos del cliente socket
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public interface OnConnectedListenerClient extends EventListener{
    /**
     * Se produce cuando se conecta al servidor
     * @param sender Referencia del objeto que esta generando el evento
     * */
    public void onConnect(OnConnectedEventClient sender);

    /**
     * Se produce cuando se desconecta del servidor
     * @param sender Referencia del objeto que esta generando el evento
     * */	
    public void onDisconnect(OnConnectedEventClient sender); // no se esta utilizando

    /**
     *  Se produce cuando ocurre un error
     * @param msg Mensaje de error
     * */		
    public void onError(String msg); // no se esta utilizando

    /**
     * Se produce cuando llegan mensajes del servidor
     * @param sender Referencia del objeto [ComunicationClient]
     * */		
    public void OnRead(OnConnectedEventClient sender); // ya esta mejorado

    /**
     * Se produce cuando se envia un mensaje al servidor
     * @param sender Referencia del objeto [ComunicationClient]
     * */			
    public void onWrite(OnConnectedEventClient sender); // ya esta mejorado
}