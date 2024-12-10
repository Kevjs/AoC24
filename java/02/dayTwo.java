import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

class DayTwo {
    public static void main(String[] args) {
        try {
            File myFile = new File("raw_input2");
            FileWriter safe = new FileWriter("SAFE");
            FileWriter unsafe = new FileWriter("UNSAFE");

            // safe.write("");
            // unsafe.write("");

            Scanner myReader = new Scanner(myFile);

            int total = 0;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                int[] nums = Arrays.stream(data.split(" ")).mapToInt(Integer::parseInt).toArray();

                if(nums.length <= 2) {
                    total++;
                    continue;
                }

                int[] numsSans0 = Arrays.copyOfRange(nums, 1, nums.length);
                int[] numsSans1 = new int[nums.length - 1];
                int[] numsSans2 = new int[nums.length - 1];
                System.arraycopy(nums, 0, numsSans1, 0, 1);
                System.arraycopy(nums, 2, numsSans1, 1, nums.length - 2);
                
                System.arraycopy(nums, 0, numsSans2, 0, 2);
                System.arraycopy(nums, 3, numsSans2, 2, nums.length - 3);


                boolean answer = isSafe(numsSans1) || isSafe(numsSans0) || isSafe(numsSans2);

                answer = answer || isSafeRemovingMaxOne(nums);

                if (answer) {
                    // safe.append(Arrays.toString(nums) + "\n");
                    total++;
                    continue;
                } else {
                    // unsafe.append(Arrays.toString(nums) + "\n");
                }
            }

            System.out.println("Total is: " + total);

            unsafe.close();
            safe.close();
            myReader.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean isSafeRemovingMaxOne(int[] nums) {
        int ascending = nums[1] - nums[0] > 0 ? 1 : -1;
        boolean dampnerUsed = false;

        for (int i = 1, prevI = 0; i < nums.length; i++) {
            int diff = (nums[i] - nums[prevI]) * ascending;
            if (diff < 1 || diff > 3) {
                if (dampnerUsed) {
                    return false;
                }
                dampnerUsed = true;
                continue;
            }
            prevI = i;
        }

        return true;
    }

    public static boolean isSafe(int[] nums) {
        int ascending = nums[1] - nums[0] > 0 ? 1 : -1;

        for (int i = 1; i < nums.length; i++) {
            int diff = (nums[i] - nums[i - 1]) * ascending;
            if (diff < 1 || diff > 3) {
                return false;
            }
        }

        return true;
    }
}