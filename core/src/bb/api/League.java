package bb.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class League {

	public int maxTeams;
	public boolean finished;
	private Collection<League> lowerLeagues = new ArrayList<League>();
	private League upperLeague;
	public List<MatchDay> matchDays=new ArrayList<MatchDay>();
	public List<Team> teams=new ArrayList<Team>();
	public List<ClassifiedTeam> teamClassification=new ArrayList<ClassifiedTeam>();
	public boolean hasSecondLeg;
	public int winPoints = 3;
	public int tiePoints = 1;
	public void addLowerLeague(League league) {
		lowerLeagues.add(league);
	}
	public League getUpperLeague() {
		return upperLeague;
	}
	public Collection<League> getLowerLeagues() {
		return lowerLeagues;
		
	}
	public void setUpperLeague(League upperLeague) {
		this.upperLeague = upperLeague;
	}

}
