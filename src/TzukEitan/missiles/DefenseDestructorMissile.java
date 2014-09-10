package TzukEitan.missiles;

import java.util.LinkedList;
import java.util.List;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.launchers.EnemyLauncher;
import TzukEitan.utils.Utils;
import TzukEitan.war.WarStatistics;

/** Missile for Plane or Ship **/
public class DefenseDestructorMissile extends Thread {
	private List<WarEventListener> allListeners;

	private String id;
	private String whoLaunchedMeId;
	private String whoLaunchedMeType;
	private EnemyLauncher launcherToDestroy;
	private WarStatistics statistics;

	public DefenseDestructorMissile(String id, EnemyLauncher LauncherToDestroy,
			String whoLunchedMeId, String whoLaunchedMeType,
			WarStatistics statistics) {

		allListeners = new LinkedList<WarEventListener>();

		this.id = id;
		this.launcherToDestroy = LauncherToDestroy;
		this.whoLaunchedMeId = whoLunchedMeId;
		this.whoLaunchedMeType = whoLaunchedMeType;
		this.statistics = statistics;
	}

	public void run() {
		synchronized (launcherToDestroy) {
			if (launcherToDestroy.isAlive() && !launcherToDestroy.getIsHidden()
					&& Utils.randomDestractorSucces()) {
				// Check if the launcher is hidden or not
				launcherToDestroy.interrupt();
			}
		}// synchronized
	
		if(launcherToDestroy.isInterrupted()){
			fireHitEvent();
			
		}else {
			fireMissEvent();
		}
	}

	// Event
	private void fireHitEvent() {
		for (WarEventListener l : allListeners) {
			l.defenseHitInterceptionLauncher(whoLaunchedMeId,
					whoLaunchedMeType, id, launcherToDestroy.getLauncherId());
		}

		// update statistics
		statistics.increaseNumOfLauncherDestroyed();
	}

	// Event
	private void fireMissEvent() {
		for (WarEventListener l : allListeners) {
			l.defenseMissInterceptionLauncher(whoLaunchedMeId,
					whoLaunchedMeType, id, launcherToDestroy.getLauncherId());
		}
	}

	public void registerListeners(WarEventListener listener) {
		allListeners.add(listener);
	}

	public String getMissileId() {
		return id;
	}

}
