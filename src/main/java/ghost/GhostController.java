package ghost;

public class GhostController {
    private Vector2D[] directions = {
            new Vector2D(0, 1),
            new Vector2D(0, -1),
            new Vector2D(1, 0),
            new Vector2D(-1, 0),
    };

    protected Map map;
    protected Ghost entity;
    protected Entity chasing;

    /**
     * Set up the map
     * @param map map
     * @return this
     */
    public GhostController setMap(Map map) {
        this.map = map;
        return this;
    }

    /**
     * Set the entity
     * @param entity entity
     * @return this
     */
    public GhostController setEntity(Ghost entity) {
        this.entity = entity;
        return this;
    }

    /**
     * Get entity
     * @return entity
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Set chase
     * @param entity entity
     * @return this
     */
    public GhostController chase(Entity entity) {
        this.chasing = entity;
        return this;
    }

    /**
     * Find out whether the direction is moveable
     * @param direction direction
     * @return whether the direction is moveable
     */
    private Boolean isDirectionMoveable(Vector2D direction) {
        if (entity.direction != null && entity.direction.plus(direction).isZero()) {
            return false;
        }
        Integer block = map.getBlock(entity.position.plus(direction));
        return block == 0 || map.isFruit(block);
    }

    /**
     * Get next step to the target
     * @param target coordinate
     * @return the direction
     */
    private Vector2D getNextStepTo(Vector2D target) {
        if (target == null) {
            return getRandomNextStep();
        }
        Vector2D distance = target.minus(entity.position);
        Vector2D maxDirection = null;
        if (entity.direction != null) {
            maxDirection = entity.direction.multiply(-1);
        }
        int minMovedDistance = -1;
        for (Vector2D direction : directions) {
            if (!isDirectionMoveable(direction)) {
                continue;
            }
            int movedDistance = distance.minus(direction).squareOfEuclideanDistance();
            if (minMovedDistance == -1 || movedDistance < minMovedDistance) {
                maxDirection = direction;
                minMovedDistance = movedDistance;
            }
        }
        return maxDirection;
    }

    /**
     * Get a random next step
     * @return coordinate
     */
    private Vector2D getRandomNextStep() {
        Vector2D validDirection[] = new Vector2D[4];
        int count = 0;
        for (Vector2D direction : directions) {
            if (isDirectionMoveable(direction)) {
                validDirection[count++] = direction;
            }
        }
        if (count == 0) {
            if (entity.direction != null) {
                return entity.direction.multiply(-1);
            }
            return new Vector2D(0, 0);
        }

        return validDirection[(int)Math.floor(Math.random() * count)];
    }

    /**
     * Get next step toward the target
     * @return position
     */
    private Vector2D getNextStep() {
        return getNextStepTo(getTarget());
    }

    /**
     * Get the position of the target
     * @return the position of the target
     */
    public Vector2D getTarget() {
        if (entity.mode != Ghost.Mode.FRIGHTENED) {
            return chasing.position;
        }
        return null;
    }

    /**
     * Move the ghost
     */
    public void move() {
        entity.direction = getNextStep();
        entity.position.plusEqual(entity.direction);
    }
}
