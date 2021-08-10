package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WakaControllerTest {
    static final int blocks[] = {
            0, 7, 0,
            1, 1, 0
    };
    static final Map map = new Map()
            .setSize(new Vector2D(3, 2))
            .setBlocks(blocks);

    // setMap should returns the object
    @Test
    public void settingMapTest() {
        Map map = new Map();

        WakaController controller = new WakaController();
        WakaController newController = controller.setMap(map);
        assertSame(controller, newController);
    }

    // setEntity should returns the object
    @Test
    public void settingEntityTest() {
        Entity entity = new Entity();

        WakaController controller = new WakaController();
        WakaController newController = controller.setEntity(entity);
        assertSame(controller, newController);
    }

    // addDirection should returns the object
    @Test
    public void settingDirectionTest() {
        Entity entity = new Entity();

        WakaController controller = new WakaController();
        WakaController newController = controller
                .addDirection(1, new Vector2D(0, 0));
        assertSame(controller, newController);
    }

    private WakaController addDirectionsToController(WakaController controller) {
        return controller
                .addDirection(0, new Vector2D(0, 0))
                .addDirection(1, new Vector2D(0, -1))
                .addDirection(2, new Vector2D(-1, 0))
                .addDirection(3, new Vector2D(0, 1))
                .addDirection(4, new Vector2D(1, 0));
    }

    // move should not move the entity without next direction
    @Test
    public void moveWithoutNextDirectionTest() {
        int x = 0;
        int y = 0;
        Entity entity = new Entity().setPosition(new Vector2D(x, y));

        WakaController controller = addDirectionsToController(new WakaController()
                .setMap(map)
                .setEntity(entity)
        );

        controller.move();
        assertSame(entity.position.x, x);
        assertSame(entity.position.y, y);
    }

    // if the key code is invalid, nothing will change
    @Test
    public void wrongKeyCodeWillDoNothing() {
        Vector2D position = new Vector2D(0, 0);
        Entity entity = new Entity().setPosition(position);

        WakaController controller = addDirectionsToController(new WakaController()
                .setMap(map)
                .setEntity(entity)
        );

        controller.processKeyCode('T');

        controller.move();
        assertTrue(entity.position.equals(position));
    }

    // if no barrier, move should move the entity one step with next direction and
    // set the direction for next move
    @Test
    public void moveWithOneNextDirectionWithoutBarrierTest() {
        int x = 0;
        int y = 0;
        Entity entity = new Entity().setPosition(new Vector2D(x, y));

        WakaController controller = addDirectionsToController(new WakaController()
                .setMap(map)
                .setEntity(entity)
        );

        controller.processKeyCode('D');

        controller.move();
        assertSame(entity.position.x, x + 1);
        assertSame(entity.position.y, y);

        controller.move();
        assertSame(entity.position.x, x + 2);
        assertSame(entity.position.y, y);
    }

    // with barrier, move should not move the entity
    @Test
    public void moveWithOneNextDirectionWithBarrierTest() {
        int x = 0;
        int y = 0;
        Entity entity = new Entity().setPosition(new Vector2D(x, y));

        WakaController controller = addDirectionsToController(new WakaController()
                .setMap(map)
                .setEntity(entity)
        );

        controller.processKeyCode('S');

        controller.move();
        assertSame(entity.position.x, x);
        assertSame(entity.position.y, y);
    }

    // move should turn the direction to nextDirection when possible
    @Test
    public void moveWithTwoNextDirectionTest() {
        int x = 0;
        int y = 0;
        Entity entity = new Entity().setPosition(new Vector2D(x, y));

        WakaController controller = addDirectionsToController(new WakaController()
                .setMap(map)
                .setEntity(entity)
        );

        controller.processKeyCode('D');

        controller.move();
        assertSame(entity.position.x, x + 1);
        assertSame(entity.position.y, y);

        controller.processKeyCode('S');

        controller.move();
        assertSame(entity.position.x, x + 2);
        assertSame(entity.position.y, y);

        controller.move();
        assertSame(entity.position.x, x + 2);
        assertSame(entity.position.y, y + 1);
    }
}
