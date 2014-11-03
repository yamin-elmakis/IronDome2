package TzukEitan.view.swing.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import TzukEitan.view.swing.utils.ImageUtils;

public class GrfxPanel extends JPanel implements ComponentListener {

	private static String GRAFIXS = "gfx";
	public static String LUNCH_IMAGE = "../../../../images/bazuka.png";
	public static String DORA_IMAGE = "../../../../images/dora.jpg";
	private int width;
	private JLabel dora;
	private JLabel bazuka;
	private ArrayList<FlingMissile> flingMissiles = new ArrayList<GrfxPanel.FlingMissile>();

	public GrfxPanel() {
		addComponentListener(this);
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder(GRAFIXS));

		bazuka = new JLabel();
		bazuka.setIcon(ImageUtils.getImageIcon(LUNCH_IMAGE));
		bazuka.setSize(100, 100);
		bazuka.setLocation(10, 60);
		add(bazuka);

		dora = new JLabel();
		dora.setIcon(ImageUtils.getImageIcon(DORA_IMAGE));
		dora.setSize(100, 150);
		add(dora);

		// start the painting
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						for (FlingMissile flingMissile : flingMissiles) {
							flingMissile.updatePozition();
						}
					}
				});
			}
		}, 0, 10);

	}

	public void addMissile(String id, int flyTime) {
		FlingMissile bird = new FlingMissile(id, flyTime);
		add(bird);
		flingMissiles.add(bird);

	}
	
	public void removeMissile(String id){
		int index=-1;
		for (int i = 0; i < flingMissiles.size(); i++) {
			if (flingMissiles.get(i).getId().equals(id)){
				index = i;
				break;
			}
		}
		
		if (index>-1){
			FlingMissile f = flingMissiles.get(index);
//			SwingUtilities.invokeLater(new Runnable() {
//				public void run() {	
				
					remove(f);
					validate();
					repaint();
					flingMissiles.remove(f);
//				}
//			});
		}
	}

	public void explode(String id) {
		int index=-1;
		for (int i = 0; i < flingMissiles.size(); i++) {
			if (flingMissiles.get(i).getId().equals(id)){
				index = i;
				break;
			}
		}
		
		if (index>-1){
			FlingMissile f = flingMissiles.get(index);
			f.explode();
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(2000);
								remove(f);
								flingMissiles.remove(f);
								validate();
								repaint();
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	// ////////////////////// events ////////////////////////////

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		width = e.getComponent().getWidth();
		dora.setLocation(width - 100, 50);

		for (FlingMissile flingMissile : flingMissiles) {
			flingMissile.updatePozition();
		}

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	class FlingMissile extends JLabel {
		public final String MISSILE_IMAGE = "../../../../images/bird.png";
		public final String MISSILE_x_IMAGE = "../../../../images/bird_x.png";
		public final String EXPLODE_IMAGE = "../../../../images/explode.png";

		String id;
		private int flyTime;
		private long startTimeInSeconds = (System.currentTimeMillis() / 10);
		private int x = 0;
		private boolean isFling = true;

		public FlingMissile(String id, int flyTime) {
			this.id = id;
			this.flyTime = flyTime;
			setIcon(ImageUtils.getImageIcon(MISSILE_IMAGE));
			setSize(50, 50);
			setLocation(x + 95, 60);
			addMouseListener(click);
		}

		public void updatePozition() {
			double step = ((width - 100) / (double) flyTime) / 100;
			x = (int) (step * ((System.currentTimeMillis() / 10) - startTimeInSeconds));
			if (x>(width - 250))
				isFling=false;
			if (isFling) {
				setLocation(x + 95, 60);
			}
		}

		public void explode() {
			setIcon(ImageUtils.getImageIcon(EXPLODE_IMAGE));
			isFling = false;
		}

		public String getId() {
			return id;
		}

		public int getFlyTime() {
			return flyTime;
		}
		
		MouseListener click = new MouseAdapter() {
		
			@Override
			public void mouseClicked(MouseEvent e) {

				// I want to respond to LEFT button DOUBLE CLICK
				if (e.getClickCount() == 1
						&& e.getButton() == MouseEvent.BUTTON1) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							setIcon(ImageUtils.getImageIcon(MISSILE_x_IMAGE));
						}
					});
				}
			}
		
		} ;

	}

}
