import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DaySixPartTwo {
    public static void main(String[] args) throws Exception {
        Path filePath = Path.of("day6input");
        List<String> lines = Files.readAllLines(filePath);

        System.out.println("Part Two Result: " + partTwo(lines));
    }

    public static int partTwo(List<String> lines) {
        int i = 0, x;
        // Locate the guard's starting position (^)
        for (; i < lines.size(); i++) {
            if (lines.get(i).contains("^"))
                break;
        }
        x = lines.get(i).indexOf("^");

        // Prepare initial guard direction (up)
        int[] directionMoving = { -1, 0 };

        // Initialize total possible obstruction positions
        int total = 0;

        // Iterate through all map positions to test obstructions
        for (int row = 0; row < lines.size(); row++) {
            for (int col = 0; col < lines.get(row).length(); col++) {
                char current = lines.get(row).charAt(col);

                // Skip invalid obstruction locations
                if (current == '#' || current == '^' || current == 'X')
                    continue;

                // Simulate placing an obstruction
                List<String> simulatedMap = new ArrayList<>(lines);
                StringBuilder rowBuilder = new StringBuilder(simulatedMap.get(row));
                rowBuilder.setCharAt(col, '#');
                simulatedMap.set(row, rowBuilder.toString());

                // Check if the guard forms a loop with this obstruction
                if (formsLoop(simulatedMap, i, x, directionMoving.clone())) {
                    total++;
                }
            }
        }
        return total;
    }

    public static boolean formsLoop(List<String> map, int startRow, int startCol, int[] direction) {
        Set<String> visited = new HashSet<>();
        int row = startRow, col = startCol;

        while (true) {
            // Record the current position and direction
            String state = row + "," + col + "," + direction[0] + "," + direction[1];
            if (visited.contains(state)) {
                return true; // Loop detected
            }
            visited.add(state);

            // Calculate next position
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            // Check if the next position is out of bounds
            if (newRow < 0 || newRow >= map.size() || newCol < 0 || newCol >= map.get(newRow).length()) {
                return false; // Guard leaves the map
            }

            char nextCell = map.get(newRow).charAt(newCol);

            if (nextCell == '#') {
                // Obstacle detected, turn right
                if (direction[0] == -1) { // Moving up
                    direction[0] = 0;
                    direction[1] = 1;
                } else if (direction[1] == 1) { // Moving right
                    direction[0] = 1;
                    direction[1] = 0;
                } else if (direction[0] == 1) { // Moving down
                    direction[0] = 0;
                    direction[1] = -1;
                } else { // Moving left
                    direction[0] = -1;
                    direction[1] = 0;
                }
            } else {
                // Move forward
                row = newRow;
                col = newCol;
            }
        }
    }
}
