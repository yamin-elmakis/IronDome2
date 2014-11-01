package TzukEitan.view.swing.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import TzukEitan.view.swing.events.TzukEitanFrameEvents;
import TzukEitan.view.swing.view.ZahalLuncherPanel.Ztype;

public class MainPanel extends JPanel {

	private JSplitPane warSplit, gfxSplt;
	private HamasPanel humus;
	private GrfxPanel grfx;
	private IDFPanel idfPanel;

	// private TribePanel tribe1, tribe2,ilend;

	public MainPanel(TzukEitanFrameEvents tzukEitanFrameEvents) {
		setLayout(new BorderLayout());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension panelSize = new Dimension();
		panelSize.setSize(screenSize.width * 0.5, screenSize.height * 0.5);
		setSize(panelSize);

		warSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		gfxSplt = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		humus = new HamasPanel(tzukEitanFrameEvents);
		idfPanel = new IDFPanel(tzukEitanFrameEvents);
		grfx = new GrfxPanel();

		gfxSplt.setTopComponent(warSplit);
		gfxSplt.setBottomComponent(grfx);
		warSplit.setLeftComponent(humus);
		warSplit.setRightComponent(idfPanel);

		gfxSplt.setResizeWeight(0.5);
		warSplit.setResizeWeight(0.5); // distributes the extra space when
										// resizing.
										// without it the proportions will not
										// be saved during resize

		add(gfxSplt, BorderLayout.CENTER);
	}

	public void addHamasLuncher(String id, boolean isVisible) {
		humus.addLuncherToPanel(id, isVisible);
	}

	public void hamaschangeVisability(String id, boolean isVisible) {
		humus.changeVisability(id, isVisible);
	}

	public void addIronLuncher(String id, boolean isVisible) {
		idfPanel.addIronLuncher(id, isVisible);
	}

	public void ironChangeVisability(String id, boolean isVisible) {
		idfPanel.ironChangeVisability(id, isVisible);
	}

	public void addZahalLuncher(String id, String type) {
		if (type.equalsIgnoreCase("ship"))
			idfPanel.addZahalLuncher(id, Ztype.ship);
		else
			idfPanel.addZahalLuncher(id, Ztype.playn);
	}

	public void zahalChangeVisability(String id, boolean isVisible) {
		idfPanel.zahalChangeVisability(id, isVisible);
	}
}
