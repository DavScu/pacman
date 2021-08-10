package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GhostControllerTest {
    private int blocks[] = {
            6, 1, 1, 1, 1, 5,
            2, 0, 7, 0, 0, 2,
            4, 1, 1, 1, 1, 3
    };

    private Map getMap() {
        return new Map().setSize(new Vector2D(6, 3)).setBlocks(blocks);
    }

    // methods for setting properties should return the obejct
    @Test
    public void settingPropertiesTest() {
        GhostController controller = new GhostController();

        Ghost ghost = new Ghost();
        GhostController newController = controller.setEntity(ghost);
        assertSame(controller, newController);
        assertSame(controller.getEntity(), ghost);

        newController = controller.setMap(new Map());
        assertSame(controller, newController);

        newController = controller.chase(new Entity());
        assertSame(controller, newController);
    }

    // getTarget should return null if the ghost is frightened
    @Test
    public void targetInFrightenedTest() {
        Ghost ghost = new Ghost();

        GhostController controller = new GhostController().setEntity(ghost);

        ghost.mode = Ghost.Mode.FRIGHTENED;

        assertSame(controller.getTarget(), null);
    }

    // getTarget should return target's position in chase
    @Test
    public void targetInChaseTest() {
        Ghost ghost = new Ghost();
        Vector2D targetPosition = new Vector2D(0, 0);
        Entity chasing = new Entity().setPosition(targetPosition);

        GhostController controller = new GhostController()
                .setEntity(ghost)
                .chase(chasing);

        ghost.mode = Ghost.Mode.CHASE;

        assertTrue(controller.getTarget().equals(targetPosition));
    }

    // ghost should move randomly in frightened mode
    @Test
    public void frightenedMoveTest() {
        Ghost ghost = (Ghost)new Ghost().setPosition(new Vector2D(1, 1));
        Entity chasing = new Entity();
        Map map = getMap();

        GhostController controller = new GhostController()
                .setEntity(ghost)
                .setMap(map);

        ghost.mode = Ghost.Mode.FRIGHTENED;

        controller.move();
        assertTrue(ghost.position.equals(new Vector2D(2, 1)));
    }

    // ghost should not trun back if there is other choice in frightened mode
    @Test
    public void frightenedNoMoveTrunBackTest() {
        Ghost ghost = (Ghost)new Ghost().setPosition(new Vector2D(3, 1));
        ghost.direction = new Vector2D(1, 0);
        Map map = getMap();

        GhostController controller = new GhostController()
                .setEntity(ghost)
                .setMap(map);

        ghost.mode = Ghost.Mode.FRIGHTENED;

        controller.move();
        assertTrue(ghost.position.equals(new Vector2D(4, 1)));
    }

    // ghost should turn back if there is no other choice in frightened mode
    @Test
    public void frightenedMoveTrunBackTest() {
        Ghost ghost = (Ghost)new Ghost().setPosition(new Vector2D(4, 1));
        ghost.direction = new Vector2D(1, 0);
        Entity chasing = new Entity();
        Map map = getMap();

        GhostController controller = new GhostController()
                .setEntity(ghost)
                .setMap(map);

        ghost.mode = Ghost.Mode.FRIGHTENED;

        controller.move();
        assertTrue(ghost.position.equals(new Vector2D(3, 1)));
    }

    // ghost should chase target in chase mode
    @Test
    public void chaseMoveTest() {
        int blocks[] = {
                6, 1, 1, 1, 1, 5,
                2, 0, 7, 0, 0, 2,
                2, 1, 0, 1, 1, 3,
                4, 1, 3, 0, 0, 0
        };

        Ghost ghost = (Ghost)new Ghost().setPosition(new Vector2D(2, 1));
        Entity chasing = new Entity().setPosition(new Vector2D(4, 1));
        Map map = new Map().setSize(new Vector2D(6, 4)).setBlocks(blocks);

        GhostController controller = new GhostController()
                .setEntity(ghost)
                .chase(chasing)
                .setMap(map);

        ghost.mode = Ghost.Mode.CHASE;

        controller.move();
        assertTrue(ghost.position.equals(new Vector2D(3, 1)));
    }

    // ghost should turn back only when no other choice in chase mode
    @Test
    public void chaseMoveTurnBackTest() {
        Ghost ghost = (Ghost)new Ghost().setPosition(new Vector2D(4, 1));
        ghost.direction = new Vector2D(1, 0);
        Entity chasing = new Entity().setPosition(new Vector2D(1, 1));
        Map map = getMap();

        GhostController controller = new GhostController()
                .setEntity(ghost)
                .chase(chasing)
                .setMap(map);

        ghost.mode = Ghost.Mode.CHASE;

        controller.move();
        assertTrue(ghost.position.equals(new Vector2D(3, 1)));
    }
}
