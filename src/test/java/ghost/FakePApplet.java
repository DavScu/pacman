package ghost;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.LinkedList;

public class FakePApplet extends PApplet {
    public class ImageRenderingArguments {
        PImage image;
        float x;
        float y;

        ImageRenderingArguments(PImage image, float x, float y) {
            this.image = image;
            this.x = x;
            this.y = y;
        }
    }

    public class TextRenderingArguments {
        String text;
        float x;
        float y;

        TextRenderingArguments(String text, float x, float y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }
    }

    public class LineRenderingArguments {
        float sx;
        float sy;
        float ex;
        float ey;

        LineRenderingArguments(float sx, float sy, float ex, float ey) {
            this.sx = sx;
            this.sy = sy;
            this.ex = ex;
            this.ey = ey;
        }
    }

    public LinkedList<ImageRenderingArguments> imageRenderingCalling = new LinkedList<ImageRenderingArguments>();

    public LinkedList<TextRenderingArguments> textRenderingCalling = new LinkedList<TextRenderingArguments>();

    public LinkedList<LineRenderingArguments> lineRenderingCalling = new LinkedList<LineRenderingArguments>();

    @Override
    public void image(PImage image, float x, float y) {
        imageRenderingCalling.push(new ImageRenderingArguments(image, x, y));
    }

    @Override
    public void text(String text, float x, float y) {
        textRenderingCalling.push(new TextRenderingArguments(text, x, y));
    }

    @Override
    public void line(float sx, float sy, float ex, float ey) {
        lineRenderingCalling.push(new LineRenderingArguments(sx, sy, ex, ey));
    }
}
