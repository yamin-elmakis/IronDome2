package TzukEitan.view.swing;

import java.awt.Dimension;

import javax.swing.JLabel;

import TzukEitan.utils.ImageUtils;

class LauncherLable extends JLabel {
	
	private String name;
	public String SURVIVOR_IMAGE = "./../images/pirate_64x64.png";
	
	public LauncherLable(String name, boolean visible){
		this.name = name;
		setIcon(ImageUtils.getImageIcon(SURVIVOR_IMAGE));
		setPreferredSize(new Dimension(65, 65));
		setVisibility(visible);
	}

	@Override
	public boolean equals(Object o2) {
		LauncherLable other = (LauncherLable) o2;
		return this.name.equals(other.name);
	}
	
	public void setVisibility(boolean visible){
		setVisible(visible);
	}
}
