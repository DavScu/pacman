package ghost;

import processing.core.PApplet;
import java.io.FileNotFoundException;

public class App extends PApplet {

    private int WIDTH = 448;
    private int HEIGHT = 576;
    private ProcessingMapRenderer mapRenderer;
    private ProcessingEntitiesRenderer entitiesRenderer;
    private ProcessingDebugRenderer debugRenderer;
    private ProcessingUIRenderer uiRenderer;
    private World world;
    private GameStatus status;
    private boolean isInDebugMode = false;
    private WakaController controller;

    /**
     * Initialize ghosts
     * @param ghostType type of the ghost
     * @return ghostcontroller
     */
    private GhostController newGhostController(int ghostType) {
        switch (ghostType) {
            case 5:
                return new AmbusherController();
            case 6:
                return new ChaserController();
            case 7:
                return new IgnorantController();
            case 8:
                return new WhimController();
            default:
                return new GhostController();
        }
    }

    /**
     * Initialize map and world
     */
    public App() {
        FileMapReader reader = new FileMapReader(new Vector2D(28, 36));
        FileMapReader.MapInformation information;
        try {
            information = reader.readMapFromPath("map.txt");
        } catch (FileNotFoundException exception) {
            System.out.println("Map not found!!");
            return;
        }
        mapRenderer = new ProcessingMapRenderer(this).setMap(information.map).setBlockSize(new Vector2D(16, 16));
        status = new GameStatus(3);
        status.status = GameStatus.Status.NORMAL;
        world = new World().setMap(information.map).setWaka(information.waka).setModeLength(3).setFrightenedLength(8).setStatus(status);
        controller = new WakaController().setEntity(information.waka).setMap(information.map).addDirection(0, new Vector2D(0, 0)).addDirection(1, new Vector2D(0, -1)).addDirection(2, new Vector2D(-1, 0)).addDirection(3, new Vector2D(0, 1)).addDirection(4, new Vector2D(1, 0));
        entitiesRenderer = new ProcessingEntitiesRenderer(this).setBlockSize(new Vector2D(16, 16)).setSpeed(2).setStatus(status);
        debugRenderer = new ProcessingDebugRenderer(this).setBlockSize(new Vector2D(16, 16));
        uiRenderer = new ProcessingUIRenderer(this).setStatus(status).setOffset(new Vector2D(8, HEIGHT - 26)).setTipOffset(new Vector2D(WIDTH / 2, HEIGHT / 2)).setLifeIconWidth(28);
        for (Ghost ghost : information.ghosts) {
            GhostController controller = newGhostController(ghost.type).setMap(information.map).setEntity(ghost).chase(information.waka);
            entitiesRenderer.addEntity(ghost, () -> { controller.move();world.processGhostMoved(ghost); });
            world.addGhost(ghost);
            debugRenderer.addGhostController(controller);
        }
        entitiesRenderer.addEntity(information.waka, () -> { world.processSituation();controller.move(); });
    }

    /**
     * Load images
     */
    public void setup() {
        frameRate(60);
        stroke(255);
        textFont(createFont("PressStart2P-Regular.ttf", 32));
        fill(255);
        textAlign(CENTER);
        mapRenderer.bindImage(1, new Image(loadImage("horizontal.png"))).bindImage(2, new Image(loadImage("vertical.png"))).bindImage(3, new Image(loadImage("upLeft.png"))).bindImage(4, new Image(loadImage("upRight.png"))).bindImage(5, new Image(loadImage("downLeft.png"))).bindImage(6, new Image(loadImage("downRight.png"))).bindImage(7, new Image(loadImage("fruit.png"))).bindImage(8, new Image(loadImage("fruit.png")).resize(new Vector2D(32, 32)).setOffset(new Vector2D(-8, -8))).bindImage(9, new Image(loadImage("sodaCan.png")));
        Vector2D entityOffset = new Vector2D(-5, -5);
        Image playerRight = new Image(loadImage("playerRight.png")).setOffset(entityOffset);
        entitiesRenderer.bindImage(0, new Image(loadImage("playerClosed.png")).setOffset(entityOffset)).bindImage(1, new Image(loadImage("playerUp.png")).setOffset(entityOffset)).bindImage(2, new Image(loadImage("playerLeft.png")).setOffset(entityOffset)).bindImage(3, new Image(loadImage("playerDown.png")).setOffset(entityOffset)).bindImage(4, playerRight).bindImage(5, new Image(loadImage("ambusher.png")).setOffset(entityOffset)).bindImage(6, new Image(loadImage("chaser.png")).setOffset(entityOffset)).bindImage(7, new Image(loadImage("ignorant.png")).setOffset(entityOffset)).bindImage(8, new Image(loadImage("whim.png")).setOffset(entityOffset)).bindImage(9, new Image(loadImage("frightened.png")).setOffset(entityOffset));
        uiRenderer.setLifeImage(playerRight);
    }

    /**
     * Set the size
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Draw the game
     */
    public void draw() {
        world.processFrame();
        background(0, 0, 0);
        if (status.status == GameStatus.Status.NORMAL || status.status == GameStatus.Status.WAKAHIT) {
            mapRenderer.render();
            entitiesRenderer.render();
            if (isInDebugMode) {
                debugRenderer.render();
            }
        }
        uiRenderer.render();
    }

    /**
     * Detect the key pressing
     */
    public void keyPressed() {
        controller.processKeyCode(keyCode);
        if (keyCode == 32) {
            isInDebugMode = !isInDebugMode;
        }
    }

    /**
     * Main
     * @param args nah
     */
    public static void main(String[] args) {
        PApplet.main("ghost.App");
    }
}
