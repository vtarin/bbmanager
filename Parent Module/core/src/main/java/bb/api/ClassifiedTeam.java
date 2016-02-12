package bb.api;

public class ClassifiedTeam {
    public int points;
    public Team team;

    public ClassifiedTeam(Team team) {
        this.team = team;
        points = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Team) {
            return this.team.equals(obj);
        } else {
            return super.equals(obj);
        }

    }
}
