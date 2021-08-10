package ghost;

interface BlockHandler {
    int process(int block, Vector2D position);
}

public class Map {
    private Vector2D size;
    private int originFruitCount;
    private int fruitCount;
    private int originBlocks[];
    private int blocks[];

    /**
     * Set the size of the map
     * @param size height and width of the map
     * @return this
     */
    public Map setSize(Vector2D size) {
        this.size = size;
        return this;
    }

    /**
     * Get the size
     * @return size
     */
    public Vector2D getSize() {
        return size;
    }

    /**
     * Set the blocks
     * @param blocks blocks
     * @return this
     */
    public Map setBlocks(int[] blocks) {
        originBlocks = blocks;
        originFruitCount = 0;
        for (int i = 0; i < blocks.length; i ++) {
            if (isFruit(blocks[i])) {
                originFruitCount ++;
            }
        }
        reset();
        return this;
    }

    /**
     * Get the block in specific position
     * @param position coordinate
     * @return block
     */
    public int getBlock(Vector2D position) {
        return this.blocks[position.y * size.x + position.x];
    }

    /**
     * Set the limitation
     * @param position coordinate
     */
    public void limitEqualToMap(Vector2D position) {
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.y < 0) {
            position.y = 0;
        }
        if (position.x >= size.x) {
            position.x = size.x - 1;
        }
        if (position.y >= size.y) {
            position.y = size.y - 1;
        }
    }

    /**
     * Find out whether the block is fruit
     * @param block block
     * @return whether the block is fruit
     */
    public boolean isFruit(int block) {
        return block == 7 || block == 8 || block == 9;
    }

    /**
     * Maintain the count of the fruit
     * @param origin block
     * @param newBlock new block
     */
    private void maintainFruitCount(int origin, int newBlock) {
        if (isFruit(origin)) {
            fruitCount -= 1;
        }
        if (isFruit(newBlock)) {
            fruitCount += 1;
        }
    }

    /**
     * Handle each block
     * @param handler helper
     */
    public void forEachBlock(BlockHandler handler) {
        int currentPosition = 0;
        Vector2D position = new Vector2D(0, 0);
        for (position.y = 0; position.y < size.y; position.y ++) {
            for (position.x = 0; position.x < size.x; position.x ++) {
                int origin = blocks[currentPosition];
                int newBlock = handler.process(origin, position);
                blocks[currentPosition] = newBlock;
                maintainFruitCount(origin, newBlock);
                currentPosition ++;
            }
        }
    }

    /**
     * Process the functionality of each block
     * @param position the coordinate of the block
     * @param handler helper
     */
    public void processBlock(Vector2D position, BlockHandler handler) {
        int index = position.y * size.x + position.x;
        int origin = blocks[index];
        int newBlock = handler.process(origin, position);
        blocks[index] = newBlock;
        maintainFruitCount(origin, newBlock);
    }

    /**
     * Reset the map
     */
    public void reset() {
        blocks = originBlocks.clone();
        fruitCount = originFruitCount;
    }

    /**
     * Find out whether the fruit is empty
     * @return whether the fruit is empty
     */
    public boolean isFruitEmpty() {
        System.out.println(fruitCount);
        return fruitCount == 0;
    }
}
