package TzukEitan.missiles;

import java.util.LinkedList;
import java.util.List;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.utils.Utils;
import TzukEitan.war.WarStatistics;

/** Missile for iron dome **/
public class DefenseMissile extends Thread {
	private List<WarEventListener> allListeners;

	private String id;
	private String whoLunchedMeId;
	private EnemyMissile missileToDestroy;
	private WarStatistics statistics;

	public DefenseMissile(String id, EnemyMissile missileToDestroy,
			String whoLunchedMeId, WarStatistics statistics) {
		allListeners = new LinkedList<WarEventListener>();

		this.id = id;
		this.missileToDestroy = missileToDestroy;
		this.whoLunchedMeId = whoLunchedMeId;
		this.statistics = statistics;
	}

	public void run() {
		synchronized (missileToDestroy) {
			// Check if the missile is still in the air before trying to destroy
			if (missileToDestroy.isAlive() && Utils.randomSuccesRate()) {
				missileToDestroy.interrupt();
			}
		}//synchronized
				
		if (missileToDestroy.isInterrupted()){
			fireHitEvent();
		}
		else{
			fireMissEvent();
		}
	}// run

	// Event
	private void fireHitEvent() {
		for (WarEventListener l : allListeners) {
			l.defenseHitInterceptionMissile(whoLunchedMeId, id,
					missileToDestroy.getMissileId());
		}

		// update statistics
		statistics.increaseNumOfInterceptMissiles();
	}

	// Event
	private void fireMissEvent() {
		for (WarEventListener l : allListeners) {
			l.defenseMissInterceptionMissile(whoLunchedMeId, id,
					missileToDestroy.getMissileId(),
					missileToDestroy.getDamage());
		}
	}

	public void registerListeners(WarEventListener listener) {
		allListeners.add(listener);
	}

	public String getMissileId() {
		return id;
	}

}
