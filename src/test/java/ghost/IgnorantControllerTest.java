package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IgnorantControllerTest {
    private int blocks[] = { 0, 7, 7, 7, 7, 7, 7, 7, 7, 7 };

    private Map getMap() {
        return new Map().setSize(new Vector2D(10, 1)).setBlocks(blocks);
    }

    // getTarget should return null if the ghost is frightened
    @Test
    public void targetInFrightenedTest() {
        Ghost ghost = new Ghost();

        IgnorantController controller = (IgnorantController)new IgnorantController()
                .setEntity(ghost);

        ghost.mode = Ghost.Mode.FRIGHTENED;

        assertSame(controller.getTarget(), null);
    }

    // getTarget should return bottom left corner if the ghost is scatter
    @Test
    public void targetInScatterTest() {
        Ghost ghost = new Ghost();
        Map map = getMap();

        IgnorantController controller = (IgnorantController)new IgnorantController()
                .setEntity(ghost)
                .setMap(map);

        ghost.mode = Ghost.Mode.SCATTER;

        assertTrue(controller.getTarget().equals(new Vector2D(0, 0)));
    }

    // In chase, getTarget should return target's position when it's 8 blocks away
    // from target
    @Test
    public void targetInChaseTest() {
        Ghost ghost = new Ghost();
        ghost.position = new Vector2D(0, 0);
        Vector2D targetPosition = new Vector2D(9, 0);
        Entity chasing = new Entity().setPosition(targetPosition);
        Map map = getMap();

        IgnorantController controller = (IgnorantController)new IgnorantController()
                .setEntity(ghost)
                .chase(chasing)
                .setMap(map);

        ghost.mode = Ghost.Mode.CHASE;

        assertTrue(controller.getTarget().equals(targetPosition));
    }

}
