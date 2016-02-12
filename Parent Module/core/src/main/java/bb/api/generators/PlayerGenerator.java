package bb.api.generators;

import bb.api.domain.Player;
import bb.api.domain.Race;

public interface PlayerGenerator {

    Player generate(Race race);

}
