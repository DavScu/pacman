package ghost;

import java.util.LinkedList;

public class World {
    private Map map;
    private Waka waka;
    private LinkedList<Ghost> ghosts = new LinkedList<>();
    private long startTime;
    private long frightenedStartTime;
    private int modeLength;
    private int frightenedLength;
    private Ghost.Mode mode = Ghost.Mode.SCATTER;
    private GameStatus status;

    /**
     * Constructor, initialize the start time
     */
    public World() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Set up the map
     * @param map map
     * @return this
     */
    public World setMap(Map map) {
        this.map = map;
        return this;
    }

    /**
     * Set up waka
     * @param waka waka
     * @return this
     */
    public World setWaka(Waka waka) {
        this.waka = waka;
        return this;
    }

    /**
     * Set the length of the mode
     * @param modeLength length
     * @return this
     */
    public World setModeLength(int modeLength) {
        this.modeLength = modeLength * 1000;
        return this;
    }

    /**
     * Set the length of the frighted
     * @param frightenedLength length of the frighted
     * @return this
     */
    public World setFrightenedLength(int frightenedLength) {
        this.frightenedLength = frightenedLength * 1000;
        return this;
    }

    /**
     * Set the sattus of the game
     * @param status status
     * @return this
     */
    public World setStatus(GameStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Add ghost
     * @param ghost ghost
     */
    public void addGhost(Ghost ghost) {
        ghosts.push(ghost);
    }

    /**
     * Determine whether waka crashed into the ghost
     * @param ghost ghost
     * @return whether waka crashed into the ghost
     */
    private boolean hasWakaHit(Ghost ghost) {
        return waka.position.equals(ghost.position) || (waka.position.equals(ghost.position.minus(ghost.direction)) && ghost.direction.plus(waka.direction).isZero());
    }

    /**
     * Process frame
     */
    public void processFrame() {
        long time = System.currentTimeMillis();
        if (mode == Ghost.Mode.FRIGHTENED) {
            if (time - frightenedStartTime < frightenedLength) {
                return;
            }
            startTime += time - frightenedStartTime;
            ghosts.forEach((ghost) -> ghost.isVisible = true);
        }
        boolean isScatter = ((time - startTime) / modeLength) % 2 == 0;
        Ghost.Mode nowMode;
        if (isScatter) {
            nowMode = Ghost.Mode.SCATTER;
        } else {
            nowMode = Ghost.Mode.CHASE;
        }
        if (nowMode == mode) {
            return;
        }
        mode = nowMode;
        ghosts.forEach((ghost) -> ghost.mode = mode);
    }

    /**
     * Reset entities
     */
    private void resetEntities() {
        waka.reset();
        ghosts.forEach((ghostForReset) -> ghostForReset.reset());
    }

    /**
     * Reset when waka won or lost
     * @param gameStatus game status
     */
    private void resetInWinOrLose(GameStatus.Status gameStatus) {
        status.status = gameStatus;
        status.reset();
        map.reset();
    }

    /**
     * Process different situations of step into different blocks
     */
    public void processSituation() {
        map.processBlock(waka.position, (block, position) -> {
            if (block == 7) {
                return 0;
            }
            if (block == 8 || block == 9) {
                long time = System.currentTimeMillis();
                if (mode == Ghost.Mode.FRIGHTENED) {
                    startTime += time - frightenedStartTime;
                }
                frightenedStartTime = time;
                mode = Ghost.Mode.FRIGHTENED;
                ghosts.forEach((ghost) -> ghost.mode = mode);
                if (block == 9) ghosts.forEach((ghost) -> ghost.isVisible = false);
                return 0;
            }
            return block;
        });
        if (map.isFruitEmpty()) {
            resetInWinOrLose(GameStatus.Status.WIN);
            resetEntities();
        }
    }

    /**
     * Process ghost movement
     * @param ghost ghost
     */
    public void processGhostMoved(Ghost ghost) {
        if (!hasWakaHit(ghost)) {
            return;
        }

        if (ghost.mode == Ghost.Mode.FRIGHTENED) {
            ghost.isEnabled = false;
        } else {
            status.lifeLeft -= 1;
            if (status.lifeLeft > 0) {
                status.status = GameStatus.Status.WAKAHIT;
            } else {
                resetInWinOrLose(GameStatus.Status.LOSE);
            }
            resetEntities();
        }
    }
}
