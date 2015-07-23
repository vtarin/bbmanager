package bb.api;

import java.util.ArrayList;
import java.util.Collection;

public class MatchDay {

	public Collection<Match> matches = new ArrayList<Match>();

	public boolean isPlayed() {
		for (Match m : matches) {
			if (!m.played){
				return false;
			}
		}
		return true;
	}

}
