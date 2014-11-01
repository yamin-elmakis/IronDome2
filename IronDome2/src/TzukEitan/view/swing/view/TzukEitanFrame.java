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
import TzukEitan.war.WarStatistics;


public class TzukEitanFrame extends WarView implements IView{
	
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
		MainPanel mainPanel = new MainPanel();
		mainFrame.getContentPane().add(mainPanel);
		
//		JPanel tzukEitanPanel = new JPanel();
//		tzukEitanPanel.setSize(frameSize);
//		tzukEitanPanel.setBorder(BorderFactory.createTitledBorder("war panle"));
//		tzukEitanPanel.setBackground(Color.YELLOW);
//		tzukEitanPanel.setLayout(new BorderLayout());
//		
//		hamasPanel = new HamasPanel();
//		controlPanel = new ControlPanel(frameSize);
//		
//		JPanel warPanel = new JPanel();
//		warPanel.setLayout(new BorderLayout());
//		JPanel right = new JPanel();
//		JPanel left = new JPanel();
//		JLabel l2 = new JLabel("IDF");
//		left.setLayout(new BorderLayout());
//		right.setLayout(new BorderLayout());
//		left.add(hamasPanel, BorderLayout.SOUTH);
//		right.add(l2, BorderLayout.SOUTH);
//		right.add(new JLabel(), BorderLayout.CENTER);
//		left.setBackground(Color.red);
//		right.setBackground(Color.red);
//		warPanel.add(left, BorderLayout.WEST);
//		warPanel.add(right, BorderLayout.EAST);
//		
////		JButton badd = new JButton("add");
////		final LauncherLable tester = new LauncherLable("test");
////		badd.addActionListener(new ActionListener() {
////			
////			@Override
////			public void actionPerformed(ActionEvent arg0) {
////				hamasPanel.addLauncher(tester);
////			}
////		});
////		JButton sub = new JButton("sub");
////		sub.addActionListener(new ActionListener() {
////			
////			@Override
////			public void actionPerformed(ActionEvent arg0) {
////				hamasPanel.removeLauncher(tester);
////			}
////		});
////		JPanel pp = new JPanel();
////		pp.add(badd);
////		pp.add(sub);
////		tzukEitanPanel.add(pp, BorderLayout.SOUTH);
//		
//		controlPanel.setMainFrame(this);
//		tzukEitanPanel.add(controlPanel, BorderLayout.NORTH);
//		tzukEitanPanel.add(warPanel, BorderLayout.CENTER);
//		mainFrame.setContentPane(tzukEitanPanel);
		
	}
	
	public void registerListeners(WarEventUIListener listener) {
		allListeners.add(listener);
	}
	
	private void closeApplication(){
		System.exit(0);
//		int result = JOptionPane.showConfirmDialog(this,
//				"Are you sure you want to exit?", "War is over?",
//				JOptionPane.YES_NO_OPTION);
//		if (result == JOptionPane.YES_OPTION) {
//			System.exit(0);
//		}
	}

	@Override
	public void fireAddEnemyLauncher(){
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
	public void showStatistics(WarStatistics warStatistics) {
		// TODO Auto-generated method stub
		
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

	@Override
	protected void fireAddEnemyMissile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void fireAddDefenseIronDome() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void fireAddDefenseLauncherDestructor() {
		// TODO Auto-generated method stub
		
	}
}
