package TzukEitan.launchers;

import java.util.LinkedList;
import java.util.List;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.missiles.DefenseMissile;
import TzukEitan.missiles.EnemyMissile;
import TzukEitan.utils.IdGenerator;
import TzukEitan.utils.Utils;
import TzukEitan.war.WarLogger;
import TzukEitan.war.WarStatistics;

public class IronDome extends Thread implements Munitions {
	private List<WarEventListener> allListeners;

	private String id;
	private boolean isRunning = true;
	private boolean isBusy = false;
	private EnemyMissile toDestroy;
	private WarStatistics statistics;
	private DefenseMissile currentMissile;

	public IronDome(String id, WarStatistics statistics) {
		allListeners = new LinkedList<WarEventListener>();

		this.statistics = statistics;
		this.id = id;

		WarLogger.addLoggerHandler("IronDome", id);
	}

	public void run() {
		// this thread will be alive until the war is over
		while (isRunning) {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					stopRunning();
					break;
				}
			}
			
			// update that this iron-dome is in use
			isBusy = true;
			try {
				launchMissile();
				
				// update that this iron dome is not in use
				isBusy = false;
			} catch (InterruptedException e) {
				stopRunning();
				break;
			}

			// update that is no missile in this iron-dome
			currentMissile = null;

		}// while

		// close the handler of the logger
		WarLogger.closeMyHandler(id);
		//ironDomeHandler.close();
	}// run

	// set the next target of this iron dome, called from the war
	public void setMissileToDestroy(EnemyMissile toDestroy) {
		this.toDestroy = toDestroy;
	}

	// Launch missile with given parameters
	public void launchMissile() throws InterruptedException {	
		createMissile();
		
		// sleep for launch time
		sleep(Utils.LAUNCH_DURATION);
		
		// check if the target is still alive
		if (toDestroy != null && toDestroy.isAlive()) {
			// throw event
			fireLaunchMissileEvent(currentMissile.getMissileId());
	
			// Start missile and wait until he will finish to be able
			// to shoot anther one
			currentMissile.start();
			currentMissile.join();
		}
		else{
			fireMissileNotExist(toDestroy.getMissileId());
		}
	}

	private void fireMissileNotExist(String missileId) {
		for (WarEventListener l : allListeners)
			l.missileNotExist(getIronDomeId(), missileId);;
	}

	public void createMissile() {
		// generate missile id
		String missieId = IdGenerator.defensMissileIdGenerator();

		// create new missile
		currentMissile = new DefenseMissile(missieId, toDestroy, id, statistics);

		// register listeners
		for (WarEventListener l : allListeners)
			currentMissile.registerListeners(l);
	}

	public DefenseMissile getCurrentMissile() {
		return currentMissile;
	}

	// Event
	private void fireLaunchMissileEvent(String missileId) {
		for (WarEventListener l : allListeners) {
			l.defenseLaunchMissile(id, missileId, toDestroy.getMissileId());
		}
	}

	public void registerListeners(WarEventListener listener) {
		allListeners.add(listener);
	}

	// check if can shoot from this current iron dome
	public boolean getIsBusy() {
		return isBusy;
	}

	public String getIronDomeId() {
		return id;
	}

	// use for end the thread
	@Override
	public void stopRunning() {
		currentMissile = null;
		toDestroy = null;
		isRunning = false;
	}
	
}
