package jsocket.cliente;

import java.io.IOException;

public class JSocketClientManagerEventListener implements JSocketClientEventListener{

	@Override
	public void onConnect(JSocketClientEventObject sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnecting(JSocketClientEventObject sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnect(JSocketClientEventObject sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		System.out.println("Error : " + msg); 
	}

	@Override
	public void onLookup(JSocketClientEventObject sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnRead(JSocketClientEventObject sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWrite(JSocketClientEventObject sender) {
		// TODO Auto-generated method stub
		
		try {
			sender.getFlujoEscritura().writeUTF("soy cliente 1");
			sender.getFlujoEscritura().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
