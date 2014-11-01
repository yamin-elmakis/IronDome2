package TzukEitan.view.swing.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import TzukEitan.view.swing.utils.ImageUtils;

public class HamasLuncherPanel extends JPanel {

	public static String LUNCH_IMAGE = "../../../../images/luncher.jpg";
	public static String LUNCHER_HIDE_IMAGE = "../../../../images/luncher_hide.jpg";

	private String id;
	private JLabel hamasNameAndIcon;

	private HamasPanel hamasPanel;

	public HamasLuncherPanel(String id, boolean isVisible, HamasPanel hamasPanel) {
		this.id = id;
		setHamasPanel(hamasPanel);
		setLayout(new BorderLayout());
		initLabelAndIcon(id, isVisible);
		add(hamasNameAndIcon, BorderLayout.CENTER);

		hamasNameAndIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// I want to respond to LEFT button DOUBLE CLICK
				if (e.getClickCount() == 1
						&& e.getButton() == MouseEvent.BUTTON1) {
					JTextField destination = new JTextField();
					JTextField flyTime = new JTextField();

					final JComponent[] inputs = new JComponent[] {
							new JLabel("destination"), destination,
							new JLabel("flyTime"), flyTime,

					};
					int check = JOptionPane.showConfirmDialog(null, inputs,
							"Fire missile", JOptionPane.OK_CANCEL_OPTION);

					if (check == JOptionPane.OK_OPTION) {
						boolean isOK = true;
						String destinationSTR = destination.getText();
						String flytimeSTR = flyTime.getText().replaceAll(
								"\\D+", "");

						if (destinationSTR.equals("") || flytimeSTR.equals("")) {
							isOK = false;
						}

						while (!isOK) {
							flyTime.setText("");
							check = JOptionPane.showConfirmDialog(null, inputs,
									"Insert all fileds",
									JOptionPane.OK_CANCEL_OPTION);
							if (check == JOptionPane.OK_OPTION) {
								destinationSTR = destination.getText();
								flytimeSTR = flyTime.getText().replaceAll(
										"\\D+", "");
								if (!destinationSTR.equals("")
										&& !flytimeSTR.equals("")) {
									isOK = true;
								}
							} else {
								isOK = true;
							}
						}
						
						if (check == JOptionPane.OK_OPTION) {
							hamasPanel.fireAddEnemyMissile(id, destinationSTR, Integer.parseInt(flytimeSTR));
						}

					}

					// System.out.println("You entered " +
					// firstName.getText() + ", " +
					// lastName.getText() + ", " +
					// password.getText());
				}

			}
		});

		setPreferredSize(new Dimension(100, 120));
	}

	private void initLabelAndIcon(String id, boolean isVisible) {
		hamasNameAndIcon = new JLabel();
		hamasNameAndIcon.setText(id);
		if (isVisible) {
			hamasNameAndIcon.setIcon(ImageUtils.getImageIcon(LUNCH_IMAGE));
		} else {
			hamasNameAndIcon.setIcon(ImageUtils
					.getImageIcon(LUNCHER_HIDE_IMAGE));
		}
		hamasNameAndIcon.setHorizontalAlignment(SwingConstants.CENTER);
		hamasNameAndIcon.setBorder(BorderFactory.createEtchedBorder());
		hamasNameAndIcon.setVerticalTextPosition(SwingConstants.TOP);
		hamasNameAndIcon.setHorizontalTextPosition(JLabel.CENTER);
		hamasNameAndIcon.setPreferredSize(new Dimension(70, 80));
	}

	public void changeVisability(boolean isVisible) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Here, we can safely update the GUI
				// because we'll be called from the
				// event dispatch thread
				if (isVisible) {
					hamasNameAndIcon.setIcon(ImageUtils
							.getImageIcon(LUNCH_IMAGE));
				} else {
					hamasNameAndIcon.setIcon(ImageUtils
							.getImageIcon(LUNCHER_HIDE_IMAGE));
				}
			}
		});
	}

	public void setHamasPanel(HamasPanel hamasPanel) {
		this.hamasPanel = hamasPanel;
	}

	public HamasPanel getTribePanel() {
		return hamasPanel;
	}

	public String getId() {
		return id;
	}

}
