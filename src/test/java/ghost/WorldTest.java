package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FakeMap extends Map {
    int resetCount = 0;

    @Override
    public void reset() {
        resetCount += 1;
    }
}

public class WorldTest {
    private int blocks[] = { 0, 7, 7 };

    // setMap should returns the object
    @Test
    public void settingMapTest() {
        World world = new World();
        World newWorld = world.setMap(new Map());

        assertSame(world, newWorld);
    }

    // setWaka should returns the object
    @Test
    public void settingWakaTest() {
        World world = new World();
        World newWorld = world.setWaka(new Waka());

        assertSame(world, newWorld);
    }

    private void assertToBeEmptyAfterEating(Vector2D position) {
        Map map = new Map().setSize(new Vector2D(3, 1)).setBlocks(blocks);
        World world = new World()
                .setMap(map)
                .setWaka((Waka)new Waka().setPosition(position))
                .setStatus(new GameStatus(1));

        world.processSituation();
        assertSame(map.getBlock(position), 0);
    }

    // waka should eat existing fruit
    @Test
    public void eatingFruitTest() {
        assertToBeEmptyAfterEating(new Vector2D(1, 0));
    }

    // waka should do nothing when there's no fruit
    @Test
    public void eatingNoFruitTest() {
        assertToBeEmptyAfterEating(new Vector2D(0, 0));
    }

    // ghost should disappear if it hits waka when it's frightened
    @Test
    public void removeGhostTest() {
        Vector2D position = new Vector2D(0, 0);
        Waka waka = (Waka)new Waka().setPosition(position);
        World world = new World().setWaka(waka);

        Ghost ghost = (Ghost)new Ghost().setPosition(position);
        ghost.mode = Ghost.Mode.FRIGHTENED;

        world.processGhostMoved(ghost);

        assertFalse(ghost.isEnabled);
    }

    // nothing happens if ghost didn't hit waka
    @Test
    public void noHittingTest() {
        Waka waka = (Waka)new Waka().setPosition(new Vector2D(0, 1));
        World world = new World().setWaka(waka);

        Ghost ghost = (Ghost)new Ghost().setPosition(new Vector2D(0, 0));
        ghost.direction = new Vector2D(0, 0);
        ghost.mode = Ghost.Mode.FRIGHTENED;

        world.processGhostMoved(ghost);

        assertTrue(ghost.isEnabled);
    }

    // ghost and waka pass each other should also regard as hitted
    @Test
    public void passingByHittingTest() {
        Waka waka = (Waka)new Waka().setPosition(new Vector2D(0, 0));
        waka.direction = new Vector2D(-1, 0);
        World world = new World().setWaka(waka);

        Ghost ghost = (Ghost)new Ghost().setPosition(new Vector2D(1, 0));
        ghost.direction = new Vector2D(1, 0);
        ghost.mode = Ghost.Mode.FRIGHTENED;

        world.processGhostMoved(ghost);

        assertFalse(ghost.isEnabled);
    }

    // ghost and waka didn't pass each other should not regard as hitted
    @Test
    public void notPassingByHittingTest() {
        Waka waka = (Waka)new Waka().setPosition(new Vector2D(0, 0));
        waka.direction = new Vector2D(0, 0);
        World world = new World().setWaka(waka);

        Ghost ghost = (Ghost)new Ghost().setPosition(new Vector2D(1, 0));
        ghost.direction = new Vector2D(1, 0);
        ghost.mode = Ghost.Mode.FRIGHTENED;

        world.processGhostMoved(ghost);

        assertTrue(ghost.isEnabled);
    }

}
