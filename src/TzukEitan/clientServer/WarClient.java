package TzukEitan.clientServer;

import java.io.IOException;
import java.net.Socket;
//import javafx.scene.layout.HBox;

public class WarClient extends Thread {

	static SocketData socket;

	public WarClient() {
		System.out.println("CLIENT constractor ");
	}

	
	@Override
	public void run() {
		try {
			socket = new SocketData(new Socket("localhost", 7000));
			System.out.println("CLIENT: "+socket.readData());
			socket.sendData("got it");
			socket.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
