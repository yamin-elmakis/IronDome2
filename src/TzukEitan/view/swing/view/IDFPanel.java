package TzukEitan.view.swing.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import TzukEitan.view.swing.action.AddLauncherAction;


public class IDFPanel extends JPanel {
	
	private static String IDFU_TITEL ="IDF";
	private JPanel humusInnerPanel;
	private JSplitPane idfSplit;
	private IronDomPanel ironDomPanel;
	private ZahalPanel zahalPanel;
	
	public IDFPanel() {
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(IDFU_TITEL));
		idfSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		ironDomPanel = new IronDomPanel();
		zahalPanel = new ZahalPanel();
		
		idfSplit.setLeftComponent(zahalPanel);
		idfSplit.setRightComponent(ironDomPanel);
	
		idfSplit.setResizeWeight(0.5);
		
		
		add(idfSplit, BorderLayout.CENTER);
		
		
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