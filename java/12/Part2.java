import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part2 {
    public static void main(String[] args) {
        List<String> lines = readInput();

        BigInteger total = new BigInteger("0");

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (lines.get(i).charAt(j) != '.') {

                    // System.out.println("Starting at " + i + "-" + j);
                    // for (String lineee : lines) {
                    // System.out.println(lineee);
                    // }
                    Price p = getPrice(lines, lines.get(i).charAt(j), i, j);

                    // System.out.println("-------------");

                    total = total.add(new BigInteger(p.getPricePerSide() + ""));
                }
            }
        }

        System.out.println("TOTAL: " + total);
    }

    public static Price getPrice(List<String> lines, char c, int i, int j) {
        if (i < 0 || j < 0 || i >= lines.size() || j >= lines.get(i).length()) {
            return null;
        }

        if (lines.get(i).charAt(j) != c) {
            return null;
        }

        StringBuilder sb = new StringBuilder(lines.get(i));
        sb.setCharAt(j, '.');
        lines.set(i, sb.toString());

        Price thisPrice = new Price();

        thisPrice.coords.add(i + "-" + j);

        thisPrice.addPrice(getPrice(lines, c, i - 1, j));
        thisPrice.addPrice(getPrice(lines, c, i + 1, j));
        thisPrice.addPrice(getPrice(lines, c, i, j - 1));
        thisPrice.addPrice(getPrice(lines, c, i, j + 1));

        return thisPrice;
    }

    public static List<String> readInput() {
        Path p = Path.of("input");
        // p = Path.of("example");
        // p = Path.of("example2");
        try {
            return Files.readAllLines(p);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read input file.");
        }
    }
}

@FunctionalInterface
interface CoordUpdater {
    int[] update(int i, int j);
}

@FunctionalInterface
interface CheckIfCoordIsThere {
    boolean isThere(int[] initialCoordinates);
}

@FunctionalInterface
interface VisitFunc {
    void visit(int[] coordinates);
}

class Price {
    List<String> coords;

    private void runRight(int i, int j, Set<String> visited) {
        int runnerI = i - 1;
        while (true) {
            String edge = runnerI + "-" + (j + 1) + "v";
            String nextCoordinate = runnerI + "-" + (j + 1);
            String currentCoordinate = runnerI + "-" + j;
            if (!coords.contains(currentCoordinate) || coords.contains(nextCoordinate) || visited.contains(edge)) {
                break;
            }
            visited.add(edge);
            runnerI--;
        }
        runnerI = i + 1;
        while (true) {
            String edge = runnerI + "-" + (j + 1) + "v";
            String nextCoordinate = runnerI + "-" + (j + 1);
            String currentCoordinate = runnerI + "-" + j;
            if (!coords.contains(currentCoordinate) || coords.contains(nextCoordinate) || visited.contains(edge)) {
                break;
            }
            visited.add(edge);
            runnerI++;
        }
    }

    private void runLeft(int i, int j, Set<String> visited) {
        int runnerI = i - 1;
        while (true) {
            String edge = runnerI + "-" + j + "v";
            String nextCoordinate = runnerI + "-" + (j - 1);
            String currentCoordinate = runnerI + "-" + j;
            if (!coords.contains(currentCoordinate) || coords.contains(nextCoordinate) || visited.contains(edge)) {
                break;
            }
            visited.add(edge);
            runnerI--;
        }
        runnerI = i + 1;
        while (true) {
            String edge = runnerI + "-" + j + "v";
            String nextCoordinate = runnerI + "-" + (j - 1);
            String currentCoordinate = runnerI + "-" + j;
            if (!coords.contains(currentCoordinate) || coords.contains(nextCoordinate) || visited.contains(edge)) {
                break;
            }
            visited.add(edge);
            runnerI++;
        }
    }

    private void runTop(int i, int j, Set<String> visited) {
        int runnerJ = j - 1;
        while (true) {
            String edge = i + "-" + runnerJ + "h";
            String nextCoordinate = (i - 1) + "-" + runnerJ;
            String currentCoordinate = i + "-" + runnerJ;
            if (!coords.contains(currentCoordinate) || coords.contains(nextCoordinate) || visited.contains(edge)) {
                break;
            }
            visited.add(edge);
            runnerJ--;
        }
        runnerJ = j + 1;
        while (true) {
            String edge = i + "-" + runnerJ + "h";
            String nextCoordinate = (i - 1) + "-" + runnerJ;
            String currentCoordinate = i + "-" + runnerJ;
            if (!coords.contains(currentCoordinate) || coords.contains(nextCoordinate) || visited.contains(edge)) {
                break;
            }
            visited.add(edge);
            runnerJ++;
        }
    }

    private void runBottom(int i, int j, Set<String> visited) {
        int runnerJ = j - 1;
        while (true) {
            String edge = (i + 1) + "-" + runnerJ + "h";
            String nextCoordinate = (i + 1) + "-" + runnerJ;
            String currentCoordinate = i + "-" + runnerJ;
            if (!coords.contains(currentCoordinate) || coords.contains(nextCoordinate) || visited.contains(edge)) {
                break;
            }
            visited.add(edge);
            runnerJ--;
        }
        runnerJ = j + 1;
        while (true) {
            String edge = (i + 1) + "-" + runnerJ + "h";
            String nextCoordinate = (i + 1) + "-" + runnerJ;
            String currentCoordinate = i + "-" + runnerJ;
            if (!coords.contains(currentCoordinate) || coords.contains(nextCoordinate) || visited.contains(edge)) {
                break;
            }
            visited.add(edge);
            runnerJ++;
        }
    }

    private int checkCoordinate(int i, int j, Set<String> visited) {
        int sides = 0;
        String edge = i + "-" + j + "v";
        String nextCoordinate = i + "-" + (j - 1);

        if (!coords.contains(nextCoordinate) && !visited.contains(edge)) {
            sides++;
            runLeft(i, j, visited);
        }

        edge = i + "-" + (j + 1) + "v";
        nextCoordinate = i + "-" + (j + 1);

        if (!coords.contains(nextCoordinate) && !visited.contains(edge)) {
            sides++;
            runRight(i, j, visited);
        }

        // Check top
        edge = i + "-" + j + "h";
        nextCoordinate = (i - 1) + "-" + j;
        if (!coords.contains(nextCoordinate) && !visited.contains(edge)) {
            sides++;
            runTop(i, j, visited);
        }

        // Check bottom
        edge = (i + 1) + "-" + j + "h";
        nextCoordinate = (i + 1) + "-" + j;
        if (!coords.contains(nextCoordinate) && !visited.contains(edge)) {
            sides++;
            runBottom(i, j, visited);
        }

        return sides;
    }

    public int getPricePerSide() {
        int sides = 0;
        Set<String> visited = new HashSet<>();

        for (String point : coords) {
            int[] p = Arrays.stream(point.split("-")).mapToInt(Integer::parseInt).toArray();
            sides += checkCoordinate(p[0], p[1], visited);
        }

        return sides * coords.size();
    }

    public BigInteger getPrice() {
        return null;
    }

    public Price() {
        coords = new ArrayList<>();
    }

    public void addPrice(Price otherPrice) {
        if (otherPrice == null)
            return;

        if (otherPrice.coords.size() > 0) {
            coords.addAll(otherPrice.coords);
        }
    }
}
