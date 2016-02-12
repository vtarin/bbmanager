package bb.api;

public class MarketFilter {

    private Race race;

    public MarketFilter(Race race) {
        this.race = race;
    }

    public boolean check(PlayerOnMarket player) {
        //TODO how to perform multiple checks on same or different properties?
        return player.getRace().equals(race);
    }


}
