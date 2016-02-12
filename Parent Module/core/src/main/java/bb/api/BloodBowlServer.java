package bb.api;


import java.util.ArrayList;
import java.util.List;

public class BloodBowlServer {


    public static void main(String[] args) {

        //----INITIALISATION---------------------------------------------- 

        BBApi api = new BBApi();

        List<User> userPool = new ArrayList<>();
        List<Team> teamPool = new ArrayList<>();

        userPool.add(api.createNewUser("Vicente", "vicente@mail.com"));
        teamPool.add(api.createNewTeamForUser(userPool.get(0), "Vicente's Finest", Race.ORK));
        Season season = api.createNewSeason();
        League league = api.createNewLeague(10, false, season);
        League league2 = api.createNewLeague(10, false, season);

        for (Team team : teamPool) {
            api.addTeamToLeague(team, league);
        }

        api.populateLeagueWithBotTeams(league);
        api.populateLeagueWithBotTeams(league2);
        api.generateMatchDaysForLeague(league);
        api.generateMatchDaysForLeague(league2);

        //------MAIN LOOP----------------------------------------------

        while (!season.finished) {
            api.runSeason(season);
            System.out.println(api.generateHeadlinesForMatchDay(api.getLastPerformedMatchDay(league)));
            System.out.println(api.generateHeadlinesForMatchDay(api.getLastPerformedMatchDay(league2)));
            System.out.println(api.renderLeagueTable(league));
            System.out.println(api.renderLeagueTable(league2));
        }
    }
}
