package bb.api;

import org.junit.Test;

public class BergerTablesMatchSchedulingAlgorithmTest {

	@Test
	public void testSchedule() {
		BBApi api = new BBApi();
		Season season = api.createNewSeason();
		League league = api.createNewLeague(8,true, season);
		api.populateLeagueWithBotTeams(league);
		MatchSchedulingAlgorithm scheduler  =  new BergerTablesMatchSchedulingAlgorithm();
		scheduler.schedule(league);
		System.out.println(league.matchDays.toString());
	}

}
