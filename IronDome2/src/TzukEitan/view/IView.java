package TzukEitan.view;

import TzukEitan.listeners.WarEventUIListener;
import TzukEitan.war.WarStatistics;

public interface IView {

	void registerListeners(WarEventUIListener listener);
	
	void showDefenseLaunchMissile(String myMunitionsId, String missileId, String enemyMissileId);

	void showDefenseLaunchMissile(String myMunitionsId, String type, String missileId, String enemyLauncherId);
	
	void showEnemyLaunchMissile(String myMunitionsId, String missileId, String destination, int damage);
	
	void showLauncherIsVisible(String id,boolean visible);
	
	void showMissInterceptionMissile(String whoLaunchedMeId, String missileId, String enemyMissileId);
	
	void showHitInterceptionMissile(String whoLaunchedMeId, String interceptorId, String enemyMissileId);
	
	void showEnemyHitDestination(String whoLaunchedMeId, String missileId, String destination, int damage);
	
	void showMissInterceptionLauncher(String whoLaunchedMeId, String type, String missileId, String enemyLauncherId);
	
	void showMissInterceptionHiddenLauncher(String whoLaunchedMeId, String type, String enemyLauncherId);
	
	void showHitInterceptionLauncher(String whoLaunchedMeId, String type, String missileId, String enemyLauncherId);
	
	void showWarHasBeenFinished();
	
	void showWarHasBeenStarted();
	
	void showNoSuchObject(String type);
	
	void showMissileNotExist(String defenseLauncherId, String enemyId);
	
	void showLauncherNotExist(String defenseLauncherId, String launcherId);
	
	void showEnemyMissDestination(String whoLaunchedMeId, String id, String destination, String launchTime);
	
	void showEnemyAddedLauncher(String launcherId, boolean visible);
	
	void showDefenseAddedIronDome(String ironDomeId);
	
	void showDefenseAddedLD(String ldId, String type);
	
	void showStatistics(WarStatistics warStatistics);
}
