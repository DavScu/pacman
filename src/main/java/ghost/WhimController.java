package ghost;

public class WhimController extends GhostController {
    private Vector2D corner;

    /**
     * Set up the map
     * @param map map
     * @return this
     */
    public GhostController setMap(Map map) {
        super.setMap(map);
        corner = new Vector2D(map.getSize().x - 1, map.getSize().y - 1);
        return this;
    }

    /**
     * Get the target of ghost
     * @return position
     */
    public Vector2D getTarget() {
        if (entity.mode == Ghost.Mode.CHASE) {
            return chasing.position.plus(chasing.direction.multiply(2)).minus(entity.position).multiply(2).plus(entity.position);
        }
        if (entity.mode == Ghost.Mode.SCATTER) {
            return corner;
        }
        return null;
    }
}
