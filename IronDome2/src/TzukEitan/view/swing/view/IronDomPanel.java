package TzukEitan.view.swing.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import TzukEitan.view.swing.action.AddIronDomAction;
import TzukEitan.view.swing.action.AddLauncherAction;
import TzukEitan.view.swing.events.TzukEitanFrameEvents;


public class IronDomPanel extends JPanel {
	
	private JButton btnAddIron;
	private JPanel ironInnerPanel;
	private TzukEitanFrameEvents tzukEitanFrameEvents;
	private ArrayList<IronLuncherPanel> lunchers= new ArrayList<IronLuncherPanel>();

	
	public IronDomPanel(TzukEitanFrameEvents tzukEitanFrameEvents) {
		this.tzukEitanFrameEvents = tzukEitanFrameEvents;
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(""));

		btnAddIron = new JButton(new AddIronDomAction(this));
		add(btnAddIron, BorderLayout.NORTH);
		
		ironInnerPanel = new JPanel();
		ironInnerPanel.setLayout(new GridLayout(0, 2, 10, 10));
		
		JScrollPane scroller = new JScrollPane(ironInnerPanel);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroller, BorderLayout.CENTER);
	}

	public void addLuncher() {
		if (tzukEitanFrameEvents!=null){
			tzukEitanFrameEvents.fireAddDefenseIronDome();
		}
	}
	
	public void addLuncherToPanel(String id,boolean isVisible){
		IronLuncherPanel luncher = new IronLuncherPanel(id, this);
		lunchers.add(luncher);
		ironInnerPanel.add(luncher);
		validate();
		repaint();
	}
	
	public synchronized void changeVisability(String id, boolean isVisible){
		for (IronLuncherPanel ironLuncherPanel : lunchers) {
			if (ironLuncherPanel.getId().equals(id)){
				ironLuncherPanel.changeVisability(isVisible);
				return;
			}
		}
	}
		
}





//private JPanel launcherPanel;
//
//public HamasPanel(){
//	setLayout(new BorderLayout());
//	launcherPanel = new JPanel();
//	add(new JLabel("Hamas"), BorderLayout.NORTH);
////	launcherPanel.setBorder(BorderFactory.createTitledBorder("Hamas"));
//	launcherPanel.setLayout(new GridLayout(0, 2, 10, 10));
//	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//	Dimension frameSize = new Dimension();
//	frameSize.setSize(screenSize.width * 0.15, screenSize.height * 0.2);
//	launcherPanel.setPreferredSize(frameSize);
//	
//	JScrollPane scroller = new JScrollPane(launcherPanel);
//	scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//	add(scroller, BorderLayout.CENTER);
//	
//}
//
//public void addLauncher(LauncherLable lable){
//	launcherPanel.add(lable);
//	validate();
//	repaint();
//}
//
//public void removeLauncher(LauncherLable lable){
//	launcherPanel.remove(lable);
//	validate();
//	repaint();
//}