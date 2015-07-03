package jsocket.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSocketServerEventObject extends EventObject implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1218754636739260460L;
	
	private Socket skcRead = null;
	private Socket skcWrite = null;
	
        private DataInputStream objRead;
        private DataOutputStream objWrite;
	
	private boolean LISTENING = true;
	private Thread HiloProceso = null;
        
	public JSocketServerEventObject(Object source, Socket skcRead, Socket skcWrite) {
		super(source);
		this.skcRead = skcRead;
		this.skcWrite = skcWrite;
		this.LISTENING = true;
	}
	
	public String getDireccionIp(){
		return skcWrite.getRemoteSocketAddress().toString();
	}
	public DataInputStream getFlujoLectura(){
		return objRead;
	}
	public DataOutputStream getFlujoEscritura(){
		return objWrite;
	}
	public void iniciarEscuchador(){
		this.LISTENING = true;
		System.out.println("antes de start");
                if(HiloProceso == null){
                    HiloProceso = new Thread(this);
                    HiloProceso.start();
                }
		System.out.println("despues de start");
	}
	public void detenerEscuchador(){
		this.LISTENING = false;
	}
	public void cerrarConexion(){
		try{
			objRead.close();
			objWrite.close();
			
			if(!skcRead.isClosed())
				skcRead.close();
			
			if(!skcWrite.isClosed())
				skcWrite.close();
			((JSocketServer) this.getSource()).getJSocketEventListener().onClientDisconnect(this);
		}catch(IOException e){
			((JSocketServer) this.getSource()).getJSocketEventListener().onClientError("cerrarConexion:" + e.getMessage());
		}
	}	
	private void obtenerFlujo()throws IOException{ 
            objRead = new DataInputStream(skcRead.getInputStream());
            objWrite = new DataOutputStream(skcWrite.getOutputStream());
            objWrite.flush();            
	}
	@Override
	public void run() {
		try{
			System.out.println("antes de obtener flujo");
                        if(skcRead==null)
                            System.out.println("socket lectura nulo");
                        
			this.obtenerFlujo();
                        Thread.sleep(1000);
			System.out.println("despues de obtener flujo");
			while(LISTENING){			
                            if(skcRead.isClosed())
                                System.out.println("socket cerrado!!!");
                            
                            if(!skcRead.isConnected())
                                System.out.println("socket no conectado!!!");
                            
				this.LeerDatos();
				
			}
		}
		catch(IOException e){
                        System.out.println("error server IOEXcep : " + e.getMessage());
			((JSocketServer) this.getSource()).getJSocketEventListener().onClientError("run:" + e.getMessage());
                        this.cerrarConexion();}
		catch(InterruptedException ex){
                        System.out.println("error server Inter: " + ex.getMessage());
			((JSocketServer) this.getSource()).getJSocketEventListener().onClientError("run:" + ex.getMessage());
                }
	}
	private void EscribirDatos(){
		JSocketServer obj  = (JSocketServer) this.getSource();
		obj.getJSocketEventListener().onClientWrite(this, this.getDireccionIp());
	}
	private void LeerDatos(){                
                JSocketServer obj  = (JSocketServer) this.getSource();
                obj.getJSocketEventListener().onClientRead(this, skcRead);
	}
}