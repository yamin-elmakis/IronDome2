package TzukEitan.view.swing.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

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
	private ArrayList<HumosLuncherPanel> luncherPanels = new ArrayList<HumosLuncherPanel>();
	
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
	
	public void addLuncherToPanel(String id ,boolean isVisebale) {
		HumosLuncherPanel luncher = new HumosLuncherPanel(id,isVisebale, this);
		luncherPanels.add(luncher);
		humusInnerPanel.add(luncher);
		validate();
		repaint();
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