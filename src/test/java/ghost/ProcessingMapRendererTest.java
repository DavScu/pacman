package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;

public class ProcessingMapRendererTest {
    // setBlockSize should returns the object
    @Test
    public void settingBlockSizeTest() {
        FakePApplet applet = new FakePApplet();

        ProcessingMapRenderer renderer = new ProcessingMapRenderer(applet);

        ProcessingMapRenderer newRenderer = renderer.setBlockSize(new Vector2D(16, 16));

        assertSame(renderer, newRenderer);
    }

    // bindImage should returns the object
    @Test
    public void bindingImageTest() {
        FakePApplet applet = new FakePApplet();
        FakeImage image = new FakeImage(new FakePImage());

        ProcessingMapRenderer renderer =
                new ProcessingMapRenderer(applet);

        ProcessingMapRenderer newRenderer = renderer.bindImage(0, image);

        assertSame(renderer, newRenderer);
    }

    // setMap should returns the object
    @Test
    public void settingMapTest() {
        FakePApplet applet = new FakePApplet();
        Map map = new Map();

        ProcessingMapRenderer renderer = new ProcessingMapRenderer(applet);

        ProcessingMapRenderer newRenderer = renderer.setMap(map);

        assertSame(renderer, newRenderer);
    }

    // render should render all blocks
    @Test
    public void renderingTest() {
        FakePApplet applet = new FakePApplet();
        int blocks[] = {
                0, 1,
                2, 3
        };
        Map map = new Map()
                .setBlocks(blocks)
                .setSize(new Vector2D(2, 2));

        FakeImage image1 = new FakeImage(new FakePImage());

        ProcessingMapRenderer renderer = new ProcessingMapRenderer(applet)
                .setMap(map)
                .setBlockSize(new Vector2D(16, 16))
                .bindImage(1, image1);

        renderer.render();
        assertSame(image1.imageRenderingCalling.size(), 1);

        FakeImage.ImageRenderingArguments arguments = image1.imageRenderingCalling.pop();
        assertSame(arguments.applet, applet);
        assertTrue(arguments.position.equals(new Vector2D(16, 0)));
    }
}
