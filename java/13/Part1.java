import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Part1 {
    public static void main(String[] args) {
        List<String> lines = readInput(args[0]);
        BigInteger total = new BigInteger("0");

        for(int i = 0; i < lines.size(); i+=4) {
            Machine m = new Machine(lines, i);
            Integer price = m.calculateMinPriceForPrize();
            if(price >= 0) {
                total = total.add(new BigInteger("" + price));
            // } else {
            //     m.print();
            }
        }

        System.out.println("TOTAL: " + total);
    }

    public static int getMinPriceForPrize() {

        return 0;
    }

    private static List<String> readInput(String path) {
        try {
            Path p = Path.of(path);
            return Files.readAllLines(p);
        } catch (Exception e) {
            throw new RuntimeException("Error reading file");
        }
    }
}

class Machine {
    int[] moveOnA;
    int[] moveOnB;
    int[] prizeLocation;

    public int priceA = 3;
    public int priceB = 1;

    public int maxAPresses = 100;
    public int maxBPresses = 100;

    public void print() {
        System.out.println("Move on A: " + moveOnA[0] + "-" + moveOnA[1]);
        System.out.println("Move on B: " + moveOnB[0] + "-" + moveOnB[1]);
        System.out.println("prizeLocation: " + prizeLocation[0] + "-" + prizeLocation[1]);
    }

    public Machine(List<String> input) {
        setMovement(input.get(0), true);
        setMovement(input.get(1), false);
        setPrize(input.get(2));
    }

    public Machine(List<String> input, int i) {
        setMovement(input.get(i + 0), true);
        setMovement(input.get(i + 1), false);
        setPrize(input.get(i + 2));
    }

    public void setMaxPresses(int max) {
        maxAPresses = max;
        maxBPresses = max;
    }

    public Integer calculateMinPriceForPrize() {
        return calculateMinPriceForPrize(maxAPresses, maxBPresses, new int[]{0, 0});
    }

    private Integer calculateMinPriceForPrize(int AsLeft, int BsLeft, int[] currentLocation) {
        int leftX = (prizeLocation[0] - currentLocation[0]);
        int leftY = (prizeLocation[1] - currentLocation[1]);

        if(leftX == 0 && leftY == 0) return 0;

        if(
            leftX % moveOnB[0] == 0
            && leftY % moveOnB[1] == 0
            && leftX / moveOnB[0] == leftY / moveOnB[1]
            && leftX / moveOnB[0] <= BsLeft
        ) {
            return leftX / moveOnB[0];
        }

        if(AsLeft <= 0) return -1;

        currentLocation[0] += moveOnA[0];
        currentLocation[1] += moveOnA[1];
        
        Integer cost = calculateMinPriceForPrize(AsLeft - 1, BsLeft, currentLocation);

        if(cost == -1) return cost;

        return cost + 3;
    }

    private void setPrize(String input) {
        prizeLocation = new int[2];

        String[] split = input.split(" ");

        prizeLocation[0] = Integer.parseInt(split[1].substring(2, split[1].length() - 1));
        prizeLocation[1] = Integer.parseInt(split[2].substring(2));
    }

    private void setMovement(String input, boolean A) {
        int[] movement = new int[2];

        String[] split = input.split(" ");

        movement[0] = Integer.parseInt(split[2].substring(2, split[2].length() - 1));
        movement[1] = Integer.parseInt(split[3].substring(2));

        if (A) {
            moveOnA = movement;
        } else {
            moveOnB = movement;
        }
    }
}
