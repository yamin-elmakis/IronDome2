package TzukEitan.view;

public abstract class WarView {

	protected abstract void fireShowStatistics();
	
	protected abstract void fireInterceptMissile();
	
	protected abstract void fireFinishWar();
	
	protected abstract void fireInterceptEnemyLauncher();
	
	protected abstract void fireAddEnemyMissile(String launcherId, String destination,
			int damage, int flyTime);
	
	protected abstract void fireAddEnemyLauncher();
	
	protected abstract void fireAddDefenseIronDome();
	
	protected abstract void fireAddDefenseLauncherDestructor(String type);
}
