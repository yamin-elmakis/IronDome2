package TzukEitan.view.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HamasPanel extends JPanel {

	private JPanel launcherPanel;
	
	public HamasPanel(){
		setLayout(new BorderLayout());
		launcherPanel = new JPanel();
		add(new JLabel("Hamas"), BorderLayout.NORTH);
//		launcherPanel.setBorder(BorderFactory.createTitledBorder("Hamas"));
		launcherPanel.setLayout(new GridLayout(0, 2, 10, 10));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension();
		frameSize.setSize(screenSize.width * 0.15, screenSize.height * 0.2);
		launcherPanel.setPreferredSize(frameSize);
		
		JScrollPane scroller = new JScrollPane(launcherPanel);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scroller, BorderLayout.CENTER);
		
	}
	
	public void addLauncher(LauncherLable lable){
		launcherPanel.add(lable);
		validate();
		repaint();
	}
	
	public void removeLauncher(LauncherLable lable){
		launcherPanel.remove(lable);
		validate();
		repaint();
	}
}
