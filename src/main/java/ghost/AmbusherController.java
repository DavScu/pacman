package ghost;

public class AmbusherController extends GhostController {
    private Vector2D corner;

    /**
     * Set up the map
     * @param map map
     * @return this
     */
    public GhostController setMap(Map map) {
        super.setMap(map);
        corner = new Vector2D(map.getSize().x - 1, 0);
        return this;
    }

    /**
     * Get the target of the ambusher
     * @return the position of the target
     */
    public Vector2D getTarget() {
        if (entity.mode == Ghost.Mode.CHASE) {
            Vector2D target = chasing.position.plus(chasing.direction.multiply(4));
            map.limitEqualToMap(target);
            return target;
        }
        if (entity.mode == Ghost.Mode.SCATTER) {
            return corner;
        }
        return null;
    }
}
