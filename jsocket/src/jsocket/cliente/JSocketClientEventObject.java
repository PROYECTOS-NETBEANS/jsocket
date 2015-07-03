package jsocket.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.EventObject;

public class JSocketClientEventObject extends EventObject implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8307280632707356172L;

	private boolean LISTENING;
	private Socket skcRead;
	private Socket skcWrite;

	private DataOutputStream objWrite;
	private DataInputStream objRead;
	
	public JSocketClientEventObject(Object source, Socket skcWrite, Socket skcRead) {
		super(source);
		this.skcWrite = skcWrite;
		this.skcRead = skcRead;
		this.LISTENING = true;
	}
	public DataOutputStream getFlujoEscritura(){
		return objWrite;
	}
	public DataInputStream getFlujoLEctura(){
		return objRead;
	}
	public void iniciarEscuchador(){
		LISTENING = true;
		new Thread(this).start();
	}
	public void detenerEscuchador(){
		LISTENING = false;
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			obtenerFlujos();
			while(LISTENING){
				
				this.leerDatos();
				
			}
                        cerrarConexion();
		}catch(IOException e){
                    System.out.println("jsokectClienteEventObject.run" + e.getMessage());
                    cerrarConexion();
		} 
		
	}
	private void cerrarConexion(){
		try{
			objWrite.close();
			objRead.close();
			skcRead.close();
			skcWrite.close();
                        System.out.println("cerrando cliente");
			((JSocketClient) this.getSource()).getJSocketClientEventListener().onDisconnect(this);
		}catch(IOException e){
			((JSocketClient) this.getSource()).getJSocketClientEventListener().onError(e.getMessage());
		}
	}
	private void obtenerFlujos()throws IOException{
		objWrite = new DataOutputStream(skcWrite.getOutputStream());
		objRead = new DataInputStream(skcRead.getInputStream());
		objWrite.flush();
	}
	public void leerDatos(){
		try{
			((JSocketClient) this.getSource()).getJSocketClientEventListener().OnRead(this); 
		}catch(Exception e){
			
		}
	}
	public void escribirDatos(String msg){
		try{
			objWrite.writeUTF(msg);
                        objWrite.flush();
                        System.out.println("mensaje enviado");
		}catch(IOException e){
                    System.out.println("Error : escribirDatos. " +e.getMessage());
		}
	}
}
