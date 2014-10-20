package TzukEitan.view.swing.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import TzukEitan.view.swing.view.HamasPanel;


public class AddLauncherAction extends AbstractAction /*implements ActionListener*/ {

	private HamasPanel humusPanel;
	
	public AddLauncherAction(HamasPanel hunusPanel) {
		super("Add Launcher");
		this.humusPanel = hunusPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg) {
		//TODO implement function to add luncher
	}
}
