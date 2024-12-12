import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    public static void main(String[] args) {
        List<String> lines = readInput();

        BigInteger total = new BigInteger("0");

        for (String rock : lines.get(0).split(" ")) {
            // System.out.print("For rock " + rock);
            Rock r = new Rock(rock);
            BigInteger rOut = r.blink_x_times(75);
            // System.out.println(" - " + rOut);

            total = total.add(rOut);
        }
        System.out.println("TOTAL: " + total);
    }

    public static List<String> readInput() {
        Path p = Path.of("input1");
        try {
            return Files.readAllLines(p);
        } catch (Exception e) {
            throw new RuntimeException("");
        }
    }
}

class Rock {
    BigInteger num;

    private static Map<String, Map<Integer, BigInteger>> map = new HashMap<>();

    public Rock(String num) {
        this.num = new BigInteger(num);
    }

    public Rock(BigInteger num) {
        this.num = num;
    }

    public Rock dup() {
        return new Rock(num);
    }

    public BigInteger blink_x_times(int max) {
        return temp(max);
    }

    public BigInteger temp(int jumpsLeft) {
        String key = num.toString();

        if (jumpsLeft == 0) {
            return new BigInteger("1");
        }

        Map<Integer, BigInteger> mapForNumber = map.get(key);

        if (mapForNumber != null) {
            BigInteger cachedResult = mapForNumber.get(jumpsLeft);
            if (cachedResult != null) {
                return cachedResult;
            }
        } else {
            // System.out.println("New rock: " + num.toString());
            mapForNumber = new HashMap<>();
        }

        List<Rock> postBlink = blink();

        BigInteger result;
        if (postBlink != null) {
            BigInteger blink0 = postBlink.get(0).temp(jumpsLeft - 1);
            BigInteger blink1 = postBlink.get(1).temp(jumpsLeft - 1);
            result = blink0.add(blink1);
        } else {
            result = temp(jumpsLeft - 1);
        }

        mapForNumber.put(jumpsLeft, result);
        map.put(key, mapForNumber);
        return result;
    }

    public List<Rock> blink() {
        if (num.compareTo(new BigInteger("0")) == 0) {
            num = new BigInteger("1");
            return null;
        }

        if (num.toString().length() % 2 == 0) {
            String t = num + "";
            String t0 = t.substring(0, t.length() / 2);
            String t1 = t.substring(t.length() / 2);

            List<Rock> a = new ArrayList<Rock>();
            a.add(new Rock(t0));
            a.add(new Rock(t1));
            return a;
        }

        num = num.multiply(new BigInteger("2024"));
        return null;
    }
}
