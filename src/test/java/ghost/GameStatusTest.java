package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameStatusTest {
    // Constructor should set life left members
    @Test
    public void constructorTest() {
        int lifeLeft = 3;
        GameStatus status = new GameStatus(lifeLeft);

        assertSame(status.originLifeLeft, lifeLeft);
        assertSame(status.lifeLeft, lifeLeft);
    }

    // reset method should reset the lifeLeft member
    @Test
    public void resettingTest() {
        int lifeLeft = 3;
        GameStatus status = new GameStatus(lifeLeft);

        status.lifeLeft = 0;
        status.reset();

        assertSame(status.lifeLeft, lifeLeft);
    }
}
