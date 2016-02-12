package bb.api.resolvers;

import bb.api.BBApi;
import bb.api.domain.Match;

public class RandomMatchResolver implements MatchResolver {

    @Override
    public void playMatch(Match match) {
        match.played = true;
        match.result.scoreHome = BBApi.getRandomInt(5);
        match.result.scoreAway = BBApi.getRandomInt(5);
    }


}
