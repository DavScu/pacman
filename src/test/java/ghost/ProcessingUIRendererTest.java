package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProcessingUIRendererTest {
    // methods for setting properties should return the obejct
    @Test
    public void settingPropertiesTest() {
        ProcessingUIRenderer renderer = new ProcessingUIRenderer(new FakePApplet());

        ProcessingUIRenderer newRenderer = renderer.setLifeIconWidth(1);
        assertSame(renderer, newRenderer);

        newRenderer = renderer.setLifeImage(new FakeImage(new FakePImage()));
        assertSame(renderer, newRenderer);

        newRenderer = renderer.setStatus(new GameStatus(0));
        assertSame(renderer, newRenderer);

        newRenderer = renderer.setOffset(new Vector2D(0, 0));
        assertSame(renderer, newRenderer);

        newRenderer = renderer.setTipOffset(new Vector2D(0, 0));
        assertSame(renderer, newRenderer);
    }

    private void testLifeIconRendering(GameStatus.Status statusStatus) {
        FakePApplet applet = new FakePApplet();
        FakeImage image = new FakeImage(new FakePImage());
        GameStatus status = new GameStatus(2);
        status.status = statusStatus;

        ProcessingUIRenderer renderer = new ProcessingUIRenderer(applet)
                .setLifeIconWidth(16)
                .setLifeImage(image)
                .setStatus(status)
                .setOffset(new Vector2D(0, 0));

        renderer.render();

        assertSame(image.imageRenderingCalling.size(), 2);

        FakeImage.ImageRenderingArguments arguments =
                image.imageRenderingCalling.pop();
        assertSame(arguments.applet, applet);
        assertTrue(arguments.position.equals(new Vector2D(16, 0)));
        arguments = image.imageRenderingCalling.pop();
        assertSame(arguments.applet, applet);
        assertTrue(arguments.position.equals(new Vector2D(0, 0)));
    }

    // render function should render life left in normal status
    @Test
    public void normalLifeIconRenderingTest() {
        testLifeIconRendering(GameStatus.Status.NORMAL);
    }

    // render function should render life left in wakahit status
    @Test
    public void wakahitLifeIconRenderingTest() {
        testLifeIconRendering(GameStatus.Status.WAKAHIT);
    }

    private void tipRenderingTest(GameStatus.Status statusStatus, String tip) {
        FakePApplet applet = new FakePApplet();
        FakeImage image = new FakeImage(new FakePImage());
        GameStatus status = new GameStatus(0);
        status.status = statusStatus;

        ProcessingUIRenderer renderer = new ProcessingUIRenderer(applet)
                .setStatus(status)
                .setTipOffset(new Vector2D(0, 0));

        renderer.render();

        assertSame(applet.textRenderingCalling.size(), 1);

        FakePApplet.TextRenderingArguments arguments = applet.textRenderingCalling.pop();
        assertSame(arguments.text, tip);
        System.out.println(arguments.x);
        assertEquals(arguments.x, 0);
        assertEquals(arguments.y, (float)0);
    }

    // render function should render win tips in win status
    @Test
    public void winTipRenderingTest() {
        tipRenderingTest(GameStatus.Status.WIN, "YOU WIN");
    }

    // render function should render win tips in win status
    @Test
    public void loseTipRenderingTest() {
        tipRenderingTest(GameStatus.Status.LOSE, "GAME OVER");
    }
}
