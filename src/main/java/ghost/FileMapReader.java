package ghost;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.HashMap;

public class FileMapReader {
    private Vector2D size;
    private static final HashMap<Character, Integer> ghostTypes = new HashMap<Character, Integer>() {{
        put('a', 5);
        put('c', 6);
        put('i', 7);
        put('w', 8);
    }};

    /**
     * The information of the map
     */
    public class MapInformation {
        public Waka waka;
        public Ghost ghosts[];
        public Map map;
    }

    /**
     * Constructor
     * @param size size of the map
     */
    public FileMapReader(Vector2D size) {
        this.size = size;
    }

    /**
     * Read the map
     * @param path path of the map
     * @return the information of the map
     * @throws FileNotFoundException file not found
     */
    public MapInformation readMapFromPath(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner fileReader = new Scanner(file);

        MapInformation result = new MapInformation();
        LinkedList<Ghost> ghosts = new LinkedList<>();
        int[] blocks = new int[size.x * size.y];
        int currentPosition = 0;
        for (int y = 0; y < size.y; y ++) {
            String row = fileReader.nextLine();
            for (int x = 0; x < size.x; x ++) {
                char rawBlock = row.charAt(x);
                int block = 0;
                Integer ghostType = ghostTypes.get(rawBlock);
                if (ghostType != null) {
                    ghosts.push((Ghost)new Ghost().setSpawnPoint(new Vector2D(x, y))
                            .setPosition(new Vector2D(x, y))
                            .setType(ghostType));
                } else if (rawBlock == 'p') {
                    result.waka = (Waka)new Waka()
                            .setSpawnPoint(new Vector2D(x, y))
                            .setPosition(new Vector2D(x, y))
                            .setType(0);
                } else {
                    block = Character.getNumericValue(rawBlock);
                }
                blocks[currentPosition ++] = block;
            }
        }
        result.ghosts = new Ghost[ghosts.size()];
        ghosts.toArray(result.ghosts);
        result.map = new Map().setSize(size).setBlocks(blocks);
        return result;
    }
}
