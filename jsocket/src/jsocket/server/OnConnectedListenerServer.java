/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsocket.server;

import java.util.EventListener;

/**
 *
 * @author limbe
 */
public interface OnConnectedListenerServer  extends EventListener{
    /**
     * Ocurre cuando el servidor levanta el servicio correctamente
     * @param sender Referencia [managerConnection]
     */
    public void onServerStar(OnConnectedEventServer sender);// ya esta mejorado

    /**
     * Se produce cuando un cliente se conecta al servidor
     * @param sender Referencia Mensaje que se genera [String]
     * */
    public void onConnect(OnConnectedEventServer sender); // ya esta mejorado

    /**
     * Se produce cuando un cliente se desconecta del servidor
     * @param sender Referencia del objeto que esta generando el evento
     * */
    public void onDisconnect(OnConnectedEventServer sender);	

    /**
     * Se produce cuando se ocurre un error dentro del socket
     * @param msg mensaje de error
     * */
    public void onError(String msg); // no esta siendo utilizado

    /**
     * Se produce cuando llegan mensajes del cliente
     * @param sender Referencia del objeto [ComunicationServer] 
     * @param keyClient String Identificador del cliente que envia el mensaje
     * */
    public void onRead(OnConnectedEventServer sender, String keyClient); // ya esta mejorado
        // los que tengan X estan ya solucionado
    /**
     * Se produce cuando se envia mensaje al servidor
     * @param sender Referencia al EventObject [managerConections]
     * */
    public void onWrite(OnConnectedEventServer sender); // ya esta mejorado
}
