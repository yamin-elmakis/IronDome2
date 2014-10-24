package TzukEitan.view.swing.utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class CloseJFrameUtil {
	public static void closeApplication(JFrame parent) {
		int result = JOptionPane.showConfirmDialog(parent,
				"Are you sure you want to exit?", "Goodbye?",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
}
