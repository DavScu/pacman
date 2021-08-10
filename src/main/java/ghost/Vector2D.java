package ghost;

public class Vector2D {
    public int x;
    public int y;

    /**
     * Constructor
     * @param x x
     * @param y y
     */
    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Clone the vector2d
     * @return The clone of the vector2D
     */
    public Vector2D clone() {
        return new Vector2D(x, y);
    }

    /**
     * Plus 2 vector2D
     * @param vector Added vector
     */
    public void plusEqual(Vector2D vector) {
        x += vector.x;
        y += vector.y;
    }

    /**
     * Use clone to add 2 vector2D
     * @param vector Added vector
     * @return the result of this calculation
     */
    public Vector2D plus(Vector2D vector) {
        Vector2D result = clone();
        result.plusEqual(vector);
        return result;
    }

    /**
     * Minus 2 vector2D
     * @param vector Minus vector
     */
    public void minusEqual(Vector2D vector) {
        x -= vector.x;
        y -= vector.y;
    }

    /**
     * Use clone to minus 2 vector2D
     * @param vector minus vector
     * @return the result of this calculation
     */
    public Vector2D minus(Vector2D vector) {
        Vector2D result = clone();
        result.minusEqual(vector);
        return result;
    }

    /**
     * Multiply a vector2D
     * @param scale scale of this calculation
     */
    public void multiplyEqual(int scale) {
        x *= scale;
        y *= scale;
    }

    /**
     * Use clone to multiply a vector2D
     * @param scale the scale of this calculation
     * @return this result of this calculation
     */
    public Vector2D multiply(int scale) {
        Vector2D result = clone();
        result.multiplyEqual(scale);
        return result;
    }

    /**
     * Find whether this vector2D equals to the other one
     * @param vector compare vector2D
     * @return whether they are the same
     */
    public Boolean equals(Vector2D vector) {
        return x == vector.x && y == vector.y;
    }

    /**
     * Find out whether the vector2D is 0, 0
     * @return whether the vector2D is 0, 0
     */
    public Boolean isZero() {
        return x == 0 && y == 0;
    }

    /**
     * Calculate the sum of the square of x and y
     * @return the result of the calculation
     */
    public int squareOfEuclideanDistance() {
        return x * x + y * y;
    }
}
