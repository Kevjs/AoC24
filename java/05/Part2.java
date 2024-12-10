import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part2 {
    public static void main(String[] args) throws Exception {
        System.out.println("");
        Path filePath = Path.of("part_1.input");
        // Path filePath = Path.of("part_1_sample.input");
        List<String> lines = Files.readAllLines(filePath);

        Rules rules = new Rules();
        Updates updates = new Updates();

        Addable rn = rules;

        for (String line : lines) {
            if (line.isBlank()) {
                rn = updates;
                continue;
            }
            ;
            rn.add(line);
        }

        int beforeFixing = updates.getCorrectUpdates(rules);
        int fixedAmount = updates.getIncorrectUpdatesAndFix(rules);
        int afterFixing = updates.getCorrectUpdates(rules);

        System.out.println(updates.getIncorrectUpdatesAndFix(rules));
        System.out.println("----\nbeforeFixing = " + beforeFixing);
        System.out.println("fixedAmount = " + fixedAmount);
        System.out.println("afterFixing = " + afterFixing);
        System.out.println("b:" + beforeFixing + "|f:" + fixedAmount + "|added:" + (beforeFixing + fixedAmount)
                + "|afterFixing:" + afterFixing);
    }
}

interface Addable {
    public void add(String line);
}

class Updates implements Addable {
    List<Update> updates = new ArrayList<>();

    public void add(String line) {
        updates.add(new Update(line));
    }

    public int getIncorrectUpdatesAndFix(Rules currentRules) throws Exception {
        int total = 0;

        for (Update update : updates) {
            if (!update.isCorrect(currentRules)) {
                total += update.correctUpdate(currentRules);
            }
        }

        return total;
    }

    public void what() {
    }

    public int getCorrectUpdates(Rules currentRules) {
        int total = 0;

        for (Update update : updates) {
            if (update.isCorrect(currentRules)) {
                total += update.getMiddle();
            }
        }

        return total;
    }
}

class Writer {
    FileWriter fixed;
    static Writer writer = new Writer();

    Writer() {
        try {
            fixed = new FileWriter("Fixed");
            fixed.write("INIT");
        } catch (Exception e) {
            System.out.println("ERROR");
            // TODO: handle exception
        }
    }

    public void write(String str) throws Exception {
        fixed.append(str + "aaa\n");
    }
}

class Update {
    int[] nums;

    public Update(String input) {
        nums = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public int getMiddle() {
        return nums[nums.length / 2];
    }

    public int correctUpdate(Rules rules) throws Exception {
        Map<Integer, Integer> map = new HashMap<>();

        map.put(nums[0], 0);
        // system.out.println(rules);
        // System.out.println("INITIAL: " + Arrays.toString(nums) + "\n");

        for (int i = 1; i < nums.length; i++) {
            addAtI(map, i, rules);
        }

        // system.out.println(Arrays.toString(nums) + "\n");

        return nums[nums.length / 2];
    }

    public void swap(int index, int numbersChanged) {
        // system.out.println("Swapping at " + index + ", " + numbersChanged + " numbers");
        int temp = nums[index];
        for (int i = 0; i < numbersChanged; i++) {
            nums[index + i] = nums[index + i + 1];
        }
        nums[index + numbersChanged] = temp;
    }

    public void addAtI(Map<Integer, Integer> map, int i, Rules rules) {
        // system.out.println("adding at " + i + " num " + nums[i] + " currently: "
                // + Arrays.toString(Arrays.copyOfRange(nums, 0, i)));
        AppearBefore numRule = rules.rulesBefore.get(nums[i]);
        if (numRule == null) {
            map.put(nums[i], i);
            return;
        }

        int smallestIndexSeen = nums.length;

        for (Integer num : numRule.nums) {
            smallestIndexSeen = Math.min(map.getOrDefault(num, smallestIndexSeen), smallestIndexSeen);
        }

        List<Integer> numbersThatAreMoving = new ArrayList<>();

        numbersThatAreMoving.add(nums[i]);

        int numbersChanged = 1;
        int index = i;

        outer: while (index > smallestIndexSeen) {
            // system.out.println("Checking " + numbersThatAreMoving);
            AppearBefore currentRule = rules.rulesBefore.get(nums[index - 1]);
            if (currentRule == null) {
                swap(--index, numbersChanged);
                continue;
            }

            for (Integer num : currentRule.nums) {
                if (numbersThatAreMoving.contains(num)) {
                    numbersChanged += 1;
                    index--;
                    // system.out.println("HERE?");
                    continue outer;
                }
            }

            swap(--index, numbersChanged);
            continue;
        }

        for (; index <= i; index++) {
            map.put(nums[index], index);
        }
    }

    public boolean isCorrect(Rules rules) {
        Set<Integer> currentSeen = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (rules.seeingValid(currentSeen, nums[i])) {
                currentSeen.add(nums[i]);
                continue;
            }
            return false;
        }
        return true;
    }
}

class Rules implements Addable {
    Map<Integer, AppearBefore> rulesBefore = new HashMap<>();
    Map<Integer, AppearAfter> rulesAfter = new HashMap<>();

    public void add(String line) {
        int[] nums = Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).toArray();
        if (nums.length != 2)
            return;
        this.add(nums[0], nums[1]);
    }

    public void add(int before, int after) {
        AppearBefore b = rulesBefore.get(before);
        if (b == null) {
            b = new AppearBefore(before);
        }
        b.add(after);
        rulesBefore.put(before, b);

        AppearAfter a = rulesAfter.get(after);
        if (a == null) {
            a = new AppearAfter(after);
        }
        a.add(before);
        rulesAfter.put(after, a);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Integer key : rulesBefore.keySet()) {
            builder.append(rulesBefore.get(key).toString() + "\n");
        }

        return builder.toString();
    }

    public boolean seeingValid(Set<Integer> prevSeen, int current) {
        AppearBefore numRule = rulesBefore.get(current);
        if (numRule == null)
            return true;

        for (Integer num : numRule.nums) {
            if (prevSeen.contains(num))
                return false;
        }

        return true;
    }
}

class AppearAfter {
    final int num;
    List<Integer> nums = new ArrayList<>();

    AppearAfter(int num) {
        this.num = num;
    }

    public String toString() {
        return num + " must appear before: " + nums.toString();
    }

    public void add(int num) {
        nums.add(num);
    }
}

class AppearBefore {
    final int num;
    List<Integer> nums = new ArrayList<>();

    AppearBefore(int num) {
        this.num = num;
    }

    public String toString() {
        return num + " must appear before: " + nums.toString();
    }

    public void add(int num) {
        nums.add(num);
    }
}