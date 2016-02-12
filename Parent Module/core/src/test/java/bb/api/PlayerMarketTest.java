package bb.api;

import bb.api.domain.*;
import bb.api.filters.MarketFilter;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.*;


public class PlayerMarketTest {
    @Test
    public void playerMarketManagementTest() {
        BBApi api = new BBApi();
        Team team = api.generateBotTeam();
        Team team2 = api.generateBotTeam();
        Player player = api.generateNewPlayer(Race.HUMAN);
        Player player2 = api.generateNewPlayer(Race.ORK);
        api.addPlayerToTeam(team, player);

        PlayerMarket market = api.createPlayerMarket();
        assertNotNull(market);

        api.populateMarketWithGeneratedPlayers(market, 10, Race.HUMAN, Race.ORK);
        assertEquals(10, market.getPlayers().size());

        MarketFilter onlyHumansFilter = api.getRaceMarketFilter(Race.HUMAN);
        assertNotNull(onlyHumansFilter);
        assertEquals(5, api.getMarketPlayers(market, onlyHumansFilter).size());

        api.putPlayerOnSale(market, player, team, 1500);
        assertEquals(6, api.getMarketPlayers(market, onlyHumansFilter).size());

        api.putFreePlayerOnSale(market, player2);

        api.buyPlayerFromMarket(market, player2, team2, 2500);
        List<Bid> bids = api.getBidsForPlayer(market, player2);
        assertEquals(1, bids.size());

        api.performBidsOnMarket(market);
        assertFalse(api.isPlayerOnMarket(market, player2));
        assertTrue(api.isPlayerOnTeam(player2, team2));

        api.discardPlayerFromTeam(market, player2, team2);
        assertFalse(api.isPlayerOnTeam(player2, team2));
        assertTrue(api.isPlayerOnMarket(market, player2));

        api.removePlayerFromMarket(market, player2);
        assertFalse(api.isPlayerOnMarket(market, player2));

    }
}
