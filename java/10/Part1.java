import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 {
    public static void main(String[] args) {
        List<String> lines = readFile();

        int total = 0;
        char temp = 47;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                Set<String> set = new HashSet<>();
                pathsStartingAt(lines, i, j, temp, set);
                if (set.size() > 0) {
                    // System.out.println("Paths starting at " + i + "-" + j + "= " + set.size());
                    total += set.size();
                }
            }
        }

        System.out.println(total);
    }

    public static void pathsStartingAt(List<String> lines, int i, int j, char prevChar, Set<String> set) {
        if (i < 0 || j < 0 || i >= lines.size() || j >= lines.get(i).length())
            return;

        if (lines.get(i).charAt(j) - prevChar != 1)
            return;

        char curChar = lines.get(i).charAt(j);

        if (curChar == '9') {
            set.add(i + "-" + j);
            return;
        }

        pathsStartingAt(lines, i + 1, j, curChar, set);
        pathsStartingAt(lines, i - 1, j, curChar, set);
        pathsStartingAt(lines, i, j + 1, curChar, set);
        pathsStartingAt(lines, i, j - 1, curChar, set);
    }

    public static List<String> readFile() {
        try {
            Path p;
            p = Path.of("input");
            // p = Path.of("sample1");
            // p = Path.of("sample2");
            return Files.readAllLines(p);
        } catch (Exception e) {
            System.out.println("ERROR READING FILE " + e.getMessage());
            throw new RuntimeException("");
        }
    }
}
