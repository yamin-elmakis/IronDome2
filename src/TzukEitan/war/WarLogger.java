package TzukEitan.war;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.utils.Utils;
import TzukEitan.utils.WarFormater;

public class WarLogger implements WarEventListener {
	private static Logger theLogger = Logger.getLogger("warLogger");
	private static Vector<FileHandler> allHandlers = new Vector<FileHandler>();
	private static FileHandler warHandler;
	
	// static cons't
	static {
		Utils.deleteFolder();
		theLogger.setUseParentHandlers(false);
	}
	
	//Handler for the war
	public static void addWarLoggerHandler(String fileName) {
		try {
			warHandler = new FileHandler("log\\" + fileName + "Logger.xml", false);

			warHandler.setFilter(new Filter() {
				public boolean isLoggable(LogRecord rec) {
						return true;
				}
			});// add filter

			warHandler.setFormatter(new WarFormater());

			theLogger.addHandler(warHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// addLoggerHandler
	
	public static void addLoggerHandler(String fileName, String id) {
		FileHandler handler;
		final String tempId = id;// for the filter anonymous class
		
		try {
			handler = new FileHandler("log\\" + fileName + id + "Logger.xml", false);

			handler.setFilter(new Filter() {
				public boolean isLoggable(LogRecord rec) {
					if (rec.getMessage().contains(tempId))
						return true;
					return false;
				}
			});// add filter

			handler.setFormatter(new WarFormater());

			theLogger.addHandler(handler);

			allHandlers.add(handler);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}// addLoggerHandler
	
	
	public static void closeWarHandler(){
		warHandler.close();
	}
	
	public static void closeAllHandlers(){
		for(FileHandler fh : allHandlers)
			fh.close();
	}
	
	public static void closeMyHandler(String id){
		LogRecord rec = new LogRecord(Level.INFO, id);
		for(FileHandler fh : allHandlers)
			if(fh.getFilter().isLoggable(rec)){
				fh.close();
				//allHandlers.remove(fh);
			}
	}

	@Override
	public void defenseLaunchMissile(String myMunitionsId, String missileId,
			String enemyMissileId) {
		theLogger.log(Level.INFO, myMunitionsId + "\tLaunch: " + missileId
				+ "\t\t\tTarget: " + enemyMissileId + "\n");
	}

	@Override
	public void defenseLaunchMissile(String myMunitionsId, String type,
			String missileId, String enemyLauncherId) {
		theLogger.log(Level.INFO, myMunitionsId + "\tLaunch: " + missileId
				+ "\t\t\tTarget: " + enemyLauncherId + "\n");
	}

	@Override
	public void defenseHitInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId) {
		theLogger.log(Level.INFO, whoLaunchedMeId + "\tIntercept: " + enemyMissileId
				+ "\t\t\tStatus: SUCCESS\n");
	}

	@Override
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId) {
		theLogger.log(Level.INFO, whoLaunchedMeId + "\tIntercept: " + enemyLauncherId
				+ "\t\t\tStatus: SUCCESS\n");
		theLogger.log(Level.INFO, enemyLauncherId + "\tStatus: DESTROYED\n");
	}

	@Override
	public void defenseMissInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId, int damage) {
		theLogger.log(Level.INFO, whoLaunchedMeId + "\tIntercept: " + enemyMissileId
				+ "\t\t\tStatus: FAIL \t\t\tDamage: " + damage + "\n");
		
//		theLogger.log(Level.INFO, whoLaunchedMeId + "\t" + enemyMissileId
//				+ "\tFail: " + damage + "\n");
	}

	@Override
	public void defenseMissInterceptionHiddenLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId) {
		theLogger.log(Level.INFO, whoLaunchedMeId + "\tIntercept: " + enemyLauncherId
				+ "\t\t\tStatus: FAIL\t\t\tReason: Launcher is hidden\n");
	}

	@Override
	public void defenseMissInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId) {
		theLogger.log(Level.INFO, whoLaunchedMeId + "\tIntercept: " + enemyLauncherId
				+ "\t\t\tStatus: FAIL\n");
//		theLogger.log(Level.INFO, whoLaunchedMeId + "\t" + enemyLauncherId
//				+ "\tFail" + "\n");
	}

	@Override
	public void enemyLaunchMissile(String myMunitionsId, String missileId,
			String destination, int damage) {
		theLogger.log(Level.INFO, myMunitionsId + "\tLaunch: " + missileId
				+ "\t\t\tTarget: " + destination + "\n");
	}

	@Override
	public void enemyLauncherIsVisible(String id, boolean visible) {
		String str = visible ? "visible\n" : "hidden\n";
		theLogger.log(Level.INFO, id + "\tis now: " + str);
	}

	@Override
	public void enemyHitDestination(String whoLaunchedMeId, String id,
			String destination, int damage, String launchTime) {
		theLogger.log(Level.INFO, whoLaunchedMeId + "\tTarget: " + destination + "\t\tStatus: HIT" + 
			 "\t\t\tLand Time: " + Utils.getCurrentTime() + "\t\t\tDamage: " + damage + "\n");
	}

	@Override
	public void enemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {
		theLogger.log(Level.INFO, whoLaunchedMeId + "\tTarget: " + destination + "\t\tStatus: MISS" + 
				 "\t\t\tLand Time: " + Utils.getCurrentTime() + "\n");
	}

	@Override
	public void warHasBeenStarted() {
		theLogger.log(Level.INFO, "====== >\tWar started\t< ======\n");
	
	}

	@Override
	public void warHasBeenFinished() {
		theLogger.log(Level.INFO, "====== >\tWar finished\t< ====== \n");
		
	}

	@Override
	public void missileNotExist(String defenseLauncherId, String enemyId) {
		theLogger.log(Level.INFO, defenseLauncherId + "\tFAIL: missile " 
					+ enemyId + " does not exist" + "\n");
	}

	@Override
	public void enemyLauncherNotExist(String defenseLauncherId,
			String launcherId) {
		theLogger.log(Level.INFO, defenseLauncherId + "\tFAIL: launcher " 
				+ launcherId + " does not exist" + "\n");
	}

	@Override
	public void noSuchObject(String type) {
		// Not needed
	}

}
