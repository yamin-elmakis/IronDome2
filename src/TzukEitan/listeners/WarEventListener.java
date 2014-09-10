package TzukEitan.listeners;

/** Event interface between the model and the control **/
public interface WarEventListener {

	/** Enemy Launch missile **/
	public void enemyLaunchMissile(String myMunitionsId, String missileId,
			String destination, int damage);

	/** Enemy is now visible **/
	public void enemyLauncherIsVisible(String id, boolean visible);

	/** Enemy event for hit destination **/
	public void enemyHitDestination(String whoLaunchedMeId, String id,
			String destination, int damage, String launchTime);

	/** Enemy event for miss destination **/
	public void enemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime);

	/** Defense Iron Dome launch interception missile **/
	public void defenseLaunchMissile(String myMunitionsId, String missileId,
			String enemyMissileId);

	/** Defense Airplane or ship launch interception launcher **/
	public void defenseLaunchMissile(String myMunitionsId, String type,
			String missileId, String enemyLauncherId);

	/** Defense event for hit interception (to missile) **/
	public void defenseHitInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId);

	/** Defense event for miss interception (to missile) **/
	public void defenseMissInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId, int damage);

	/** Defense event for hit interception (to Launcher) **/
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId);

	/** Defense event for miss interception (to Launcher) **/
	public void defenseMissInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId);

	/** Defense event. try to intercept launcher but he was hidden **/
	void defenseMissInterceptionHiddenLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId);

	/** Announce when the war endss **/
	public void warHasBeenFinished();

	/** Announce when the war starts **/
	public void warHasBeenStarted();

	/** Will occur when ship/plane/iron dome is not found in war **/
	public void noSuchObject(String type);

	/** Will occur when the target that selected isn't exist **/
	public void missileNotExist(String defenseLauncherId, String enemyId);

	/** Will occur when the target that selected isn't exist **/
	public void enemyLauncherNotExist(String defenseLauncherId, String launcherId);

}
