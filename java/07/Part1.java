import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws Exception {
        Path path = Path.of("./7/input");
        // Path path = Path.of("./7/input_sample");
        List<String> lines = Files.readAllLines(path);

        BigInteger total = new BigInteger("0");

        for (String line : lines) {
            BigInteger[] lineNums = convertToIntArray(line);
            // System.out.println("CHECKING " + Arrays.toString(lineNums));
            if (canBeMadeTrue(lineNums)) {
                // System.out.println("ADDING");
                total = total.add(lineNums[0]);
            }
        }

        System.out.println("Total is: " + total);
    }

    public static BigInteger[] convertToIntArray(String line) {
        String[] parts = line.split(" ");

        parts[0] = parts[0].substring(0, parts[0].length() - 1);

        BigInteger[] nums = new BigInteger[parts.length];

        int i = 0;
        for (String p : parts) {
            nums[i++] = new BigInteger(p);
        }

        return nums;
    }

    public static boolean canBeMadeTrue(BigInteger[] nums) {
        if (nums.length == 1)
            return false;

        return canBeMadeTrue(nums, nums[1], 2);
    }

    public static boolean canBeMadeTrue(BigInteger[] nums, BigInteger total, int i) {
        if (i >= nums.length) {
            return total.compareTo(nums[0]) == 0;
        }
        if (total.compareTo(nums[0]) > 0)
            return false;

        BigInteger concatenated = new BigInteger(total.toString() + nums[i].toString());
        if (canBeMadeTrue(nums, concatenated, i + 1)) {
            return true;
        }

        return canBeMadeTrue(nums, total.add(nums[i]), i + 1) || canBeMadeTrue(nums, total.multiply(nums[i]), i + 1);

    }
}
