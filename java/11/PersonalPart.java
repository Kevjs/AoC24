import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonalPart {

    public static void main(String[] args) {
        BigInteger total = new BigInteger("0");

        int mod = 10;

        for (int i = 0; i < 10000000; i++) {
            total = total.add(new Rock(i).blink_x_times(1000000));
            if(i % mod == 0) {
                mod *= 10;
                System.out.println("At i="+i+" total is "+total);
            }
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

    private static Set<String> s = new HashSet<>();

    public Rock(String num) {
        this.num = new BigInteger(num);
    }

    public Rock(int num) {
        this.num = new BigInteger(num + "");
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
            return new BigInteger("0");
        }
        
        if(s.contains(key)) {
            return new BigInteger("0");
        }

        // System.out.println("Found rock: " + key);
        s.add(key);

        List<Rock> postBlink = blink();

        BigInteger result = new BigInteger("1");

        if (postBlink != null) {
            BigInteger blink0 = postBlink.get(0).temp(jumpsLeft - 1);
            result = result.add(blink0);
            BigInteger blink1 = postBlink.get(1).temp(jumpsLeft - 1);
            result = result.add(blink1);
        } else {
            result = temp(jumpsLeft - 1);
        }

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
