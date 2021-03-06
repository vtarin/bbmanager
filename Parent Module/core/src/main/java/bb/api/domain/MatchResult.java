package bb.api.domain;

import java.util.List;

public class MatchResult {

    public int scoreHome;
    public int scoreAway;
    public List<Player> scorersList;
    public List<Player> passersList;
    public List<Player> injurersList;
    public List<Player> casualtiesList;

    @Override
    public String toString() {
        return scoreHome +
                " - " + scoreAway;
    }
}
