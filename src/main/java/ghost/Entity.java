package ghost;

public class Entity {
    public Vector2D spawnPoint;
    public Vector2D position;
    public int type;
    public Vector2D direction;
    public boolean isEnabled = true;
    public boolean isVisible = true;

    /**
     * Set the position of the entity
     * @param position the expected position of the enetity
     * @return this
     */
    public Entity setPosition(Vector2D position) {
        this.position = position;
        return this;
    }

    /**
     * Set the spawn point
     * @param spawnPoint point
     * @return this
     */
    public Entity setSpawnPoint(Vector2D spawnPoint) {
        this.spawnPoint = spawnPoint;
        return this;
    }

    /**
     * Set the type of the entity
     * @param type type of the entity
     * @return this
     */
    public Entity setType(int type) {
        this.type = type;
        return this;
    }

    /**
     * Get the appearance of the entity
     * @param frameCount frame count
     * @return type
     */
    public int getAppearanceType(int frameCount) {
        return type;
    }

    /**
     * Reset
     */
    public void reset() {
        position = spawnPoint.clone();
        direction = new Vector2D(0, 0);
        isEnabled = true;
        isVisible = true;
    }
}
