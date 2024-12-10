import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Part1 {
    public static void main(String[] args) {
        // runExample();
        runPart();
    }

    public static void runPart() {
        Path path = Path.of("input");
        String input;
        try {
            input = Files.readAllLines(path).get(0);
        } catch (Exception e) {
            System.out.println("Error reading file\n" + e.toString());
            return;
        }

        List<Integer[]> finalizedString = convert(input);

        // printOutput(finalizedString);

        System.out.println("For the actual input: " + calculateChecksum(finalizedString));
    }

    public static void runExample() {
        String input = "2333133121414131402";

        List<Integer[]> finalizedString = convert(input);

        System.out.println("For 2333133121414131402: " + calculateChecksum(finalizedString));
    }

    public static void printOutput(List<Integer[]> out) {
        StringBuilder sb = new StringBuilder();
        for (Integer[] a : out) {
            // System.out.println(a[0]+"-"+a[1]);
            int a1 = a[1];
            while (a1 > 0) {
                a1--;
                sb.append("|"+a[0]);
            }
        }
        System.out.println(sb.toString());
    }

    public static BigInteger calculateChecksum(List<Integer[]> finalizedString) {
        BigInteger res = new BigInteger("0");

        int currentIndex = 0;
        for (Integer[] currentBlock : finalizedString) {
            for (int i = 0; i < currentBlock[1]; i++) {
                int t = (currentIndex++) * currentBlock[0];
                res = res.add(new BigInteger(t + ""));
            }
        }

        return res;
    }

    public static List<Integer[]> convert(String in) {
        int[] nums = Arrays.stream(in.split("")).mapToInt(Integer::parseInt).toArray();

        List<Integer[]> out = new ArrayList<>();

        int r = in.length() - 1, l = 0, endID = r / 2;

        if (in.length() % 2 == 0)
            r--;

        while (r >= l) {
            if (l % 2 == 0) {
                out.add(new Integer[] { l / 2, nums[l++] });
                continue;
            }

            int space = nums[l++];

            while (true) {
                int wantToUse = nums[r];

                out.add(new Integer[] { endID, Math.min(wantToUse, space) });

                nums[r] -= space;

                space -= wantToUse;

                if (space >= 0) {
                    r -= 2;
                    endID--;
                }

                if (space <= 0) {
                    break;
                }
            }
        }

        return out;
    }
}
