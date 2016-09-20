package jsocket.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class JSocketServer extends Thread{
	
	private boolean LISTENING;
	private int PUERTO_LECTURA;
	private int PUERTO_ESCRITURA;
	
	private Socket skcLectura = null; 
	private Socket skcEscritura = null;
	
	private ServerSocket sksLectura = null; 
	private ServerSocket sksEscritura = null;
	
	private JSocketServerEventListener listener;
	
	public JSocketServer(int portRead, int portWrite){
		PUERTO_LECTURA = portRead;
		PUERTO_ESCRITURA = portWrite;
		LISTENING = true;
		listener = null;
	}
	public void addJSocketEventListener(JSocketServerEventListener listener){
		this.listener = listener;
	}
	protected JSocketServerEventListener getJSocketEventListener(){
		return listener;
	}
	@Override
	public void run(){
		try{
			
			sksLectura = new ServerSocket(PUERTO_LECTURA);
			sksEscritura = new ServerSocket(PUERTO_ESCRITURA);
			listener.onServerStar();
			while(LISTENING){
				skcLectura = null; 
				skcEscritura = null;
				
				// esperando usuarios
				this.onAccept();
				
			}			
		}catch(IOException e){
			listener.onClientError(e.getMessage());
		}finally{
			cerrarConexion();
		}
	}
	private void cerrarConexion(){
		try{
			if(!sksEscritura.isClosed())
				sksEscritura.close();
			
			if(!sksLectura.isClosed())
				sksLectura.close();
			
			if(!skcEscritura.isClosed())
				skcEscritura.close();
			
			if(!skcLectura.isClosed())
				skcLectura.close();
			System.out.println("cerrando conexion de sokect");
		}catch(IOException e){
			
		}
	}
	public void onAccept() throws IOException {
		skcLectura = sksLectura.accept();
		skcEscritura = sksEscritura.accept();
		JSocketServerEventObject jsocketObject = new JSocketServerEventObject(this, skcLectura, skcEscritura );
		jsocketObject.iniciarEscuchador();
		listener.onAccept(jsocketObject);
	}
}
