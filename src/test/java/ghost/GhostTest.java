package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GhostTest {
    // getAppearanceType should return 9 if the ghost is frightened
    @Test
    public void frightenedAppearanceTest() {
        Ghost ghost = new Ghost();
        ghost.mode = Ghost.Mode.FRIGHTENED;

        assertSame(ghost.getAppearanceType(0), 9);
    }

    // getAppearanceType should return its type if the ghost is not frightened
    @Test
    public void notFrightenedAppearanceTest() {
        int type = 5;
        Ghost ghost = (Ghost)new Ghost().setType(type);

        assertSame(ghost.getAppearanceType(0), type);
    }
}
