package bb.api;

public class Match {
	public Team homeTeam;
	public Team awayTeam;
	public MatchResult result = null;
	public boolean played;

	public Match(Team home, Team away) {
		this.result = new MatchResult();
		homeTeam = home;
		awayTeam = away;
	}


}