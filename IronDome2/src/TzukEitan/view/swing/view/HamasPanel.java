package TzukEitan.view.swing.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import TzukEitan.view.swing.action.AddLauncherAction;
import TzukEitan.view.swing.events.TzukEitanFrameEvents;


public class HamasPanel extends JPanel {
	
	private static String HAMAS_TITEL ="Humus";
	private JButton btnAddSurvivor;
	private JPanel humusInnerPanel;
	private ArrayList<HamasLuncherPanel> lunchers= new ArrayList<HamasLuncherPanel>();
	
	private TzukEitanFrameEvents tzukEitanFrameEvents;
	
	public HamasPanel(TzukEitanFrameEvents tzukEitanFrameEvents) {
		this.tzukEitanFrameEvents = tzukEitanFrameEvents;
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(HAMAS_TITEL));

		btnAddSurvivor = new JButton(new AddLauncherAction(this));
		add(btnAddSurvivor, BorderLayout.NORTH);
		
		humusInnerPanel = new JPanel();
		humusInnerPanel.setLayout(new GridLayout(0, 2, 10, 10));
		
		JScrollPane scroller = new JScrollPane(humusInnerPanel);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroller, BorderLayout.CENTER);
	}

	public void addLuncher() {
		if (tzukEitanFrameEvents!=null){
			tzukEitanFrameEvents.fireAddLauncherEvent();
		}
	}
	
	public void addLuncherToPanel(String id,boolean isVisible){
		HamasLuncherPanel luncher = new HamasLuncherPanel(id,isVisible, this);
		lunchers.add(luncher);
		humusInnerPanel.add(luncher);
		validate();
		repaint();
	}
	
	public synchronized void changeVisability(String id, boolean isVisible){
		for (HamasLuncherPanel hamasLuncherPanel : lunchers) {
			if (hamasLuncherPanel.getId().equals(id)){
				hamasLuncherPanel.changeVisability(isVisible);
				return;
			}
		}
	}
	
	public void fireAddEnemyMissile(String launcherId, String destination, int flyTime){
		Random random = new Random(System.currentTimeMillis());
		int damege = random.nextInt(100);
		
		if (tzukEitanFrameEvents!=null) {
			tzukEitanFrameEvents.fireAddEnemyMissile(launcherId, destination, damege, flyTime);
		}
	}
		
}
