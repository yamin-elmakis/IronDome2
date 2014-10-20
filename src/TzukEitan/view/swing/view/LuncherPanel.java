package TzukEitan.view.swing.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import TzukEitan.view.swing.utils.ImageUtils;

public class LuncherPanel extends JPanel {

	public static String LUNCH_IMAGE = "/images/luncher.jpg";
	
	private JLabel survivorNameAndIcon;

	private HamasPanel hamasPanel;


	
	public LuncherPanel(String id, HamasPanel hamasPanel) {
		setHamasPanel(hamasPanel);
		setLayout(new BorderLayout());
		initLabelAndIcon(id);
		add(survivorNameAndIcon, BorderLayout.CENTER);

		survivorNameAndIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				// TODO open dialog send misiile
				
//				// I want to respond to LEFT button DOUBLE CLICK
//				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1)
//					moveSurvivorAction.actionPerformed(null); // or to cast e to ActionEvent
			}	
		});

		setPreferredSize(new Dimension(100, 120));
	}
	
	private void initLabelAndIcon(String name) {
		survivorNameAndIcon = new JLabel();
		survivorNameAndIcon.setText(name);
		survivorNameAndIcon.setIcon(ImageUtils.getImageIcon(LUNCH_IMAGE));
		survivorNameAndIcon.setHorizontalAlignment(SwingConstants.CENTER);
		survivorNameAndIcon.setBorder(BorderFactory.createEtchedBorder());
		survivorNameAndIcon.setVerticalTextPosition(SwingConstants.TOP);
		survivorNameAndIcon.setHorizontalTextPosition(JLabel.CENTER);
		survivorNameAndIcon.setPreferredSize(new Dimension(70, 80));
	}
	
	public void setHamasPanel(HamasPanel hamasPanel) {
		this.hamasPanel = hamasPanel;
	}
	
	public HamasPanel getTribePanel() {
		return hamasPanel;
	}
}
