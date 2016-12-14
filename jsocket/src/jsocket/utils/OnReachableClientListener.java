/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsocket.utils;

import java.util.EventListener;

/**
 * Escuhador de eventos de los clientes que se desconecten
 * @author Alex Limbert Yalusqui
 */
public interface OnReachableClientListener extends EventListener{
    /**
     * Ocurre cuando se perdio la conexion con el cliente
     * @param key identificador del cliente que acaba de desconectarse
     */
    public void onUnAvailable(int key);

}
