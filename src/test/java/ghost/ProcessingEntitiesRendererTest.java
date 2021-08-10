package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;

public class ProcessingEntitiesRendererTest {
    // setBlockSize should returns the object
    @Test
    public void settingBlockSizeTest() {
        FakePApplet applet = new FakePApplet();

        ProcessingEntitiesRenderer renderer =
                new ProcessingEntitiesRenderer(applet);

        ProcessingEntitiesRenderer newRenderer = renderer
                .setBlockSize(new Vector2D(16, 16));

        assertSame(renderer, newRenderer);
    }

    // bindImage should returns the object
    @Test
    public void bindingImageTest() {
        FakePApplet applet = new FakePApplet();
        FakeImage image = new FakeImage(new FakePImage());

        ProcessingEntitiesRenderer renderer =
                new ProcessingEntitiesRenderer(applet);
        ProcessingEntitiesRenderer newRenderer = renderer.bindImage(0, image);

        assertSame(renderer, newRenderer);
    }

    // setSpeed should returns the object
    @Test
    public void settingSpeedTest() {
        FakePApplet applet = new FakePApplet();

        ProcessingEntitiesRenderer renderer =
                new ProcessingEntitiesRenderer(applet);

        ProcessingEntitiesRenderer newRenderer = renderer.setSpeed(2);

        assertSame(renderer, newRenderer);
    }

    // addEntity should returns the object
    @Test
    public void addingEnityTest() {
        Entity entity = new Entity().setPosition(new Vector2D(0, 0)).setType(0);
        FakePApplet applet = new FakePApplet();

        ProcessingEntitiesRenderer renderer =
                new ProcessingEntitiesRenderer(applet).setBlockSize(new Vector2D(16, 16));

        ProcessingEntitiesRenderer newRenderer = renderer.addEntity(
                entity,
                () -> {}
        );

        assertSame(renderer, newRenderer);
    }

    // render should not render if there is no image
    @Test
    public void noImageRenderTest() {
        Entity entity = new Entity().setPosition(new Vector2D(0, 0)).setType(0);
        FakePApplet applet = new FakePApplet();
        FakeImage image = new FakeImage(new FakePImage());

        AtomicInteger moveStopedCallingCount = new AtomicInteger(0);

        ProcessingEntitiesRenderer renderer =
                new ProcessingEntitiesRenderer(applet)
                        .setBlockSize(new Vector2D(16, 16))
                        .setSpeed(9)
                        .addEntity(entity, () -> moveStopedCallingCount.incrementAndGet())
                        .setStatus(new GameStatus(0));

        entity.setPosition(new Vector2D(1, 0));
        renderer.render();
        assertSame(image.imageRenderingCalling.size(), 0);
    }

    private void testMoving(
            Vector2D start,
            Vector2D end,
            Vector2D imageBegin,
            Vector2D imageMiddle,
            Vector2D imageFinal
    ) {
        Entity entity = new Entity().setPosition(start).setType(0);
        FakePApplet applet = new FakePApplet();
        FakeImage image = new FakeImage(new FakePImage());

        ProcessingEntitiesRenderer renderer = new ProcessingEntitiesRenderer(applet)
                .setBlockSize(new Vector2D(16, 16))
                .setSpeed(9)
                .addEntity(entity, () -> {})
                .bindImage(0, image)
                .setStatus(new GameStatus(0));

        entity.setPosition(end);
        renderer.render();
        assertSame(image.imageRenderingCalling.size(), 1);

        FakeImage.ImageRenderingArguments arguments = image.imageRenderingCalling.pop();
        assertSame(arguments.applet, applet);
        assertTrue(arguments.position.equals(imageBegin));

        renderer.render();
        assertSame(image.imageRenderingCalling.size(), 1);

        arguments = image.imageRenderingCalling.pop();
        assertSame(arguments.applet, applet);
        assertTrue(arguments.position.equals(imageMiddle));

        renderer.render();
        assertSame(image.imageRenderingCalling.size(), 1);

        arguments = image.imageRenderingCalling.pop();
        assertSame(arguments.applet, applet);
        assertTrue(arguments.position.equals(imageFinal));
    }

    // render should render the image smoothly while moving forward
    @Test
    public void movingForwardRenderTest() {
        testMoving(
                new Vector2D(0, 0),
                new Vector2D(1, 0),
                new Vector2D(0, 0),
                new Vector2D(9, 0),
                new Vector2D(16, 0)
        );
    }

    // render should render the image smoothly while moving backward
    @Test
    public void movingBackwardRenderTest() {
        testMoving(
                new Vector2D(0, 1),
                new Vector2D(0, 0),
                new Vector2D(0, 16),
                new Vector2D(0, 7),
                new Vector2D(0, 0)
        );
    }

    // render should call the stop handler once if the entity didn't move
    @Test
    public void stopRenderTest() {
        Entity entity = new Entity().setPosition(new Vector2D(0, 0)).setType(0);
        FakePApplet applet = new FakePApplet();
        FakeImage image = new FakeImage(new FakePImage());

        AtomicInteger moveStopedCallingCount = new AtomicInteger(0);

        ProcessingEntitiesRenderer renderer =
                new ProcessingEntitiesRenderer(applet)
                        .setBlockSize(new Vector2D(16, 16))
                        .setSpeed(9)
                        .addEntity(entity, () -> moveStopedCallingCount.incrementAndGet())
                        .setStatus(new GameStatus(0));

        renderer.render();
        assertSame(moveStopedCallingCount.get(), 1);
    }

    // render should reset entities if waka hit
    @Test
    public void wakaHitTest() {
        Entity entity = new Entity().setPosition(new Vector2D(0, 0)).setType(0);
        FakePApplet applet = new FakePApplet();
        FakeImage image = new FakeImage(new FakePImage());
        GameStatus status = new GameStatus(0);

        ProcessingEntitiesRenderer renderer = new ProcessingEntitiesRenderer(applet)
                .setBlockSize(new Vector2D(16, 16))
                .setSpeed(9)
                .addEntity(entity, () -> {})
                .bindImage(0, image)
                .setStatus(status);

        entity.setPosition(new Vector2D(0, 1));
        status.status = GameStatus.Status.WAKAHIT;

        renderer.render();
        assertSame(image.imageRenderingCalling.size(), 1);

        FakeImage.ImageRenderingArguments arguments =
                image.imageRenderingCalling.pop();
        assertSame(arguments.applet, applet);
        assertTrue(arguments.position.equals(new Vector2D(0, 16)));
    }

    // render should not render invisible entity
    @Test
    public void invisibleTest() {
        Entity entity = new Entity()
                .setPosition(new Vector2D(0, 0))
                .setType(0);
        FakePApplet applet = new FakePApplet();
        FakeImage image = new FakeImage(new FakePImage());
        GameStatus status = new GameStatus(0);

        ProcessingEntitiesRenderer renderer = new ProcessingEntitiesRenderer(applet)
                .setBlockSize(new Vector2D(16, 16))
                .setSpeed(9)
                .addEntity(entity, () -> {})
                .bindImage(0, image)
                .setStatus(status);

        entity.setPosition(new Vector2D(0, 1));

        entity.isVisible = false;
        renderer.render();
        assertSame(image.imageRenderingCalling.size(), 0);

        entity.isVisible = true;
        renderer.render();
        assertSame(image.imageRenderingCalling.size(), 1);

        FakeImage.ImageRenderingArguments arguments = image.imageRenderingCalling.pop();
        assertSame(arguments.applet, applet);
        assertTrue(arguments.position.equals(new Vector2D(0, 9)));
    }

    // render should not move disabled entity
    @Test
    public void disabledTest() {
        Entity entity = new Entity().setPosition(new Vector2D(0, 0)).setType(0);
        FakePApplet applet = new FakePApplet();
        FakeImage image = new FakeImage(new FakePImage());
        GameStatus status = new GameStatus(0);

        ProcessingEntitiesRenderer renderer = new ProcessingEntitiesRenderer(applet)
                .setBlockSize(new Vector2D(16, 16))
                .setSpeed(9)
                .addEntity(entity, () -> {})
                .bindImage(0, image)
                .setStatus(status);

        entity.setPosition(new Vector2D(0, 1));

        entity.isEnabled = false;
        renderer.render();
        assertSame(image.imageRenderingCalling.size(), 0);

        entity.isEnabled = true;
        renderer.render();
        assertSame(image.imageRenderingCalling.size(), 1);

        FakeImage.ImageRenderingArguments arguments = image.imageRenderingCalling.pop();
        assertSame(arguments.applet, applet);
        assertTrue(arguments.position.equals(new Vector2D(0, 0)));
    }
}
