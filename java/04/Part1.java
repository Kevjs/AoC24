import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws Exception {
        Path filePath = Path.of("raw_input_1");
        // Path filePath = Path.of("part_1_sample_input");
        // Path filePath = Path.of("part_1_sample_input2");
        // Path filePath = Path.of("part_1_sample_input3");
        List<String> lines = Files.readAllLines(filePath);

        int total = 0;

        for (int i = 0; i < lines.size(); i++) {
        for (int j = 0; j < lines.get(i).length(); j++) {
        total += amountStartingAt(lines, i, j);
        }
        }

        System.out.println("TOTAL: " + total);
    }

    public static int amountStartingAt(List<String> lines, int i, int j) {
        int amount = 0;
        String searchTerm = "XMAS";
        int[][] directions = new int[][] {
                { 1, 0 },
                { 1, 1 },
                { -1, 1 },
                { 1, -1 },
                { 0, 1 },
                { -1, -1 },
                { 0, -1 },
                { -1, 0 },
        };

        directionLoop: for (int[] direction : directions) {
            for (int z = 0; z < searchTerm.length(); z++) {
                int currentI = (i + z * direction[0]);
                int currentJ = (j + z * direction[1]);
                // System.out.println("Current POS " + currentI + "|" + currentJ);
                if (currentI < 0 || currentI >= lines.size()) {
                    // System.out.println("I out of bounds");
                    continue directionLoop;
                }
                if (currentJ < 0 || currentJ >= lines.get(currentI).length()) {
                    // System.out.println("J out of bounds");
                    continue directionLoop;
                }
                if (lines.get(currentI).charAt(currentJ) != searchTerm.charAt(z)) {
                    // System.out.println(lines.get(currentI).charAt(currentJ) + " does not equal " +
                    // searchTerm.charAt(z));
                    continue directionLoop;
                }
                // System.out.println("CONTINUE: " + lines.get(currentI).charAt(currentJ) + " equals " + searchTerm.charAt(z));
            }
            amount++;
        }

        return amount;
    }
}
