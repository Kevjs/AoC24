import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws Exception {
        Path filePath = Path.of("raw_input_2");
        // Path filePath = Path.of("part_2_sample_input");
        
        List<String> lines = Files.readAllLines(filePath);

        int total = 0;

        for (int i = 1; i < lines.size() - 1; i++) {
            for (int j = 1; j < lines.get(i).length() - 1; j++) {
                total += amountStartingAt(lines, i, j);
            }
        }

        System.out.println("TOTAL: " + total);
    }

    public static int amountStartingAt(List<String> lines, int i, int j) {
        if(lines.get(i).charAt(j) != 'A') return 0;

        String one = lines.get(i - 1).charAt(j - 1) + "" + lines.get(i + 1).charAt(j + 1);
        String two = lines.get(i - 1).charAt(j + 1) + "" + lines.get(i + 1).charAt(j - 1);

        if(one.indexOf("M") == -1 || one.indexOf("S") == -1
           || two.indexOf("M") == -1 || two.indexOf("S") == -1
        ) {
            return 0;
        }

        return 1;
    }
}
