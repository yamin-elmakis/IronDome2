package TzukEitan.listeners;

import java.sql.Timestamp;

import TzukEitan.war.WarStatistics;

public interface IGetWarStatistics {

	public WarStatistics getWarStatistics(Timestamp startTime, Timestamp endTime);
}
