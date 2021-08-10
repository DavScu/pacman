package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {
    static final Vector2D oneVector = new Vector2D(5, 5);

    // the constructor should set the x and y members
    @Test
    public void contructorTest() {
        int x = 1;
        int y = 2;
        Vector2D vector = new Vector2D(x, y);

        assertSame(vector.x, x);
        assertSame(vector.y, y);
    }

    // clone should creates a new vector
    @Test
    public void cloningTest() {
        int x = 1;
        int y = 2;
        Vector2D vector = new Vector2D(x, y);
        Vector2D clonedVector = vector.clone();

        assertNotSame(vector, clonedVector);
        assertSame(clonedVector.x, x);
        assertSame(clonedVector.y, y);
    }

    // plusEqual should plus the given vector to the origin vector
    @Test
    public void plusEqualTest() {
        int x = 1;
        int y = 2;
        Vector2D vector = new Vector2D(x, y);
        vector.plusEqual(oneVector);

        assertSame(vector.x, x + oneVector.x);
        assertSame(vector.y, y + oneVector.y);
    }

    // plusEqual should minus the given vector from the origin vector
    @Test
    public void minusEqualTest() {
        int x = 1;
        int y = 2;
        Vector2D vector = new Vector2D(x, y);
        vector.minusEqual(oneVector);

        assertSame(vector.x, x - oneVector.x);
        assertSame(vector.y, y - oneVector.y);
    }

    // plus should return the sum of two vectors
    @Test
    public void plusingTest() {
        int x = 1;
        int y = 2;
        Vector2D vector = new Vector2D(x, y);
        Vector2D newVector = vector.plus(oneVector);

        assertNotSame(vector, newVector);

        assertSame(vector.x, x);
        assertSame(vector.y, y);
        assertSame(newVector.x, x + oneVector.x);
        assertSame(newVector.y, y + oneVector.y);
    }

    // minus should return the result of the vector minusing the given vector
    @Test
    public void minusingTest() {
        int x = 1;
        int y = 2;
        Vector2D vector = new Vector2D(x, y);
        Vector2D newVector = vector.minus(oneVector);

        assertNotSame(vector, newVector);

        assertSame(vector.x, x);
        assertSame(vector.y, y);
        assertSame(newVector.x, x - oneVector.x);
        assertSame(newVector.y, y - oneVector.y);
    }

    // multiplyEqual should multiply the given scale with the origin vector
    @Test
    public void multiplyEqualTest() {
        int x = 1;
        int y = 2;
        Vector2D vector = new Vector2D(x, y);
        int scale = 2;
        vector.multiplyEqual(scale);

        assertSame(vector.x, x * scale);
        assertSame(vector.y, y * scale);
    }

    // multiply should return the result of the vector multiplying the scale
    @Test
    public void multiplyingTest() {
        int x = 1;
        int y = 2;
        Vector2D vector = new Vector2D(x, y);
        int scale = 2;
        Vector2D newVector = vector.multiply(scale);

        assertNotSame(vector, newVector);

        assertSame(vector.x, x);
        assertSame(vector.y, y);
        assertSame(newVector.x, x * scale);
        assertSame(newVector.y, y * scale);
    }

    // isZero should return true when both x and y is zero
    @Test
    public void isZeroBothZeroTest() {
        assertTrue(new Vector2D(0, 0).isZero());
    }

    // isZero should return false when x is not zero
    @Test
    public void isZeroXNotZeroTest() {
        assertFalse(new Vector2D(1, 0).isZero());
    }

    // isZero should return false when y is not zero
    @Test
    public void isZeroYNotZeroTest() {
        assertFalse(new Vector2D(0, 1).isZero());
    }

    // squareOfEuclideanDistance should return the squrare of its length
    @Test
    public void squareOfEuclideanDistanceTest() {
        int x = 1;
        int y = 2;
        Vector2D vector = new Vector2D(x, y);
        assertSame(vector.squareOfEuclideanDistance(), 5);
    }

    // two vectors with same value should be the same
    @Test
    public void vectorWthSameValueShouldEqualsTest() {
        Vector2D vector = new Vector2D(oneVector.x, oneVector.y);
        assertTrue(vector.equals(oneVector));
    }

    // two vectors are not equals if their x members are different
    @Test
    public void vectorWthDifferentXShouldNotEqualsTest() {
        Vector2D vector = new Vector2D(oneVector.x + 1, oneVector.y);
        assertFalse(vector.equals(oneVector));
    }

    // two vectors are not equals if their y members are different
    @Test
    public void vectorWthDifferentYShouldNotEqualsTest() {
        Vector2D vector = new Vector2D(oneVector.x, oneVector.y + 1);
        assertFalse(vector.equals(oneVector));
    }
}
