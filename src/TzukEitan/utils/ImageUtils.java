package TzukEitan.utils;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageUtils {
	public static final String PASSENGER_ICON = "../images/pirate_64x64.png";
	
	public static Image getImage(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		}

		URL imageURL = ImageUtils.class.getResource(name);
		if (imageURL == null) {
			return null;
		}

		return Toolkit.getDefaultToolkit().createImage(imageURL);
	}

	public static ImageIcon getImageIcon(String name) {
		Image image = getImage(name);
		if (image == null) {
			return null;
		}
		return new ImageIcon(image);
	}
}
