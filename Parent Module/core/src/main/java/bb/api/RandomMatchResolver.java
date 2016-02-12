package bb.api;

public class RandomMatchResolver implements MatchResolver {

    @Override
    public void playMatch(Match match) {
        match.played = true;
        match.result.scoreHome = BBApi.getRandomInt(5);
        match.result.scoreAway = BBApi.getRandomInt(5);
    }


}
