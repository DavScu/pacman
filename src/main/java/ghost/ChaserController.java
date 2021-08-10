package ghost;

public class ChaserController extends GhostController {
    private Vector2D corner = new Vector2D(0, 0);

    /**
     * get the target
     * @return coordinate of the target
     */
    public Vector2D getTarget() {
        if (entity.mode == Ghost.Mode.CHASE) {
            return chasing.position;
        }
        if (entity.mode == Ghost.Mode.SCATTER) {
            return corner;
        }
        return null;
    }
}
