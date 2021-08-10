package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {
    // setPosition should set the position member and return the object
    @Test
    public void settingPositionTest() {
        Vector2D position = new Vector2D(0, 0);

        Entity entity = new Entity();
        Entity newEntity = entity.setPosition(position);

        assertSame(entity, newEntity);
        assertTrue(entity.position.equals(position));
    }

    // setType should set the type member and return the object
    @Test
    public void settingTypeTest() {
        int type = 0;

        Entity entity = new Entity();
        Entity newEntity = entity.setType(type);

        assertSame(entity, newEntity);
        assertSame(entity.type, type);
    }
}
