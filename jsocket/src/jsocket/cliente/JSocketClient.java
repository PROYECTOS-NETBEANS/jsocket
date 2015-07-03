package jsocket.cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class JSocketClient {
	
	private String IP_SERVER;
	
	private Socket skcLectura;
	private Socket skcEscritura;
	
	private int PUERTO_LECTURA;
	private int PUERTO_ESCRITURA;

	private JSocketClientEventListener listener;
	
	private JSocketClientEventObject objEscuchador;
	
	public JSocketClient(int portRead, int portWrite, String ipServer){
		this.PUERTO_ESCRITURA = portWrite;
		this.PUERTO_LECTURA = portRead;
		this.IP_SERVER = ipServer;
		this.listener = null;
	}
	public void addJSocketClientEventListener(JSocketClientEventListener listener){
		this.listener = listener;
	}
	protected JSocketClientEventListener getJSocketClientEventListener(){
		return this.listener;
	}
	
	public void conectarServidor(){
		try{
			this.abrirConexion();
			this.iniciarServicio();
		}catch(IOException e){
				listener.onError(e.getMessage());
		}
	}
	private void cerrarConexion(){
		listener.onDisconnect(objEscuchador);
	}
	private void abrirConexion() throws IOException{
		skcLectura = new Socket(IP_SERVER, PUERTO_ESCRITURA);
		skcEscritura = new Socket(IP_SERVER, PUERTO_LECTURA);
		listener.onConnecting(objEscuchador);
	}
	private void iniciarServicio(){
		objEscuchador = new JSocketClientEventObject(this, skcEscritura, skcLectura);
		objEscuchador.iniciarEscuchador();
	}
	public void desconectarServidor(){
		objEscuchador.detenerEscuchador();
	}
	public void enviarMensajeAServidor(){
		listener.onWrite(objEscuchador);
	}
}
