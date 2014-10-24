package TzukEitan.view.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import TzukEitan.view.swing.view.HamasPanel;
import TzukEitan.view.swing.view.IronDomPanel;


public class AddIronDomAction extends AbstractAction /*implements ActionListener*/ {

	private IronDomPanel ironPanel;
	
	public AddIronDomAction(IronDomPanel ironPanel) {
		super("Add Iron Dom");
		this.ironPanel = ironPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg) {
		//TODO implement function to add iron dome
	}
}
