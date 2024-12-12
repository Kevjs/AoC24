import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    public static void main(String[] args) {
        List<String> lines = readInput();
        List<Rock> rocks = new ArrayList<>();

        for(String rock : lines.get(0).split(" ")) {
            System.out.println("For rock " + rock);
            Rock r = new Rock(rock);
            List<Rock> blinkedRocks = r.blink_x_times(25);
            System.out.println("Blinked rocks: " + blinkedRocks.size());
            for(Rock br : blinkedRocks) {
                rocks.add(br);
            }
        }
        System.out.println(rocks.size());
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

    public Rock(String num) {
        this.num = new BigInteger(num);
    }

    public Rock(BigInteger num) {
        this.num = num;
    }

    public List<Rock> blink_x_times(int n) {
        List<Rock> res = new ArrayList<>();
        while(n > 0) {
            List<Rock> list = blink();
            if(list == null) {
                // System.out.println("Rock is now: " + num.toString());
                n--;
                continue;
            }

            List<Rock> blink0 = list.get(0).blink_x_times(n - 1);
            List<Rock> blink1 = list.get(1).blink_x_times(n - 1);

            for(Rock r : blink0) {
                res.add(r);
            }
            for(Rock r : blink1) {
                res.add(r);
            }
            
            break;
        }
        if(res.size() == 0) {
            res.add(this);
        }
        return res;
    }

    public List<Rock> blink() {
        if(num.compareTo(new BigInteger("0")) == 0) {
            num = new BigInteger("1");
            return null;
        }

        if(num.toString().length() % 2 == 0) {
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