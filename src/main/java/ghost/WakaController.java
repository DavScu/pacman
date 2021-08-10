package ghost;

import java.util.HashMap;

public class WakaController {
    private Map map;
    private Entity entity;
    private HashMap<Integer, Vector2D> directions = new HashMap<>();
    private static final HashMap<Integer, Integer> types = new HashMap<Integer, Integer>() {{
        put((int)'W', 1);
        put((int)'A', 2);
        put((int)'S', 3);
        put((int)'D', 4);
    }};
    private int nextType;

    /**
     * Set up the map
     * @param map map
     * @return this
     */
    public WakaController setMap(Map map) {
        this.map = map;
        return this;
    }

    /**
     * Set the entity
     * @param entity entity
     * @return this
     */
    public WakaController setEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    /**
     * Add the direction of waka
     * @param type type
     * @param direction direction
     * @return this
     */
    public WakaController addDirection(int type, Vector2D direction) {
        directions.put(type, direction);
        return this;
    }

    /**
     * Find out whether the given position movable
     * @param position target position
     * @return whether the given position movable
     */
    private Boolean isPositionMoveable(Vector2D position) {
        int block = map.getBlock(position);
        return block == 0 || map.isFruit(block);
    }

    /**
     * Define how the entity moves
     * @param type type
     * @return whether can move
     */
    private boolean moveAs(int type) {
        Vector2D direction = directions.get(type);
        Vector2D nextPosition = entity.position.plus(direction);
        if (!isPositionMoveable(nextPosition)) {
            return false;
        }
        entity.type = type;
        entity.position = nextPosition;
        entity.direction = direction;
        return true;
    }

    /**
     * Move the waka
     */
    public void move() {
        if (nextType != entity.type && moveAs(nextType)) {
            return;
        }
        moveAs(entity.type);
    }

    /**
     * move waka due to the input
     * @param keyCode input
     */
    public void processKeyCode(int keyCode) {
        Integer type = types.get(keyCode);
        if (type == null) {
            return;
        }
        this.nextType = type;
    }

    public WakaController setNextType(int i) {
        nextType = i;
        return this;
    }
}
