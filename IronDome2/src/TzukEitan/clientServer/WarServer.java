package TzukEitan.clientServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import TzukEitan.clientServer.SocketData.ObjType;
import TzukEitan.listeners.WarEventUIListener;
import TzukEitan.utils.Utils;
import TzukEitan.view.IView;

public class WarServer extends Thread implements IView{

	private ServerSocket server;
	
	private List<WarEventUIListener> allListeners;
	private SocketData socket;
	
	public WarServer() {
		System.out.println("enter SERVER constractor");
		allListeners = new LinkedList<WarEventUIListener>();
	}

	@Override
	public void run() {
		try {
			server = new ServerSocket(7000);
			Socket newSocket = server.accept(); // blocking
			// connect
			socket = new SocketData(newSocket);
			SocketObject startObj = new SocketObject();
			startObj.setType(ObjType.connect);
			startObj.setMessage("Hello from server");
			socket.sendData(startObj);
			while (true){
				SocketObject obj = socket.readData();
			
				new Thread(new Runnable() {
					@Override
					public void run() {
						// manage connection
						if (obj.getType() == ObjType.newLauncher){
							fireAddLauncher();
						}else if(obj.getType() == ObjType.shootMissile){
							fireShootMissile(obj.getLauncherId(), obj.getDestination());
						}
						// dis-connect
//						obj.setType(ObjType.disConnect);
//						obj.setMessage("bye from server");
//						socket.sendData(obj);
//						socket.closeConnection();
					}
				}).start();
				if (obj != null && obj.getType() != ObjType.disConnect)
					break;
			} 
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public void registerListeners(WarEventUIListener listener) {
		allListeners.add(listener);
	}
	
	public void closeServer() {
		try {
			socket.closeConnection();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fireAddLauncher() {
		for (WarEventUIListener l : allListeners)
			l.addEnemyLauncher();
	}
	
	private void fireShootMissile(String launcherId, String destination) {
		int damage = (int) ((Math.random() * Utils.SECOND) + Utils.SECOND * 2);
		int flyTime = (int) ((Math.random() * Utils.FLY_TIME) + Utils.FLY_TIME);
		
		for (WarEventUIListener l : allListeners)
			l.addEnemyMissile(launcherId, destination, damage, flyTime);
	}
	
	@Override
	public void showEnemyAddedLauncher(String launcherId, boolean visible) {
		// TODO implement
//		obj.setMessage("launcher added");
//		socket.sendData(obj);
	}

	@Override
	public void showEnemyLaunchMissile(String myMunitionsId, String missileId,
			String destination, int damage) {
		// TODO implement
//		obj.setMessage("missile out");
//		socket.sendData(obj);
	}

	@Override
	public void showWarHasBeenFinished() {
		// TODO implement
		
	}

	@Override
	public void showDefenseLaunchMissile(String myMunitionsId, String missileId, String enemyMissileId) {
		/** Not needed */
	}

	@Override
	public void showDefenseLaunchMissile(String myMunitionsId, String type,
			String missileId, String enemyLauncherId) {
		/** Not needed */
	}

	@Override
	public void showLauncherIsVisible(String id, boolean visible) {
		/** Not needed */
	}

	@Override
	public void showMissInterceptionMissile(String whoLaunchedMeId,
			String missileId, String enemyMissileId) {
		/** Not needed */
	}

	@Override
	public void showHitInterceptionMissile(String whoLaunchedMeId,
			String interceptorId, String enemyMissileId) {
		/** Not needed */
	}

	@Override
	public void showEnemyHitDestination(String whoLaunchedMeId,
			String missileId, String destination, int damage) {
		/** Not needed */
	}

	@Override
	public void showMissInterceptionLauncher(String whoLaunchedMeId,
			String type, String missileId, String enemyLauncherId) {
		/** Not needed */
	}

	@Override
	public void showMissInterceptionHiddenLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId) {
		/** Not needed */
	}

	@Override
	public void showHitInterceptionLauncher(String whoLaunchedMeId,
			String type, String missileId, String enemyLauncherId) {
		/** Not needed */
	}

	@Override
	public void showWarHasBeenStarted() {
		/** Not needed */
	}

	@Override
	public void showNoSuchObject(String type) {
		/** Not needed */
	}

	@Override
	public void showMissileNotExist(String defenseLauncherId, String enemyId) {
		/** Not needed */
	}

	@Override
	public void showLauncherNotExist(String defenseLauncherId, String launcherId) {
		/** Not needed */
	}

	@Override
	public void showEnemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {
		/** Not needed */
	}

	@Override
	public void showDefenseAddedIronDome(String ironDomeId) {
		/** Not needed */
	}

	@Override
	public void showDefenseAddedLD(String ldId, String type) {
		/** Not needed */
	}

	@Override
	public void showStatistics(long[] array) {
		/** Not needed */
	}
}
