package bb.api;

public class Bid {

	private Player player;
	private Team team;
	private int bidAmount;

	public Bid(Player player, Team team, int bidAmount) {
		this.player = player;
		this.team = team;
		this.bidAmount = bidAmount;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public int getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(int bidAmount) {
		this.bidAmount = bidAmount;
	}

}
