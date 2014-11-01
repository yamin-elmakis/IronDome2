package TzukEitan.view.swing.events;

public interface TzukEitanFrameEvents {
	
	public void fireAddLauncherEvent();
	
	public void fireAddEnemyMissile(String launcherId, String destination,
			int damage, int flyTime);
	
	public void fireAddDefenseIronDome();

	public void fireAddDefenseLauncherDestructor(String type);

}
