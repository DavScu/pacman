package ghost;

public class GameStatus {
    enum Status {
        NORMAL,
        WAKAHIT,
        WIN,
        LOSE
    }
    public int originLifeLeft;
    public int lifeLeft;
    public Status status;

    /**
     * constructor
     * @param lifeLeft left life
     */
    public GameStatus(int lifeLeft) {
        originLifeLeft = lifeLeft;
        this.lifeLeft = lifeLeft;
    }

    /**
     * Reset
     */
    public void reset() {
        lifeLeft = originLifeLeft;
    }
}
