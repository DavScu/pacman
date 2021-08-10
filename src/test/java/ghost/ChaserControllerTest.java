package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChaserControllerTest {
    private int blocks[] = { 0, 7, 7, 7, 7 };

    private Map getMap() {
        return new Map()
                .setSize(new Vector2D(5, 1))
                .setBlocks(blocks);
    }

    // getTarget should return null if the ghost is frightened
    @Test
    public void targetInFrightenedTest() {
        Ghost ghost = new Ghost();

        ChaserController controller = (ChaserController)new ChaserController()
                .setEntity(ghost);

        ghost.mode = Ghost.Mode.FRIGHTENED;

        assertSame(controller.getTarget(), null);
    }

    // getTarget should return top left corner if the ghost is scatter
    @Test
    public void targetInScatterTest() {
        Ghost ghost = new Ghost();
        Map map = getMap();

        ChaserController controller = (ChaserController)new ChaserController()
                .setEntity(ghost)
                .setMap(map);

        ghost.mode = Ghost.Mode.SCATTER;

        assertTrue(controller.getTarget().equals(new Vector2D(0, 0)));
    }

    // getTarget should return target's position in chase
    @Test
    public void targetInChaseTest() {
        Ghost ghost = new Ghost();
        Vector2D targetPosition = new Vector2D(0, 0);
        Entity chasing = new Entity().setPosition(targetPosition);
        Map map = getMap();

        ChaserController controller = (ChaserController)new ChaserController()
                .setEntity(ghost)
                .chase(chasing)
                .setMap(map);

        ghost.mode = Ghost.Mode.CHASE;

        assertTrue(controller.getTarget().equals(targetPosition));
    }
}
