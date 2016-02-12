package bb.api.calculators;

import bb.api.BBApi;
import bb.api.domain.Player;

public class RandomPlayerPriceCalculator implements PlayerPriceCalculator {
    @Override
    public int calculatePrice(Player player) {
        return BBApi.getRandomInt(2000);
    }
}
