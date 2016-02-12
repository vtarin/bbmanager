package bb.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Team {
    public boolean bot;
    public Race race;
    public Roster defaultRoster = new Roster();
    public String name;
    public Collection<Roster> rosters = new ArrayList<Roster>();
    public Tactic tactic;
    public List<Player> players = new ArrayList<Player>();

    public Roster getDefaultRoster() {
        return defaultRoster;
    }

    public void setDefaultRoster(Roster defaultRoster) {
        this.defaultRoster = defaultRoster;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Team) {
            return name.equals(((Team) obj).name);
        } else if (obj instanceof ClassifiedTeam) {
            return name.equals(((ClassifiedTeam) obj).team.name);
        }
        return false;

    }

    public void copyOver(Team team) {
        team.name = name;
        team.race = race;
        team.players = players;
        team.defaultRoster = defaultRoster;
        team.rosters = rosters;
        team.tactic = tactic;
        team.bot = bot;
    }

    @Override
    public String toString() {
        return name + "(" + (bot ? "bot" : "") + "," + race + ")";
    }
}
