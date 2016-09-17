package jsocket.cliente;

import java.util.EventListener;

public interface JSocketClientEventListener extends EventListener{
	// ejercicio de pueba 
	/**
	 * Se produce en sockets de cliente justo después de que se abre la conexión con el servidor.
	 * @param sender Referencia del objeto que esta generando el evento
	 * */
	void onConnect(JSocketClientEventObject sender);
	
	/**
	 *  Se produce para un socket de cliente después de que el socket del servidor 
	 *  ha sido localizado, pero antes de que se establezca la conexión. 
	 * @param sender Referencia del objeto que esta generando el evento
	 * */
	void onConnecting(JSocketClientEventObject sender);
	
	/**
	 * Se produce inmediatamente antes de una toma de cliente cierra la conexión a un socket de servidor.   
	 * @param sender Referencia del objeto que esta generando el evento
	 * */	
	void onDisconnect(JSocketClientEventObject sender);
	
	/**
	 *  Se produce cuando la toma de falla en la fabricación, el uso, o el cierre de una conexión.    
	 * @param sender Referencia del objeto que esta generando el evento
	 * */		
	void onError(String msg);

	/**
	 * Se produce cuando un socket de cliente está a punto de buscar el socket del servidor con el que se quiere conectar.     
	 * @param sender Referencia del objeto que esta generando el evento
	 * */		
	void onLookup(JSocketClientEventObject sender);
	
	/**
	 * Se produce cuando un socket cliente debe leer la información de la conexión de socket.     
	 * @param sender Referencia del objeto que esta generando el evento
	 * */		
	void OnRead(JSocketClientEventObject sender);
	
	/**
	 * Se produce cuando un socket cliente debe escribir información en la conexión de socket.      
	 * @param sender Referencia del objeto que esta generando el evento
	 * */			
	void onWrite(JSocketClientEventObject sender);
}
