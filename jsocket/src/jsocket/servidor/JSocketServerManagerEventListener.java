package jsocket.servidor;

import java.io.IOException;

public class JSocketServerManagerEventListener implements JSocketServerEventListener {

	public JSocketServerManagerEventListener(){
		System.out.println("Administrador creado");
	}
		
	@Override
	public void onAccept(JSocketServerEventObject sender) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("manager:Acaba de conectarse un usuario!!! " + sender.getDireccionIp());
	}
	@Override
	public void onClientConnect(JSocketServerEventObject sender) {
		// TODO Auto-generated method stub
		System.out.println("manager: Cliente conectado y listo!!! " + sender.getDireccionIp());
	}
	@Override
	public void onClientDisconnect(JSocketServerEventObject sender) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClientError(String msg) {
		// TODO Auto-generated method stub
		System.out.println("Error : " + msg);
	}
	@Override
	public void onClientRead(JSocketServerEventObject sender, String ip) {
		// TODO Auto-generated method stub
		try {
			String str = sender.getFlujoLectura().readUTF();
			System.out.println("enviado de [" + ip + "] " + str);
		} catch (IOException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error de socket al leer!!!");
		}		
	}
	@Override
	public void onClientWrite(JSocketServerEventObject sender, String ip) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void onServerStar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
