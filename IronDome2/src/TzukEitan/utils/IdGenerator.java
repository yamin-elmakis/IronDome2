package TzukEitan.utils;

public class IdGenerator {
	private static int ironDomeId = 201;
	private static int enemyLauncherId = 101;
	private static int defenseLauncherDestractorId = 301;
	private static int enemyMissileId = 1;
	private static int defenseMissileId = 1001;
	private static int defenseDestractorLauncherMissileId = 2001;

	private static int maxIronDomeId = -1;
	private static int maxEnemyLauncherId = -1;
	private static int maxEnemyMissileId = -1;

	public static synchronized String ironDomeIdGenerator() {
		return "D" + ironDomeId++;
	}

	public static synchronized String enemyLauncherIdGenerator() {
		return "L" + enemyLauncherId++;
	}

	public static synchronized String defenseLauncherDestractorIdGenerator(char type) {
		return ("" + type).toUpperCase() + defenseLauncherDestractorId++;
	}

	public static synchronized String enemyMissileIdGenerator() {
		return "M" + enemyMissileId++;
	}

	public static synchronized String defensMissileIdGenerator() {
		return "DM" + defenseMissileId++;
	}

	public static synchronized String defenseDestractorLauncherMissileIdGenerator(char t) {
		return t + "M" + defenseDestractorLauncherMissileId++;
	}

	// Iron Dome:
	/** Used for sync xml id's and the program id's **/
	public static void updateIronDomeId(String id) {
		String temp = id.substring(2, id.length());
		try {
			ironDomeId = Integer.parseInt(temp);
			maxIronDomeId = (ironDomeId > maxIronDomeId) ? ironDomeId
					: maxIronDomeId;
		} catch (NumberFormatException e) {
			e.getStackTrace();
		}
	}

	/** Used for updated the max id **/
	public static synchronized void updateFinalIronDomeId() {
		ironDomeId = ++maxIronDomeId;
	}

	// Enemy Missile:
	/** Used for sync xml id's and the program id's **/
	public static synchronized void updateEnemyMissileId(String id) {
		String temp = id.substring(1, id.length());
		try {
			enemyMissileId = Integer.parseInt(temp);
			if (enemyMissileId > maxEnemyMissileId)
				maxEnemyMissileId = enemyMissileId;
		} catch (NumberFormatException e) {
			e.getStackTrace();
		}
	}

	/** Used for updated the max id **/
	public static synchronized void updateFinalEnemyMissileId() {
		enemyMissileId = ++maxEnemyMissileId;
	}

	// EnemyLauncher:
	/** Used for sync xml id's and the program id's **/
	public static synchronized void updateEnemyLauncherId(String id) {
		String temp = id.substring(1, id.length());
		try {
			enemyLauncherId = Integer.parseInt(temp);
			maxEnemyLauncherId = (enemyLauncherId > maxEnemyLauncherId) ? enemyLauncherId
					: maxEnemyLauncherId;
		} catch (NumberFormatException e) {
			e.getStackTrace();
		}
	}

	/** Used for updated the max id **/
	public static void updateFinalEnemyLauncherId() {
		enemyLauncherId = ++maxEnemyLauncherId;
	}

}
