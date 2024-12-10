import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part1 {
    public static void main(String[] args) throws Exception {
        Path path = Path.of("input");
        // Path path = Path.of("input_sample1");
        // Path path = Path.of("input_sample2");
        List<String> lines = Files.readAllLines(path);

        Map<Character, List<Integer[]>> towerLocations = new HashMap<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (c == '.')
                    continue;

                List<Integer[]> listAtChar = towerLocations.getOrDefault(c, new ArrayList<>());

                listAtChar.add(new Integer[] { i, j });

                towerLocations.put(c, listAtChar);
            }
        }

        Set<String> antinodes = new HashSet<>();
        System.out.println("TOTAL TOWER NAMES SIZE " + towerLocations.size());

        for (Character towerName : towerLocations.keySet()) {
            List<Integer[]> locations = towerLocations.get(towerName);
            for (int i = 0; i < locations.size(); i++) {
                Integer[] fromCoords = locations.get(i);
                for (int j = i + 1; j < locations.size(); j++) {
                    Integer[] toCoords = locations.get(j);

                    int diffI = fromCoords[0] - toCoords[0];
                    int diffJ = fromCoords[1] - toCoords[1];

                    
                    // antinodes.add(new Integer[]{fromCoords[0] + diffI, fromCoords[1] + diffJ});

                    // antinodes.add(new Integer[]{toCoords[0] + (-1 * diffI), toCoords[1] + (-1 * diffJ)});

                    int a = fromCoords[0] + diffI;
                    int b = fromCoords[1] + diffJ;

                    if (a >= 0 && b >= 0 && a < lines.size() && b < lines.get(a).length())
                        antinodes.add(a +"_"+ b);

                    a = toCoords[0] + (-1 * diffI);
                    b = toCoords[1] + (-1 * diffJ);

                    if (a >= 0 && b >= 0 && a < lines.size() && b < lines.get(a).length())
                        antinodes.add(a +"_"+ b);
                }
            }
        }

        System.out.println(lines.size());
        System.out.println(lines.get(0).length());

        // for(String antinode : antinodes) {
        //     String[] t = antinode.split("_");
        //     int z = Integer.parseInt(t[0]);
        //     int o = Integer.parseInt(t[1]);
        //     StringBuilder l = new StringBuilder(lines.get(z));
        //     l.setCharAt(o, '#');
        //     lines.set(z, l.toString());
        // }

        // for(String line : lines) {
        //     System.out.println(line);
        // }

        System.out.println("TOTAL IS: " + antinodes.size());

    }
}
