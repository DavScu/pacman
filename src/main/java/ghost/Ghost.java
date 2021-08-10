package ghost;

public class Ghost extends Entity {
    public enum Mode {
        SCATTER,
        CHASE,
        FRIGHTENED
    }

    public Mode mode = Mode.SCATTER;

    /**
     * Get teh type of appearance
     * @param frameCount frame count
     * @return type
     */
    @Override
    public int getAppearanceType(int frameCount) {
        if (mode == Mode.FRIGHTENED) {
            return 9;
        }
        return type;
    }
}
