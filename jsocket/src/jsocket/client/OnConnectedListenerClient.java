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
    public void onConnect(Object sender, OnConnectedEventClient data);

    /**
     * Se produce cuando se desconecta del servidor
     * @param sender Referencia del objeto que esta generando el evento
     * */	
    public void onDisconnect(Object sender, OnConnectedEventClient data);

    /**
     * Se produce cuando llegan mensajes del servidor
     * @param sender Referencia del objeto [ComunicationClient]
     * */		
    public void onRead(Object sender, OnConnectedEventClient data);

}