package bb.api;

public class PlayerOnMarket extends Player {

    private int price;
    private Player player;

    public PlayerOnMarket(Player player, int price) {
        this.setPlayer(player);
        this.setPrice(price);
    }

    public Race getRace() {
        return getPlayer().getRace();
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerOnMarket) {
            return ((PlayerOnMarket) obj).getPlayer().equals(this.getPlayer());
        } else if (obj instanceof Player) {
            return ((Player) obj).getName().equals(this.getPlayer().getName());
        } else {
            return super.equals(obj);
        }
    }
}
