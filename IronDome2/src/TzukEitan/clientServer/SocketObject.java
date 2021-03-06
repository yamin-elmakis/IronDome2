package TzukEitan.clientServer;

import java.io.Serializable;

import TzukEitan.clientServer.SocketData.ObjType;

public class SocketObject implements Serializable {

	private ObjType type;
	private String message, launcherId, destination;
	private String [] cityNames;

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

	public String[] getCityNames() {
		return cityNames;
	}

	public void setCityNames(String[] cityNames) {
		this.cityNames = cityNames;
	}

	public String getLauncherId() {
		return launcherId;
	}

	public void setLauncherId(String launcherId) {
		this.launcherId = launcherId;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
}
