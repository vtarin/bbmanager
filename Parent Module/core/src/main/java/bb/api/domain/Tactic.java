package bb.api.domain;

public class Tactic {
    private int passing;
    private int violence;
    private int scoring;
    private int position;

    public Tactic(int passing, int violence, int scoring, int position) {
        // TODO asegurarse de que los tres primeros valores suman 100 o
        // formatearlos para ello
        this.passing = passing;
        this.violence = violence;
        this.scoring = scoring;
        this.position = position;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public int getViolence() {
        return violence;
    }

    public void setViolence(int violence) {
        this.violence = violence;
    }

    public int getScoring() {
        return scoring;
    }

    public void setScoring(int scoring) {
        this.scoring = scoring;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
