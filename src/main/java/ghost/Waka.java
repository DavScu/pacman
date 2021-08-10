package ghost;

public class Waka extends Entity {
    /**
     *Get the appearance of waka
     * @param frameCount framecount
     * @return appearance type
     */
    @Override
    public int getAppearanceType(int frameCount) {
        if ((frameCount / 8) % 2 == 0) {
            return type;
        }
        return 0;
    }

    /**
     * Reset
     */
    @Override
    public void reset() {
        type = 0;
        super.reset();
    }
}
