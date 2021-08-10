package ghost;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.LinkedList;

public class FakeImage extends Image {
    class ImageRenderingArguments {
        PApplet applet;
        Vector2D position;

        ImageRenderingArguments(PApplet applet, Vector2D position) {
            this.applet = applet;
            this.position = position.clone();
        }
    }

    public FakeImage(PImage image) {
        super(image);
    }

    public LinkedList<ImageRenderingArguments> imageRenderingCalling =
            new LinkedList<ImageRenderingArguments>();

    @Override
    public void render(PApplet applet, Vector2D position) {
        imageRenderingCalling.push(new ImageRenderingArguments(applet, position));
    }
}
