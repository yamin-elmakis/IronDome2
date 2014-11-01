package TzukEitan.view.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import TzukEitan.view.IView;
import TzukEitan.view.swing.view.TzukEitanFrame;

public class ControlPanel extends JPanel implements ActionListener {
	
	private JComboBox<String> test;
	private JButton bAddLauncher;
	private TzukEitanFrame mainFrame;

	public ControlPanel(Dimension size){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Color.BLUE);
		
		String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit" };
		bAddLauncher = new JButton("Add launcher");
		bAddLauncher.setActionCommand("add_launcher");
		bAddLauncher.addActionListener(this);
		JPanel p = new JPanel();
		test = new JComboBox<>(petStrings);
		p.add(test);
		p.setBackground(Color.black);
		JPanel p2 = new JPanel();
		p2.add(test);
		add(bAddLauncher);
		add(p2);
		add(p);
	}

	@Override
	public void actionPerformed(ActionEvent btn) {
		String action = btn.getActionCommand();
		System.out.println("ACTION: "+action);
		mainFrame.fireAddEnemyLauncher();
	}
	
	public void setMainFrame(TzukEitanFrame frame){
		this.mainFrame = frame;
	}
}
