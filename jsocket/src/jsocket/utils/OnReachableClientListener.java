/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsocket.utils;

import java.util.EventListener;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public interface OnReachableClientListener extends EventListener{
    /**
     * Ocurre cuando se perdio la conexion con el servidor.
     * @param key identificador del cliente que se 
     */
    public void onUnAvailable(int key);

}
