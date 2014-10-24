package TzukEitan.launchers;

/** Inteface for all munitions using in the program **/
public interface Munitions {
	//public void addLoggerHandler();
	public void launchMissile() throws InterruptedException;
	public void createMissile();
	public void stopRunning();
}
