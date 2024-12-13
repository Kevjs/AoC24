import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part1 {
    public static void main(String[] args) {
        List<String> lines = readInput();

        BigInteger total = new BigInteger("0");

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (lines.get(i).charAt(j) != '.') {

                    System.out.println("Starting at " + i + "-" + j);
                    for (String lineee : lines) {
                        System.out.println(lineee);
                    }
                    Price p = getPrice(lines, lines.get(i).charAt(j), i, j);
                    
                    p.print();

                    p.clear(lines);
                    System.out.println("-------------");

                    total = total.add(p.getPrice());
                }
            }
        }

        System.out.println("TOTAL: " + total);
    }

    public static Price getPrice(List<String> lines, char c, int i, int j) {
        if (i < 0 || j < 0 || i >= lines.size() || j >= lines.get(i).length()) {
            return null;
        }

        if (lines.get(i).charAt(j) == '#') {
            Price alreadySeen = new Price();
            alreadySeen.area = 0;
            alreadySeen.perimeter = 0;
            return alreadySeen;
        }

        if (lines.get(i).charAt(j) != c) {
            return null;
        }

        StringBuilder sb = new StringBuilder(lines.get(i));
        sb.setCharAt(j, '#');
        lines.set(i, sb.toString());

        Price thisPrice = new Price();

        thisPrice.coords.add(new Integer[]{i, j});

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
            throw new RuntimeException("");
        }
    }
}

class Price {
    int area;
    int perimeter;
    List<Integer[]> coords;

    public void clear(List<String> lines) {
        for(Integer[] c : coords) {
            StringBuilder sb = new StringBuilder(lines.get(c[0]));
            sb.setCharAt(c[1], '.');
            lines.set(c[0], sb.toString());
        }
    }

    public void print() {
        System.out.print("[");
        for (Integer[] c : coords) {
            System.out.print(Arrays.toString(c));
        }
        System.out.println("]");
        System.out.println("Area: " + area + " - Perimeter: " + perimeter + " - Price " + getPrice());
    }

    public BigInteger getPrice() {
        return new BigInteger((area * perimeter) + "");
    }

    public Price() {
        coords = new ArrayList<>();
        area = 1;
        perimeter = 4;
    }

    public void addPrice(Price otherPrice) {
        if (otherPrice == null)
            return;
        perimeter += otherPrice.perimeter - 1;
        area += otherPrice.area;
        if(otherPrice.coords.size() > 0) {
            for(Integer[] c : otherPrice.coords) {
                coords.add(c);
            }
        }
    }
}
