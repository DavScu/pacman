package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProcessingDebugRendererTest {
    // render function should render line from target to controller position
    @Test
    public void lineRenderingTest() {
        FakePApplet applet = new FakePApplet();
        ProcessingDebugRenderer renderer =
                new ProcessingDebugRenderer(applet).setBlockSize(new Vector2D(16, 16));

        Ghost entity = (Ghost)new Ghost().setPosition(new Vector2D(0, 0));
        Entity chasing = new Entity().setPosition(new Vector2D(1, 0));
        GhostController controller = new GhostController()
                .setEntity(entity)
                .chase(chasing);
        renderer.addGhostController(controller);

        renderer.render();
        assertSame(applet.lineRenderingCalling.size(), 1);

        FakePApplet.LineRenderingArguments arguments =
                applet.lineRenderingCalling.pop();

        assertEquals(arguments.sx, 16);
        assertEquals(arguments.sy, 0);
        assertEquals(arguments.ex, 0);
        assertEquals(arguments.ey, 0);
    }

    // render function should render nothing if there is no target
    @Test
    public void noLineRenderingTest() {
        FakePApplet applet = new FakePApplet();
        ProcessingDebugRenderer renderer = new ProcessingDebugRenderer(applet).setBlockSize(new Vector2D(16, 16));

        Ghost entity = (Ghost)new Ghost().setPosition(new Vector2D(0, 0));
        entity.mode = Ghost.Mode.FRIGHTENED;
        GhostController controller = new GhostController().setEntity(entity);
        renderer.addGhostController(controller);

        renderer.render();
        assertSame(applet.lineRenderingCalling.size(), 0);
    }
}
