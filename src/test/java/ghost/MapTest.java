package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    // after setting properties of a map, getBlock should returns correct value
    @Test
    public void settingPropertiesTest() {
        int blocks[] = {
                0, 1,
                2, 3
        };
        Vector2D size = new Vector2D(2, 2);

        Map map = new Map();

        Map newMap = map.setSize(size);
        assertSame(map, newMap);

        newMap = map.setBlocks(blocks);
        assertSame(map, newMap);

        assertSame(map.getBlock(new Vector2D(0, 0)), 0);
        assertSame(map.getBlock(new Vector2D(1, 0)), 1);
        assertSame(map.getBlock(new Vector2D(0, 1)), 2);
        assertSame(map.getBlock(new Vector2D(1, 1)), 3);
    }

    // forEachBlock will invoke the lambda function for each block and maintain
    // the fruit count
    @Test
    public void forEachBlocksTest() {
        int blocks[] = { 0, 4 };

        Map map = new Map().setSize(new Vector2D(2, 1)).setBlocks(blocks);

        map.forEachBlock((block, position) -> {
            assertSame(block, position.x * 4);
            return 2 * block;
        });

        assertSame(map.getBlock(new Vector2D(0, 0)), 0);
        assertSame(map.getBlock(new Vector2D(1, 0)), 8);

        assertFalse(map.isFruitEmpty());

        map.forEachBlock((block, position) -> 0 );

        assertTrue(map.isFruitEmpty());
    }

    // isFruit should return true for fruit
    @Test
    public void isFruitForFruitTest() {
        assertTrue(new Map().isFruit(7));
    }

    // isFruit should return true for super fruit
    @Test
    public void isFruitForSuperFruitTest() {
        assertTrue(new Map().isFruit(8));
    }

    // isFruit should return true for soda can
    @Test
    public void isFruitForSodaCanTest() {
        assertTrue(new Map().isFruit(9));
    }

    // isFruitEmpty should return true when fruit is empty
    @Test
    public void emptyFruitTest() {
        int blocks[] = { 0, 0 };
        Map map = new Map().setSize(new Vector2D(2, 1)).setBlocks(blocks);

        assertTrue(map.isFruitEmpty());
    }

    // isFruitEmpty should return false when fruit is not empty
    @Test
    public void notEmptyFruitTest() {
        int blocks[] = { 0, 7 };
        Map map = new Map().setSize(new Vector2D(2, 1)).setBlocks(blocks);

        assertFalse(map.isFruitEmpty());
    }

    // processBlock should handle the block and maintain the fruit count
    @Test
    public void processBlockTest() {
        int blocks[] = { 0, 7 };
        Map map = new Map().setSize(new Vector2D(2, 1)).setBlocks(blocks);

        Vector2D position1 = new Vector2D(1, 0);
        map.processBlock(position1, (block, pos) -> 0);

        assertSame(map.getBlock(position1), 0);
        assertTrue(map.isFruitEmpty());

        Vector2D position2 = new Vector2D(0, 0);
        map.processBlock(position2, (block, pos) -> 7);

        assertFalse(map.isFruitEmpty());
    }

    // limitEqualToMap should limit too big position
    @Test
    public void limitTooBigPositionTest() {
        int blocks[] = { 0, 7 };
        Map map = new Map().setSize(new Vector2D(2, 1)).setBlocks(blocks);

        Vector2D position = new Vector2D(2, 1);
        map.limitEqualToMap(position);

        assertTrue(position.equals(new Vector2D(1, 0)));
    }

    // limitEqualToMap should limit too small position
    @Test
    public void limitTooSmallPositionTest() {
        int blocks[] = { 0, 7 };
        Map map = new Map().setSize(new Vector2D(2, 1)).setBlocks(blocks);

        Vector2D position = new Vector2D(-1, -1);
        map.limitEqualToMap(position);

        assertTrue(position.equals(new Vector2D(0, 0)));
    }
}
