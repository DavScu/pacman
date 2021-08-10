package ghost;

import processing.core.PApplet;
import java.util.*;

interface MovingStopedHandler {
    void process();
}

public class ProcessingEntitiesRenderer {

    private HashMap<Integer, Image> images = new HashMap<>();
    private PApplet applet;
    private Vector2D blockSize;
    private int speed;
    private LinkedList<ContinuousEntity> entities = new LinkedList<>();
    private int frameCount = 0;
    private GameStatus status;

    /**
     * Create a continuous entity
     */
    private class ContinuousEntity {
        Entity entity;
        Vector2D position;
        MovingStopedHandler handler;
    }

    /**
     * Constructor
     * @param applet PApplet
     */
    public ProcessingEntitiesRenderer(PApplet applet) {
        this.applet = applet;
    }

    /**
     * Bind the image of the entity
     * @param type type of the entity
     * @param image The image of the entity
     * @return this
     */
    public ProcessingEntitiesRenderer bindImage(int type, Image image) {
        this.images.put(type, image);
        return this;
    }

    /**
     * Set the size of the block
     * @param blockSize The size pof the block
     * @return this
     */
    public ProcessingEntitiesRenderer setBlockSize(Vector2D blockSize) {
        this.blockSize = blockSize;
        return this;
    }

    /**
     * Set the speed of the entity
     * @param speed speed of the enetity
     * @return this
     */
    public ProcessingEntitiesRenderer setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    /**
     * Set the status of the game
     * @param status status
     * @return this
     */
    public ProcessingEntitiesRenderer setStatus(GameStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Get the position of the target
     * @param entity entity
     * @return the coordinate of the target
     */
    private Vector2D getTargetPosition(Entity entity) {
        return new Vector2D(entity.position.x * blockSize.x, entity.position.y * blockSize.y);
    }

    /**
     * Add entity
     * @param entity entity
     * @param handler Helper handler
     * @return this
     */
    public ProcessingEntitiesRenderer addEntity(Entity entity, MovingStopedHandler handler) {
        ContinuousEntity continuousEntity = new ContinuousEntity();
        continuousEntity.entity = entity;
        continuousEntity.position = getTargetPosition(entity);
        continuousEntity.handler = handler;
        entities.push(continuousEntity);
        return this;
    }

    /**
     * Calculate the difference of two coordinates
     * @param current current coordinate
     * @param target target coordinate
     * @return Difference
     */
    private int moveInOneCoordinate(int current, int target) {
        if (current == target) {
            return 0;
        }

        if (current > target) {
            if (current - target > speed) {
                return -speed;
            }
        } else {
            if (target - current > speed) {
                return speed;
            }
        }
        return target - current;
    }

    /**
     * Move the entity
     * @param entity entity
     */
    private void moveEntity(ContinuousEntity entity) {
        Vector2D target = getTargetPosition(entity.entity);
        int xDifference = moveInOneCoordinate(entity.position.x, target.x);
        int yDifference = moveInOneCoordinate(entity.position.y, target.y);
        if (xDifference == 0 && yDifference == 0) {
            entity.handler.process();
            return;
        }
        entity.position.x += xDifference;
        entity.position.y += yDifference;

        if (entity.position.equals(target)) {
            entity.handler.process();
        }
    }

    /**
     * Render a entity
     * @param entity entity
     */
    private void renderOneEntity(ContinuousEntity entity) {
        if (!entity.entity.isEnabled) {
            return;
        }

        if (entity.entity.isVisible) {
            Image image = images.get(entity.entity.getAppearanceType(frameCount));
            if (image != null) {
                image.render(applet, entity.position);
            }
        }

        moveEntity(entity);
    }

    /**
     * Render all of those entity
     */
    public void render() {
        if (status.status == GameStatus.Status.WAKAHIT) {
            status.status = GameStatus.Status.NORMAL;
            entities.forEach((entity) -> entity.position = getTargetPosition(entity.entity));
        }
        entities.forEach((entity) -> renderOneEntity(entity));
        frameCount += 1;
    }
}
