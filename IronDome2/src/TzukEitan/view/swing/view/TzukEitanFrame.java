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
import TzukEitan.view.swing.events.TzukEitanFrameEvents;


public class TzukEitanFrame implements IView{
	
	private List<WarEventUIListener> allListeners;
	private JFrame mainFrame;

	public TzukEitanFrame(){
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
		MainPanel mainPanel = new MainPanel(tzukEitanFrameEvents);
		mainFrame.getContentPane().add(mainPanel);
		
		
	}
	
	public void registerListeners(WarEventUIListener listener) {
		allListeners.add(listener);
	}
	
	private void closeApplication(){
		System.exit(0);
	}

	public void fireAddLauncherEvent(){
		for (WarEventUIListener listener : allListeners) {
			listener.addEnemyLauncher();
		}
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
	public void showEnemyLaunchMissile(String myMunitionsId, String missileId,
			String destination, int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showLauncherIsVisible(String id, boolean visible) {
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
	public void showEnemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showDefenseAddedIronDome(String ironDomeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showDefenseAddedLD(String ldId, String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showMissInterceptionMissile(String whoLaunchedMeId,
			String missileId, String enemyMissileId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showEnemyHitDestination(String whoLaunchedMeId,
			String missileId, String destination, int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showEnemyAddedLauncher(String launcherId, boolean visible) {
		// TODO Auto-generated method stub
	}

	@Override
	public void showStatistics(long[] array) {
		// TODO Auto-generated method stub
		
	}
	
	
	TzukEitanFrameEvents tzukEitanFrameEvents = new TzukEitanFrameEvents() {
		
		@Override
		public void fireAddLauncherEvent() {
			TzukEitanFrame.this.fireAddLauncherEvent();
		}
	};
	
}
