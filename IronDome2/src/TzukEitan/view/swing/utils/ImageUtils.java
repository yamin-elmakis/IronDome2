package TzukEitan.view.swing.utils;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.nio.file.Paths;

import javax.swing.ImageIcon;

public class ImageUtils {
	
	public static Image getImage(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		}

		URL imageURL = ImageUtils.class.getResource(name);
		Paths.get(".").toAbsolutePath().normalize().toString();
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
