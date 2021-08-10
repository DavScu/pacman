
package ghost;

import processing.core.PApplet;
import processing.core.PFont;

public class ProcessingUIRenderer {
    private Image lifeImage;
    private PFont font;
    private PApplet applet;
    private int lifeIconWidth;
    private Vector2D offset;
    private Vector2D tipOffset;
    private GameStatus status;
    private long tipScreenStartTime = 0;

    /**
     * Constructor
     * @param applet PApplet
     */
    public ProcessingUIRenderer(PApplet applet) {
        this.applet = applet;
    }

    /**
     * Set the width of the life icon
     * @param width teh width of the life icon
     * @return this
     */
    public ProcessingUIRenderer setLifeIconWidth(int width) {
        lifeIconWidth = width;
        return this;
    }

    /**
     * Set the image of the life icon
     * @param image the image of trh life icon
     * @return this
     */
    public ProcessingUIRenderer setLifeImage(Image image) {
        lifeImage = image;
        return this;
    }

    /**
     * Set the status of the game
     * @param status status of the game
     * @return this
     */
    public ProcessingUIRenderer setStatus(GameStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Set the offset
     * @param offset offset
     * @return this
     */
    public ProcessingUIRenderer setOffset(Vector2D offset) {
        this.offset = offset;
        return this;
    }

    /**
     * Set tip offset
     * @param offset tipoffset
     * @return this
     */
    public ProcessingUIRenderer setTipOffset(Vector2D offset) {
        tipOffset = offset;
        return this;
    }

    /**
     * Render the image in this class
     */
    public void render() {
        if (status.status == GameStatus.Status.NORMAL || status.status == GameStatus.Status.WAKAHIT) {
            Vector2D position = offset.clone();
            for (int i = 0; i < status.lifeLeft; ++i) {
                lifeImage.render(applet, position);
                position.x += lifeIconWidth;
            }
        } else {
            if (status.status == GameStatus.Status.LOSE) {
                applet.text("GAME OVER", tipOffset.x, tipOffset.y);
            } else if (status.status == GameStatus.Status.WIN) {
                applet.text("YOU WIN", tipOffset.x, tipOffset.y);
            }
            long time = System.currentTimeMillis();
            if (tipScreenStartTime == 0) {
                tipScreenStartTime = time;
            }
            if (time - tipScreenStartTime > 10000) {
                tipScreenStartTime = 0;
                status.status = GameStatus.Status.WAKAHIT;
            }
        }
    }
}
