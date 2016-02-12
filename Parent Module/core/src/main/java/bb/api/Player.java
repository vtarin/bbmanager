package bb.api;

public class Player {

    public Team team;
    private Tactic tactic;
    private boolean injured;
    private Race race;
    private String name;

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInjured() {
        return injured;
    }

    public void setInjured(boolean injured) {
        this.injured = injured;
    }

    public Tactic getTactic() {
        return tactic;
    }

    public void setTactic(Tactic tactic) {
        this.tactic = tactic;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerOnMarket) {
            return ((PlayerOnMarket) obj).getPlayer().getName().equals(this.getName());
        } else if (obj instanceof Player) {
            return ((Player) obj).getName().equals(this.getName());
        } else {
            return super.equals(obj);
        }
    }

}
