package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WhimControllerTest {
    private int blocks[] = { 0, 7, 7, 7, 7, 7, 7, 7, 7 };

    private Map getMap() {
        return new Map()
                .setSize(new Vector2D(9, 1))
                .setBlocks(blocks);
    }

    // getTarget should return null if the ghost is frightened
    @Test
    public void targetInFrightenedTest() {
        Ghost ghost = new Ghost();

        WhimController controller = (WhimController)new WhimController()
                .setEntity(ghost);

        ghost.mode = Ghost.Mode.FRIGHTENED;

        assertSame(controller.getTarget(), null);
    }

    // getTarget should return bottom right corner if the ghost is scatter
    @Test
    public void targetInScatterTest() {
        Ghost ghost = new Ghost();
        Map map = getMap();

        WhimController controller = (WhimController)new WhimController()
                .setEntity(ghost)
                .setMap(map);

        ghost.mode = Ghost.Mode.SCATTER;

        assertTrue(controller.getTarget().equals(new Vector2D(8, 0)));
    }

    // getTarget should return target by whim's rule
    @Test
    public void targetInChaseTest() {
        Ghost ghost = (Ghost)new Ghost().setPosition(new Vector2D(0, 0));
        Entity chasing = new Entity().setPosition(new Vector2D(2, 0));
        chasing.direction = new Vector2D(1, 0);
        Map map = getMap();

        WhimController controller = (WhimController)new WhimController()
                .setEntity(ghost)
                .chase(chasing)
                .setMap(map);

        ghost.mode = Ghost.Mode.CHASE;

        assertTrue(controller.getTarget().equals(new Vector2D(8, 0)));
    }


}
