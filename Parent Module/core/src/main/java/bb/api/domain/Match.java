package bb.api.domain;

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

    @Override
    public String toString() {
        return "\n" + homeTeam +
                " " + result + " " + awayTeam +
                (!played ? " NOT PLAYED " : " PLAYED ")
                ;
    }
}
