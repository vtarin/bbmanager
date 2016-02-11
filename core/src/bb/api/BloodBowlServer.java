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
        teamPool.add(api.generateBotTeam());
        teamPool.add(api.generateBotTeam());
        teamPool.add(api.generateBotTeam());

        Season season = api.createNewSeason();

        League league = api.createNewLeague(4, true, season);

        for (Team team : teamPool) {
            api.addTeamToLeague(team, league);
        }

        api.generateMatchDaysForLeague(league);
        
        //------MAIN LOOP----------------------------------------------
        
        while (!league.finished) {
            api.performNextMatchDayLeagueMatches(league);

            MatchDay lastPerformedMatchDay = api.getLastPerformedMatchDay(league);
            
            String headlinesForMatchDay = api.generateHeadlinesForMatchDay(lastPerformedMatchDay);
            System.out.println(headlinesForMatchDay);
        }

    }
}
