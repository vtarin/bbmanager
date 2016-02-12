package bb.api;

import java.util.List;

public class SillyMatchSchedulingAlgorithm implements MatchSchedulingAlgorithm {

    @Override
    public void schedule(League league) {
        List<Team> teams = league.teams;
        int nteams = teams.size();
        int nteamshalf = nteams / 2;

        for (int round = 0; round < nteams - 1; round++) {
            MatchDay md = new MatchDay();
            for (int j = 0; j < nteamshalf; j++) {
                new Match(teams.get(j), teams.get(nteams - 1 - j));
            }

            league.matchDays.add(md);
        }

    }

}
