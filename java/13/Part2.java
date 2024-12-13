import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Part2 {
    public static void main(String[] args) {
        List<String> lines = readInput(args[0]);
        BigInteger toAMovesl = new BigInteger("0");

        for (int i = 0; i < lines.size(); i += 4) {
            Machine m = new Machine(lines, i);
            BigInteger price = m.calculateMinPriceForPrize();
            if (price != null) {
                toAMovesl = toAMovesl.add(new BigInteger("" + price));
            }
        }

        System.out.println("TOAMovesL: " + toAMovesl);
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
    BigInteger[] moveOnA;
    BigInteger[] moveOnB;
    BigInteger[] prizeLocation;

    public BigInteger priceA = new BigInteger("3");
    public BigInteger priceB = new BigInteger("1");

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

    /**
     * Follow this redditor's analysis
     * 
     * // ax * ta + bx * tb = X
     * // ay * ta + by * tb = Y
     * // thus:
     * // ta = (X - bx * tb) / ax, where ax !== 0
     * // ta = (Y - by * tb) / ay, where ay !== 0
     * // thus:
     * // (X - bx * tb) / ax = (Y - by * tb) / ay, where ax !== 0, ay !== 0
     * // thus:
     * // ay * (X - bx * tb) = ax * (Y - by * tb)
     * // thus:
     * // ay * X - ay * bx * tb = ax * Y - ax * by * tb
     * // ay * X - ax * Y = ay * bx * tb - ax * by * tb
     * // thus:
     * // tb = (ay * X - ax * Y) / (ay * bx - ax * by), 
            where ay !== 0, bx !== 0, ax !== 0, by != 0
     * 
     * @return
     */
    public BigInteger calculateMinPriceForPrize() {
        BigInteger bmoves = moveOnA[1].multiply(prizeLocation[0]).subtract(moveOnA[0].multiply(prizeLocation[1]))
                .divide(
                        moveOnA[1].multiply(moveOnB[0]).subtract(moveOnA[0].multiply(moveOnB[1])));

        BigInteger totalBX = bmoves.multiply(moveOnB[0]);
        BigInteger totalBY = bmoves.multiply(moveOnB[1]);

        BigInteger[] moveByAX = prizeLocation[0].subtract(totalBX).divideAndRemainder(moveOnA[0]);
        BigInteger[] moveByAY = prizeLocation[1].subtract(totalBY).divideAndRemainder(moveOnA[1]);

        if (moveByAX[1].compareTo(BigInteger.ZERO) != 0 || moveByAY[1].compareTo(BigInteger.ZERO) != 0)
            return new BigInteger("0");

        if (moveByAX[0].compareTo(moveByAY[0]) != 0)
            return new BigInteger("0");

        return moveByAX[0].multiply(priceA).add(bmoves.multiply(priceB));
    }

    private void setPrize(String input) {
        prizeLocation = new BigInteger[2];

        String[] split = input.split(" ");

        BigInteger displacement = new BigInteger("10000000000000");

        prizeLocation[0] = displacement.add(new BigInteger(split[1].substring(2, split[1].length() - 1)));
        prizeLocation[1] = displacement.add(new BigInteger(split[2].substring(2)));
    }

    private void setMovement(String input, boolean A) {
        BigInteger[] movement = new BigInteger[2];

        String[] split = input.split(" ");

        movement[0] = new BigInteger(split[2].substring(2, split[2].length() - 1));
        movement[1] = new BigInteger(split[3].substring(2));

        if (A) {
            moveOnA = movement;
        } else {
            moveOnB = movement;
        }
    }
}
