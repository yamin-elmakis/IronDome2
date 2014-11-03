package TzukEitan.view.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import TzukEitan.view.swing.view.ZahalPanel;
import TzukEitan.view.swing.view.ZahalLuncherPanel.Ztype;


public class AddLaunchDistructorAction extends AbstractAction /*implements ActionListener*/ {

	private ZahalPanel zhalPanel;
	
	public AddLaunchDistructorAction(ZahalPanel zhalPanel) {
		super("Add Launch Distructor");
		this.zhalPanel = zhalPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg) {
		
		
		Object[] possibilities = {"playn", "ship"};
		String s = (String)JOptionPane.showInputDialog(
                null,
                "Add humus destroyer",
                "Choose destroyer type!",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "playn");

		//If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			if (s.equals("ship")){
				zhalPanel.addLuncher(Ztype.ship);
			}else{
				zhalPanel.addLuncher(Ztype.playn);
			}
		}
		
	}
}
