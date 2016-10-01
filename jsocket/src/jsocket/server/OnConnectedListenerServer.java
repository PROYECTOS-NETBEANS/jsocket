/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsocket.server;

import java.util.EventListener;

/**
 * Interface que escucha los eventos del servidor socket
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public interface OnConnectedListenerServer  extends EventListener{
    
    /**
     * Ocurre cuando el servidor levanta el servicio correctamente
     * @param data   Evento que es generado
     */
    public void onServerStar(OnConnectedEventServer data);

    /**
     * Se produce cuando un cliente se conecta al servidor
     * @param sender Referencia del objeto que esta generando el evento
     * @param data   Evento que es generado
     * */
    public void onConnect(Object sender, OnConnectedEventServer data, String userName);

    /**
     * Se produce cuando un cliente se desconecta del servidor
     * @param sender Referencia del objeto que esta generando el evento
     * @param data   Evento que es generado
     * */
    public void onDisconnect(Object sender, OnConnectedEventServer data);	

    /**
     * Se produce cuando llegan mensajes del cliente
     * @param sender Referencia del objeto que genera el evento
     * @param data   Evento que es generado [ComunicationServer]
     * */
    public void onRead(Object sender, OnConnectedEventServer data, String userName);
    
    /**
     * Se produce cuando se envia mensaje desde el servidor a los clientes
     * @param sender Referencia al EventObject [managerConections]
     * */
    //public void onWrite(OnConnectedEventServer sender); // ya esta mejorado
}
