import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Part1 {
    public static void main(String[] args) {
        runExample();
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

        printOutput(finalizedString);

        System.out.println("For 2333133121414131402: " + calculateChecksum(finalizedString));
    }

    public static void printOutput(List<Integer[]> out) {
        StringBuilder sb = new StringBuilder();
        for (Integer[] a : out) {
            int a1 = a[1];
            while (a1 > 0) {
                a1--;
                sb.append(a[0] == -1 ? "." : a[0]);
            }
        }
        System.out.println(sb.toString());
    }

    public static BigInteger calculateChecksum(List<Integer[]> finalizedString) {
        BigInteger res = new BigInteger("0");

        // int currentIndex = 0;
        // for (Integer[] a : finalizedString) {
        // int a1 = a[1];
        // while (a1 > 0) {
        // a1--;
        // if (a[0] > 0) {
        // // System.out.println("a[0]="+a[0]+"|currentIndex="+currentIndex);
        // int t = (currentIndex) * a[0];
        // res = res.add(new BigInteger(t + ""));
        // }

        // currentIndex++;
        // }
        // }

        int currentIndex = 0;
        for (Integer[] currentBlock : finalizedString) {
            for (int i = 0; i < currentBlock[1]; i++) {
                int t = (currentIndex++) * currentBlock[0];
                // Forgot this if statement, so empty blocks (where [0] is -1) subtracted from
                // the total, which we simply wanted to ignore
                if (t > 0)
                    res = res.add(new BigInteger(t + ""));
            }
        }

        return res;
    }

    public static List<Integer[]> convert(String in) {
        int[] nums = Arrays.stream(in.split("")).mapToInt(Integer::parseInt).toArray();

        List<Integer[]> internal = new ArrayList<>();
        List<Integer[]> out = new ArrayList<>();

        int r = in.length() - 1, l = 0;

        if (in.length() % 2 == 0)
            r--;

        while (r >= l) {
            if (l % 2 == 0) {
                internal.add(new Integer[] { l / 2, nums[l++] });
                continue;
            }
            internal.add(new Integer[] { -1, nums[l++] });
        }

        for (int index = 0; index < internal.size(); index++) {
            Integer[] i = internal.get(index);

            if (i[0] >= 0) {
                if (i[1] > 0)
                    out.add(i);
                continue;
            }

            int space = i[1];

            spaceLoop: while (space > 0) {
                int j = internal.size() - 1;
                while (internal.get(j)[0] < 0
                        || internal.get(j)[1] > space) {
                    j -= 2;
                    if (j < index)
                        break spaceLoop;
                }
                Integer[] jElem = internal.get(j);
                out.add(new Integer[] { jElem[0], jElem[1] });

                space -= jElem[1];
                jElem[0] = -1;
            }

            if (space > 0) {
                out.add(new Integer[] { -1, space });
            }
        }

        return out;
    }
}

// 00992111777.44.333....5555.6666.....8888..
// 00992111777.44.333....5555.6666.....8888..