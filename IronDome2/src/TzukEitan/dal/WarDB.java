package TzukEitan.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import TzukEitan.listeners.IGetWarStatistics;
import TzukEitan.listeners.WarEventListener;
import TzukEitan.war.War;
import TzukEitan.war.WarStatistics;

public class WarDB implements WarEventListener, IGetWarStatistics{

	private Connection connection;
	
	private final String LAUNCHER = "Launcher";
	private final String MISSILE = "Missile";
	private final String IRON_DOME = "Iron_Dome";
	private final String DEFENCE_MISSILE = "Defense_Missile";
	private final String LAUNCHER_DESTRUCTOR = "Launcher_Destructor";
	private final String DEFENSE_DESTRUCTOR_MISSILE = "Defense_Destructor_Missile";
	
	// TODO move to singleton
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
		initDB();
	}
	
	private void initDB() {

		String createLauncherTable = "CREATE TABLE IF NOT exists "+LAUNCHER+" (LauncherID varchar(64) NOT NULL,"
				+ " isHidden tinyint(1), "
				+ " createdAt TIMESTAMP NULL DEFAULT NULL, "
				+ "PRIMARY KEY (LauncherID))";
		String createMissileTable = "CREATE TABLE IF NOT exists "+MISSILE+" (MissileID varchar(64) NOT NULL, "
				+ "LauncherID varchar(64) NOT NULL, "
				+ "destination varchar(64), "
				+ "didHit tinyint(1), "
				+ "launchTime TIMESTAMP NULL DEFAULT NULL, "
				+ "damage int, "
				+ "interceptedBy varchar(64), "
				+ "PRIMARY KEY (MissileID), "
				+ "FOREIGN KEY (LauncherID) REFERENCES "+LAUNCHER+"(LauncherID))";
		String createIronDomeTable = "CREATE TABLE IF NOT exists "+IRON_DOME+" (IronDomeID varchar(64) NOT NULL, "
				+ " createdAt TIMESTAMP NULL DEFAULT NULL, "
				+ "PRIMARY KEY (IronDomeID))";
		String createDefenseMissileTable = "CREATE TABLE IF NOT exists "+DEFENCE_MISSILE+" (DefenseMissileID varchar(64) NOT NULL, "
				+ "IronDomeID varchar(64) NOT NULL, "
				+ "MissileID varchar(64) NOT NULL, "
				+ "launchTime TIMESTAMP NULL DEFAULT NULL, "
				+ "didHit tinyint(1), "
				+ "PRIMARY KEY (DefenseMissileID), "
				+ "FOREIGN KEY (IronDomeID) REFERENCES "+IRON_DOME+"(IronDomeID), "
				+ "FOREIGN KEY (MissileID) REFERENCES "+MISSILE+"(MissileID))";
		String createLauncherDestructorTable = "CREATE TABLE IF NOT exists "+LAUNCHER_DESTRUCTOR+" (LauncherDestructorID varchar(64) NOT NULL,"
				+ " type varchar(12) NOT NULL, "
				+ " createdAt TIMESTAMP NULL DEFAULT NULL, "
				+ "PRIMARY KEY (LauncherDestructorID))";
		String createDefenseDestructorMissileTable = "CREATE TABLE IF NOT exists "+DEFENSE_DESTRUCTOR_MISSILE+" (DefenseDestructorMissileID varchar(64) NOT NULL, "
				+ "LauncherDestructorID varchar(64) NOT NULL, "
				+ "LauncherID varchar(64) NOT NULL, "
				+ "launchTime TIMESTAMP NULL DEFAULT NULL, "
				+ "didHit tinyint(1), "
				+ "PRIMARY KEY (DefenseDestructorMissileID), "
				+ "FOREIGN KEY (LauncherDestructorID) REFERENCES "+LAUNCHER_DESTRUCTOR+"(LauncherDestructorID), "
				+ "FOREIGN KEY (LauncherID) REFERENCES "+LAUNCHER+"(LauncherID))";
		
		String delLauncherTable = "DROP TABLE IF exists "+LAUNCHER;
		String delMissileTable = "DROP TABLE IF exists "+MISSILE;
		String delIronDomeTable = "DROP TABLE IF exists "+IRON_DOME;
		String delDefenseMissileTable = "DROP TABLE IF exists "+DEFENCE_MISSILE;
		String delLauncherDestructorTable = "DROP TABLE IF exists "+LAUNCHER_DESTRUCTOR;
		String delDefenseDestructorMissileTable = "DROP TABLE IF exists "+DEFENSE_DESTRUCTOR_MISSILE;
		
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute(delDefenseDestructorMissileTable);
			statement.execute(delDefenseMissileTable);
			statement.execute(delLauncherDestructorTable);
			statement.execute(delIronDomeTable);
			statement.execute(delMissileTable);
			statement.execute(delLauncherTable);
			// TODO delete this 
			System.out.println("droping tables done");
			statement.execute(createLauncherTable);
			statement.execute(createMissileTable);
			statement.execute(createIronDomeTable);
			statement.execute(createDefenseMissileTable);
			statement.execute(createLauncherDestructorTable);
			statement.execute(createDefenseDestructorMissileTable);
			System.out.println("dreate tables done");
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
			System.out.println(e.getMessage()+ " ");
			e = e.getNextException();
		}
	}
	
	@Override
<<<<<<< HEAD
	public WarStatistics getWarStatistics() {
		WarStatistics statistics = new WarStatistics();
		
		Timestamp start = new Timestamp(System.currentTimeMillis() - 20 * 1000);
		Timestamp end = new Timestamp(System.currentTimeMillis());
		statistics.setNumOfLaunchMissiles(getNumOfLaunchMissiles(start, end));
		statistics.setNumOfLaunchersDestroyed(getNumOfLaunchersDestroyed(start, end));
		statistics.setNumOfInterceptMissiles(getNumOfInterceptMissiles(start, end));
		statistics.setNumOfHitTargetMissiles(getNumOfHitTargetMissiles(start, end));
		statistics.setTotalDamage(getTotalDamage(start, end));	
		
		System.out.println("min: " + start.getMinutes());
		System.out.println("sec: " + start.getSeconds());
=======
	public WarStatistics getWarStatistics(Timestamp startTime, Timestamp end) {
		WarStatistics statistics = new WarStatistics();
		
		statistics.setNumOfLaunchMissiles(getNumOfLaunchMissiles(startTime , end));
		statistics.setNumOfLaunchersDestroyed(getNumOfLaunchersDestroyed(startTime , end));
		statistics.setNumOfInterceptMissiles(getNumOfInterceptMissiles(startTime , end));
		statistics.setNumOfHitTargetMissiles(getNumOfHitTargetMissiles(startTime , end));
		statistics.setTotalDamage(getTotalDamage(startTime , end));	
		
>>>>>>> ddbb993c0b24229c0415f43ea8f49a234597e23c
		return statistics;
	}

	@Override
	public void enemyAddedLauncher(String launcherId, boolean visible) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("INSERT INTO "+LAUNCHER+" VALUES(?, ?, ?)");
			statement.setString(1, launcherId);
			statement.setBoolean(2, visible);
<<<<<<< HEAD
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
=======
			statement.setTimestamp(3,  new Timestamp(System.currentTimeMillis()));
>>>>>>> ddbb993c0b24229c0415f43ea8f49a234597e23c
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
			statement = connection.prepareStatement("INSERT INTO "+MISSILE+" (MissileID, LauncherID, destination, launchTime, damage) VALUES(?, ?, ?, ?, ?)");
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
			statement = connection.prepareStatement("UPDATE "+MISSILE+" SET didHit = ?, interceptedBy = ? WHERE MissileID = ?");
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
			statement = connection.prepareStatement("UPDATE "+MISSILE+" SET didHit = ?, interceptedBy = ? WHERE MissileID = ?");
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
			statement = connection.prepareStatement("INSERT INTO "+LAUNCHER_DESTRUCTOR+" VALUES(?, ?, ?)");
			statement.setString(1, ldId);
			statement.setString(2, type);
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
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
			statement = connection.prepareStatement("INSERT INTO "+IRON_DOME+" VALUES(?, ?)");
			statement.setString(1, ironDomeId);
			statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
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
			statement = connection.prepareStatement("INSERT INTO "+DEFENCE_MISSILE+" (DefenseMissileID, IronDomeID, MissileID, launchTime) VALUES(?, ?, ?, ?)");
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
			statement = connection.prepareStatement("INSERT INTO "+DEFENSE_DESTRUCTOR_MISSILE+" (DefenseDestructorMissileID, LauncherDestructorID, LauncherID, launchTime) VALUES(?, ?, ?, ?)");
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
		try (PreparedStatement statement = connection.prepareStatement("UPDATE "+DEFENCE_MISSILE+" SET didHit = ? WHERE MissileID = ? AND DefenseMissileID = ?")){
			statement.setBoolean(1, true);
			statement.setString(2, enemyMissileId);
			statement.setString(3, interceptorId);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		}// statement is auto closed here, even if SQLException is thrown
		
		// update enemy missile table
		try (PreparedStatement statement = connection.prepareStatement("UPDATE "+MISSILE+" SET didHit = ? , damage = ?, interceptedBy = ? WHERE MissileID = ?")){
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
		try (PreparedStatement statement = connection.prepareStatement("UPDATE "+DEFENCE_MISSILE+" SET didHit = ? WHERE MissileID = ? AND DefenseMissileID = ?")){
			statement.setBoolean(1, false);
			statement.setString(2, enemyMissileId);
			statement.setString(3, interceptorId);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		}// statement is auto closed here, even if SQLException is thrown
	}
	
	@Override
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId, String Type, String id, String enemyLauncherId) {
		try (PreparedStatement statement = connection.prepareStatement("UPDATE "+DEFENSE_DESTRUCTOR_MISSILE+" SET didHit = ? WHERE DefenseDestructorMissileID = ?")){
			statement.setBoolean(1, true);
			statement.setString(2, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		}// statement is auto closed here, even if SQLException is thrown
	}
	
	@Override
	public void defenseMissInterceptionLauncher(String whoLaunchedMeId, String Type, String id, String enemyLauncherId) {
		try (PreparedStatement statement = connection.prepareStatement("UPDATE "+DEFENSE_DESTRUCTOR_MISSILE+" SET didHit = ? WHERE DefenseDestructorMissileID = ?")){
			statement.setBoolean(1, false);
			statement.setString(2, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		}// statement is auto closed here, even if SQLException is thrown
	}
	
	@Override
	public void defenseMissInterceptionHiddenLauncher(String whoLaunchedMeId, String type, String enemyLauncherId) { 
		try (PreparedStatement statement = connection.prepareStatement("UPDATE "+DEFENSE_DESTRUCTOR_MISSILE+" SET didHit = ? WHERE LauncherDestructorID = ? AND LauncherID = ?")){
			statement.setBoolean(1, false);
			statement.setString(2, whoLaunchedMeId);
			statement.setString(3, enemyLauncherId);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLErrors(e);
		}// statement is auto closed here, even if SQLException is thrown
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
	
	public void warHasBeenStarted() {	/** Not needed */ }

	@Override
	public void noSuchObject(String type) { /** Not needed */	}
	
	
	private int getNumOfLaunchMissiles(Timestamp startTime, Timestamp endTime) {
		PreparedStatement statement = null;
		try {
<<<<<<< HEAD
			statement = connection.prepareStatement("SELECT count(*) as count FROM "+MISSILE +" WHERE launchTime BETWEEN (?, ?)");
=======
			statement = connection.prepareStatement("SELECT count(*) FROM "+MISSILE +" WHERE launchTime BETWEEN ? AND ?");
>>>>>>> ddbb993c0b24229c0415f43ea8f49a234597e23c
			statement.setTimestamp(1, startTime);
			statement.setTimestamp(2, endTime);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
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
		return 0;
	}
	
	private int getNumOfHitTargetMissiles(Timestamp startTime, Timestamp endTime) {
		PreparedStatement statement = null;
		try {
<<<<<<< HEAD
			statement = connection.prepareStatement("SELECT count(*) FROM "+MISSILE+" WHERE didHit = ? AND launchTime BETWEEN (?, ?)");
=======
			statement = connection.prepareStatement("SELECT count(*) FROM "+MISSILE+" WHERE didHit = ? AND launchTime BETWEEN ? AND ?");
>>>>>>> ddbb993c0b24229c0415f43ea8f49a234597e23c
			statement.setBoolean(1, true);
			statement.setTimestamp(2, startTime);
			statement.setTimestamp(3, endTime);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
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
		return 0;
	}
	
	private int getNumOfLaunchersDestroyed(Timestamp startTime, Timestamp endTime) {
		PreparedStatement statement = null;
		try {
<<<<<<< HEAD
			statement = connection.prepareStatement("SELECT count(*) FROM "+DEFENSE_DESTRUCTOR_MISSILE+" WHERE didHit = ? AND launchTime BETWEEN (?, ?)");
=======
			statement = connection.prepareStatement("SELECT count(*) FROM "+DEFENSE_DESTRUCTOR_MISSILE+" WHERE didHit = ? AND launchTime BETWEEN ? AND ?");
>>>>>>> ddbb993c0b24229c0415f43ea8f49a234597e23c
			statement.setBoolean(1, true);
			statement.setTimestamp(2, startTime);
			statement.setTimestamp(3, endTime);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
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
		return 0;
	}
	
	private int getTotalDamage(Timestamp startTime, Timestamp endTime) {
		PreparedStatement statement = null;
		try {
<<<<<<< HEAD
			statement = connection.prepareStatement("SELECT sum(damage) FROM "+MISSILE +" WHERE didHit = ?AND launchTime BETWEEN (?, ?)");
=======
			statement = connection.prepareStatement("SELECT sum(damage) FROM "+MISSILE +" WHERE didHit = ? AND launchTime BETWEEN ? AND ?");
>>>>>>> ddbb993c0b24229c0415f43ea8f49a234597e23c
			statement.setBoolean(1, true);
			statement.setTimestamp(2, startTime);
			statement.setTimestamp(3, endTime);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
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
		return 0;
	}
	
	private int getNumOfInterceptMissiles(Timestamp startTime, Timestamp endTime) {
		PreparedStatement statement = null;
		try {
<<<<<<< HEAD
			statement = connection.prepareStatement("SELECT count(*) FROM " + DEFENCE_MISSILE + " WHERE didHit = ? AND launchTime BETWEEN (?, ?)");
=======
			statement = connection.prepareStatement("SELECT count(*) FROM " + DEFENCE_MISSILE + " WHERE didHit = ? AND launchTime BETWEEN ? AND ?");
>>>>>>> ddbb993c0b24229c0415f43ea8f49a234597e23c
			statement.setBoolean(1, true);
			statement.setTimestamp(2, startTime);
			statement.setTimestamp(3, endTime);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
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
		return 0;
	}
}
