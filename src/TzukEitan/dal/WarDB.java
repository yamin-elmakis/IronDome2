package TzukEitan.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.war.War;

public class WarDB implements WarEventListener{

	private Connection connection;
	
	public WarDB(War warModel) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection("jdbc:mysql://localhost/TzoukEitan", "root", "");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			printSQLErrors(e);
		}
		warModel.registerListenerts(this);
	}
	
	private void initDB() {

		String createLauncherTable = "CREATE TABLE IF NOT exists Launcher (LauncherID varchar(64) NOT NULL,"
				+ " isHidden tinyint(1), PRIMARY KEY (LauncherID))";
		String createMissileTable = "CREATE TABLE IF NOT exists Missile (MissileID varchar(64) NOT NULL, "
				+ "LauncherID varchar(64) NOT NULL, "
				+ "destination varchar(64), "
				+ "didHit tinyint(1), "
				+ "launchTime TIMESTAMP NULL DEFAULT NULL, "
				+ "damage int, "
				+ "interceptedBy varchar(64), "
				+ "PRIMARY KEY (MissileID), "
				+ "FOREIGN KEY (LauncherID) REFERENCES Launcher(LauncherID))";
		String createIronDomeTable = "CREATE TABLE IF NOT exists IronDome (IronDomeID varchar(64) NOT NULL, PRIMARY KEY (IronDomeID))";
		String createDefenseMissileTable = "CREATE TABLE IF NOT exists DefenseMissile (DefenseMissileID varchar(64) NOT NULL, "
				+ "IronDomeID varchar(64) NOT NULL, "
				+ "MissileID varchar(64) NOT NULL, "
				+ "launchTime TIMESTAMP NULL DEFAULT NULL, "
				+ "didHit tinyint(1), "
				+ "PRIMARY KEY (DefenseMissileID), "
				+ "FOREIGN KEY (IronDomeID) REFERENCES IronDome(IronDomeID), "
				+ "FOREIGN KEY (MissileID) REFERENCES Missile(MissileID))";
		String createLauncherDestructorTable = "CREATE TABLE IF NOT exists LauncherDestructor (LauncherDestructorID varchar(64) NOT NULL,"
				+ " type varchar(12) NOT NULL, PRIMARY KEY (LauncherDestructorID))";
		String createDefenseDestructorMissileTable = "CREATE TABLE IF NOT exists DefenseDestructorMissile (DefenseDestructorMissileID varchar(64) NOT NULL, "
				+ "LauncherDestructorID varchar(64) NOT NULL, "
				+ "LauncherID varchar(64) NOT NULL, "
				+ "launchTime TIMESTAMP NULL DEFAULT NULL, "
				+ "didHit tinyint(1), "
				+ "PRIMARY KEY (DefenseDestructorMissileID), "
				+ "FOREIGN KEY (LauncherDestructorID) REFERENCES LauncherDestructor(LauncherDestructorID), "
				+ "FOREIGN KEY (LauncherID) REFERENCES Launcher(LauncherID))";
		
		String delLauncherTable = "DROP TABLE IF exists Launcher";
		String delMissileTable = "DROP TABLE IF exists Missile";
		String delIronDomeTable = "DROP TABLE IF exists IronDome";
		String delDefenseMissileTable = "DROP TABLE IF exists DefenseMissile";
		String delLauncherDestructorTable = "DROP TABLE IF exists LauncherDestructor";
		String delDefenseDestructorMissileTable = "DROP TABLE IF exists DefenseDestructorMissile";
		
		Statement statement = null;
		try {
			statement = connection.createStatement();
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
			printSQLErrors(e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				printSQLErrors(e);
			}
		}
	}

	private void printSQLErrors(SQLException e) {
		while (e != null) {
			System.out.println(e.getMessage());
			e = e.getNextException();
		}
	}

	@Override
	public void warHasBeenStarted() {
		initDB();
	}

	@Override
	public void enemyAddedLauncher(String launcherId, boolean visible) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("INSERT INTO launcher VALUES(?, ?)");
			statement.setString(1, launcherId);
			statement.setBoolean(2, visible);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		} finally {
			try {
				if (statement != null) 
					statement.close();
			} catch (SQLException e) {
				printSQLErrors(e);
			}
		}
	}

	@Override
	public void enemyLaunchMissile(String myMunitionsId, String missileId, String destination, int damage) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("INSERT INTO missile (MissileID, LauncherID, destination, launchTime, damage) VALUES(?, ?, ?, ?, ?)");
			statement.setString(1, missileId);
			statement.setString(2, myMunitionsId);
			statement.setString(3, destination);
			statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			statement.setInt(5, damage);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		} finally {
			try {
				if (statement != null) 
					statement.close();
			} catch (SQLException e) {
				printSQLErrors(e);
			}
		}
	}
	
	@Override
	public void enemyHitDestination(String whoLaunchedMeId, String missileId, String destination, int damage, String launchTime) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("UPDATE missile SET didHit = ?, interceptedBy = ? WHERE MissileID = ?");
			statement.setBoolean(1, true);
			statement.setString(2, "not intercepted");
			statement.setString(3, missileId);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		} finally {
			try {
				if (statement != null) 
					statement.close();
			} catch (SQLException e) {
				printSQLErrors(e);
			}
		}
	}
	
	@Override
	public void enemyMissDestination(String whoLaunchedMeId, String missileId, String destination, String launchTime) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("UPDATE missile SET didHit = ?, interceptedBy = ? WHERE MissileID = ?");
			statement.setBoolean(1, false);
			statement.setString(2, "not intercepted");
			statement.setString(3, missileId);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		} finally {
			try {
				if (statement != null) 
					statement.close();
			} catch (SQLException e) {
				printSQLErrors(e);
			}
		}
	}
	
	@Override
	public void enemyLauncherIsVisible(String id, boolean visible) { /** Not needed */	}

	@Override
	public void missileNotExist(String defenseLauncherId, String enemyId) { /** Not needed */	}

	@Override
	public void enemyLauncherNotExist(String defenseLauncherId, String launcherId) { /** Not needed */	}

	@Override
	public void defenseLauncherDestructorAdded(String ldId, String type) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("INSERT INTO launcherdestructor VALUES(?, ?)");
			statement.setString(1, ldId);
			statement.setString(2, type);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		} finally {
			try {
				if (statement != null) 
					statement.close();
			} catch (SQLException e) {
				printSQLErrors(e);
			}
		}
	}

	@Override
	public void defenseIronDomeAdded(String ironDomeId) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("INSERT INTO IronDome VALUES(?)");
			statement.setString(1, ironDomeId);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		} finally {
			try {
				if (statement != null) 
					statement.close();
			} catch (SQLException e) {
				printSQLErrors(e);
			}
		}
	}

	@Override
	public void defenseLaunchMissile(String myMunitionsId, String missileId, String enemyMissileId) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("INSERT INTO DefenseMissile (DefenseMissileID, IronDomeID, MissileID, launchTime) VALUES(?, ?, ?, ?)");
			statement.setString(1, missileId);
			statement.setString(2, myMunitionsId);
			statement.setString(3, enemyMissileId);
			statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		} finally {
			try {
				if (statement != null) 
					statement.close();
			} catch (SQLException e) {
				printSQLErrors(e);
			}
		}
	}
	
	@Override
	public void defenseLaunchMissile(String myMunitionsId, String type, String missileId, String enemyLauncherId) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("INSERT INTO DefenseDestructorMissile (DefenseDestructorMissileID, LauncherDestructorID, LauncherID, launchTime) VALUES(?, ?, ?, ?)");
			statement.setString(1, missileId);
			statement.setString(2, myMunitionsId);
			statement.setString(3, enemyLauncherId);
			statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		} finally {
			try {
				if (statement != null) 
					statement.close();
			} catch (SQLException e) {
				printSQLErrors(e);
			}
		}
	}
	
	@Override
	public void defenseHitInterceptionMissile(String whoLaunchedMeId, String interceptorId, String enemyMissileId) {
		// update defense missile table 
		try (PreparedStatement statement = connection.prepareStatement("UPDATE defensemissile SET didHit = ? WHERE MissileID = ?")){
			statement.setBoolean(1, true);
			statement.setString(2, interceptorId);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		}// statement is auto closed here, even if SQLException is thrown
		
		// update enemy missile table
		try (PreparedStatement statement = connection.prepareStatement("UPDATE missile SET didHit = ? , damage = ?, interceptedBy = ? WHERE MissileID = ?")){
			statement.setBoolean(1, false);
			statement.setInt(2, 0);
			statement.setString(3, interceptorId);
			statement.setString(4, enemyMissileId);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		}// statement is auto closed here, even if SQLException is thrown
	}
	
	@Override
	public void defenseMissInterceptionMissile(String whoLaunchedMeId, String interceptorId, String enemyMissileId, int damage) {
		try (PreparedStatement statement = connection.prepareStatement("UPDATE defensemissile SET didHit = ? WHERE MissileID = ?")){
			statement.setBoolean(1, false);
			statement.setString(2, interceptorId);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		}// statement is auto closed here, even if SQLException is thrown
	}
	
	@Override
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId, String Type, String id, String enemyLauncherId) {
		try (PreparedStatement statement = connection.prepareStatement("UPDATE defensedestructormissile SET didHit = ? WHERE DefenseDestructorMissileID = ?")){
			statement.setBoolean(1, true);
			statement.setString(2, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		}// statement is auto closed here, even if SQLException is thrown
	}
	@Override
	public void defenseMissInterceptionLauncher(String whoLaunchedMeId, String Type, String id, String enemyLauncherId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void defenseMissInterceptionHiddenLauncher(String whoLaunchedMeId, String type, String enemyLauncherId) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void warHasBeenFinished() { 
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			printSQLErrors(e);
		}
	}
	
	@Override
	public void noSuchObject(String type) { /** Not needed */	}
	
}
