import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaySix {
    public static void main(String[] args) throws Exception {
        System.out.println("");
        Path filePath = Path.of("day6input");
        // Path filePath = Path.of("day6input_sample");
        List<String> lines = Files.readAllLines(filePath);

        // partOne(lines);
        partTwo(lines);
    }

    public static void partOne(List<String> lines) {
        int i = 0, x;
        for (; i < lines.size(); i++) {
            if (lines.get(i).contains("^"))
                break;
        }
        x = lines.get(i).indexOf("^");

        StringBuilder sb = new StringBuilder(lines.get(i));
        sb.setCharAt(x, 'X');
        lines.set(i, sb.toString());

        int[] directionMoving = new int[] { -1, 0 };
        int total = 1;

        while (i < lines.size() && i >= 0 && x < lines.get(i).length() && x >= 0) {
            int[] newCoords = new int[] { i + directionMoving[0], x + directionMoving[1] };
            if (!(newCoords[0] < lines.size() && newCoords[0] >= 0 && newCoords[1] < lines.get(newCoords[0]).length()
                    && newCoords[1] >= 0)) {
                break;
            }

            char destination = lines.get(newCoords[0]).charAt(newCoords[1]);

            if (destination == '#') {
                if (directionMoving[0] == -1) {
                    directionMoving[0] = 0;
                    directionMoving[1] = 1;
                } else if (directionMoving[1] == -1) {
                    directionMoving[0] = -1;
                    directionMoving[1] = 0;
                } else if (directionMoving[0] == 1) {
                    directionMoving[0] = 0;
                    directionMoving[1] = -1;
                } else {
                    directionMoving[0] = 1;
                    directionMoving[1] = 0;
                }
                continue;
            }
            if (destination != 'X')
                total++;
            sb = new StringBuilder(lines.get(newCoords[0]));
            sb.setCharAt(newCoords[1], 'X');
            lines.set(newCoords[0], sb.toString());
            i = newCoords[0];
            x = newCoords[1];
        }
        System.out.println("-------------------\n");
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println(total);
    }

    public static boolean updateDirectionHistory(Map<String, String> map, String key, int[] direction) {
        String prev = map.getOrDefault(key, "");
        char dir;
        if (direction[0] == 1) {
            dir = 'u';
        } else if (direction[0] == -1) {
            dir = 'd';
        } else if (direction[1] == 1) {
            dir = 'r';
        } else {
            dir = 'l';
        }
        if (prev.indexOf(dir) != -1)
            return true;

        prev += dir;
        map.put(key, prev);
        return false;
    }

    public static boolean formsLoop(Map<String, String> map, int[] directionMoving, int i, int x,
            List<String> lines) {

        while (true) {
            if (updateDirectionHistory(map, i + "-" + x, directionMoving)) {
                System.out.println("RESULT");

                for (int j = 0; j < lines.size(); j++) {
                    String line = lines.get(j);
                    StringBuilder lineOfMap = new StringBuilder();
                    for (int io = 0; io < line.length(); io++) {
                        String c = map.getOrDefault(j + "-" + io, "");
                        int x2 = 0;
                        if (c.indexOf("u") != -1)
                            x2 += 1;
                        if (c.indexOf("d") != -1)
                            x2 += 2;
                        if (c.indexOf("l") != -1)
                            x2 += 4;
                        if (c.indexOf("r") != -1)
                            x2 += 8;
                        lineOfMap.append((char)('a' + x2));
                    }
                    System.out.println(lineOfMap);
                }
                return true;
            }

            int[] newCoords = new int[] { i + directionMoving[0], x + directionMoving[1] };

            if (!(newCoords[0] < lines.size() && newCoords[0] >= 0 && newCoords[1] < lines.get(newCoords[0]).length()
                    && newCoords[1] >= 0)) {
                return false;
            }

            char destination = lines.get(newCoords[0]).charAt(newCoords[1]);

            if (destination == '#') {
                if (directionMoving[0] == -1) {
                    directionMoving[0] = 0;
                    directionMoving[1] = 1;
                } else if (directionMoving[1] == -1) {
                    directionMoving[0] = -1;
                    directionMoving[1] = 0;
                } else if (directionMoving[0] == 1) {
                    directionMoving[0] = 0;
                    directionMoving[1] = -1;
                } else {
                    directionMoving[0] = 1;
                    directionMoving[1] = 0;
                }
                continue;
            }

            StringBuilder sb = new StringBuilder(lines.get(newCoords[0]));
            sb.setCharAt(newCoords[1], 'X');
            lines.set(newCoords[0], sb.toString());

            i = newCoords[0];
            x = newCoords[1];
        }
    }

    public static void partTwo(List<String> lines) throws Exception {
        int i = 0, x;
        for (; i < lines.size(); i++) {
            if (lines.get(i).contains("^"))
                break;
        }
        x = lines.get(i).indexOf("^");

        StringBuilder sb = new StringBuilder(lines.get(i));
        sb.setCharAt(x, 'X');
        lines.set(i, sb.toString());

        int[] directionMoving = new int[] { -1, 0 };
        int total = 0;

        while (true) {

            int[] newCoords = new int[] { i + directionMoving[0], x + directionMoving[1] };

            if (!(newCoords[0] < lines.size() && newCoords[0] >= 0 && newCoords[1] < lines.get(newCoords[0]).length()
                    && newCoords[1] >= 0)) {
                break;
            }

            char destination = lines.get(newCoords[0]).charAt(newCoords[1]);

            if (destination == '#') {
                if (directionMoving[0] == -1) {
                    directionMoving[0] = 0;
                    directionMoving[1] = 1;
                } else if (directionMoving[1] == -1) {
                    directionMoving[0] = -1;
                    directionMoving[1] = 0;
                } else if (directionMoving[0] == 1) {
                    directionMoving[0] = 0;
                    directionMoving[1] = -1;
                } else {
                    directionMoving[0] = 1;
                    directionMoving[1] = 0;
                }
                continue;
            }

            sb = new StringBuilder(lines.get(newCoords[0]));
            sb.setCharAt(newCoords[1], 'X');
            lines.set(newCoords[0], sb.toString());

            i = newCoords[0];
            x = newCoords[1];

            newCoords = new int[] { i + directionMoving[0], x + directionMoving[1] };

            if (!(newCoords[0] < lines.size() && newCoords[0] >= 0 && newCoords[1] < lines.get(newCoords[0]).length()
                    && newCoords[1] >= 0)) {
                break;
            }

            destination = lines.get(newCoords[0]).charAt(newCoords[1]);

            if (destination != '#' && destination != 'X') {
                sb = new StringBuilder(lines.get(newCoords[0]));
                sb.setCharAt(newCoords[1], '#');
                lines.set(newCoords[0], sb.toString());

                if (formsLoop(new HashMap<>(), new int[] { directionMoving[0], directionMoving[1] }, i,
                        x, new ArrayList<>(lines))) {
                    total++;
                    sb = new StringBuilder(lines.get(newCoords[0]));
                    sb.setCharAt(newCoords[1], 'O');
                    lines.set(newCoords[0], sb.toString());
                    for (String line : lines) {
                        System.out.println(line);
                    }
                    System.out.println("===================");
                    System.out.println("===================");
                    System.out.println("===================");
                }

                sb = new StringBuilder(lines.get(newCoords[0]));
                sb.setCharAt(newCoords[1], '.');
                lines.set(newCoords[0], sb.toString());
            }
        }

        System.out.println("-------------------\n");
        System.out.println(total);
    }
}
