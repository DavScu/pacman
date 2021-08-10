package ghost;

import processing.core.PImage;

import java.util.LinkedList;

public class FakePImage extends PImage {
    public LinkedList<Vector2D> resizingCalling = new LinkedList<Vector2D>();

    @Override
    public void resize(int w, int h) {
        resizingCalling.push(new Vector2D(w, h));
    }
}
