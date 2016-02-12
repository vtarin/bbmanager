package bb.api;

import bb.api.domain.*;
import org.junit.Test;

public class WalkthroughTest {

    @Test
    public void newUserTest() {
        BBApi api = new BBApi();
        String username = "username";
        String email = "email";
        String teamName = "teamName";
        Race teamRace = Race.HUMAN;

        User newUser = api.createNewUser(username, email);
        Team newTeam = api.createNewTeamForUser(newUser, teamName, teamRace);
        api.populateTeamWithGeneratedPlayers(newTeam, 11);
    }

    @Test
    public void mainLoopTest() {

        BBApi api = new BBApi();
        Season season1 = api.createNewSeason();
        League league1 = api.createNewLeague(10, true, season1);
        Team newTeam = new Team();

        api.addTeamToLeague(newTeam, league1);
        api.populateLeagueWithBotTeams(league1);
        api.runSeason(season1);

    }

    @Test
    public void teamRosterSelectionTest() {

        BBApi api = new BBApi();
        Team newTeam = new Team();
        api.populateTeamWithGeneratedPlayers(newTeam, 11);
        Player playerOne = new Player();

        Roster roster = api.getDefaultTeamRoster(newTeam);
        api.populateTeamRoster(newTeam, roster);
        Player playerToRemove = api.getNthPlayerFromRoster(0, roster);
        api.switchPlayersOnRoster(newTeam, playerOne, playerToRemove, roster);

    }

    @Test
    public void tacticsConfigurationTest() {
        BBApi api = new BBApi();
        Team newTeam = new Team();
        Player player = new Player();
        int passing = 10, violence = 30, scoring = 40, position = 10;

        Tactic teamTactic = api.getDefaultTactic();

        Tactic playerTactic = api.getDefaultTactic();

        api.changeTacticConfiguration(teamTactic, passing, violence, scoring,
                position);

        api.changeTacticConfiguration(playerTactic, passing, violence, scoring,
                position);

        Tactic newTactic = new Tactic(10, 10, 80, 80);

        api.changeTacticConfiguration(newTactic, passing, violence, scoring,
                position);

        api.setTacticForPlayer(newTactic, player);

        api.setTacticForTeam(newTactic, newTeam);

    }

    @Test
    public void runLeagueTest() {
        BBApi api = new BBApi();
        Season season1 = api.createNewSeason();
        League league1 = api.createNewLeague(10, true, season1);
        api.populateLeagueWithBotTeams(league1);
        api.generateMatchDaysForLeague(league1);
        api.runLeague(league1);
    }

    @Test
    public void matchDayResultsTest() {
        BBApi api = new BBApi();
        Season season1 = api.createNewSeason();
        League league1 = api.createNewLeague(10, true, season1);
        api.populateLeagueWithBotTeams(league1);
        api.generateMatchDaysForLeague(league1);
        api.runLeague(league1);
        MatchDay matchDay = api.getLastPerformedMatchDay(league1);
        String headlines = api.generateHeadlinesForMatchDay(matchDay);
        System.out.print(headlines);
    }

    @Test
    public void leagueManagementTest() {
        BBApi api = new BBApi();
        Season season1 = api.createNewSeason();

        League division1_1 = api.createNewLeague(4, true, season1);
        League division2_1 = api.createNewLeague(4, true, season1);

        api.populateLeagueWithBotTeams(division1_1);
        api.populateLeagueWithBotTeams(division2_1);


        api.setLeagueHierarchy(division1_1, division2_1);

        api.generateMatchDaysForLeague(division1_1);
        api.generateMatchDaysForLeague(division2_1);

        Team team = new Team();
        team.name = "No BOT Team";
        team.race = Race.HUMAN;
        team.bot = false;

        api.replaceBotTeam(team, division1_1);

        while (!season1.finished) {
            api.runSeason(season1);
        }
        System.out.print(api.renderLeagueTable(division1_1));
        System.out.print(api.renderLeagueTable(division2_1));

        api.performTeamAscensionsAndDescensions(division1_1, division2_1);

        System.out.print(api.renderLeagueTable(division1_1));
        System.out.print(api.renderLeagueTable(division2_1));

    }

    @Test
    public void matchDayGenerationTest() {
        BBApi api = new BBApi();
        Season season1 = api.createNewSeason();
        League division1_1 = api.createNewLeague(4, false, season1);
        api.populateLeagueWithBotTeams(division1_1);
        api.generateMatchDaysForLeague(division1_1);


        while (!season1.finished) {
            printDebug(api, division1_1);
            debugMatchDays(division1_1);
            api.runSeason(season1);
        }


    }

    @Test
    public void botTeamReplacementTest() {
        BBApi api = new BBApi();
        Season season1 = api.createNewSeason();

        League division1_1 = api.createNewLeague(4, false, season1);
        api.populateLeagueWithBotTeams(division1_1);
        api.generateMatchDaysForLeague(division1_1);

        Team team = new Team();
        team.name = "No BOT Team";
        team.race = Race.HUMAN;
        team.bot = false;

        api.runSeason(season1);
        printDebug(api, division1_1);

        api.replaceBotTeam(team, division1_1);

        while (!season1.finished) {
            api.runSeason(season1);
            printDebug(api, division1_1);
        }
        printDebug(api, division1_1, true);

    }

    protected void debugMatchDays(League league) {
        int i = 0;

        for (MatchDay md : league.matchDays) {
            System.out.println("***** Match Day #" + (i++) + " *******");
            for (Match m : md.matches) {
                System.out.println(m.homeTeam + " vs " + m.awayTeam + " " + (m.played ? " PLAYED [" + m.result.scoreHome + "-" + m.result.scoreAway + "]" : ""));
            }
        }
    }

    protected void printDebug(BBApi api, League league) {
        printDebug(api, league, false);
    }

    protected void printDebug(BBApi api, League league, boolean showClassification) {
        System.out.print("*************************************\n");
        System.out.print(api.generateHeadlinesForMatchDay(api.getLastPerformedMatchDay(league)));
        if (showClassification) {
            System.out.print(api.renderLeagueTable(league));
        }

    }
}
