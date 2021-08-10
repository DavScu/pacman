package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ImageTest {
    // the constructor should construct the object with the given image
    @Test
    public void constructorTest() {
        FakePImage pImage = new FakePImage();

        Image image = new Image(pImage);

        assertSame(image.image, pImage);
    }

    // setOffset should set the offfset member and return the object
    @Test
    public void settingOffsetTest() {
        FakePImage pImage = new FakePImage();
        Vector2D offset = new Vector2D(0, 0);

        Image image = new Image(pImage);
        Image newImage = image.setOffset(offset);

        assertSame(image, newImage);
        assertSame(image.offset, offset);
    }

    // resize should invoke the resize method of the image and return the objet
    @Test
    public void resizingTest() {
        int resizeX = 5;
        int resizeY = 6;
        FakePImage pImage = new FakePImage();

        Image image = new Image(pImage);
        Image newImage = image.resize(new Vector2D(resizeX, resizeY));

        assertSame(image, newImage);
        assertSame(pImage.resizingCalling.size(), 1);

        Vector2D arguments = pImage.resizingCalling.get(0);
        assertSame(arguments.x, resizeX);
        assertSame(arguments.y, resizeY);
    }

    // render should invoke the image method of the applet by
    // the position if the offset member is null
    @Test
    public void imageRenderingWithoutOffsetTest() {
        Vector2D position = new Vector2D(100, 200);
        FakePImage pImage = new FakePImage();
        FakePApplet pApplet = new FakePApplet();

        Image image = new Image(pImage);
        image.render(pApplet, position);

        assertSame(pApplet.imageRenderingCalling.size(), 1);

        FakePApplet.ImageRenderingArguments arguments =
                pApplet.imageRenderingCalling.get(0);
        assertSame(arguments.image, pImage);
        assertEquals(arguments.x, position.x);
        assertEquals(arguments.y, position.y);
    }

    // render should invoke the image method of the applet by
    // the position with offset if it is not null
    @Test
    public void imageRenderingWithOffsetTest() {
        Vector2D offset = new Vector2D(5, 6);
        Vector2D position = new Vector2D(100, 200);
        FakePImage pImage = new FakePImage();
        FakePApplet pApplet = new FakePApplet();

        Image image = new Image(pImage).setOffset(offset);
        image.render(pApplet, position);

        assertSame(pApplet.imageRenderingCalling.size(), 1);

        FakePApplet.ImageRenderingArguments arguments =
                pApplet.imageRenderingCalling.get(0);
        Vector2D realPosition = offset.plus(position);
        assertSame(arguments.image, pImage);
        assertEquals(arguments.x, realPosition.x);
        assertEquals(arguments.y, realPosition.y);
    }
}
