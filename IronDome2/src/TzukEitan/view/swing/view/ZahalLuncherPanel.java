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

public class ZahalLuncherPanel extends JPanel {

	public static String LUNCH_PLAIN_IMAGE = "../../../../images/superman.png";
	public static String LUNCH_SHIP_IMAGE = "../../../../images/ship.jpg";
	
	public static String LUNCHER_HIDE_IMAGE = "../../../../images/luncher_hide.jpg";


	private String id;
	private JLabel ironNameAndIcon;
	private Ztype type;
	private ZahalPanel zahalPanel;

	public ZahalLuncherPanel(String id,Ztype type,ZahalPanel zahalPanel) {
		this.id = id;
		this.type = type;
		setZahalPanel(zahalPanel);
		setLayout(new BorderLayout());
		initLabelAndIcon(id, true);
		add(ironNameAndIcon, BorderLayout.CENTER);

		ironNameAndIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 1
						&& e.getButton() == MouseEvent.BUTTON1) {
					
					
				}

			}
		});

		setPreferredSize(new Dimension(100, 120));
	}

	private void initLabelAndIcon(String id, boolean isVisible) {
		ironNameAndIcon = new JLabel();
		ironNameAndIcon.setText(id);
		if (type  == Ztype.playn) {
			ironNameAndIcon.setIcon(ImageUtils.getImageIcon(LUNCH_PLAIN_IMAGE));
		} else {
			ironNameAndIcon.setIcon(ImageUtils
					.getImageIcon(LUNCH_SHIP_IMAGE));
		}
		ironNameAndIcon.setHorizontalAlignment(SwingConstants.CENTER);
		ironNameAndIcon.setBorder(BorderFactory.createEtchedBorder());
		ironNameAndIcon.setVerticalTextPosition(SwingConstants.TOP);
		ironNameAndIcon.setHorizontalTextPosition(JLabel.CENTER);
		ironNameAndIcon.setPreferredSize(new Dimension(70, 80));
	}

	public void changeVisability(boolean isVisible) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Here, we can safely update the GUI
				// because we'll be called from the
				// event dispatch thread
				if (isVisible) {
					if (type  == Ztype.playn) {
						ironNameAndIcon.setIcon(ImageUtils.getImageIcon(LUNCH_PLAIN_IMAGE));
					} else {
						ironNameAndIcon.setIcon(ImageUtils
								.getImageIcon(LUNCH_SHIP_IMAGE));
					}
				} else {
					ironNameAndIcon.setIcon(ImageUtils
							.getImageIcon(LUNCHER_HIDE_IMAGE));
				}
			}
		});
	}

	public void setZahalPanel(ZahalPanel zahalPanel) {
		this.zahalPanel = zahalPanel;
	}


	public String getId() {
		return id;
	}
	
	public enum Ztype {playn,ship};

}
