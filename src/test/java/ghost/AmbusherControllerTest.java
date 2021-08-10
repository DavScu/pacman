package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AmbusherControllerTest {
    private int blocks[] = { 0, 7, 7, 7, 7 };

    private Map getMap() {
        return new Map().setSize(new Vector2D(5, 1)).setBlocks(blocks);
    }

    // getTarget should return null if the ghost is frightened
    @Test
    public void targetInFrightenedTest() {
        Ghost ghost = new Ghost();

        AmbusherController controller = (AmbusherController)new AmbusherController()
                .setEntity(ghost);

        ghost.mode = Ghost.Mode.FRIGHTENED;

        assertSame(controller.getTarget(), null);
    }

    // getTarget should return top right corner if the ghost is scatter
    @Test
    public void targetInScatterTest() {
        Ghost ghost = new Ghost();
        Map map = getMap();

        AmbusherController controller = (AmbusherController)new AmbusherController()
                .setEntity(ghost)
                .setMap(map);

        ghost.mode = Ghost.Mode.SCATTER;

        assertTrue(controller.getTarget().equals(new Vector2D(4, 0)));
    }

    // getTarget should return 4 blocks in front of target in chase
    @Test
    public void targetInChaseTest() {
        Ghost ghost = new Ghost();
        Entity chasing = new Entity().setPosition(new Vector2D(0, 0));
        chasing.direction = new Vector2D(1, 0);
        Map map = getMap();

        AmbusherController controller = (AmbusherController)new AmbusherController()
                .setEntity(ghost)
                .chase(chasing)
                .setMap(map);

        ghost.mode = Ghost.Mode.CHASE;

        assertTrue(controller.getTarget().equals(new Vector2D(4, 0)));
    }

    // getTarget should return 4 blocks in front of target limited in map in chase
    @Test
    public void targetInChaseLimitedTest() {
        Ghost ghost = new Ghost();
        Entity chasing = new Entity().setPosition(new Vector2D(2, 0));
        chasing.direction = new Vector2D(1, 0);
        Map map = getMap();

        AmbusherController controller = (AmbusherController)new AmbusherController()
                .setEntity(ghost)
                .chase(chasing)
                .setMap(map);

        ghost.mode = Ghost.Mode.CHASE;

        assertTrue(controller.getTarget().equals(new Vector2D(4, 0)));
    }
}
