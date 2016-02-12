package bb.api;

public class RandomPlayerPriceCalculator implements PlayerPriceCalculator {
    @Override
    public int calculatePrice(Player player) {
        return BBApi.getRandomInt(2000);
    }
}
