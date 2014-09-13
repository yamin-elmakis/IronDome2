package TzukEitan.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.war.War;

public class WarDB implements WarEventListener{

	private Connection connection;
	private String dbUrl;
	
	public WarDB(War warModel) {
		dbUrl = "jdbc:mysql://localhost/TzoukEitan";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(dbUrl, "root", "");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			while (e != null) {
				System.out.println(e.getMessage());
				e = e.getNextException();
			}
		}
		warModel.registerListenerts(this);
		initDB();
	}
	
	private void initDB() {

		String createLauncherTable = "CREATE TABLE IF NOT exists Launcher (LauncherID varchar(64) NOT NULL,"
				+ " isHidden varchar(12), PRIMARY KEY (LauncherID))";
		String createMissileTable = "CREATE TABLE IF NOT exists Missile (MissileID varchar(64) NOT NULL, "
				+ "LauncherID varchar(64) NOT NULL, "
				+ "destination varchar(64), "
				+ "launchTime DATE, "
				+ "damage int, "
				+ "interceptedBy varchar(64), "
				+ "PRIMARY KEY (MissileID), "
				+ "FOREIGN KEY (LauncherID) REFERENCES Launcher(LauncherID))";
		String createIronDomeTable = "CREATE TABLE IF NOT exists IronDome (IronDomeID varchar(64) NOT NULL, PRIMARY KEY (IronDomeID))";
		String createDefenseMissileTable = "CREATE TABLE IF NOT exists DefenseMissile (DefenseMissileID varchar(64) NOT NULL, "
				+ "IronDomeID varchar(64) NOT NULL, "
				+ "MissileID varchar(64) NOT NULL, "
				+ "launchTime DATE, "
				+ "didHit varchar(12), "
				+ "PRIMARY KEY (DefenseMissileID), "
				+ "FOREIGN KEY (IronDomeID) REFERENCES IronDome(IronDomeID), "
				+ "FOREIGN KEY (MissileID) REFERENCES Missile(MissileID))";
		String createLauncherDestructorTable = "CREATE TABLE IF NOT exists LauncherDestructor (LauncherDestructorID varchar(64) NOT NULL,"
				+ " type varchar(12) NOT NULL, PRIMARY KEY (LauncherDestructorID))";
		String createDefenseDestructorMissileTable = "CREATE TABLE IF NOT exists DefenseDestructorMissile (DefenseDestructorMissileID varchar(64) NOT NULL, "
				+ "LauncherDestructorID varchar(64) NOT NULL, "
				+ "LauncherID varchar(64) NOT NULL, "
				+ "launchTime DATE, "
				+ "didHit varchar(12), "
				+ "PRIMARY KEY (DefenseDestructorMissileID), "
				+ "FOREIGN KEY (LauncherDestructorID) REFERENCES LauncherDestructor(LauncherDestructorID), "
				+ "FOREIGN KEY (LauncherID) REFERENCES Launcher(LauncherID))";
		
		String delLauncherTable = "DROP TABLE Launcher";
		String delMissileTable = "DROP TABLE Missile";
		String delIronDomeTable = "DROP TABLE IronDome";
		String delDefenseMissileTable = "DROP TABLE DefenseMissile";
		String delLauncherDestructorTable = "DROP TABLE LauncherDestructor";
		String delDefenseDestructorMissileTable = "DROP TABLE DefenseDestructorMissile";
		
		try {
			Statement statement = connection.createStatement();
			statement.execute(delDefenseDestructorMissileTable);
			statement.execute(delDefenseMissileTable);
			statement.execute(delLauncherDestructorTable);
			statement.execute(delIronDomeTable);
			statement.execute(delMissileTable);
			statement.execute(delLauncherTable);
			
			statement.execute(createLauncherTable);
			statement.execute(createMissileTable);
			statement.execute(createIronDomeTable);
			statement.execute(createDefenseMissileTable);
			statement.execute(createLauncherDestructorTable);
			statement.execute(createDefenseDestructorMissileTable);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void enemyLaunchMissile(String myMunitionsId, String missileId,
			String destination, int damage) {
		// TODO Auto-generated method stub
		PreparedStatement statement;
//		try {
//			statement = connection.prepareStatement("SELECT * FROM tribes where id > ?");
//			statement.setInt(1, 3);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
	}
	@Override
	public void enemyLauncherIsVisible(String id, boolean visible) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enemyHitDestination(String whoLaunchedMeId, String id,
			String destination, int damage, String launchTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void defenseLaunchMissile(String myMunitionsId, String missileId,
			String enemyMissileId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void defenseLaunchMissile(String myMunitionsId, String type,
			String missileId, String enemyLauncherId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void defenseHitInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void defenseMissInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId, int damage) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void defenseMissInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void defenseMissInterceptionHiddenLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void warHasBeenFinished() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void warHasBeenStarted() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void noSuchObject(String type) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void missileNotExist(String defenseLauncherId, String enemyId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enemyLauncherNotExist(String defenseLauncherId,
			String launcherId) {
		// TODO Auto-generated method stub
		
	}
	
	
}
