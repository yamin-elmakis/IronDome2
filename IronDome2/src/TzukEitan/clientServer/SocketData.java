package TzukEitan.clientServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketData {
	private Socket socket;
	private ObjectInputStream inputStream; 
	private ObjectOutputStream outputStream;
	
	public static enum ObjType {
		connect (0), disConnect(1), newLauncher(2), shootMissile(3), launcherDestroied(4);
		private int value;
		
		private ObjType (int value){
			this.value = value;
		}
	}
	
	public SocketData(Socket socket) {
		this.socket = socket;
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}
	
	public void sendData(SocketObject obj) {
		try {
			outputStream.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SocketObject readData() {
		try {
			return (SocketObject) inputStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ObjectInputStream getInputStream() {
		return inputStream;
	}

	public void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
