package TzukEitan.clientServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class SocketData {
	private Socket socket;
	private ObjectInputStream inputStream; 
	private ObjectOutputStream outputStream;
	private String clientAddress;
	
	public SocketData(Socket socket) {
		this.socket = socket;
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		clientAddress = socket.getInetAddress() + ":" + socket.getPort();
	}

	public Socket getSocket() {
		return socket;
	}
	
	public void sendData(String text) throws IOException{
		outputStream.writeObject(text);
	}
	
	public String readData() throws ClassNotFoundException, IOException{
		return (String) inputStream.readObject();
	}
	
	public void closeConnection() throws IOException{
		socket.close();
	}
}
