package TzukEitan.view.swing.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import TzukEitan.listeners.WarEventUIListener;
import TzukEitan.view.IView;
import TzukEitan.view.WarView;
import TzukEitan.view.swing.events.TzukEitanFrameEvents;
import TzukEitan.war.WarStatistics;

public class TzukEitanFrame extends WarView implements IView {

	private List<WarEventUIListener> allListeners;
	private JFrame mainFrame;
	private MainPanel mainPanel;

	public TzukEitanFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainFrame = new JFrame();
				mainFrame.setTitle("Tzuk Eitan War");
				// set the frame's Close operation
				mainFrame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						closeApplication();
					}
				});
				setupLayout();

				mainFrame.setVisible(true);
			}
		});

		allListeners = new Vector<WarEventUIListener>();

	}

	public void setupLayout() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension();
		frameSize.setSize(screenSize.width * 0.7, screenSize.height * 0.8);
		mainFrame.setSize(frameSize);
		 mainPanel = new MainPanel(tzukEitanFrameEvents);
		mainFrame.getContentPane().add(mainPanel);

	}

	public void registerListeners(WarEventUIListener listener) {
		allListeners.add(listener);
	}

	private void closeApplication() {
		System.exit(0);
	}

	// ///////////////////////////////// events
	// ////////////////////////////////////

	// //////////////////////// get comands
	// ////////////////////////////////////////////

	@Override
	public void showEnemyAddedLauncher(String launcherId, boolean visible) {
		System.out.println("showEnemyAddedLauncher "+launcherId+" "+visible);
		mainPanel.addHamasLuncher(launcherId, visible);
	}

	@Override
	public void showLauncherIsVisible(String id, boolean visible) {
		System.out.println("showLauncherIsVisible "+id+" "+visible);
		mainPanel.hamaschangeVisability(id, visible);

	}
	
	@Override
	public void showEnemyLaunchMissile(String myMunitionsId, String missileId,
			String destination, int damage, int flytime) {
		System.out.println("showEnemyLaunchMissile "+myMunitionsId+"->"+missileId+" "+destination);
		mainPanel.addHumosMissile(missileId, flytime);

	}

	@Override
	public void showEnemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {
		System.out.println("showEnemyMissDestination "+whoLaunchedMeId+"->"+id+" "+destination);
		mainPanel.removeHumosMissile(id);

	}
	
	@Override
	public void showEnemyHitDestination(String whoLaunchedMeId,
			String missileId, String destination, int damage) {
		System.out.println("showEnemyHitDestination "+whoLaunchedMeId+"->"+missileId+" "+destination);
		mainPanel.explodeHumosMissile(missileId);

	}
		
	
	@Override
	public void showDefenseAddedIronDome(String ironDomeId) {
		System.out.println("showDefenseAddedIronDome "+ironDomeId);
		mainPanel.addIronLuncher(ironDomeId, true);
	}
	
	@Override
	public void showDefenseAddedLD(String ldId, String type) {
		System.out.println("showDefenseAddedLD "+ldId+"-> "+type);
		mainPanel.addZahalLuncher(ldId, type);
	}
	
	@Override
	public void showDefenseLaunchMissile(String myMunitionsId,
			String missileId, String enemyMissileId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showDefenseLaunchMissile(String myMunitionsId, String type,
			String missileId, String enemyLauncherId) {
		// TODO Auto-generated method stub

	}



	@Override
	public void showHitInterceptionMissile(String whoLaunchedMeId,
			String interceptorId, String enemyMissileId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showMissInterceptionLauncher(String whoLaunchedMeId,
			String type, String missileId, String enemyLauncherId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showMissInterceptionHiddenLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showHitInterceptionLauncher(String whoLaunchedMeId,
			String type, String missileId, String enemyLauncherId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showWarHasBeenFinished() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showWarHasBeenStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showNoSuchObject(String type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showMissileNotExist(String defenseLauncherId, String enemyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showLauncherNotExist(String defenseLauncherId, String launcherId) {
		// TODO Auto-generated method stub

	}



	@Override
	public void showMissInterceptionMissile(String whoLaunchedMeId,
			String missileId, String enemyMissileId) {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void showStatistics(WarStatistics warStatistics) {
		// TODO Auto-generated method stub

	}

	// /////////////////// send comand ///////////////////////////////////

	@Override
	protected void fireAddEnemyLauncher() {
		for (WarEventUIListener listener : allListeners) {
			listener.addEnemyLauncher();
		}
	}
	
	@Override
	protected void fireAddEnemyMissile(String launcherId, String destination,
			int damage, int flyTime){

		for (WarEventUIListener listener : allListeners) {
			listener.addEnemyMissile(launcherId, destination, damage, flyTime);;
		}
	}

	@Override
	protected void fireAddDefenseIronDome() {
		for (WarEventUIListener listener : allListeners) {
			listener.addIronDome();
		}
	}
	
	@Override
	protected void fireAddDefenseLauncherDestructor(String type) {
		for (WarEventUIListener listener : allListeners) {
			listener.addDefenseLauncherDestructor(type);
		}
	}
	
	@Override
	protected void fireShowStatistics() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fireInterceptMissile() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fireFinishWar() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fireInterceptEnemyLauncher() {
		// TODO Auto-generated method stub

	}


	TzukEitanFrameEvents tzukEitanFrameEvents = new TzukEitanFrameEvents() {

		@Override
		public void fireAddLauncherEvent() {

			TzukEitanFrame.this.fireAddEnemyLauncher();
		}

		@Override
		public void fireAddEnemyMissile(String launcherId, String destination,
				int damage, int flyTime) {
			TzukEitanFrame.this.fireAddEnemyMissile(launcherId, destination, damage, flyTime);
			
		}

		@Override
		public void fireAddDefenseIronDome() {
			TzukEitanFrame.this.fireAddDefenseIronDome();
		}

		@Override
		public void fireAddDefenseLauncherDestructor(String type) {
			TzukEitanFrame.this.fireAddDefenseLauncherDestructor(type);			
		}
	};
}
