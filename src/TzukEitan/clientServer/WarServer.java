package TzukEitan.clientServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WarServer extends Thread{

	static SocketData socket;
	
	public WarServer() {
		System.out.println("SERVER constractor");
	}

	
	@Override
	public void run() {

		ServerSocket server;
		try {
			server = new ServerSocket(7000);
			socket = new SocketData(server.accept());
			test();
			socket.sendData("connected to server");
			try {
				System.out.println("SERVER: "+socket.readData());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			socket.closeConnection();
			server.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
	}

	public static void test(){
		System.out.println("enter test");
	}
}
