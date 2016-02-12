package bb.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BBApi {
	private MatchResolver matchResolver = new RandomMatchResolver();
	private PlayerGenerator playerGenerator = new RandomPlayerGenerator();
	private MatchSchedulingAlgorithm matchSchedulingAlgorithm = new BergerTablesMatchSchedulingAlgorithm();
	private long botTeamCounter = System.currentTimeMillis();
	private PlayerPriceCalculator playerPriceCalculator = new RandomPlayerPriceCalculator();
	private Comparator<Bid> bidAmountComparator = new BidAmountComparator();

	public User createNewUser(String username, String email) {
		User u = new User();
		u.name = username;
		u.email = email;
		return u;
	}

	public Team createNewTeamForUser(User newUser, String teamName,
			Race teamRace) {
		Team t = new Team();
		t.race = teamRace;
		t.name = teamName;
		newUser.team = t;
		return t;
	}

	public Player generateNewPlayer(Race race) {
		return playerGenerator.generate(race);
	}

	public void addPlayerToTeam(Team newTeam, Player player) {
		if (!newTeam.players.contains(player)) {
			newTeam.players.add(player);
			player.team = newTeam;
		}
	}

	public void populateTeamWithGeneratedPlayers(Team newTeam, int number) {

		for (int i = 0; i < number; i++) {
			addPlayerToTeam(newTeam, generateNewPlayer(newTeam.race));
		}
	}

	public League createNewLeague(int maxTeams, boolean secondLeg,
			Season season1) {
		League l = new League();
		l.hasSecondLeg = secondLeg;
		l.maxTeams = maxTeams;
		season1.leagues.add(l);
		return l;
	}

	public void addTeamToLeague(Team team, League league) {
		if (league.maxTeams > league.teams.size()) {
			league.teams.add(team);
			league.teamClassification.add(new ClassifiedTeam(team));
		}
	}

	public Team generateBotTeam() {
		Team team = new Team();
		team.name = "bot_team_" + botTeamCounter++;
		team.bot = true;
		team.race = Race.HUMAN;
		return team;
	}

	public void populateLeagueWithBotTeams(League league) {
		int n = league.maxTeams - league.teams.size();
		for (int j = 0; j < n; j++) {
			addTeamToLeague(generateBotTeam(), league);
		}
	}

	public void performNextMatchDayLeagueMatches(League league) {
		MatchDay matchDay = getNextMatchDayForleague(league);
		if (matchDay == null) {
			league.finished = true;
		} else {
			playMatchesForMatchDay(matchDay);
			updateTeamClassificationsForMatchDay(league, matchDay);
		}

	}

	public void updateTeamClassificationsForMatchDay(League league,
			MatchDay matchDay) {
		for (Match m : matchDay.matches) {
			updateTeamsClassificationForMatch(league, m);
		}
	}

	public void updateTeamsClassificationForMatch(League league, Match m) {
		Team homeTeam = m.homeTeam;
		Team awayTeam = m.awayTeam;

		if (m.result.scoreHome > m.result.scoreAway) {// home win
			increaseTeamClassificationPointsInLeague(league, homeTeam,
					league.winPoints);
		} else {
			if (m.result.scoreHome < m.result.scoreAway) {// away win
				increaseTeamClassificationPointsInLeague(league, awayTeam,
						league.winPoints);
			} else {// tie
				increaseTeamClassificationPointsInLeague(league, homeTeam,
						league.tiePoints);
				increaseTeamClassificationPointsInLeague(league, awayTeam,
						league.tiePoints);
			}
		}
	}

	public void increaseTeamClassificationPointsInLeague(League league,
			Team team, int points) {
		ClassifiedTeam teamClassification = getClassificationForTeamInLeague(
				league, team);
		if (teamClassification != null) {
			teamClassification.points += points;
		}
	}

	private ClassifiedTeam getClassificationForTeamInLeague(League league,
			Team team) {
		int index = league.teamClassification.indexOf(team);
		if (index == -1) {
			return null;
		}
		return league.teamClassification.get(index);
	}

	public MatchDay getNextMatchDayForleague(League league) {
		Iterator<MatchDay> ite = league.matchDays.iterator();
		while (ite.hasNext()) {
			MatchDay md = ite.next();
			if (!md.isPlayed()) {
				return md;
			}
		}
		return null;
	}

	public void playMatch(Match match) {
		matchResolver.playMatch(match);
	}

	public void playMatchesForMatchDay(MatchDay matchDay) {
		for (Match match : matchDay.matches) {
			playMatch(match);
		}

	}

	public Season createNewSeason() {
		return new Season();
	}

	public void runSeason(Season season1) {
		season1.finished = true;
		for (League league : season1.leagues) {
			runLeague(league);
			season1.finished = season1.finished && league.finished;
		}
	}

	public void runLeague(League league) {
		if (!league.finished) {
			performNextMatchDayLeagueMatches(league);
		}
	}

	public Roster createNewTeamRoster(Team newTeam) {
		Roster r = new Roster();
		newTeam.rosters.add(r);
		return r;
	}

	public Iterator<Player> getPlayersAvailableToSelectFromTeam(Team newTeam) {
		List<Player> available = new ArrayList<Player>();
		// quitar los lesionados
		for (Player player : newTeam.players) {
			if (!player.isInjured()) {
				available.add(player);
			}
		}

		return available.iterator();
	}

	public void setDefaultTeamRoster(Team newTeam, Roster roster) {
		newTeam.defaultRoster = roster;
	}

	public Roster getDefaultTeamRoster(Team newTeam) {
		if (newTeam.defaultRoster == null) {
			setDefaultTeamRoster(newTeam, createNewTeamRoster(newTeam));
		}
		return newTeam.defaultRoster;
	}

	public void populateTeamRoster(Team newTeam, Roster roster) {

		Iterator<Player> players = getPlayersAvailableToSelectFromTeam(newTeam);
		while (roster.size < 11 && players.hasNext()) {
			Player player = players.next();
			assignPlayerToTeamRoster(player, roster);
		}

	}

	public void assignPlayerToTeamRoster(Player playerOne, Roster roster) {
		if (!roster.players.contains(playerOne) && roster.players.size() < 11) {
			roster.players.add(playerOne);
		}

	}

	public void removePlayerFromTeamRoster(Player playerOne, Roster roster) {
		if (roster.players.contains(playerOne)) {
			roster.players.remove(playerOne);
		}

	}

	public void switchPlayersOnRoster(Team newTeam, Player playerOne,
			Player playerToRemove, Roster roster) {

		removePlayerFromTeamRoster(playerToRemove, roster);

		assignPlayerToTeamRoster(playerOne, roster);

	}

	public Player getNthPlayerFromRoster(int i, Roster roster) {
		return roster.players.get(i);

	}

	public Tactic getDefaultTactic() {
		return new Tactic(30, 30, 30, 50);
	}

	public void setTacticForPlayer(Tactic newTactic, Player player) {
		player.setTactic(newTactic);

	}

	public void setTacticForTeam(Tactic newTactic, Team newTeam) {
		newTeam.tactic = newTactic;

	}

	public void changeTacticConfiguration(Tactic teamTactic, int passing,
			int violence, int scoring, int position) {
		teamTactic.setPassing(passing);
		teamTactic.setViolence(violence);
		teamTactic.setScoring(scoring);
		teamTactic.setPosition(position);

	}

	public MatchDay getLastPerformedMatchDay(League league) {
		Iterator<MatchDay> ite = league.matchDays.iterator();
		MatchDay tmp_md = null;
		while (ite.hasNext()) {
			MatchDay md = ite.next();
			if (md.isPlayed()) {
				tmp_md = md;
			} else {
				return tmp_md;
			}
		}
		return tmp_md;
	}

	public String generateNewsHeadlineForMatch(Match match) {
		StringBuffer headline = new StringBuffer();

		headline.append(match.homeTeam.name).append(" ")
				.append(match.result.scoreHome).append(" vs ")
				.append(match.result.scoreAway).append(" ")
				.append(match.awayTeam.name);
		headline.append("\n");
		/*
		 * headline.append("Top scorers: ").append(match.result.scorersList);
		 * headline.append("\n");
		 * headline.append("Top passers: ").append(match.result.passersList);
		 * headline.append("\n");
		 * headline.append("Top injurers: ").append(match.result.injurersList);
		 * headline.append("\n");
		 * headline.append("Casualties: ").append(match.result.casualtiesList);
		 * headline.append("\n");
		 */
		return headline.toString();
	}

	public Team getHomeTeamForMatch(Match match) {
		return match.homeTeam;
	}

	public String generateHeadlinesForMatchDay(MatchDay matchDay) {
		StringBuffer matchResultNews = new StringBuffer();
		if (matchDay != null) {
			for (Match match : matchDay.matches) {
				matchResultNews.append(generateNewsHeadlineForMatch(match));
			}
		}
		return matchResultNews.toString();
	}

	public void setLeagueHierarchy(League upperLeague, League... lowerLeagues) {
		for (League lowerLeague : lowerLeagues) {
			upperLeague.addLowerLeague(lowerLeague);
		}
	}

	public Collection<Team> getDescendingTeamsFromLeague(League league) {
		Collection<Team> _teams = null;
		_teams = getTeamsInPositionRange(league,
				league.teamClassification.size() - 2,
				league.teamClassification.size());
		return _teams;
	}

	private Collection<Team> getTeamsInPositionRange(League league, int from,
			int to) {
		Collection<Team> _teams;
		if (league.teamClassification.size() > 2) {
			Collection<ClassifiedTeam> teams = league.teamClassification
					.subList(from, to);
			_teams = getAsTeams(teams);
		} else {
			_teams = getAsTeams(league.teamClassification);
		}
		return _teams;
	}

	public Collection<Team> getAscendingTeamsFromLeague(League league) {
		Collection<Team> _teams;
		_teams = getTeamsInPositionRange(league, 0, 2);
		return _teams;
	}

	private Collection<Team> getAsTeams(Collection<ClassifiedTeam> teams) {
		// TODO Esto no es la mejor forma de hacerlo seguramente, revisar
		Collection<Team> _teams = new ArrayList<Team>();
		for (ClassifiedTeam clTeam : teams) {
			_teams.add(clTeam.team);
		}
		return _teams;
	}

	public void performTeamAscensionsAndDescensions(League upperLeague,
			League lowerLeague) {
		if (upperLeague.finished && lowerLeague.finished) {
			Collection<Team> descending = getDescendingTeamsFromLeague(upperLeague);
			Collection<Team> ascending = getAscendingTeamsFromLeague(lowerLeague);

			// FIXME shouldnt we create new Leagues instead / reinitialize
			// leagues?
			for (Team team : descending) {
				removeTeamFromLeague(team, upperLeague);
			}
			for (Team team : ascending) {
				removeTeamFromLeague(team, lowerLeague);
			}

			for (Team team : descending) {
				addTeamToLeague(team, lowerLeague);
			}

			for (Team team : ascending) {
				addTeamToLeague(team, upperLeague);
			}

		}
	}

	private void removeTeamFromLeague(Team team, League league) {
		int index = league.teams.indexOf(team);
		if (index != -1) {
			league.teams.remove(index);
		}

		index = league.teamClassification.indexOf(team);
		if (index != -1) {
			league.teamClassification.remove(index);
		}

	}

	public void generateMatchDaysForLeague(League league) {
		matchSchedulingAlgorithm.schedule(league);
	}

	public String renderLeagueTable(League league) {
		StringBuffer strb = new StringBuffer();
		Collections.sort(league.teamClassification,
				new TeamClassificationComparator());
		for (ClassifiedTeam team : league.teamClassification) {
			strb.append(team.team.name).append(" ").append(team.points)
					.append(" points");
			strb.append("\n");
		}
		return strb.toString();
	}

	public void replaceBotTeam(Team team, League league) {
		Team botTeam = findBotTeamInLeague(league);
		team.copyOver(botTeam);
	}

	private Team findBotTeamInLeague(League league) {
		ListIterator<Team> teams = league.teams.listIterator();
		Team t = null;
		do {
			t = teams.next();
		} while (!t.bot && teams.hasNext());

		return t;
	}

	public PlayerMarket createPlayerMarket() {
		return new PlayerMarket();
	}

	public MarketFilter getRaceMarketFilter(Race race) {
		MarketFilter filter = new MarketFilter(race);

		return filter;
	}

	public List<PlayerOnMarket> getMarketPlayers(PlayerMarket market,
			MarketFilter playerFilter) {

		List<PlayerOnMarket> players = new ArrayList<PlayerOnMarket>();
		// Collections.copy(players,market.getPlayers());

		for (PlayerOnMarket player : market.getPlayers()) {
			if (playerFilter.check(player)) {
				players.add(player);
			}
		}

		return players;
	}

	public void buyPlayerFromMarket(PlayerMarket market, Player player,
			Team team, int bidAmount) {
		if (market.getPlayers().contains(player)) {
			PlayerOnMarket playerOnMarket = market.getPlayerOnMarket(player);
			if (playerOnMarket.getPrice() <= bidAmount) {
				List<Bid> bidsForPlayer = market.bids.get(player);
				bidsForPlayer.add(new Bid(player, team, bidAmount));
			} else {
				// invalid bid, too lower
			}
		}

	}

	public List<Bid> getBidsForPlayer(PlayerMarket market, Player player) {
		List<Bid> bidsForPlayer = market.bids.get(player);
		return bidsForPlayer;
	}

	public void putFreePlayerOnSale(PlayerMarket market, Player player) {
		// TODO how to check if the player belongs to a team?
		if (player.team == null) {
			market.addPlayer(player,
					playerPriceCalculator.calculatePrice(player));
		}

	}

	public void putPlayerOnSale(PlayerMarket market, Player player, Team team,
			int price) {
		if (isPlayerOnTeam(player, team)) {
			market.addPlayer(player, price);
		}

	}

	public void populateMarketWithGeneratedPlayers(PlayerMarket market,
			int amount, Race... races) {
		int playersPerRace = amount / races.length;
		if (playersPerRace < 1) {
			playersPerRace = 1;
		}
		for (Race race : races) {
			int i = 0;
			while (i < playersPerRace && amount > 0) {
				Player player = generateNewPlayer(race);
				market.addPlayer(player,
						playerPriceCalculator.calculatePrice(player));
				i++;
				amount--;
			}
		}

	}

	public void addFreePlayerToMarket(PlayerMarket market, Player player) {
		market.addPlayer(player, playerPriceCalculator.calculatePrice(player));
	}

	public void performBidsOnMarket(PlayerMarket market) {
		// get existing bids and resolve it
		Collection<List<Bid>> allBids = market.bids.values();
		for (List<Bid> bidsForPlayer : allBids) {
			//List<Bid> bidsForPlayer = market.bids.get(p);

			if (bidsForPlayer.size() > 0) {
				// get the higher bid
				Collections.sort(bidsForPlayer, bidAmountComparator);

				Bid bid = bidsForPlayer.get(0);
				
				
				// move the player from the selling team to the purchasing team				
				removePlayerFromTeam(bid.getPlayer());
				addPlayerToTeam(bid.getTeam(), bid.getPlayer());

				removePlayerFromMarket(market,bid.getPlayer());
				// TODO how do we manage the money exchange? before or now?
				
			}
		}		
		
		removeAllBids(market);
	}

	public void removeAllBids(PlayerMarket market) {
		//reset
		market.bids=new HashMap<Player,List<Bid>>();
	}

	public void removePlayerFromTeam(Player player) {
		if (player.team != null) {
			player.team.players.remove(player);
			removePlayerFromTeamRosters(player, player.team);
			player.team=null;
		}

	}

	private void removePlayerFromTeamRosters(Player player, Team team) {
		team.getDefaultRoster().players.remove(player);
		for (Roster r : team.rosters) {
			r.players.remove(player);
		}

	}

	public void discardPlayerFromTeam(PlayerMarket market, Player player, Team team) {
		if(isPlayerOnTeam(player, team)){
			removePlayerFromTeam(player);
			addFreePlayerToMarket(market, player);
		}
	}

	public boolean isPlayerOnMarket(PlayerMarket market,Player player) {		
		return market.getPlayers().contains(player);
	}

	public boolean isPlayerOnTeam(Player player, Team team) {
		return team.players.contains(player);
	}

	public void removePlayerFromMarket(PlayerMarket market,Player player) {
		market.bids.get(player).clear();
		market.getPlayers().remove(player);
	}

	public static int getRandomInt(int max) {
		return new Long(Math.round(Math.random() * max)).intValue();
	}

}
