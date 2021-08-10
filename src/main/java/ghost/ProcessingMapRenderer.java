package ghost;

import processing.core.PApplet;
import java.util.HashMap;

public class ProcessingMapRenderer {
    private HashMap<Integer, Image> images = new HashMap<>();
    private PApplet applet;
    private Map map;
    private Vector2D blockSize;

    /**
     * Constructor
     * @param applet PApplet
     */
    public ProcessingMapRenderer(PApplet applet) {
        this.applet = applet;
    }

    /**
     * Set up the map
     * @param map map
     * @return this
     */
    public ProcessingMapRenderer setMap(Map map) {
        this.map = map;
        return this;
    }

    /**
     * Set the size of the block
     * @param blockSize the length and the width of the block
     * @return this
     */
    public ProcessingMapRenderer setBlockSize(Vector2D blockSize) {
        this.blockSize = blockSize;
        return this;
    }

    /**
     * Bind the image of the block
     * @param block block
     * @param image Image of the block
     * @return this
     */
    public ProcessingMapRenderer bindImage(int block, Image image) {
        images.put(block, image);
        return this;
    }

    /**
     * Render a single block
     * @param block block
     * @param position the coordinate of the block
     * @return block
     */
    private int renderSingleBlock(int block, Vector2D position) {
        Image image = images.get(block);
        if (image != null) {
            Vector2D imagePosition = new Vector2D(position.x * blockSize.x, position.y * blockSize.y);
            image.render(applet, imagePosition);
        }
        return block;
    }

    /**
     * Render each block
     */
    public void render() {
        map.forEachBlock((block, position) -> renderSingleBlock(block, position));
    }
}
