package jsocket.servidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EventListener;

public interface JSocketServerEventListener extends EventListener{

    	/**
	 * Ocurre cuando el servidor levanta el servicio correctamente
	 * @param sender Referencia del objeto que esta generando el evento
	 * */
        void onServerStar();
	/**
	 * Ocurre en sockets de servidor justo despu�s de que se acepte la conexi�n a un socket de cliente.
	 * @param sender Referencia del objeto que esta generando el evento
	 * */
	void onAccept(JSocketServerEventObject sender) throws IOException ;


	/**
	 * Se produce cuando un socket de cliente completa una conexi�n aceptada por el socket del servidor
	 * @param sender Referencia del objeto que esta generando el evento
	 * */
	void onClientConnect(JSocketServerEventObject sender);
	
	/**
	 * Se produce cuando una de las conexiones a un socket de cliente est� cerrado.
	 * @param sender Referencia del objeto que esta generando el evento
	 * */
	void onClientDisconnect(JSocketServerEventObject sender);	
	
	/**
	 * Se produce cuando se produce un fallo en el establecimiento, utilizando, 
	 * o terminar la conexi�n de socket a un socket de cliente individual. 
	 * @param msg mensaje de error
	 * */
	void onClientError(String msg);
	
	/**
	 * Se produce cuando el socket del servidor debe leer la informaci�n desde un socket de cliente.
	 * @param sender Referencia del objeto que esta generando el evento
	 * @param scRead Socket que tiene la conexion del del servidor hacia el cliente
	 * */
	void onClientRead(JSocketServerEventObject sender, Socket scRead);
	
	/**
	 * Se produce cuando el socket del servidor debe escribir informaci�n en un socket de cliente.
	 * @param sender Referencia del objeto que esta generando el evento
	 * @param ip Direccion a donde se enviara el mensaje
	 * */
	void onClientWrite(JSocketServerEventObject sender, String ip);

}