package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WakaTest {
    // getAppearanceType should return 0 per after 8 frames
    @Test
    public void after8FrameAppearanceTest() {
        Waka waka = new Waka();

        assertSame(waka.getAppearanceType(9), 0);
    }

    // getAppearanceType should return its type at beginning
    @Test
    public void notFrightenedAppearanceTest() {
        int type = 3;
        Waka waka = (Waka)new Waka().setType(type);

        assertSame(waka.getAppearanceType(0), type);
    }

}
