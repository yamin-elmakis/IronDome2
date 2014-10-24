package TzukEitan.clientServer;

import java.io.Serializable;

import TzukEitan.clientServer.SocketData.ObjType;

public class SocketObject implements Serializable {

	private ObjType type;
	private String message;
	private String [] names;

	public SocketObject() {	}

	public SocketObject(ObjType type, String message) {
		this.type = type;
		this.message = message;
	}

	public ObjType getType() {
		return type;
	}

	public void setType(ObjType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}
	
	
	
}
