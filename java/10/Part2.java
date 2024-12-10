import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Part2 {
    public static void main(String[] args) {
        List<String> lines = readFile();

        int total = 0;
        char temp = 47;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                int c = pathsStartingAt(lines, i, j, temp);
                if(c > 0) {
                    // System.out.println("Paths starting at " + i + "-" + j + "= " + c);
                    total += c;
                }
            }
        }

        System.out.println(total);
    }

    public static int pathsStartingAt(List<String> lines, int i, int j, char prevChar) {
        if (i < 0 || j < 0 || i >= lines.size() || j >= lines.get(i).length())
            return 0;

        if (lines.get(i).charAt(j) - prevChar != 1)
            return 0;

        char curChar = lines.get(i).charAt(j);

        if (curChar == '9') {
            return 1;
        }

        return pathsStartingAt(lines, i + 1, j, curChar)
                + pathsStartingAt(lines, i - 1, j, curChar)
                + pathsStartingAt(lines, i, j + 1, curChar)
                + pathsStartingAt(lines, i, j - 1, curChar);
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
