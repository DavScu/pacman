package ghost;

import processing.core.PImage;
import processing.core.PApplet;

public class Image {
    public PImage image;
    public Vector2D offset;

    /**
     * Constructor
     * @param image image
     */
    public Image(PImage image) {
        this.image = image;
    }

    /**
     * Set the offset
     * @param offset offset
     * @return this
     */
    public Image setOffset(Vector2D offset) {
        this.offset = offset;
        return this;
    }

    /**
     * Resize the image
     * @param size size of the image
     * @return this
     */
    public Image resize(Vector2D size) {
        image.resize(size.x, size.y);
        return this;
    }

    /**
     * Render the image
     * @param applet Applet
     * @param position position of the image
     */
    public void render(PApplet applet, Vector2D position) {
        Vector2D realPosition = position.clone();
        if (offset != null) {
            realPosition.plusEqual(offset);
        }
        applet.image(image, realPosition.x, realPosition.y);
    }
}
