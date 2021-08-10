package ghost;

import processing.core.PApplet;
import java.util.LinkedList;

public class ProcessingDebugRenderer {
    private PApplet applet;
    private Vector2D blockSize;
    private LinkedList<GhostController> ghostControllers = new LinkedList<>();

    public ProcessingDebugRenderer(PApplet applet) {
        this.applet = applet;
    }

    /**
     * Set the size of the block
     * @param blockSize the length and the width of the block
     * @return this
     */
    public ProcessingDebugRenderer setBlockSize(Vector2D blockSize) {
        this.blockSize = blockSize;
        return this;
    }

    /**
     * Add ghost controller
     * @param controller controller
     */
    public void addGhostController(GhostController controller) {
        ghostControllers.push(controller);
    }

    /**
     * Render a controller
     * @param controller controller
     */
    private void renderSingleController(GhostController controller) {
        Vector2D target = controller.getTarget();
        if (target == null) {
            return;
        }
        Vector2D position = controller.getEntity().position;
        applet.line(
                target.x * blockSize.x,
                target.y * blockSize.y,
                position.x * blockSize.x,
                position.y * blockSize.y
        );
    }

    /**
     * Render each controller
     */

    public void render() {
        ghostControllers.forEach((controller) -> renderSingleController(controller));
    }
}
