package bb.api;

public class RandomPlayerGenerator implements PlayerGenerator {
	private long playerNameCounter= System.currentTimeMillis();

	@Override
	public Player generate(Race race) {
		Player p = new Player();
		p.setName("player_" + playerNameCounter++);
		p.setRace(race);
		return p;
	}
}
