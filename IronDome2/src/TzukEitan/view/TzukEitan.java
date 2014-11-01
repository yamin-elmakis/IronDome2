/*
 * Made by:
 * Guy Eshel: eshelguy@gmail.com
 * &
 * Ben Amir: amir.ben@gmail.com
 */

package TzukEitan.view;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import TzukEitan.clientServer.WarServer;
import TzukEitan.dal.WarDB;
import TzukEitan.view.swing.view.TzukEitanFrame;
import TzukEitan.war.War;
import TzukEitan.war.WarControl;

public class TzukEitan {

	public static void main(String[] args) {
		
<<<<<<< HEAD
//		////// Yamin's code /////
//		WarServer server = new WarServer();
//		server.start();
//		/////////
=======
		////// Yamin's code /////
//		WarServer server = new WarServer();
//		new Thread() {
//		    public void run() {
//		    	server.run();
//		    }
//		}.start();
		/////////
>>>>>>> ddbb993c0b24229c0415f43ea8f49a234597e23c
		
		////// Vova's code //////
		
		////////
		WarXMLReader warXML;

//		IView view = new ConsoleView();
        IView view = new TzukEitanFrame();
//		
		War warModel = new War();
//
//		WarDB warDB = new WarDB(warModel);
		WarControl warControl = new WarControl(warModel);
//		warControl.registerListeners(server);
		warControl.registerListeners(view);
//		warControl.setWarStatistics(warDB);
<<<<<<< HEAD
//
=======
		
//		 TODO delete this comment
>>>>>>> ddbb993c0b24229c0415f43ea8f49a234597e23c
//		try {
//			warXML = new WarXMLReader("warStart.xml", warControl);
//			warXML.start();
//			warXML.join();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		warModel.start();
		
	}

}
