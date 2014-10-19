package TzukEitan.war;

import java.util.Vector;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.listeners.WarEventUIListener;
import TzukEitan.view.ConsoleView;
import TzukEitan.view.IView;
import TzukEitan.view.WarXMLReader;


public class WarControl implements WarEventListener, WarEventUIListener{
	private War warModel;
	private IView view;
	
	public WarControl(War warModel, IView view){
		this.warModel = warModel;
		this.view = view;
		
		warModel.registerListenerts(this);
		view.registerListeners(this);
		
		if (view instanceof ConsoleView)
			((ConsoleView) view).start();
	}
	
	
	//Method that related to the view
	@Override
	public void defenseLaunchMissile(String myMunitionsId, String missileId, String enemyMissileId) {
		view.showDefenseLaunchMissile(myMunitionsId,missileId,enemyMissileId);
	}

	@Override
	public void defenseLaunchMissile(String myMunitionsId, String type,	String missileId, String enemyLauncherId) {
		view.showDefenseLaunchMissile(myMunitionsId, type, missileId, enemyLauncherId);
	}

	@Override
	public void enemyLaunchMissile(String myMunitionsId, String missileId, String destination, int damage) {
		view.showEnemyLaunchMissile(myMunitionsId, missileId, destination, damage);	
	}

	@Override
	public void enemyLauncherIsVisible(String id,boolean visible) {
		view.showLauncherIsVisible(id,visible);
	}

	@Override
	public void defenseMissInterceptionMissile(String whoLaunchedMeId, String missileId, String enemyMissileId, int damage) {
		view.showMissInterceptionMissile(whoLaunchedMeId, missileId, enemyMissileId);
	}

	@Override
	public void defenseHitInterceptionMissile(String whoLaunchedMeId, String interceptorId, String enemyMissileId) {
		view.showHitInterceptionMissile(whoLaunchedMeId, interceptorId, enemyMissileId);
	}

	@Override
	public void enemyHitDestination(String whoLaunchedMeId, String missileId, String destination, int damage, String launchTime) {
		view.showEnemyHitDestination(whoLaunchedMeId, missileId, destination, damage);
	}

	@Override
	public void defenseMissInterceptionLauncher(String whoLaunchedMeId,	String type, String missileId, String enemyLauncherId) {
		view.showMissInterceptionLauncher(whoLaunchedMeId,type, enemyLauncherId, missileId);
	}
	
	@Override
	public void defenseMissInterceptionHiddenLauncher(String whoLaunchedMeId, String type, String enemyLauncherId) {
		view.showMissInterceptionHiddenLauncher(whoLaunchedMeId,type, enemyLauncherId);
	}
	
	@Override
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId, String type, String missileId, String enemyLauncherId) {
		view.showHitInterceptionLauncher(whoLaunchedMeId, type, enemyLauncherId, missileId);
	}
	

	@Override
	public void warHasBeenFinished() {
		//TODO check this
//		try {
//			view.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		view.showWarHasBeenFinished();
	}

	@Override
	public void warHasBeenStarted() {
		view.showWarHasBeenStarted();
	}

	@Override
	public void noSuchObject(String type) {
		view.showNoSuchObject(type);
	}

	@Override
	public void missileNotExist(String defenseLauncherId, String enemyId) {
		view.showMissileNotExist(defenseLauncherId, enemyId);
	}
	
	@Override
	public void enemyLauncherNotExist(String defenseLauncherId, String launcherId) {
		view.showLauncherNotExist(defenseLauncherId, launcherId);
	}

	@Override
	public void enemyMissDestination(String whoLaunchedMeId, String id, String destination, String launchTime) {
		view.showEnemyMissDestination(whoLaunchedMeId, id, destination, launchTime);
	}

	@Override
	public void enemyAddedLauncher(String launcherId, boolean visible) {
		view.showEnemyAddedLauncher(launcherId, visible);
	}

	@Override
	public void defenseIronDomeAdded(String ironDomeId) {
		view.showDefenseAddedIronDome(ironDomeId);
	}

	@Override
	public void defenseLauncherDestructorAdded(String ldId, String type) {
		view.showDefenseAddedLD(ldId, type);
	}

	//Methods related to the model
	@Override
	public void finishWar() {
		WarXMLReader.stopAllThreads();
		//warModel.finishWar();
		
		//notify the war
		synchronized (warModel) {
			warModel.notify();
		}
	}

	@Override
	public void showStatistics() {
		WarStatistics statistics = warModel.getStatistics();
		view.showStatistics(statistics.statisticsToArray());	
	}

	@Override
	public Vector<String> chooseMissileToIntercept() {
		return warModel.getAllDuringFlyMissilesIds();
	}

	@Override
	public void interceptGivenMissile(String ironDomeId, String missileId) {
		warModel.interceptGivenMissile(ironDomeId, missileId);
	}

	@Override
	public void interceptGivenMissile(String missileId) {
		warModel.interceptGivenMissile(missileId);
	}
	
	@Override
	public void interceptGivenLauncher(String launcherId) {
		warModel.interceptGivenLauncher(launcherId);
	}

	@Override
	public void interceptGivenLauncher(String destructorId, String launcherId) {
		warModel.interceptGivenLauncher(destructorId,launcherId);
	}
	
	@Override
	public Vector<String> chooseLauncherToIntercept() {
		return warModel.getAllVisibleLaunchersIds();
	}

	@Override
	public Vector<String> showAllLaunchers() {
		return warModel.getAllLaunchersIds();
	}

	@Override
	public void addEnemyMissile(String launcherId, String destination, int damage, int flyTime) {
		warModel.launchEnemyMissile(launcherId, destination, damage, flyTime);
	}

	@Override
	public String addEnemyLauncher(String launcherId, boolean isHidden) {
		return warModel.addEnemyLauncher(launcherId, isHidden);
	}
	
	@Override
	public String addEnemyLauncher() {
		return warModel.addEnemyLauncher();
	}

	@Override
	public String addIronDome() {
		return warModel.addIronDome();
	}
	
	@Override
	public String addIronDome(String id) {
		return warModel.addIronDome(id);
	}

	@Override
	public String addDefenseLauncherDestructor(String type) {
		return warModel.addDefenseLauncherDestructor(type);
	}

	@Override
	public String[] getAllWarDestinations() {
		return warModel.getAllTargetCities();
	}

}
