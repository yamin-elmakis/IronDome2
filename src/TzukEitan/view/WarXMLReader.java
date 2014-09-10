package TzukEitan.view;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.w3c.dom.NamedNodeMap;

import TzukEitan.utils.IdGenerator;
import TzukEitan.utils.Utils;
import TzukEitan.war.WarControl;

import javax.xml.parsers.*;

import java.io.*;

public class WarXMLReader extends Thread {
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;
	private Element root;
	private static ThreadGroup threadGroup = new ThreadGroup("group");
	private WarControl warControl;

	public WarXMLReader(String fileName, WarControl warControl)
			throws ParserConfigurationException, SAXException, IOException {
		this.warControl = warControl;

		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		document = builder.parse(new File(fileName));

		root = document.getDocumentElement();
	}

	public void run() {
		readEnemyLaunchers();
		readDefenseDestructors();
	}

	//read enemy launchers and their missiles form XML
	protected void readEnemyLaunchers() {
		NodeList launchers = root.getElementsByTagName("launcher");

		for (int i = 0; i < launchers.getLength(); i++) {
			Element tempLauncher = (Element) launchers.item(i);

			String idLauncher = tempLauncher.getAttribute("id");
			boolean isHidden = Boolean.parseBoolean(tempLauncher
					.getAttribute("isHidden"));

			// add to the war
			warControl.addEnemyLauncher(idLauncher, isHidden);
			IdGenerator.updateEnemyLauncherId(idLauncher);

			NodeList missiles = tempLauncher.getElementsByTagName("missile");

			// read all missiles
			readMissilesForGivenLauncher(missiles, idLauncher);

		}

		// update the id's in the war
		IdGenerator.updateFinalEnemyMissileId();
		IdGenerator.updateFinalEnemyLauncherId();

	}

	// read all the missiles of the current id launcher
	protected void readMissilesForGivenLauncher(NodeList missiles,
			String idLauncher) {
		int launchTime, flyTime, damage;

		for (int i = 0; i < missiles.getLength(); i++) {
			Element missile = (Element) missiles.item(i);

			String id = missile.getAttribute("id");
			String destination = missile.getAttribute("destination");

			try {
				launchTime = Integer.parseInt(missile.getAttribute("launchTime"));
				flyTime = Integer.parseInt(missile.getAttribute("flyTime"));
				damage = Integer.parseInt(missile.getAttribute("damage"));

			} catch (NumberFormatException e) {
				launchTime = -1;
				flyTime = -1;
				damage = -1;
				System.out.println("ERROR reading from XML");
				//System.out.println(e.getStackTrace());
			}

			// create the thread of the missile
			createEnemyMissile(id, destination, launchTime, flyTime, damage, idLauncher);
		}
	}

	protected void createEnemyMissile(String missileId, String destination,
			int launchTime, int flyTime, int damage, String launcherId) {
		
		// must use final for inner thread
		final int tempLaunchTime = launchTime;
		final int tempDamage = damage;
		final int tempFlyTime = flyTime;
		final String tempLauncherId = launcherId;
		final String tempDestination = destination;
		final String tempMissileId = missileId;

		Thread temp = new Thread(threadGroup, new Runnable() {
			@Override
			public void run() {
				try {
					// wait until launch time in XML
					sleep(tempLaunchTime * Utils.SECOND);
					
					// update the id's
					IdGenerator.updateEnemyMissileId(tempMissileId);
					
					// add to the war
					warControl.addEnemyMissile(tempLauncherId, tempDestination, tempDamage, tempFlyTime);

				} catch (InterruptedException e) {
					System.out.println("The program is close before expected");
					//System.out.println(e.getStackTrace());
				}
			}
		});
		temp.start();
	}

	// read all defense destructors and their missiles from XML
	protected void readDefenseDestructors() {;
		NodeList destructors = root.getElementsByTagName("destructor");
		
		int destructorsSize = destructors.getLength();
		for (int i = 0; i < destructorsSize; i++) {
			Element destructor = (Element) destructors.item(i);

			readDefenseDestructorFromGivenDestructor(destructor);
		}

		// update the id's in the war
		IdGenerator.updateFinalIronDomeId();
	}

	// read all defense missile from given defense destructor
	protected void readDefenseDestructorFromGivenDestructor(Element destructor) {
		NamedNodeMap attributes = destructor.getAttributes();
		String id = "";
		String type;

		Attr attr = (Attr) attributes.item(0);

		String name = attr.getNodeName();
		
		// if it's iron dome
		if (name.equals("id")) {
			id = attr.getNodeValue();
			
			// update id's in the war
			IdGenerator.updateIronDomeId(id);
			// add to war
			warControl.addIronDome(id);

			NodeList destructdMissiles = destructor.getElementsByTagName("destructdMissile");
			readDefensDestructoreMissiles(destructdMissiles, id);
			
		// if it's launcher destructor
		} else {
			if (name.equals("type")) {
				type = attr.getNodeValue();
				
				// add to war
				id = warControl.addDefenseLauncherDestructor(type);

				NodeList destructedLanuchers = destructor.getElementsByTagName("destructedLanucher");
				readDefensDestructoreMissiles(destructedLanuchers, id);
			}
		}
	}

	// read all defense missiles from given id
	protected void readDefensDestructoreMissiles(NodeList missiles, String whoLaunchMeId) {
		String targetId, destructTimeCheck;
		int destructTime;
		
		int size = missiles.getLength();
		for (int i = 0; i < size; i++) {
			Element missile = (Element) missiles.item(i);
			targetId = missile.getAttribute("id");

			destructTimeCheck = missile.getAttribute("destructAfterLaunch");
			
			if (destructTimeCheck.equals(""))
				destructTimeCheck = missile.getAttribute("destructTime");

			try {
				destructTime = Integer.parseInt(destructTimeCheck);
			
			} catch (NumberFormatException e) {
				destructTime = -1;
				//System.out.println(e.getStackTrace());
			}

			// create the thread of the missile
			createDefenseMissile(whoLaunchMeId, targetId, destructTime);
		}
	}

	protected void createDefenseMissile(String whoLaunchMeId, String targetId, int destructTime) {
		//must use final for inner thread
		final int tempDestructTime = destructTime;
		final String tempLauncherId = whoLaunchMeId, tempTargetId = targetId;

		Thread temp = new Thread(threadGroup, new Runnable() {
			@Override
			public void run() {
				try {
					// wait until launch time in XML
					sleep(tempDestructTime * Utils.SECOND);
					
					// update the war
					if (tempLauncherId.charAt(0) == 'D') {
						warControl.interceptGivenMissile(tempLauncherId, tempTargetId);
					} else
						warControl.interceptGivenLauncher(tempLauncherId, tempTargetId);
					
				} catch (InterruptedException e) {
					System.out.println("The program is close before expected");
					//System.out.println(e.getStackTrace());
				}
			}
		});
		temp.start();
	}

	
	/**
	 * In case of emergency when user want to stop the war during the xml
	 * reading
	 **/
	public static void stopAllThreads() {
		threadGroup.interrupt();
	}

}
