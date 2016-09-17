package jsocket.cliente;

import java.util.EventListener;

public interface JSocketClientEventListener extends EventListener{
	// ejercicio de pueba 
	/**
	 * Se produce en sockets de cliente justo despu�s de que se abre la conexi�n con el servidor.
	 * @param sender Referencia del objeto que esta generando el evento
	 * */
	void onConnect(JSocketClientEventObject sender);
	
	/**
	 *  Se produce para un socket de cliente despu�s de que el socket del servidor 
	 *  ha sido localizado, pero antes de que se establezca la conexi�n. 
	 * @param sender Referencia del objeto que esta generando el evento
	 * */
	void onConnecting(JSocketClientEventObject sender);
	
	/**
	 * Se produce inmediatamente antes de una toma de cliente cierra la conexi�n a un socket de servidor.   
	 * @param sender Referencia del objeto que esta generando el evento
	 * */	
	void onDisconnect(JSocketClientEventObject sender);
	
	/**
	 *  Se produce cuando la toma de falla en la fabricaci�n, el uso, o el cierre de una conexi�n.    
	 * @param sender Referencia del objeto que esta generando el evento
	 * */		
	void onError(String msg);

	/**
	 * Se produce cuando un socket de cliente est� a punto de buscar el socket del servidor con el que se quiere conectar.     
	 * @param sender Referencia del objeto que esta generando el evento
	 * */		
	void onLookup(JSocketClientEventObject sender);
	
	/**
	 * Se produce cuando un socket cliente debe leer la informaci�n de la conexi�n de socket.     
	 * @param sender Referencia del objeto que esta generando el evento
	 * */		
	void OnRead(JSocketClientEventObject sender);
	
	/**
	 * Se produce cuando un socket cliente debe escribir informaci�n en la conexi�n de socket.      
	 * @param sender Referencia del objeto que esta generando el evento
	 * */			
	void onWrite(JSocketClientEventObject sender);
}
