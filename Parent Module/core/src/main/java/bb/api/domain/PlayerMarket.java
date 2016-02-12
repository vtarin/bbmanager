package bb.api.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerMarket {

    public Map<Player, List<Bid>> bids = new HashMap<Player, List<Bid>>();
    private List<PlayerOnMarket> players = new ArrayList<PlayerOnMarket>();

    public List<PlayerOnMarket> getPlayers() {
        return players;
    }

    public void addPlayer(Player player, int price) {
        if (!players.contains(player)) {
            players.add(new PlayerOnMarket(player, price));
            if (!bids.containsValue(player)) {
                bids.put(player, new ArrayList<Bid>());
            }
        }
    }

    public PlayerOnMarket getPlayerOnMarket(Player player) {
        int index = players.indexOf(player);
        if (index > -1) {
            return players.get(index);
        } else
            return null;
    }

}
