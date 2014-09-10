package TzukEitan.war;

import java.util.Vector;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.listeners.WarEventUIListener;
import TzukEitan.view.ConsoleView;
import TzukEitan.view.WarXMLReader;


public class WarControl implements WarEventListener, WarEventUIListener{
	private War warModel;
	private ConsoleView view;
	
	public WarControl(War warModel, ConsoleView view){
		this.warModel = warModel;
		this.view = view;
		
		warModel.registerListenerts(this);
		view.registerListeners(this);
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
	public void defenseHitInterceptionMissile(String whoLaunchedMeId, String missileId, String enemyMissileId) {
		view.showHitInterceptionMissile(whoLaunchedMeId, missileId, enemyMissileId);
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
		Vector<String> ids = warModel.getAllDuringFlyMissilesIds();
		return ids;
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
		Vector<String> ids = warModel.getAllVisibleLaunchersIds();
		return ids;
	}

	@Override
	public Vector<String> showAllLaunchers() {
		Vector<String> ids = warModel.getAllLaunchersIds();
		return ids;
	}

	@Override
	public void addEnemyMissile(String launcherId, String destination, int damage, int flyTime) {
		warModel.launchEnemyMissile(launcherId, destination, damage, flyTime);
	}

	@Override
	public String addEnemyLauncher(String launcherId, boolean isHidden) {
		String id = warModel.addEnemyLauncher(launcherId, isHidden);
		return id;
	}
	
	@Override
	public String addEnemyLauncher() {
		String id = warModel.addEnemyLauncher();
		return id;
	}

	@Override
	public String addIronDome() {
		String id = warModel.addIronDome();
		return id;
	}
	
	@Override
	public String addIronDome(String id) {
		String iId = warModel.addIronDome(id);
		return iId;
	}

	@Override
	public String addDefenseLauncherDestructor(String type) {
		String id = warModel.addDefenseLauncherDestructor(type);
		return id;
	}

	@Override
	public String[] getAllWarDestinations() {
		String[] warTargets = warModel.getAllTargetCities();
		return warTargets;
	}

	@Override
	public void warHasBeenFinished() {	
		try {
			view.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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
	public void enemyLauncherNotExist(String defenseLauncherId,
			String launcherId) {
		view.showLauncherNotExist(defenseLauncherId, launcherId);
	}

	@Override
	public void enemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {
		view.showEnemyMissDestination(whoLaunchedMeId, id, destination, launchTime);
	}


}
