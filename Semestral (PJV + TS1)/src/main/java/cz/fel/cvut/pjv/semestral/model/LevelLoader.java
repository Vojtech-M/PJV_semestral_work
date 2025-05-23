package cz.fel.cvut.pjv.semestral.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class is responsible for loading level data from a file.
 * It reads the level data from a specified path and converts it into a 2D array of integers.
 *
 *  The level data is expected to be in a specific format, where each line represents a row of tiles,
 *  and each number represents a tile type.
 *
 */
public class LevelLoader {
    static Logger log = Logger.getLogger(LevelLoader.class.getName());

    public static int[][] loadLevel(String path) {
        try {
            InputStream is = LevelLoader.class.getResourceAsStream(path);
            if (is == null) {
                log.warning("Level file not found: " + path);
                throw new RuntimeException("Level file not found: " + path);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            List<int[]> rows = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                int[] row = new int[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    row[i] = Integer.parseInt(tokens[i]);
                }
                rows.add(row);
            }
            reader.close();
            is.close();

            return rows.toArray(new int[0][]);
        } catch (Exception e) {
            log.warning("Failed to load level from " + path);
            throw new RuntimeException("Failed to load level from " + path);

        }
    }

}
