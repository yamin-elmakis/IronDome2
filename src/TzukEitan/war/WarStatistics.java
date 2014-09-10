package TzukEitan.war;

public class WarStatistics {
	private long numOfLaunchMissiles;
	private long numOfInterceptMissiles;
	private long numOfHitTargetMissiles;
	private long numOfLaunchersDestroyed;
	private long totalDamage;
	
	public WarStatistics(){
		numOfLaunchMissiles = 0;
		numOfInterceptMissiles = 0;
		numOfHitTargetMissiles = 0;
		numOfLaunchersDestroyed = 0;
		totalDamage = 0;
	}
	
	public synchronized void increaseNumOfLaunchMissiles(){
		numOfLaunchMissiles++;
	}
	
	public synchronized void increaseNumOfInterceptMissiles(){
		numOfInterceptMissiles++;
	}
	
	public synchronized void increaseNumOfHitTargetMissiles(){
		numOfHitTargetMissiles++;
	}
	
	public synchronized void increaseNumOfLauncherDestroyed(){
		numOfLaunchersDestroyed++;
	}
	
	public synchronized void increaseTotalDamage(int damage){
		totalDamage += damage;
	}
	
	public long getNumOfLaunchMissiles(){
		return numOfLaunchMissiles;
	}
	
	public long getNumOfInterceptMissiles(){
		return numOfInterceptMissiles;
	}
	
	public long getNumOfHitTargetMissiles(){
		return numOfHitTargetMissiles;
	}
	
	public long getNumOfLaunchersDestroyed(){
		return numOfLaunchersDestroyed;
	}
	
	public long getTotalDamage(){
		return totalDamage;
	}
	
	
	public long[] statisticsToArray(){
		long[] arr = new long[5];
		arr[0] = numOfLaunchMissiles;
		arr[1] = numOfInterceptMissiles;
		arr[2] = numOfHitTargetMissiles;
		arr[3] = numOfLaunchersDestroyed;
		arr[4] = totalDamage;
		
		return arr;
	}
}
