package ghost;

public class IgnorantController extends GhostController {
    private Vector2D corner;

    /**
     * Set up the map
     * @param map map
     * @return this
     */
    public GhostController setMap(Map map) {
        super.setMap(map);
        corner = new Vector2D(0, map.getSize().y - 1);
        return this;
    }

    /**
     * Get the target of ignorant
     * @return position
     */
    public Vector2D getTarget() {
        if (entity.mode == Ghost.Mode.CHASE && chasing.position.minus(entity.position).squareOfEuclideanDistance() > 64) {
            return chasing.position;
        }
        if (entity.mode == Ghost.Mode.SCATTER) {
            return corner;
        }
        return null;
    }
}
