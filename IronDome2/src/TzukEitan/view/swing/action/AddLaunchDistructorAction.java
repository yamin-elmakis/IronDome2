package TzukEitan.view.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import TzukEitan.view.swing.view.HamasPanel;
import TzukEitan.view.swing.view.IronDomPanel;
import TzukEitan.view.swing.view.ZahalPanel;


public class AddLaunchDistructorAction extends AbstractAction /*implements ActionListener*/ {

	private ZahalPanel zhalPanel;
	
	public AddLaunchDistructorAction(ZahalPanel zhalPanel) {
		super("Add Launch Distructor");
		this.zhalPanel = zhalPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg) {
		//TODO implement function to add LaunchDistructor
	}
}
