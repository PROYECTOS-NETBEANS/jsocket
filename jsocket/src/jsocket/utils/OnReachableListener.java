
package jsocket.utils;

import java.util.EventListener;

/**
 * Escuchador de eventos de desconecion al servidor.
 * @author Alex Limbert Yalusqui
 */
public interface OnReachableListener extends EventListener{
    /**
     * Ocurre cuando se perdio la conexion con el servidor.
     */
    public void onUnAvailable();
    
    /**
     * Ocurre cuando se agotaron los intentos de reconexion al servidor
     */
    public void onLostConnection();
    
}
