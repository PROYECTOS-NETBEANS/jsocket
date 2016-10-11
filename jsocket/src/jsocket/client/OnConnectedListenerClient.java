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
     * @param data
     * */
    public void onConnect(Object sender, OnConnectedEventClient data);

    /**
     * Se produce cuando se desconecta del servidor
     * @param sender Referencia del objeto que esta generando el evento
     * @param data
     * */	
    public void onDisconnect(Object sender, OnConnectedEventClient data);

    /**
     * Se produce cuando llegan mensajes del servidor
     * @param sender Referencia del objeto [ComunicationClient]
     * @param data
     * */		
    public void onRead(Object sender, OnConnectedEventClient data);
    
    /**
     * Metodo que se genera cuando el cliente falla en el intento
     * de conexion con el servidor.
     */
    public void onConnectRefused();
    /**
     * Metodo que se desencadena cuando no se pudo conectar al servidor
     * durante un numero de intentos, por tanto finalizando la aplicacion
     */
    public void onConnectFinally();
}