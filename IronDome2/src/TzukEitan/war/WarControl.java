package TzukEitan.war;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import TzukEitan.listeners.IGetWarStatistics;
import TzukEitan.listeners.WarEventListener;
import TzukEitan.listeners.WarEventUIListener;
import TzukEitan.view.ConsoleView;
import TzukEitan.view.IView;
import TzukEitan.view.WarXMLReader;


public class WarControl implements WarEventListener, WarEventUIListener{
	private War warModel;
	private List<IView> allViewers;
	private IGetWarStatistics warStatistics;
	
	public WarControl(War warModel){
		this.warModel = warModel;
		
		warModel.registerListenerts(this);
		allViewers = new LinkedList<IView>();
	}
	
	public void registerListeners(IView viewer) {
		allViewers.add(viewer);
		viewer.registerListeners(this);
		if (viewer instanceof ConsoleView)
			new Thread() {
		    public void run() {
		    	((ConsoleView) viewer).run();
		    }
		}.start();
	}
	
	public void setWarStatistics(IGetWarStatistics warStatistics) {
		this.warStatistics = warStatistics;
	}

	//Method that related to the view
	@Override
	public void defenseLaunchMissile(String myMunitionsId, String missileId, String enemyMissileId) {
		for (IView view : allViewers)
			view.showDefenseLaunchMissile(myMunitionsId,missileId,enemyMissileId);
	}

	@Override
	public void defenseLaunchMissile(String myMunitionsId, String type,	String missileId, String enemyLauncherId) {
		for (IView view : allViewers)	
			view.showDefenseLaunchMissile(myMunitionsId, type, missileId, enemyLauncherId);
	}

	@Override
	public void enemyLaunchMissile(String myMunitionsId, String missileId, String destination, int damage) {
		for (IView view : allViewers)
			view.showEnemyLaunchMissile(myMunitionsId, missileId, destination, damage);	
	}

	@Override
	public void enemyLauncherIsVisible(String id,boolean visible) {
		for (IView view : allViewers)
			view.showLauncherIsVisible(id,visible);
	}

	@Override
	public void defenseMissInterceptionMissile(String whoLaunchedMeId, String missileId, String enemyMissileId, int damage) {
		for (IView view : allViewers)
			view.showMissInterceptionMissile(whoLaunchedMeId, missileId, enemyMissileId);
	}

	@Override
	public void defenseHitInterceptionMissile(String whoLaunchedMeId, String interceptorId, String enemyMissileId) {
		for (IView view : allViewers)
			view.showHitInterceptionMissile(whoLaunchedMeId, interceptorId, enemyMissileId);
	}

	@Override
	public void enemyHitDestination(String whoLaunchedMeId, String missileId, String destination, int damage, String launchTime) {
		for (IView view : allViewers)
			view.showEnemyHitDestination(whoLaunchedMeId, missileId, destination, damage);
	}

	@Override
	public void defenseMissInterceptionLauncher(String whoLaunchedMeId,	String type, String missileId, String enemyLauncherId) {
		for (IView view : allViewers)
			view.showMissInterceptionLauncher(whoLaunchedMeId,type, enemyLauncherId, missileId);
	}
	
	@Override
	public void defenseMissInterceptionHiddenLauncher(String whoLaunchedMeId, String type, String enemyLauncherId) {
		for (IView view : allViewers)
			view.showMissInterceptionHiddenLauncher(whoLaunchedMeId,type, enemyLauncherId);
	}
	
	@Override
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId, String type, String missileId, String enemyLauncherId) {
		for (IView view : allViewers)
			view.showHitInterceptionLauncher(whoLaunchedMeId, type, missileId, enemyLauncherId);
	}
	
	@Override
	public void warHasBeenFinished() {
		//TODO check this
//		try {
//			view.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		for (IView view : allViewers)
			view.showWarHasBeenFinished();
	}

	@Override
	public void warHasBeenStarted() {
		for (IView view : allViewers)
			view.showWarHasBeenStarted();
	}

	@Override
	public void noSuchObject(String type) {
		for (IView view : allViewers)
			view.showNoSuchObject(type);
	}

	@Override
	public void missileNotExist(String defenseLauncherId, String enemyId) {
		for (IView view : allViewers)
			view.showMissileNotExist(defenseLauncherId, enemyId);
	}
	
	@Override
	public void enemyLauncherNotExist(String defenseLauncherId, String launcherId) {
		for (IView view : allViewers)
			view.showLauncherNotExist(defenseLauncherId, launcherId);
	}

	@Override
	public void enemyMissDestination(String whoLaunchedMeId, String id, String destination, String launchTime) {
		for (IView view : allViewers)
			view.showEnemyMissDestination(whoLaunchedMeId, id, destination, launchTime);
	}

	@Override
	public void enemyAddedLauncher(String launcherId, boolean visible) {
		for (IView view : allViewers)
			view.showEnemyAddedLauncher(launcherId, visible);
	}

	@Override
	public void defenseIronDomeAdded(String ironDomeId) {
		for (IView view : allViewers)
			view.showDefenseAddedIronDome(ironDomeId);
	}

	@Override
	public void defenseLauncherDestructorAdded(String ldId, String type) {
		for (IView view : allViewers)
			view.showDefenseAddedLD(ldId, type);
	}

	//Methods related to the model
	@Override
	public void finishWar() {
		WarXMLReader.stopAllThreads();
//		warModel.finishWar();
		
		//notify the war
		synchronized (warModel) {
			warModel.notify();
		}
	}

	@Override
	public void showStatistics(Timestamp startTime, Timestamp endTime) {
		WarStatistics statistics = warStatistics.getWarStatistics(startTime, endTime); //warModel.getStatistics();
		
		if (statistics == null)
			return;
		
		for (IView view : allViewers)
			view.showStatistics(statistics);	
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
}
